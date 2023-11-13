package main;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import data_structures.BasicHashFunction;
import data_structures.HashTableSC;
import interfaces.Map;

public class TesterMain { 
    public static void main(String[] args) {
        try {
            CarPartFactory cpf = new CarPartFactory("input/orders.csv", "input/parts.csv");
            System.out.println(cpf.getMachines().get(1));
            System.out.println(cpf.getMachines().get(6));
            System.out.println(cpf.getMachines().get(2));
            System.out.println(cpf.getPartCatalog().get(1).getName());
            cpf.runFactory(30, 1000);
            cpf.generateReport();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }

}

