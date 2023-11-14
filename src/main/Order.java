package main;

import interfaces.Map;

/**
 * The Order class represents an order made by a customer for car parts.
 * Each order has an id, a customer name, a map of requested parts and a boolean indicating whether or not the order has been fulfilled.
 */
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
        if(requestedParts == null || requestedParts.isEmpty()) throw new IllegalArgumentException("Requested parts cannot be null");
        if(!this.verifyMap(requestedParts)) throw new IllegalArgumentException("Requested parts cannot contain negative keys or values");
        this.id = id;
        this.customerName = customerName;
        this.requestedParts = requestedParts;
        this.fulfilled = fulfilled; 
    }
    
    /**
     * Verifies if a given map is valid.
     * A map is considered valid if it is not null, not empty, and all its keys and values are non-negative integers.
     * @param map the map to be verified
     * @return true if the map is valid, false otherwise
     */
    private boolean verifyMap(Map<Integer, Integer> map) {
        if(map == null || map.isEmpty()) return false;
        for(Integer key: map.getKeys()) {
            if(key < 0 || key == null) return false;
            if(map.get(key) < 0 || map.get(key) == null) return false;
        }
        return true;
    }
    /**
     * Gets the unique identifier of the order.
     *
     * @return The order identifier.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Sets the unique identifier of the order.
     *
     * @param id The order identifier to be set.
     * @throws IllegalArgumentException If the provided id is less than 0.
     */
    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be greater than or equal to 0");
        }
        this.id = id;
    }

    /**
     * Checks if the order is fulfilled.
     *
     * @return True if the order is fulfilled, false otherwise.
     */
    public boolean isFulfilled() {
        return this.fulfilled;
    }

    /**
     * Sets the fulfillment status of the order.
     *
     * @param fulfilled The fulfillment status to be set.
     */
    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    /**
     * Gets the map of requested parts and their quantities for the order.
     *
     * @return The map of requested parts and their quantities.
     */
    public Map<Integer, Integer> getRequestedParts() {
        return this.requestedParts;
    }

    /**
     * Sets the map of requested parts and their quantities for the order.
     *
     * @param requestedParts The map of requested parts and their quantities to be set.
     * @throws IllegalArgumentException If the provided requestedParts map is null or empty.
     */
    public void setRequestedParts(Map<Integer, Integer> requestedParts) {
        if (requestedParts == null || requestedParts.isEmpty()) {
            throw new IllegalArgumentException("Requested parts map cannot be null or empty");
        }
        this.requestedParts = requestedParts;
    }

    /**
     * Gets the name of the customer who placed the order.
     *
     * @return The customer's name.
     */
    public String getCustomerName() {
        return this.customerName;
    }

    /**
     * Sets the name of the customer who placed the order.
     *
     * @param customerName The customer's name to be set.
     * @throws IllegalArgumentException If the provided customerName is null or empty.
     */
    public void setCustomerName(String customerName) {
        if (customerName == null || customerName.isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty");
        }
        this.customerName = customerName;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param o the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    public boolean equals(Object o) {
        if(o == null) return false;
        if(!(o instanceof Order)) return false;
        Order other = (Order) o;
        return this.getId() == other.getId();
    }
    /**
     * Adds a requested part to the order.
     * 
     * @param partId the ID of the part to be added
     * @param quantity the quantity of the part to be added
     * @throws IllegalArgumentException if partId or quantity is less than or equal to 0
     */
    public void addRequestedPart(int partId, int quantity) {
        if(partId < 0) throw new IllegalArgumentException("Part id must be greater than 0");
        if(quantity < 0) throw new IllegalArgumentException("Quantity must be greater than 0");
        this.getRequestedParts().put(partId, quantity);
    }
    /**
     * Returns the order's information in the following format: {id} {customer name} {number of parts requested} {isFulfilled}
     */
    @Override
    public String toString() {
        return String.format("%d %s %d %s", this.getId(), this.getCustomerName(), this.getRequestedParts().size(), (this.isFulfilled())? "FULFILLED": "PENDING");
    }

    
    
}
