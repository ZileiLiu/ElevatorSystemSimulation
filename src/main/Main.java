package main;

import controller.BuildingController;
import controller.BuildingControllerInterface;
import view.BuildingView;
import view.BuildingViewInterface;

/**
 * main.Main class.
 * This class will create the elevator system and run it.
 */
public class Main {
  /**
   * The main method for the elevator system.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    BuildingViewInterface view = new BuildingView();
    BuildingControllerInterface controller = new BuildingController(view);
    controller.addView();
  }
}