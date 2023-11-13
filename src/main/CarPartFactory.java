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
/**
 * The CarPartFactory class represents a factory for producing and managing car parts.
 * It includes functionality to setup orders, machines, catalogs, and inventory,
 * as well as running the factory, processing orders, and generating reports.
 */

public class CarPartFactory {
    /*Fields 
     * machines: A list of machines in the factory
     * orders: A list of orders to be processed
     * partCatalog: A map of all the parts in the catalog
     * inventory: A map of all the parts in the inventory
     * defectives: A map of all the defective parts
     * productionBin: A stack of all the parts produced
     */
    private List<PartMachine> machines = new data_structures.ArrayList<>();
    private List<Order> orders =  new data_structures.ArrayList<>();
    private Map<Integer, CarPart> partCatalog = new HashTableSC<>(10, new BasicHashFunction());
    private Map<Integer, List<CarPart>> inventory = new HashTableSC<>(10, new BasicHashFunction());
    private Map<Integer, Integer> defectives = new HashTableSC<>(10, new BasicHashFunction());
    private Stack<CarPart> productionBin = new data_structures.LinkedStack<>();

     /**
     * Constructs a CarPartFactory with orders and parts setup based on provided file paths.
     *
     * @param orderPath The file path for orders setup.
     * @param partsPath The file path for machines and parts setup.
     * @throws IOException If an I/O error occurs while reading the files.
     */
    public CarPartFactory(String orderPath, String partsPath) throws IOException {
        
        this.setupOrders(orderPath);
        this.setupMachines(partsPath);
        this.setupCatalog();
        this.setupInventory();

                
    }
    /*
     * Getters and setters
     */
    public List<PartMachine> getMachines() {
        return machines;
    }
    public void setMachines(List<PartMachine> machines) {
        if(machines == null || machines.isEmpty()) throw new IllegalArgumentException("Machines cannot be null");
        this.machines = machines;
    }
    public Stack<CarPart> getProductionBin() {
        return productionBin;
    }
    public void setProductionBin(Stack<CarPart> production) {
        if(production == null || production.isEmpty()) throw new IllegalArgumentException("Production cannot be null");

       this.productionBin = production;
    }
    public Map<Integer, CarPart> getPartCatalog() {
        return partCatalog;
    }
    public void setPartCatalog(Map<Integer, CarPart> partCatalog) {
        if(partCatalog == null || partCatalog.isEmpty()) throw new IllegalArgumentException("Part catalog cannot be null");
        this.partCatalog = partCatalog;
    }
    public Map<Integer, List<CarPart>> getInventory() {
       return inventory;
    }
    public void setInventory(Map<Integer, List<CarPart>> inventory) {
        if(inventory == null || inventory.isEmpty()) throw new IllegalArgumentException("Inventory cannot be null");
        this.inventory = inventory;
    }
    public List<Order> getOrders() {
        return orders;
    }
    public void setOrders(List<Order> orders) {
        if(orders == null || orders.isEmpty()) throw new IllegalArgumentException("Orders cannot be null");
        this.orders = orders;
    }
    public Map<Integer, Integer> getDefectives() {
        return defectives;
    }
    public void setDefectives(Map<Integer, Integer> defectives) {
        if(defectives == null || defectives.isEmpty()) throw new IllegalArgumentException("Defectives cannot be null");
        this.defectives = defectives;
    }


