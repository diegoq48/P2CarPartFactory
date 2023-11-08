package main;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import data_structures.BasicHashFunction;
import data_structures.HashTableSC;
import interfaces.Map;

public class TesterMain { 
    public static void main(String[] args) {
/*         try {
            CarPartFactory cpf = new CarPartFactory("input/orders.csv", "input/parts.csv");
            System.out.println(cpf.getMachines().get(1));
            System.out.println(cpf.getMachines().get(6));
            System.out.println(cpf.getMachines().get(2));
            System.out.println(cpf.getPartCatalog().get(1).getName());
            cpf.runFactory(1, 30);
            cpf.generateReport();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } */
      /*   CarPart part = new CarPart(9, "Transmission", 15.0, true);
        PartMachine machine1 = new PartMachine(1, part, 1, 1.2, 5);
        for (int i = 0; i < 10; i++) {
            CarPart p2 = machine1.produceCarPart();
            machine1.printConveyorBelt();
            if(p2 == null){System.out.println("null");}
        }
        part = machine1.produceCarPart();
        System.out.println("Total Parts Produced: " + machine1.getTotalPartsProduced());
        System.out.println("Defective Chance: " + machine1.getChanceOfDefective());
        CarPart part2 = machine1.produceCarPart();
        System.out.println("Total Parts Produced: " + machine1.getTotalPartsProduced());
        System.out.println("Defective Chance: " + machine1.getChanceOfDefective());
        CarPart part3 = machine1.produceCarPart();
        System.out.println("Total Parts Produced: " + machine1.getTotalPartsProduced());
        System.out.println("Defective Chance: " + machine1.getChanceOfDefective());
        System.out.println(part.isDefective());
        
 */     
        try{
            CarPartFactory factory = new CarPartFactory("input/orders.csv", "input/parts.csv");
            factory.getOrders().clear();
            Map<Integer, Integer> reqParts = new HashTableSC<>(1, new BasicHashFunction());
            reqParts.put(1, 5);
            factory.getOrders().add(new Order(1, "Ben", reqParts, false));
            reqParts = new HashTableSC<>(2, new BasicHashFunction());
            reqParts.put(2, 2);
            reqParts.put(1, 7);
            reqParts.put(5, 1);
            factory.getOrders().add(new Order(2, "Jerry", reqParts, false));
            reqParts = new HashTableSC<>(2, new BasicHashFunction());
            reqParts.put(20, 15);
            reqParts.put(1, 1);
            factory.getOrders().add(new Order(3, "Louis", reqParts, false));
            reqParts = new HashTableSC<>(1, new BasicHashFunction());
            reqParts.put(1, 5);
            reqParts.put(13, 1);
            factory.getOrders().add(new Order(4, "John", reqParts, false));
            factory.runFactory(1, 30);
            System.out.println(factory.getInventory());
            System.out.println();
            System.out.println(factory.getOrders().get(0).getRequestedParts());
       //System.out.println("Total Parts Produced: " + factory.getMachines().get(0).getTotalPartsProduced());
       // System.out.println(factory.getOrders().get(0).isFulfilled());
        } catch (IOException e) {
            fail("IOException");
        }
    }

        //print the total parts produced and defective chance

    }

