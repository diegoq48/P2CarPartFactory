# Car Parts Factory
Student Name: **[Diego Quinones]**

Student Email: **[diego.quinones8@upr.edu]** 

Student ID: **[802-22-4049]**

This project simulates a factory that produces car parts and processes orders. 

## Overview

The CarPartFactory class manages the factory operations including:

- Setting up machines, parts catalog, inventory, and orders
- Running production over a number of simulated days  
- Processing orders based on inventory
- Generating reports

The key classes are:

- CarPart - Represents a car part
- PartMachine - Represents a machine that produces CarParts 
- Order - Represents a customer order for car parts

The factory uses several data structures to track state:

- List of PartMachines
- Map of part catalog
- Map of inventory 
- Map of defective part counts
- Stack of production bin
- List of orders

## Usage 

The CarPartFactory is constructed by passing file paths for order and machine setup data:
```
CarPartFactory factory = new CarPartFactory("orders.csv", "parts.csv");
```
The factory can then be simulated for a number of days:
```
factory.runFactory(10, 60); // runs 10 days of 60 minutes each
```
Finally a report can be generated:
```
factory.generateReport();
```
This will print out information on the parts produced, inventory, and orders.

## Implementation

Key methods:

- `setupMachines()` - Read machine data from CSV and instantiate PartMachine objects
- `setupOrders()` - Read order data from CSV and instantiate Order objects  
- `setupInventory()` - Initialize inventory map and defective parts map
- `storeInInventory()` - Move parts from production bin to inventory
- `processOrders()` - Check inventory for order fulfillment
- `runFactory()` - Main simulation loop

The project utilizes data structures from the jdk as well as custom implementations in `data_structures` package.

## Next Steps 

Some potential enhancements:

- Address edge cases and input validation
- Add persistence for factory state  
- Improve inventory management logic
- Add unit tests
- Support loading data from databases rather than just CSV files

## License

This project is licensed under the MIT License. See LICENSE for details.