package main;

import java.util.LinkedList;
import data_structures.ListQueue;
import interfaces.Queue;
import java.util.Random;
public class PartMachine {
    private int id;
    private Queue<Integer> timer; 
    private CarPart part1;
    private Queue<CarPart> conveyorBelt;
    private int totalPartsProduced;
    private double partWeightError;
    private int chanceOfDefective;
    private int period; 

   
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
    private Queue<Integer> setTimer(){
        // Create a new queue
        Queue<Integer> timer = new ListQueue<>();
        // Add the values to the queue in reverse order
        for (int i = this.getPeriod() - 1; i >= 0; i--) {
            timer.enqueue(i);
        }
        return timer;
    }
    
    public Queue<Integer> getTimer() {
       return this.timer;
    }
    public void setTimer(Queue<Integer> timer) {
        if(timer == null) throw new IllegalArgumentException("Timer cannot be null");
        this.timer = timer;
    }
    public CarPart getPart() {
       return this.part1;
    }
    public void setPart(CarPart part1) {
        if(part1 == null) throw new IllegalArgumentException("Part cannot be null");
        this.part1 = part1;
    }
    public Queue<CarPart> getConveyorBelt() {
        return this.conveyorBelt;
        
    }
    public void setConveyorBelt() {
        for(int i = 0; i < 10; i++){
            this.getConveyorBelt().enqueue(null);
        }
    }
    public int getTotalPartsProduced() {
        return this.totalPartsProduced;
    }
    public void setTotalPartsProduced(int count) {
        this.totalPartsProduced = count;
    }
    public double getPartWeightError() {
        return this.partWeightError;
    }
    public void setPartWeightError(double partWeightError) {
        if(partWeightError < 0) throw new IllegalArgumentException("Weight error must be greater than 0");
        this.partWeightError = partWeightError;
    }
    public int getChanceOfDefective() {
        return this.chanceOfDefective;
    }
    public void setChanceOfDefective(int chanceOfDefective) {
        if(chanceOfDefective < 0) throw new IllegalArgumentException("Chance of defective must be greater than 0");
        this.chanceOfDefective = chanceOfDefective;
    }
    public void resetConveyorBelt() {
        //make all the values null
        this.getConveyorBelt().clear();
        for(int i = 0; i < 10; i++) {
            this.getConveyorBelt().enqueue(null);
        }
    }
    public int tickTimer() {
        // returns the value at the front before rotating it to the back 
        int front = this.getTimer().front();
        this.getTimer().enqueue(this.getTimer().dequeue());
        return front;
    }
    public int getPeriod() {
        return this.period;
    }
    public void setPeriod(int period) {
        if(period < 0) throw new IllegalArgumentException("Period must be greater than 0");
        this.period = period;
    }
    public CarPart produceCarPart() {
        if(this.getConveyorBelt().size() == 0){
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
        //return the value at the front of the queue
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
