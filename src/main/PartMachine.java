package main;

import interfaces.Queue;

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
        this.timer = new Queue<Integer>();
        this.conveyorBelt = new Queue<CarPart>();
        this.totalPartsProduced = 0;

    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        if(id < 0) throw new IllegalArgumentException("Id must be greater than 0");
        this.id = id;
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
    public void setConveyorBelt(Queue<CarPart> conveyorBelt) {
    	if(conveyorBelt == null) throw new IllegalArgumentException("Conveyor belt cannot be null");
        this.conveyorBelt = conveyorBelt;
    }
    public int getTotalPartsProduced() {
        if(this.totalPartsProduced < 0) throw new IllegalArgumentException("Total parts produced must be greater than 0");
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
        this.conveyorBelt = new Queue<CarPart>();
    }
    public int tickTimer() {
        // returns the value at the front before rotating it to the back 
        int front = this.getTimer().front();
        this.getTimer().enqueue(this.getTimer().dequeue());
        return front;
    }
    public CarPart produceCarPart() {
        // Check if the timer is zero
        if (this.getTimer().front() == 0) {
            // Create a new CarPart object with the same id and name as the previous parts, but with a randomly assigned weight based on the weight error for this machine
            double weight = this.getPart().getWeight() + (Math.random() * 2 - 1) * this.getPartWeightError();
            CarPart newPart = new CarPart(this.getPart().getId(), this.getPart().getName(), weight, true);
            // Check if the new part is defective by dividing the total amount of parts produced up to this point by the defective chance given. If the remainder is 0, the part is defective.
            if (this.getTotalPartsProduced() % this.getChanceOfDefective() == 0) {
                newPart.setDetective(true);
            }
            // Place the new part in the conveyor belt
            this.getConveyorBelt().enqueue(newPart);
            // Return the new part
            return newPart;
        } else {
            this.getConveyorBelt().enqueue(null);
            return this.getConveyorBelt().front();
        }
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
