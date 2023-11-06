package main;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

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
        CarPart part = new CarPart(9, "Transmission", 15.0, true);
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

        }
        //print the total parts produced and defective chance

    }

