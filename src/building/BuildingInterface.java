package building;

import scanerzus.Request;

/**
 * This interface is used to represent a building.
 * It is used to manage the elevators and requests.
 * It is used to report the status of the building.
 * It is used to start and stop the elevator system.
 * It is used to add requests to the building.
 * It is used to distribute requests to the elevators.
 * It is used to step the building.
 * It is used to stop an elevator.
 */
public interface BuildingInterface {

  /**
   * This method is used to add a request to the building.
   * The request will be added to the upRequests or downRequests list.
   *
   * @param request The request to add.
   * @return true if the request was added, false otherwise.
   * @throws IllegalStateException if the system is out of service or stopping.
   * @throws IllegalArgumentException if the request is invalid.
   */
  boolean addRequest(Request request) throws IllegalStateException;

  /**
   * This method is used to step the building.
   * This will step the elevators and distribute requests.
   */
  void step();

  /**
   * This method is used to start the elevator system.
   * This will start the elevator system and the elevators.
   *
   * @return true if the elevator system was started, false otherwise.
   * @throws IllegalStateException if the system is already running.
   */
  boolean startElevatorSystem() throws IllegalStateException;

  /**
   * This method is used to stop the elevator system.
   * This will stop the elevator system and the elevators.
   */
  void stopElevatorSystem();

  /**
   * This method is used to stop an elevator.
   *
   * @param elevatorId The id of the elevator to start.
   */
  void stopElevator(int elevatorId);

  /**
   * This method is used to get the elevator system status.
   * This will return a BuildingReport object.
   * The BuildingReport object contains the status of the building.
   *
   * @return the elevator system status.
   */
  BuildingReport getElevatorSystemStatus();
}
