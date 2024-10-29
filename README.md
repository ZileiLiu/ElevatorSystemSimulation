# Building Elevator Simulation

## Overview
This is a Java application that simulates an elevator system in a building. The application uses the Model-View-Controller (MVC) design pattern. It provides a graphical user interface to interact with the elevator system, allowing users to add requests and observe the system's response.

## List of Features
- Create a building with a specified number of floors and elevators.
- Start and stop the elevator system.
- Add requests for the elevator system to process.
- Step through the simulation, updating the state of the elevator system and processing requests.

## How To Run
To run the application, navigate to the res directory and click on the ElevatorSystemSimulation.jar file. This will start the application and display the graphical user interface.

## How To Use
The graphical user interface provides the following options:
- Create a building with a specified number of floors and elevators.
- Start and stop the elevator system.
- Add requests for the elevator system to process.
- Step through the simulation, updating the state of the elevator system and processing requests.
- View the state of the elevator system, including the current floor of each elevator and the requests that have been processed.
- View the requests that have been added to the elevator system.
- View the requests that have been processed by the elevator system.
- View the requests that are currently being processed by the elevator system.
- View the requests that are waiting to be processed by the elevator system.

## Design/Model changes
- Version 1.0: The UML diagram.
- Version 1.1: The Model of the Elevator System.(The distribute requests method is changed into private.)
- Version 1.2: The Controller and the View of the Elevator System.

## Assumptions
- The elevator system is initialized with all elevators at the first floor.
- The elevator system processes requests in the order they are added.
- The create building option can only be used once, and the building cannot be modified once it is created.
- The number of floors and elevators can only be set from the create building option.

## Limitations
- In the GUI application, the number of floors and elevators must be set from the create building options. These values are limited compared to requirements from the assignment.
- The information displayed in the GUI is simplified and does not include all the details of the elevator system's state.
- The elevator system information is displayed in a text area, which may not be ad user-friendly as images.
- The GUI interface does not include any error handling or validation for user input. For example, when the user enters an invalid input, the application will not display an error message.

## Citations
- The codes for the Elevator System Simulation are written with the assistance of AI tool Copilot.
