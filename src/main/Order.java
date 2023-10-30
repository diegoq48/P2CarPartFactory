package main;

import interfaces.Map;

public class Order {
    private int id;
    private String customerName;
    private Map<Integer, Integer> requestedParts;
    private boolean fulfilled;
    /**
     * Creates a new order with the given id, customer name, requested parts, and whether or not the order has been fulfilled
     * @param id The order's id
     * @param customerName The customer's name
     * @param requestedParts The parts requested by the customer
     * @param fulfilled Whether or not the order has been fulfilled
     */
    public Order(int id, String customerName, Map<Integer, Integer> requestedParts, boolean fulfilled) {
        if(id < 0) throw new IllegalArgumentException("Id must be greater than 0");
        if(customerName == null || customerName.isEmpty()) throw new IllegalArgumentException("Customer name cannot be null or empty");
        if(requestedParts == null) throw new IllegalArgumentException("Requested parts cannot be null");
        this.id = id;
        this.customerName = customerName;
        this.requestedParts = requestedParts;
        this.fulfilled = fulfilled; 
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        if(id < 0) throw new IllegalArgumentException("Id must be greater than 0");
        this.id = id;
    }
    public boolean isFulfilled() {
        return this.fulfilled;
    }
    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }
    public Map<Integer, Integer> getRequestedParts() {
       return this.requestedParts;
    }
    public void setRequestedParts(Map<Integer, Integer> requestedParts) {
        if(requestedParts == null) throw new IllegalArgumentException("Requested parts cannot be null");
        this.requestedParts = requestedParts;
    }
    public String getCustomerName() {
        return this.customerName;
      
    }
    public void setCustomerName(String customerName) {
        if(customerName == null || customerName.isEmpty()) throw new IllegalArgumentException("Customer name cannot be null or empty");
        this.customerName = customerName;
    }
    /**
     * Returns the order's information in the following format: {id} {customer name} {number of parts requested} {isFulfilled}
     */
    @Override
    public String toString() {
        return String.format("%d %s %d %s", this.getId(), this.getCustomerName(), this.getRequestedParts().size(), (this.isFulfilled())? "FULFILLED": "PENDING");
    }

    
    
}