    /**
     * Reads a file from the given path and sets up orders based on the information in the file.
     * The file should have the following format:
     * - The first line is a header and should be ignored.
     * - Each subsequent line represents an order and should have the following format:
     *   id,customerName,(partId1 quantity1)-(partId2 quantity2)-...-(partIdN quantityN)
     *   where id is an integer representing the order ID, customerName is a string representing the customer name,
     *   and each partId/quantity pair represents a part ID and the quantity requested for that part.
     *   The partId and quantity should be separated by a space and enclosed in parentheses.
     *   Each partId/quantity pair should be separated by a dash.
     * @param path the path to the file to be read
     * @throws IOException if there is an error reading the file
     * @throws IllegalArgumentException if the path is null or empty
     */
    public void setupOrders(String path) throws IOException {
        
        if(path == null || path.isEmpty()) throw new IllegalArgumentException("Path cannot be null or empty");
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            String line = br.readLine();
            line = br.readLine();
            while(line != null && !line.isEmpty()) {
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
                try {
                    this.getOrders().add(new Order(id, customerName, requestedParts, false));
                } catch (NullPointerException e) {
                    System.out.println("Unable to add order: " + e.getMessage());
                }
                
                line = br.readLine();
            }
        } catch (IOException e) {
            // print unable to read file
            System.out.println("Unable to read file");
            e.printStackTrace();
        }
    }


    /**
     * Reads a CSV file from the given path and creates CarPart and PartMachine objects based on the data.
     * Adds the created PartMachine objects to the list of machines in the CarPartFactory object.
     * @param path the path of the CSV file to be read
     * @throws IOException if the file is not found or cannot be read
     * @throws IllegalArgumentException if the path is null or empty
     */
    public void setupMachines(String path) throws IOException {
        if(path == null || path.isEmpty()) throw new IllegalArgumentException("Path cannot be null or empty");
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            String line = br.readLine();
            line = br.readLine();
            while(line != null){
                 
                String[] values = line.split(",");
                CarPart new_car_part_created_by_initial_setup_read_on_csv = new CarPart(Integer.parseInt(values[0]), values[1], Double.parseDouble(values[2]), false);
                PartMachine new_machine_created_by_initial_setup_read_on_csv = new PartMachine(Integer.parseInt(values[0]), new_car_part_created_by_initial_setup_read_on_csv, Integer.parseInt(values[4]), Double.parseDouble(values[3]), Integer.parseInt(values[5]));
                try {
                    this.getMachines().add(new_machine_created_by_initial_setup_read_on_csv);
                } catch (NullPointerException e) {
                    System.out.println("Unable to add machine: " + e.getMessage());
                }
                line = br.readLine();

            }
        } catch (IOException e) {
            System.out.println(path + " not found");
            e.printStackTrace();
        }
    }

    /*
     * Sets up the catalog by creating a map of all the parts in the catalog.
     * The key should be the part ID and the value should be the CarPart object.
     */
    public void setupCatalog() throws IOException  {
        //create a map of all the parts in the catalog 
        for(PartMachine machine : this.getMachines()) {
            this.getPartCatalog().put(machine.getPart().getId(), machine.getPart());
        }
    }

    /*
     * Sets up the inventory by creating a map of all the parts in the inventory.
     * The key should be the part ID and the value should be a list of CarPart objects.
     * The list should be empty at first.
     * Also creates a map of all the defective parts.
     * The key should be the part ID and the value should be the number of defective parts.
     * The value should be 0 at first.
     */
    public void setupInventory() {
        //create a map of all the parts in the inventory
        for(PartMachine machine : this.getMachines()) {
            this.getInventory().put(machine.getPart().getId(), new data_structures.ArrayList<>());
            this.getDefectives().put(machine.getPart().getId(), 0);
        }
    }

    /*
     * Stores all the parts in the production bin in the inventory.
     * If the part is defective, it should be added to the defective map.
     */
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
    
    /**
     * Runs the car part factory for a specified number of days and minutes.
     * During each minute, each machine produces a car part if possible and adds it to the production bin.
     * At the end of each day, the production bin is stored in the inventory and orders are processed.
     * @param days the number of days to run the factory
     * @param minutes the number of minutes to run the factory each day
     */
    public void runFactory(int days, int minutes) {

        for(int i = 0; i < days; i++) {
            for(int j = 0; j < minutes; j++) {
                for(PartMachine machine : this.getMachines()) {
                    CarPart part = machine.produceCarPart();
                    if(part != null) {
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



   
/**
 * Processes the orders in the CarPartFactory's list of orders. 
 * For each order, checks if all the requested parts are available in the inventory. 
 * If so, removes the parts from the inventory and marks the order as fulfilled.
 */
public void processOrders() {
    for(Order order : this.getOrders()) {
        List<Integer> current_order_keys = order.getRequestedParts().getKeys();
        boolean order_complete = true;
        for(int i = 0; i < current_order_keys.size(); i++) {
            int current_key = current_order_keys.get(i);
            int current_value = order.getRequestedParts().get(current_key);
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
