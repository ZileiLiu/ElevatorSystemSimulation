package controller;

/**
 * Interface for the controller of the Building game.
 */
public interface BuildingControllerInterface {

  /**
   * Adds a view to the controller.
   */
  void addView();

  /**
   * Creates the elevator system.
   *
   * @param floorsNum the number of floors in the building.
   * @param elevatorsNum the number of elevators in the building.
   * @param capacityNum the capacity of the elevators in the building.
   */
  void createElevatorSystem(Integer floorsNum, Integer elevatorsNum, Integer capacityNum);

  /**
   * Switches the elevator system.
   */
  void switchElevatorSystem();

  /**
   * Adds a request to the elevator system.
   *
   * @param start the start floor of the request.
   * @param end the end floor of the request.
   */
  void addRequest(Integer start, Integer end);

  /**
   * Steps the elevator system.
   */
  void step();
}
