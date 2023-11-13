package main;

/**
 * The CarPart class represents a car part with an id, name, weight, and defect status.
 */
/**
 * Represents a car part with an id, name, weight, and defect status.
 */
public class CarPart {
    private int id;
    private String name;
    private double weight;
    private boolean isDefective;

    /**
     * Creates a new CarPart object with the specified id, name, weight, and defect status.
     *
     * @param id The id of the car part.
     * @param name The name of the car part.
     * @param weight The weight of the car part.
     * @param isDefective The defect status of the car part.
     * @throws IllegalArgumentException If the id is less than 0, the name is null or empty, or the weight is less than 0.
     */
    public CarPart(int id, String name, double weight, boolean isDefective) {
        if(id < 0) throw new IllegalArgumentException("Id must be greater than 0");
        if(name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be null or empty");
        if(weight < 0) throw new IllegalArgumentException("Weight must be greater than 0");
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.isDefective = isDefective;
    }

    /**
     * Returns the id of the car part.
     *
     * @return The id of the car part.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the car part.
     *
     * @param id The new id of the car part.
     * @throws IllegalArgumentException If the id is less than 0.
     */
    public void setId(int id) {
        if (id < 0) throw new IllegalArgumentException("Id must be greater than 0");
        this.id = id;
    }

    /**
     * Returns the name of the car part.
     *
     * @return The name of the car part.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the car part.
     *
     * @param name The new name of the car part.
     * @throws IllegalArgumentException If the name is null or empty.
     */
    public void setName(String name) {
        if(name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be null or empty");
        this.name = name;
    }

    /**
     * Returns the weight of the car part.
     *
     * @return The weight of the car part.
     */
    public double getWeight() {
      return this.weight;
    }

    /**
     * Sets the weight of the car part.
     *
     * @param weight The new weight of the car part.
     * @throws IllegalArgumentException If the weight is less than 0.
     */
    public void setWeight(double weight) {
        if(weight < 0) throw new IllegalArgumentException("Weight must be greater than 0");
        this.weight = weight;
    }

    /**
     * Returns the defect status of the car part.
     *
     * @return The defect status of the car part.
     */
    public boolean isDefective() {
        return this.isDefective;
    }

    /**
     * Sets the defect status of the car part.
     *
     * @param isDefective The new defect status of the car part.
     */
    public void setDefective(boolean isDefective) {
        this.isDefective = isDefective;
    }

    /**
     * Returns the name of the car part as its string representation.
     *
     * @return The name of the car part.
     */
    public String toString() {
        return this.getName();
    }
}