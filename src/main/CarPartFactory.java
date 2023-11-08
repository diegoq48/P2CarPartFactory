package main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.SingleSelectionModel;

import data_structures.ArrayList;
import data_structures.BasicHashFunction;
import data_structures.HashTableSC;
import data_structures.SinglyLinkedList;
import interfaces.*;
public class CarPartFactory {
    private List<PartMachine> machines = new data_structures.ArrayList<>();
    private List<Order> orders =  new data_structures.ArrayList<>();
    private Map<Integer, CarPart> partCatalog = new HashTableSC<>(10, new BasicHashFunction());
    private Map<Integer, List<CarPart>> inventory = new HashTableSC<>(10, new BasicHashFunction());
    private Map<Integer, Integer> defectives = new HashTableSC<>(10, new BasicHashFunction());
    private Stack<CarPart> productionBin = new data_structures.LinkedStack<>();

        
    public CarPartFactory(String orderPath, String partsPath) throws IOException {
        //read the orders and setup the orders list
        
        this.setupOrders(orderPath);
        //read the machines and setup the machines list
        this.setupMachines(partsPath);
        //setup the catalog
        //store the parts in the inventory
        this.setupCatalog();
        this.setupInventory();

                
    }
    public List<PartMachine> getMachines() {
        return machines;
    }
    public void setMachines(List<PartMachine> machines) {
        this.machines = machines;
    }
    public Stack<CarPart> getProductionBin() {
        return productionBin;
    }
    public void setProductionBin(Stack<CarPart> production) {
       this.productionBin = production;
    }
    public Map<Integer, CarPart> getPartCatalog() {
        return partCatalog;
    }
    public void setPartCatalog(Map<Integer, CarPart> partCatalog) {
        this.partCatalog = partCatalog;
    }
    public Map<Integer, List<CarPart>> getInventory() {
       return inventory;
    }
    public void setInventory(Map<Integer, List<CarPart>> inventory) {
        this.inventory = inventory;
    }
    public List<Order> getOrders() {
        return orders;
    }
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    public Map<Integer, Integer> getDefectives() {
        return defectives;
    }
    public void setDefectives(Map<Integer, Integer> defectives) {
        this.defectives = defectives;
    }

    public void setupOrders(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            String line = br.readLine();
            line = br.readLine();
            while(line != null) {
                String[] values = line.split(",");
                int id = Integer.parseInt(values[0]);
                String customerName = values[1];
                Map<Integer, Integer> requestedParts = new data_structures.HashTableSC<Integer, Integer>(10, new BasicHashFunction());
                String[] SplitedValuesFromOriginalStringOnDash = values[2].split("-");
                for(int i = 0; i < SplitedValuesFromOriginalStringOnDash.length; i++) {
                    String[] SplitedValuesOnSpaceToBuildTheMap = SplitedValuesFromOriginalStringOnDash[i].replaceAll("[()]", "").split(" ");
                    // add the values to the map if it already exists then add the value to the existing key
                    if(requestedParts.containsKey(Integer.parseInt(SplitedValuesOnSpaceToBuildTheMap[0]))) {
                        requestedParts.put(Integer.parseInt(SplitedValuesOnSpaceToBuildTheMap[0]), requestedParts.get(Integer.parseInt(SplitedValuesOnSpaceToBuildTheMap[0])) + Integer.parseInt(SplitedValuesOnSpaceToBuildTheMap[1]));
                    } else {
                        requestedParts.put(Integer.parseInt(SplitedValuesOnSpaceToBuildTheMap[0]), Integer.parseInt(SplitedValuesOnSpaceToBuildTheMap[1]));
                    }
                    
                }
                this.getOrders().add(new Order(id, customerName, requestedParts, false));
                line = br.readLine();
            }
        } catch (IOException e) {
            // print unable to read file
            System.out.println("Unable to read file");
            e.printStackTrace();
        }
    }
    public void setupMachines(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            String line = br.readLine();
            line = br.readLine();
            while(line != null){
                String[] values = line.split(",");
                CarPart new_car_part_created_by_initial_setup_read_on_csv = new CarPart(Integer.parseInt(values[0]), values[1], Double.parseDouble(values[2]), false);
                PartMachine new_machine_created_by_initial_setup_read_on_csv = new PartMachine(Integer.parseInt(values[0]), new_car_part_created_by_initial_setup_read_on_csv, Integer.parseInt(values[4]), Double.parseDouble(values[3]), Integer.parseInt(values[5]));
                this.getMachines().add(new_machine_created_by_initial_setup_read_on_csv);
                line = br.readLine();

            }
        } catch (IOException e) {
            System.out.println(path + " not found");
            e.printStackTrace();
        }
    }
    public void setupCatalog() throws IOException  {
        //create a map of all the parts in the catalog 
        for(PartMachine machine : this.getMachines()) {
            this.getPartCatalog().put(machine.getPart().getId(), machine.getPart());
        }
    }
    public void setupInventory() {
        //create a map of all the parts in the inventory
        for(PartMachine machine : this.getMachines()) {
            this.getInventory().put(machine.getPart().getId(), new data_structures.ArrayList<>());
            this.getDefectives().put(machine.getPart().getId(), 0);
        }
    }
    public void storeInInventory() {
        while(!this.getProductionBin().isEmpty()) {
            CarPart part = this.getProductionBin().pop();
            if(part.isDefective()) {
                this.getDefectives().put(part.getId(), this.getDefectives().get(part.getId()) + 1);
            } else {
                this.getInventory().get(part.getId()).add(part);
            }
        }
    }
    public void runFactory(int days, int minutes) {

        for(int i = 0; i < days; i++) {
            for(int j = 0; j < minutes; j++) {
                for(PartMachine machine : this.getMachines()) {
                    CarPart part = machine.produceCarPart();
                    if(part != null) {
                        //put the part in inventory the key is the id and the value is the count of the part if the part is not in the inventory then add it to the inventory with a count of 1
                        this.getProductionBin().push(part);
                    }
                }
            }
            for(PartMachine machine : this.getMachines()) {
                while(!machine.getConveyorBelt().isEmpty()) {
                    CarPart part = machine.getConveyorBelt().dequeue();
                    if(part != null) {
                        this.getProductionBin().push(part);
                    }
                }
            }
            this.storeInInventory();
    }
    this.processOrders();
}



   
public void processOrders() {
    for(Order order : this.getOrders()) {
        List<Integer> current_order_keys = order.getRequestedParts().getKeys();
        boolean order_complete = true;
        //System.out.println(current_order_keys);
        for(int i = 0; i < current_order_keys.size(); i++) {
            int current_key = current_order_keys.get(i);
            int current_value = order.getRequestedParts().get(current_key);
            /* this line is causing all of the trouble needs to be fixed 

             */
            if(this.getInventory().get(current_key).size() < current_value) {
                order_complete = false;
                break;
            }
        }
        if(order_complete) {

            for(int i = 0; i < current_order_keys.size(); i++) {
                int current_key = current_order_keys.get(i);
                int current_value = order.getRequestedParts().get(current_key);
                for(int j = 0; j < current_value; j++) {
                    this.getInventory().get(current_key).remove(0);
                }
            }
            order.setFulfilled(true);
        }

    }

}

   

}
