package main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import data_structures.HashTableSC;
import interfaces.*;
public class CarPartFactory {
    private List<PartMachine> machines = new data_structures.ArrayList<>();
    private List<Order> orders =  new data_structures.ArrayList<>();
    private Map<Integer, CarPart> partCatalog;
    private Map<Integer, List<CarPart>> inventory;
    private Map<Integer, Integer> defectives;
    private Stack<CarPart> productionBin;

        
    public CarPartFactory(String orderPath, String partsPath) throws IOException {
        //read the orders and setup the orders list
        this.setupOrders(orderPath);
        //read the machines and setup the machines list
        this.setupMachines(partsPath);
        //setup the catalog
        this.setupCatalog();
        //setup the inventory
        this.setupInventory();
        //store the parts in the inventory
        this.storeInInventory();

                
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
       //read the orders from the file and setup the orders list
       try (BufferedReader reader = new BufferedReader(new FileReader(path))){
        String line;
        //skip the first line
        reader.readLine();
        while((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            int id = Integer.parseInt(data[0]);
            String customerName = data[1];
            Map<Integer, Integer> requestedParts = new HashTableSC<Integer, Integer>(10, interfaces.HashFunction());
            for(int i = 2; i < data.length; i++) {
                String[] partData = data[i].split(":");
                int partId = Integer.parseInt(partData[0]);
                int quantity = Integer.parseInt(partData[1]);
                requestedParts.put(partId, quantity);
            }
            this.getOrders().add(new Order(id, customerName, requestedParts, false));
        }
       }
    }
    public void setupMachines(String path) throws IOException {
       try(BufferedReader reader = new BufferedReader(new FileReader(path))){
        String line;
        //skip the first line
        reader.readLine();
        while((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            int id = Integer.parseInt(data[0]);
            int period = Integer.parseInt(data[1]);
            double weightError = Double.parseDouble(data[2]);
            int chanceOfDefective = Integer.parseInt(data[3]);
            int partId = Integer.parseInt(data[4]);
            CarPart part = this.getPartCatalog().get(partId);
            this.getMachines().add(new PartMachine(id, part, period, weightError, chanceOfDefective));
        }
       }
    }
    public void setupCatalog() {
        
    }
    public void setupInventory() {
        
    }
    public void storeInInventory() {
       
    }
    public void runFactory(int days, int minutes) {
        
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
