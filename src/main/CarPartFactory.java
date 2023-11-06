package main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import data_structures.HashTableSC;
import interfaces.*;
public class CarPartFactory {
    private List<PartMachine> machines = new data_structures.ArrayList<>();
    private List<Order> orders =  new data_structures.ArrayList<>();
    private Map<Integer, CarPart> partCatalog = new HashTableSC<>(10, new interfaces.HashFunction<Integer>() {
        @Override
        public int hashCode(Integer key) {
            return key;
        }
    });
    private Map<Integer, List<CarPart>> inventory = new HashTableSC<>(10, new interfaces.HashFunction<Integer>() {
        @Override
        public int hashCode(Integer key) {
            return key;
        }
    });
    private Map<Integer, Integer> defectives = new HashTableSC<>(10, new interfaces.HashFunction<Integer>() {
        @Override
        public int hashCode(Integer key) {
            return key;
        }
    });
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
                Map<Integer, Integer> requestedParts = new data_structures.HashTableSC<Integer, Integer>(10, new interfaces.HashFunction<Integer>() {
                    @Override
                    public int hashCode(Integer key) {
                        return key;
                    }
                });
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
                    if(machine.getTimer().isEmpty()) {
                        machine.getTimer().enqueue(machine.getPeriod());
                    }
                    int time = machine.getTimer().dequeue();
                    if(time == 1) {
                        machine.getTimer().enqueue(machine.getPeriod());
                        if(!machine.getConveyorBelt().isEmpty()) {
                            CarPart part = machine.getConveyorBelt().dequeue();
                            if(part != null) {
                                this.getProductionBin().push(part);
                            }
                        }
                    } else {
                        machine.getTimer().enqueue(time - 1);
                    }
                }
            }
            this.processOrders();
            this.storeInInventory();
    }
}

   
    public void processOrders() {
        
    }
    /**
     * Generates a report indicating how many parts were produced per machine,
     * how many of those were defective and are still in inventory. Additionally, 
     * it also shows how many orders were successfully fulfilled. 
     */
    public void generateReport() {
        String report = "\t\t\tREPORT\n\n";
        report += "Parts Produced per Machine\n";
        for (PartMachine machine : this.getMachines()) {
            report += machine + "\t(" + 
            this.getDefectives().get(machine.getPart().getId()) +" defective)\t(" + 
            this.getInventory().get(machine.getPart().getId()).size() + " in inventory)\n";
        }
       
        report += "\nORDERS\n\n";
        for (Order transaction : this.getOrders()) {
            report += transaction + "\n";
        }
        System.out.println(report);
    }

   

}
