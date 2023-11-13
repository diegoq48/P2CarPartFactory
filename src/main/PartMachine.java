package main;

import java.util.LinkedList;
import data_structures.ListQueue;
import interfaces.Queue;
import java.util.Random;
/**
 * The PartMachine class represents a machine that produces car parts. 
 * It has a timer that determines when a new part is produced and a conveyor belt 
 * that stores the produced parts. The machine can also be set to produce defective parts.
 */
public class PartMachine {
    private int id;
    private Queue<Integer> timer; 
    private CarPart part1;
    private Queue<CarPart> conveyorBelt;
    private int totalPartsProduced;
    private double partWeightError;
    private int chanceOfDefective;
    private int period; 

   
    /**
     * A class representing a machine that produces car parts.
     * The machine has an id, a car part, a production period, a weight error, and a chance of defective parts.
     * The machine produces parts and adds them to a conveyor belt.
     */
    public PartMachine(int id, CarPart p1, int period, double weightError, int chanceOfDefective) {
        //check the values 
        if(id < 0) throw new IllegalArgumentException("Id must be greater than 0");
        if(p1 == null) throw new IllegalArgumentException("Part cannot be null");
        if(period < 0) throw new IllegalArgumentException("Period must be greater than 0");
        if(weightError < 0) throw new IllegalArgumentException("Weight error must be greater than 0");
        if(chanceOfDefective < 0) throw new IllegalArgumentException("Chance of defective must be greater than 0");
        this.id = id;
        this.part1 = p1;
        this.period = period;
        this.partWeightError = weightError;
        this.chanceOfDefective = chanceOfDefective;
        this.timer = this.setTimer();  
        this.conveyorBelt = new ListQueue<>();
        this.setConveyorBelt();
        this.totalPartsProduced = 0;

    }
    
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        if(id < 0) throw new IllegalArgumentException("Id must be greater than 0");
        this.id = id;
    }
    /**
     * Creates a new queue with integers representing a countdown timer.
     * The queue is initialized with integers from the period of the PartMachine
     * down to 0.
     *
     * @return a new queue with integers representing a countdown timer.
     */
    private Queue<Integer> setTimer(){
        // Create a new queue
        Queue<Integer> timer = new ListQueue<>();
        // Add the values to the queue in reverse order
        for (int i = this.getPeriod() - 1; i >= 0; i--) {
            timer.enqueue(i);
        }
        return timer;
    }

    // returns the timer which is a queue that counts down to 0 0 being when we can produce a part
    public Queue<Integer> getTimer() {
       return this.timer;
    }
    //allows us to set the timer provided it is not null accepts  a queue of integers
    public void setTimer(Queue<Integer> timer) {
        if(timer == null || timer.isEmpty() ) throw new IllegalArgumentException("Timer cannot be null");
        this.timer = timer;
    }
    //Returns the part that the machine produces 
    public CarPart getPart() {
       return this.part1;
    }
    //allows us to set the part provided it is not null accepts a car part
    public void setPart(CarPart part1) {
        if(part1 == null ) throw new IllegalArgumentException("Part cannot be null");
        this.part1 = part1;
    }
    //returns the conveyor belt which is a queue of car parts
    public Queue<CarPart> getConveyorBelt() {
        return this.conveyorBelt;
        
    }
    //allows us to set the conveyor belt provided it is not null accepts a queue of car parts
    public void setConveyorBelt() {
        this.conveyorBelt = new ListQueue<>();
        for(int i = 0; i < 10; i++){
            this.getConveyorBelt().enqueue(null);
        }
    }
    //returns the total parts produced by the machine
    public int getTotalPartsProduced() {
        return this.totalPartsProduced;
    }
    //allows us to set the total parts produced provided it is not null accepts an integer
    public void setTotalPartsProduced(int count) {
        if(count < 0) throw new IllegalArgumentException("Total parts produced must be greater than 0");
        if(count < this.totalPartsProduced) throw new IllegalArgumentException("Total parts produced cannot be less than the current total");
        this.totalPartsProduced = count;
    }
    //returns the weight error of the part
    public double getPartWeightError() {
        return this.partWeightError;
    }
    //allows us to set the weight error provided it is not null accepts a double
    public void setPartWeightError(double partWeightError) {
        if(partWeightError < 0) throw new IllegalArgumentException("Weight error must be greater than 0");
        this.partWeightError = partWeightError;
    }
    //returns the chance of defective parts
    public int getChanceOfDefective() {
        return this.chanceOfDefective;
    }
    //allows us to set the chance of defective parts provided it is not null accepts an integer
    public void setChanceOfDefective(int chanceOfDefective) {
        if(chanceOfDefective < 0 || chanceOfDefective > 100) throw new IllegalArgumentException("Chance of defective must be greater than 0 but less than 100");
        this.chanceOfDefective = chanceOfDefective;
    }
    //resets the conveyor belt to all null values
    public void resetConveyorBelt() {
        this.getConveyorBelt().clear();
        for(int i = 0; i < 10; i++) {
            this.getConveyorBelt().enqueue(null);
        }
    }

    //returns the period of the machine
    public int getPeriod() {

        return this.period;
    }
    //allows us to set the period provided it is not null accepts an integer
    public void setPeriod(int period) {
        if(period < 0) throw new IllegalArgumentException("Period must be greater than 0");
        this.period = period;
    }
    //returns the timer and rotates the values
    public int tickTimer() {
        if(this.getTimer().isEmpty()) {
            this.setTimer();
        }
        int front = this.getTimer().front();
        this.getTimer().enqueue(this.getTimer().dequeue());
        return front;
    }
    
    public CarPart produceCarPart() {
        if(this.getConveyorBelt().isEmpty()){
            this.resetConveyorBelt();
        }
        int time = tickTimer();
        CarPart priorPart = this.getConveyorBelt().dequeue();
        if(time != 0){
            conveyorBelt.enqueue(null);
        }else{
            Random random = new Random();
            CarPart newPart = new CarPart(this.getPart().getId(), this.getPart().getName(), (part1.getWeight() - partWeightError + 2 * partWeightError * random.nextDouble()), (this.getTotalPartsProduced() % this.getChanceOfDefective() == 0));
            this.setTotalPartsProduced(this.getTotalPartsProduced() + 1);
            conveyorBelt.enqueue(newPart);
        }
        return priorPart;
    }

    /**
     * Returns string representation of a Part Machine in the following format:
     * Machine {id} Produced: {part name} {total parts produced}
     */
    @Override
    public String toString() {
        return "Machine " + this.getId() + " Produced: " + this.getPart().getName() + " " + this.getTotalPartsProduced();
    }
    /**
     * Prints the content of the conveyor belt. 
     * The machine is shown as |Machine {id}|.
     * If the is a part it is presented as |P| and an empty space as _.
     */
    public void printConveyorBelt() {
        // String we will print
        String str = "";
        // Iterate through the conveyor belt
        for(int i = 0; i < this.getConveyorBelt().size(); i++){
            // When the current position is empty
            if (this.getConveyorBelt().front() == null) {
                str = "_" + str;
            }
            // When there is a CarPart
            else {
                str = "|P|" + str;
            }
            // Rotate the values
            this.getConveyorBelt().enqueue(this.getConveyorBelt().dequeue());
        }
        System.out.println("|Machine " + this.getId() + "|" + str);
    }
}
