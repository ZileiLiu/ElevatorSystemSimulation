package view;

import controller.BuildingControllerInterface;
import elevator.ElevatorReport;
import java.util.List;
import scanerzus.Request;

/**
 * Interface for the view of the Building game.
 * This interface is used to create the building view.
 */
public interface BuildingViewInterface {

  /**
   * Adds a controller to the view.
   *
   * @param controller the controller to add to the view.
   */
  void addController(BuildingControllerInterface controller);

  /**
   * Creates the building view.
   *
   * @param floorNumber      the number of floors in the building.
   * @param elevatorNumber   the number of elevators in the building.
   * @param capacityNumber   the capacity of the elevators in the building.
   * @param elevatorReports  the reports for the elevators in the building.
   */
  void createBuildingView(Integer floorNumber, Integer elevatorNumber, Integer capacityNumber,
                          ElevatorReport[] elevatorReports);

  /**
   * Switches the elevator system view.
   * @param status the status of the elevator system.
   */
  void switchElevatorSystemView(String status);

  /**
   * Updates the elevators in the building view.
   *
   * @param elevatorReports the reports for the elevators in the building.
   */
  void updateElevators(ElevatorReport[] elevatorReports);

  /**
   * Activates the requests in the building view.
   *
   * @param numFloors the number of floors in the building.
   */
  void activateRequests(int numFloors);

  /**
   * Adds a request view to the building view.
   *
   * @param upRequests   the up requests in the building.
   * @param downRequests the down requests in the building.
   */
  void addRequestView(List<Request> upRequests, List<Request> downRequests);

  /**
   * Disables the requests in the building view.
   */
  void disableRequests();

  /**
   * Disables the step button in the building view.
   */
  void disableStep();
}
