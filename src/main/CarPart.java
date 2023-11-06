package main;

public class CarPart {
    private int id;
    private String name;
    private double weight;
    private boolean isDefective;

    
    public CarPart(int id, String name, double weight, boolean isDefective) {
        if(id < 0) throw new IllegalArgumentException("Id must be greater than 0");
        if(name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be null or empty");
        if(weight < 0) throw new IllegalArgumentException("Weight must be greater than 0");
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.isDefective = isDefective;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        if (id < 0) throw new IllegalArgumentException("Id must be greater than 0");
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        if(name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be null or empty");
        this.name = name;
    }
    public double getWeight() {
      return this.weight;
    }
    public void setWeight(double weight) {
       // if(weight < 0) throw new IllegalArgumentException("Weight must be greater than 0");
        this.weight = weight;
    }

    public boolean isDefective() {
        return this.isDefective;
    }
    public void setDefective(boolean isDefective) {
        this.isDefective = isDefective;
    }
    /**
     * Returns the parts name as its string representation
     * @return (String) The part name
     */
    public String toString() {
        return this.getName();
    }
}