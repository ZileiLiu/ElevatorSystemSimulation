package main;

import building.Building;
import building.BuildingInterface;
import scanerzus.Request;

/**
 * The driver for the elevator system.
 * This class will create the elevator system and run it.
 * this is for testing the elevator system.
 * <p>
 * It provides a user interface to the elevator system.
 */
public class MainConsole {

  /**
   * The main method for the elevator system.
   * This method creates the elevator system and runs it.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {

    // the number of floors, the number of elevators, and the number of people.

    final int numFloors = 6;
    final int numElevators = 1;
    final int numPeople = 3;

    String[] introText = {
        "Welcome to the Elevator System!",
        "This system will simulate the operation of an elevator system.",
        "The system will be initialized with the following parameters:",
        "Number of floors: " + numFloors,
        "Number of elevators: " + numElevators,
        "Number of people: " + numPeople,
        "The system will then be run and the results will be displayed."
    };

    printDivider();
    for (String line : introText) {
      System.out.println(line);
    }
    printDivider();

    // create the building
    BuildingInterface building = new Building(numFloors, numElevators, numPeople);
    printDivider();
    System.out.println("(1) Building created");
    building.getElevatorSystemStatus().generateLog();
    printDivider();

    // start the elevator system
    building.startElevatorSystem();
    System.out.println("(2) Start the elevator system");
    building.getElevatorSystemStatus().generateLog();;
    printDivider();

    // step the building
    building.step();
    System.out.println("(3) Step the elevator system 1 time");
    building.getElevatorSystemStatus().generateLog();
    printDivider();

    // add requests
    building.addRequest(new Request(0, 1));
    building.addRequest(new Request(4, 2));
    building.addRequest(new Request(3, 1));
    System.out.println("(4) 3 Requests added: 0->1, 4->2, 3->1");
    building.getElevatorSystemStatus().generateLog();
    printDivider();

    // step the building 20 times
    System.out.println("(5) Step the building 20 times");
    for (int i = 0; i < 20; i++) {
      building.step();
      System.out.println(" ");
      System.out.println("Elevator system stepped " + (i + 1) + " times");
      building.getElevatorSystemStatus().generateLog();
    }
    printDivider();

    // stop the elevator system
    building.stopElevatorSystem();
    System.out.println("(6) Stop the elevator system");
    building.getElevatorSystemStatus().generateLog();
    printDivider();

    // step the building 5 times
    System.out.println("(7) Step the building 5 times");
    for (int i = 0; i < 5; i++) {
      building.step();
      System.out.println(" ");
      System.out.println("Elevator system stepped " + (i + 1) + " times");
      building.getElevatorSystemStatus().generateLog();
    }
    System.out.println(" ");
    System.out.println("Now the system is out of service.");
    printDivider();

    // start the elevator system again
    building.startElevatorSystem();
    System.out.println("(8) Start the elevator system again");
    building.getElevatorSystemStatus().generateLog();
    printDivider();

    // add requests
    building.addRequest(new Request(2, 3));
    building.addRequest(new Request(4, 1));
    System.out.println("(9) 2 requests added 2->3, 4->1");
    building.getElevatorSystemStatus().generateLog();
    printDivider();

    // step the building 5 times
    System.out.println("(10) Step the building 5 times");
    for (int i = 0; i < 5; i++) {
      building.step();
      System.out.println(" ");
      System.out.println("Elevator system stepped " + (i + 1) + " times");
      building.getElevatorSystemStatus().generateLog();
    }
    printDivider();

    // stop the elevator system
    building.stopElevatorSystem();
    System.out.println("(11) Stop the elevator system again");
    building.getElevatorSystemStatus().generateLog();
    printDivider();

    // step the building 4 times
    System.out.println("(12) Step the building 4 times");
    for (int i = 0; i < 4; i++) {
      building.step();
      System.out.println(" ");
      System.out.println("Elevator system stepped " + (i + 1) + " times");
      building.getElevatorSystemStatus().generateLog();
    }
    System.out.println(" ");
    System.out.println("Now the system is out of service.");
    printDivider();
  }

  /**
   * This method is used to print a divider to the console.
   */
  public static void printDivider() {
    System.out.println("-------------------------------------------------------------------------");
  }
}
