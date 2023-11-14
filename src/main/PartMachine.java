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
    private CarPart CarPartProducedByMachine;
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
        this.CarPartProducedByMachine = p1;
        this.period = period;
        this.partWeightError = weightError;
        this.chanceOfDefective = chanceOfDefective;
        this.timer = this.setTimer();  
        this.conveyorBelt = new ListQueue<>();
        this.setConveyorBelt();
        this.totalPartsProduced = 0;

    }
    /**
     * Returns the id of the machine.
     *
     * @return The id of the machine.
     */
    
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

    /**
     * Returns the timer, which is a queue that counts down to 0, where 0 indicates the production of a new part.
     *
     * @return The timer queue.
     */
    public Queue<Integer> getTimer() {
        return this.timer;
    }

    /**
     * Sets the timer with the provided queue of integers.
     *
     * @param timer The queue of integers representing the timer.
     * @throws IllegalArgumentException If the timer is null or empty.
     */
    public void setTimer(Queue<Integer> timer) {
        if (timer == null || timer.isEmpty()) {
            throw new IllegalArgumentException("Timer cannot be null or empty");
        }
        this.timer = timer;
    }

    /**
     * Returns the type of car part that the machine produces.
     *
     * @return The car part produced by the machine.
     */
    public CarPart getPart() {
        return this.CarPartProducedByMachine;
    }

    /**
     * Sets the type of car part that the machine produces.
     *
     * @param part1 The car part to be produced by the machine.
     * @throws IllegalArgumentException If the provided part is null.
     */
    public void setPart(CarPart part1) {
        if (part1 == null) {
            throw new IllegalArgumentException("Part cannot be null");
        }
        this.CarPartProducedByMachine = part1;
    }

    /**
     * Returns the conveyor belt, which is a queue of car parts.
     *
     * @return The conveyor belt queue.
     */
    public Queue<CarPart> getConveyorBelt() {
        return this.conveyorBelt;
    }

    /**
     * Initializes the conveyor belt with null values.
     */
    public void setConveyorBelt() {
        this.conveyorBelt = new ListQueue<>();
        for (int i = 0; i < 10; i++) {
            this.getConveyorBelt().enqueue(null);
        }
    }

    /**
     * Returns the total number of parts produced by the machine.
     *
     * @return The total number of parts produced.
     */
    public int getTotalPartsProduced() {
        return this.totalPartsProduced;
    }

    /**
     * Sets the total number of parts produced by the machine.
     *
     * @param count The total number of parts produced.
     * @throws IllegalArgumentException If the count is negative or less than the current total.
     */
    public void setTotalPartsProduced(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Total parts produced must be greater than or equal to 0");
        }
        if (count < this.totalPartsProduced) {
            throw new IllegalArgumentException("Total parts produced cannot be less than the current total");
        }
        this.totalPartsProduced = count;
    }

    /**
     * Returns the weight error of the produced part.
     *
     * @return The weight error of the part.
     */
    public double getPartWeightError() {
        return this.partWeightError;
    }

    /**
     * Sets the weight error of the produced part.
     *
     * @param partWeightError The weight error to be set.
     * @throws IllegalArgumentException If the weight error is negative.
     */
    public void setPartWeightError(double partWeightError) {
        if (partWeightError < 0) {
            throw new IllegalArgumentException("Weight error must be greater than or equal to 0");
        }
        this.partWeightError = partWeightError;
    }

    /**
     * Returns the chance of defective parts as a percentage.
     *
     * @return The chance of defective parts.
     */
    public int getChanceOfDefective() {
        return this.chanceOfDefective;
    }

    /**
     * Sets the chance of defective parts as a percentage.
     *
     * @param chanceOfDefective The chance of defective parts to be set.
     * @throws IllegalArgumentException If the chance of defective parts is not in the range [0, 100].
     */
    public void setChanceOfDefective(int chanceOfDefective) {
        if (chanceOfDefective < 0 || chanceOfDefective > 100) {
            throw new IllegalArgumentException("Chance of defective must be in the range [0, 100]");
        }
        this.chanceOfDefective = chanceOfDefective;
    }

    /**
     * Resets the conveyor belt to contain all null values.
     */
    public void resetConveyorBelt() {
        this.getConveyorBelt().clear();
        for (int i = 0; i < 10; i++) {
            this.getConveyorBelt().enqueue(null);
        }
    }

    /**
     * Returns the period of the machine.
     *
     * @return The period of the machine.
     */
    public int getPeriod() {
        return this.period;
    }

    /**
     * Sets the period of the machine.
     *
     * @param period The period to be set.
     * @throws IllegalArgumentException If the period is negative.
     */
    public void setPeriod(int period) {
        if (period < 0) {
            throw new IllegalArgumentException("Period must be greater than or equal to 0");
        }
        this.period = period;
    }

    /**
     * Simulates the ticking of a timer and retrieves the current time value.
     * If the timer is empty, it is initialized with a default value.
     *
     * @return The current time value obtained by dequeuing and enqueuing the front
     *         element of the timer, simulating the passage of time.
     */
    public int tickTimer() {
            if(this.getTimer().isEmpty()) {
                this.setTimer();
            }
            int front = this.getTimer().front();
            this.getTimer().enqueue(this.getTimer().dequeue());
            return front;
        }
    /**
     * Produces a car part from the conveyor belt. If the conveyor belt is empty, it is reset.
     * The production process involves dequeuing a part from the conveyor belt, checking the
     * timer, and enqueuing a new part with updated information based on the production time.
     *
     * @return The car part dequeued from the conveyor belt before the production process.
     */
    
    public CarPart produceCarPart() {
        // If the conveyor belt is empty, reset it
        if(this.getConveyorBelt().isEmpty()){
            this.resetConveyorBelt();
        }
        // Get the current time
        int time = tickTimer();
        // Dequeue the prior part and save it
        CarPart priorPart = this.getConveyorBelt().dequeue();
        // If the time is not 0, enqueue a null value since the machine is still producing
        if(time != 0){
            conveyorBelt.enqueue(null);
        }else{
            // Create a new part with updated information
            Random random = new Random();
            CarPart newPart = new CarPart(this.getPart().getId(), this.getPart().getName(), (CarPartProducedByMachine.getWeight() - partWeightError + 2 * partWeightError * random.nextDouble()), (this.getTotalPartsProduced() % this.getChanceOfDefective() == 0));
            this.setTotalPartsProduced(this.getTotalPartsProduced() + 1);
            conveyorBelt.enqueue(newPart);
        }
        // Return the prior part saved at the beginning
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
