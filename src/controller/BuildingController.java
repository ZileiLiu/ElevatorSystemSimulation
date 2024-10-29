package controller;

import building.Building;
import building.BuildingInterface;
import building.enums.ElevatorSystemStatus;
import scanerzus.Request;
import view.BuildingViewInterface;

/**
 * Controller class for the Building Elevator Simulation.
 */
public class BuildingController implements BuildingControllerInterface {
  private final BuildingViewInterface view;
  private BuildingInterface model;

  /**
   * Constructor for the BuildingController class.
   *
   * @param view the view for the Building Elevator Simulation
   */
  public BuildingController(BuildingViewInterface view) {
    this.view = view;
  }

  @Override
  public void addView() {
    view.addController(this);
  }

  @Override
  public void createElevatorSystem(Integer floorNumber,
                                   Integer elevatorNumber, Integer capacityNumber) {
    if (floorNumber == null || elevatorNumber == null || capacityNumber == null) {
      throw new IllegalArgumentException("Null argument");
    }
    this.model = new Building(floorNumber, elevatorNumber, capacityNumber);
    this.view.createBuildingView(floorNumber,
        elevatorNumber, capacityNumber, model.getElevatorSystemStatus().getElevatorReports());
  }

  @Override
  public void switchElevatorSystem() {
    if (model == null) {
      throw new IllegalStateException("Model is null");
    }
    if (model.getElevatorSystemStatus().getSystemStatus() == ElevatorSystemStatus.outOfService) {
      model.startElevatorSystem();
      view.switchElevatorSystemView("running");
      view.updateElevators(model.getElevatorSystemStatus().getElevatorReports());
      view.activateRequests(model.getElevatorSystemStatus().getNumFloors());
    } else if (model.getElevatorSystemStatus().getSystemStatus() == ElevatorSystemStatus.running) {
      model.stopElevatorSystem();
      view.switchElevatorSystemView("stopping");
      view.updateElevators(model.getElevatorSystemStatus().getElevatorReports());
      view.disableRequests();
    }
  }

  @Override
  public void addRequest(Integer start, Integer end) {
    if (model == null) {
      throw new IllegalStateException("Model is null");
    }
    model.addRequest(new Request(start, end));
    view.addRequestView(model.getElevatorSystemStatus().getUpRequests(),
        model.getElevatorSystemStatus().getDownRequests());
  }

  @Override
  public void step() {
    if (model == null) {
      throw new IllegalStateException("Model is null");
    }
    model.step();
    view.updateElevators(model.getElevatorSystemStatus().getElevatorReports());
    view.addRequestView(model.getElevatorSystemStatus().getUpRequests(),
        model.getElevatorSystemStatus().getDownRequests());
    if (model.getElevatorSystemStatus().getSystemStatus() == ElevatorSystemStatus.outOfService) {
      view.switchElevatorSystemView("outOfService");
      view.disableStep();
    }
  }
}


