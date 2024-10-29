package building;

import building.enums.ElevatorSystemStatus;
import elevator.Elevator;
import elevator.ElevatorInterface;
import elevator.ElevatorReport;
import java.util.ArrayList;
import java.util.List;
import scanerzus.Request;


/**
 * This class represents a building.
 * It contains a list of elevators and a list of requests.
 * It also contains the status of the elevator system.
 * It is used to manage the elevators and requests.
 * It is used to report the status of the building.
 * It is used to start and stop the elevator system.
 * It is used to add requests to the building.
 * It is used to distribute requests to the elevators.
 * It is used to step the building.
 * It is used to stop an elevator.
 */
public class Building implements BuildingInterface {
  private final List<Elevator> elevators = new ArrayList<>();
  private final List<Request> upRequests = new ArrayList<>();
  private final List<Request> downRequests = new ArrayList<>();
  private ElevatorSystemStatus systemStatus;
  private final int numberOfFloors;
  private final int numberOfElevators;
  private final int elevatorCapacity;

  /**
   * The constructor for the building.
   *
   * @param numberOfFloors    the number of floors in the building.
   * @param numberOfElevators the number of elevators in the building.
   * @param elevatorCapacity  the capacity of the elevators in the building.
   *
   * @throws IllegalArgumentException if numberOfFloors is not between 3 and 30.
   * @throws IllegalArgumentException if elevatorCapacity is not between 3 and 20.
   * @throws IllegalArgumentException if numberOfElevators is less than 1.
   */
  public Building(int numberOfFloors, int numberOfElevators, int elevatorCapacity)
      throws IllegalArgumentException {
    if (numberOfFloors < 3 || numberOfFloors > 30) {
      throw new IllegalArgumentException("numberOfFloors must be between 3 and 30");
    }
    if (elevatorCapacity < 3 || elevatorCapacity > 20) {
      throw new IllegalArgumentException("elevatorCapacity must be between 3 and 20");
    }
    if (numberOfElevators < 1) {
      throw new IllegalArgumentException("numberOfElevators must be at least 1");
    }
    System.out.println("Building constructor called");
    System.out.println("numberOfFloors: " + numberOfFloors);
    System.out.println("numberOfElevators: " + numberOfElevators);
    System.out.println("elevatorCapacity: " + elevatorCapacity);
    this.numberOfFloors = numberOfFloors;
    this.numberOfElevators = numberOfElevators;
    this.elevatorCapacity = elevatorCapacity;
    this.systemStatus = ElevatorSystemStatus.outOfService;
    for (int i = 0; i < numberOfElevators; i++) {
      elevators.add(new Elevator(numberOfFloors, elevatorCapacity));
    }
    System.out.println("\nYet to be built.");
  }

  @Override
  public boolean addRequest(Request request) throws IllegalStateException {
    if (this.systemStatus == ElevatorSystemStatus.outOfService
        || this.systemStatus == ElevatorSystemStatus.stopping) {
      throw new IllegalStateException("System is out of service or stopping.");
    }
    if (request.getStartFloor() < 0 || request.getEndFloor() < 0
        || request.getStartFloor() == request.getEndFloor()
        || request.getStartFloor() >= numberOfFloors
        || request.getEndFloor() >= numberOfFloors) {
      return false;
    }
    if (request.getStartFloor() - request.getEndFloor() > 0) {
      downRequests.add(request);
    } else {
      upRequests.add(request);
    }
    return true;
  }

  /**
   * This method is used to distribute requests to the elevators.
   * It will distribute requests to the elevators that are taking requests
   * and are at the top or bottom floor.
   * It will distribute up requests to elevators at the bottom floor.
   * It will distribute down requests to elevators at the top floor.
   * It will clear the requests after distributing them.
   * It will not distribute requests if there are no requests.
   * It will not distribute requests if the elevator is not taking requests.
   * It will not distribute requests if the elevator is not at the top or bottom floor.
   * @throws IllegalStateException if the system is out of service or stopping.
   */
  private void distributeRequests() throws IllegalStateException {
    if (systemStatus == ElevatorSystemStatus.outOfService
        || systemStatus == ElevatorSystemStatus.stopping) {
      throw new IllegalStateException("System is out of service or stopping.");
    }
    for (Elevator elevator : elevators) {
      if (upRequests.isEmpty() && downRequests.isEmpty()) {
        return;
      }
      if (elevator.isTakingRequests() && elevator.getCurrentFloor() == 0) {
        if (upRequests.size() <= elevatorCapacity) {
          elevator.processRequests(upRequests);
          upRequests.clear();
        } else {
          elevator.processRequests(upRequests.subList(0, elevatorCapacity));
          upRequests.subList(0, elevatorCapacity).clear();
        }
      } else if (elevator.isTakingRequests()
          && elevator.getCurrentFloor() == numberOfFloors - 1) {
        if (downRequests.size() <= elevatorCapacity) {
          elevator.processRequests(downRequests);
          downRequests.clear();
        } else {
          elevator.processRequests(downRequests.subList(0, elevatorCapacity));
          downRequests.subList(0, elevatorCapacity).clear();
        }
      }
    }
  }

  @Override
  public void step() {
    if (systemStatus == ElevatorSystemStatus.outOfService) {
      return;
    }
    if (systemStatus == ElevatorSystemStatus.stopping) {
      for (Elevator elevator : elevators) {
        elevator.step();
      }
      if (elevators.stream().allMatch(elevator ->
          !elevator.isDoorClosed() && elevator.getCurrentFloor() == 0)) {
        systemStatus = ElevatorSystemStatus.outOfService;
      }
      return;
    }
    this.distributeRequests();
    for (Elevator elevator : elevators) {
      elevator.step();
    }
  }

  @Override
  public boolean startElevatorSystem() throws IllegalStateException {
    if (systemStatus == ElevatorSystemStatus.stopping) {
      throw new IllegalStateException("System is stopping.");
    }
    if (systemStatus == ElevatorSystemStatus.running) {
      return false;
    }
    for (Elevator elevator : elevators) {
      elevator.start();
    }
    systemStatus = ElevatorSystemStatus.running;
    return true;
  }

  @Override
  public void stopElevatorSystem() {
    for (Elevator elevator : elevators) {
      elevator.takeOutOfService();
    }
    upRequests.clear();
    downRequests.clear();
    systemStatus = ElevatorSystemStatus.stopping;
  }

  @Override
  public void stopElevator(int elevatorId) {
    ElevatorInterface elevator = elevators.get(elevatorId);
    elevator.takeOutOfService();
  }

  @Override
  public BuildingReport getElevatorSystemStatus() {
    ElevatorReport[] elevatorReports = new ElevatorReport[elevators.size()];
    for (int i = 0; i < elevators.size(); i++) {
      elevatorReports[i] = elevators.get(i).getElevatorStatus();
    }
    return new BuildingReport(numberOfFloors, numberOfElevators, elevatorCapacity, elevatorReports,
        upRequests, downRequests, systemStatus);
  }
}
