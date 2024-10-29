package view;

import controller.BuildingControllerInterface;
import elevator.ElevatorReport;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import scanerzus.Request;

/**
 * Swing GUI for the Building Elevator Simulation.
 */
public class BuildingView extends JFrame implements BuildingViewInterface {
  private BuildingControllerInterface controller;
  private final CardLayout cardLayout = new CardLayout();
  private final JPanel welcomePage = new JPanel();
  private final JPanel buildingPage = new JPanel();
  private JComboBox<Integer> chooseFloorNumber;
  private JComboBox<Integer> chooseElevatorNumber;
  private JComboBox<Integer> chooseCapacityNumber;
  private JButton createBuildingButton;
  private JLabel elevatorStateLabel;
  private JButton switchElevatorSystemButton;
  private JLabel[][] elevatorLabels;
  private JComboBox<Integer> chooseStartFloor;
  private JComboBox<Integer> chooseEndFloor;
  private JButton addRequestButton;
  private JLabel upRequestsLabel;
  private JLabel downRequestsLabel;
  private JLabel[] elevatorRequestsLabels;
  private JTextField addStepNumber;
  private JButton stepButton;
  private ActionListener addRequestButtonListener;
  private ActionListener stepButtonListener;

  /**
   * Constructs a new BuildingView.
   */
  public BuildingView() {
    super("Building Elevator Simulation");

    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setLayout(cardLayout);

    createWelcomePage();
    add(welcomePage, "Welcome Page");

    pack();
    setVisible(true);
  }

  /**
   * Creates the welcome page.
   */
  private void createWelcomePage() {

    welcomePage.setLayout(new BoxLayout(welcomePage, BoxLayout.Y_AXIS));

    final JPanel welcomePanel = new JPanel();
    welcomePanel.setLayout(new FlowLayout());
    JLabel welcomeLabel = new JLabel("Welcome to the Building Elevator Simulation!");
    welcomePanel.add(welcomeLabel);
    welcomePage.add(welcomePanel);

    final JPanel creationBuildingPanel = new JPanel();
    creationBuildingPanel.setLayout(new FlowLayout());

    final JLabel floorNumber = new JLabel();
    floorNumber.setText("Floor Number");
    floorNumber.setVisible(true);
    creationBuildingPanel.add(floorNumber);
    this.chooseFloorNumber = new JComboBox<>();
    for (int i = 3; i <= 9; i++) {
      chooseFloorNumber.addItem(i);
    }
    creationBuildingPanel.add(chooseFloorNumber);

    final JLabel elevatorNumber = new JLabel();
    elevatorNumber.setText("Elevator Number");
    elevatorNumber.setVisible(true);
    creationBuildingPanel.add(elevatorNumber);
    this.chooseElevatorNumber = new JComboBox<>();
    for (int i = 1; i <= 9; i++) {
      chooseElevatorNumber.addItem(i);
    }
    creationBuildingPanel.add(chooseElevatorNumber);

    final JLabel capacityNumber = new JLabel();
    capacityNumber.setText("Capacity Number");
    capacityNumber.setVisible(true);
    creationBuildingPanel.add(capacityNumber);
    this.chooseCapacityNumber = new JComboBox<>();
    for (int i = 3; i <= 20; i++) {
      chooseCapacityNumber.addItem(i);
    }
    creationBuildingPanel.add(chooseCapacityNumber);

    welcomePage.add(creationBuildingPanel);

    final JPanel createBuildingPanel = new JPanel();
    createBuildingPanel.setLayout(new FlowLayout());
    createBuildingButton = new JButton("Create Building");
    createBuildingPanel.add(createBuildingButton);
    welcomePage.add(createBuildingPanel);

    welcomePage.setVisible(true);
  }

  @Override
  public void addController(BuildingControllerInterface controller) {
    this.controller = controller;
    AtomicReference<Integer> selectedFloorNumber =
        new AtomicReference<>((Integer) chooseFloorNumber.getSelectedItem());
    AtomicReference<Integer> selectedElevatorNumber =
        new AtomicReference<>((Integer) chooseElevatorNumber.getSelectedItem());
    AtomicReference<Integer> selectedCapacityNumber =
        new AtomicReference<>((Integer) chooseCapacityNumber.getSelectedItem());
    chooseFloorNumber.addActionListener(e -> selectedFloorNumber.set(
        (Integer) chooseFloorNumber.getSelectedItem()));
    chooseElevatorNumber.addActionListener(
        e -> selectedElevatorNumber.set((Integer) chooseElevatorNumber.getSelectedItem()));
    chooseCapacityNumber.addActionListener(
        e -> selectedCapacityNumber.set((Integer) chooseCapacityNumber.getSelectedItem()));
    createBuildingButton.addActionListener(
        e -> controller.createElevatorSystem(selectedFloorNumber.get(),
            selectedElevatorNumber.get(), selectedCapacityNumber.get()));
  }

  @Override
  public void createBuildingView(Integer floorNumber,
                                 Integer elevatorNumber, Integer capacityNumber,
                                 ElevatorReport[] elevatorReports) {
    elevatorLabels = new JLabel[floorNumber][elevatorNumber];
    buildingPage.setLayout(new BorderLayout());

    JPanel upperPanel = new JPanel(new FlowLayout());
    elevatorStateLabel = new JLabel("System Status: Out of Service; ");
    upperPanel.add(elevatorStateLabel);

    JLabel infoLabel = new JLabel("Floor Number: " + floorNumber
        + ", Elevator Number: " + elevatorNumber
        + ", Capacity Number: " + capacityNumber);
    upperPanel.add(infoLabel);

    switchElevatorSystemButton = new JButton("Start Elevator System");
    upperPanel.add(switchElevatorSystemButton);

    final JPanel midPanel = new JPanel(new BorderLayout());
    final JPanel midUp = getUpJpanel(elevatorNumber, elevatorReports);
    final JPanel midDown = getjPanel(floorNumber, elevatorNumber);
    midPanel.add(midUp, BorderLayout.NORTH);
    midPanel.add(midDown, BorderLayout.CENTER);
    final JPanel lowerPanel = new JPanel(new FlowLayout());
    final JLabel startFloorLabel = new JLabel("Start Floor");
    final JLabel endFloorLabel = new JLabel("End Floor");
    final JLabel enterStepNumber = new JLabel("Enter Step Number:");
    this.chooseStartFloor = new JComboBox<>();
    this.chooseEndFloor = new JComboBox<>();
    this.addRequestButton = new JButton("Add Request");
    this.addRequestButton.setVisible(true);
    this.addStepNumber = new JTextField();
    this.addStepNumber.setPreferredSize(new Dimension(40, 20));

    this.stepButton = new JButton("Step");
    this.stepButton.setVisible(true);
    JButton returnButton = new JButton("Return to Welcome Page");
    returnButton.setVisible(true);
    lowerPanel.add(startFloorLabel);
    lowerPanel.add(chooseStartFloor);
    lowerPanel.add(endFloorLabel);
    lowerPanel.add(chooseEndFloor);
    lowerPanel.add(addRequestButton);
    lowerPanel.add(enterStepNumber);
    lowerPanel.add(addStepNumber);
    lowerPanel.add(stepButton);
    lowerPanel.add(returnButton);

    buildingPage.add(upperPanel, BorderLayout.NORTH);
    buildingPage.add(midPanel, BorderLayout.CENTER);
    buildingPage.add(lowerPanel, BorderLayout.SOUTH);

    add(buildingPage, "Building Page");
    cardLayout.show(this.getContentPane(), "Building Page");
    pack();

    returnButton.addActionListener(e -> {
      buildingPage.removeAll();
      cardLayout.show(this.getContentPane(), "Welcome Page");
      pack();
    });

    switchElevatorSystemButton.addActionListener(e -> controller.switchElevatorSystem());
  }

  @Override
  public void switchElevatorSystemView(String status) {
    if ("running".equals(status)) {
      elevatorStateLabel.setText("System Status: Running; ");
      switchElevatorSystemButton.setText("Stop Elevator System");
    }
    if ("stopping".equals(status)) {
      elevatorStateLabel.setText("System Status: Stopping; ");
      switchElevatorSystemButton.setText("Not Available");
      switchElevatorSystemButton.setEnabled(false);
    }
    if ("outOfService".equals(status)) {
      elevatorStateLabel.setText("System Status: Out of Service; ");
      switchElevatorSystemButton.setText("Start Elevator System");
      switchElevatorSystemButton.setEnabled(true);
    }
    this.pack();
  }

  @Override
  public void updateElevators(ElevatorReport[] elevatorReports) {
    for (int i = 0; i < elevatorReports.length; i++) {
      ElevatorReport elevatorReport = elevatorReports[i];
      elevatorRequestsLabels[i].setText("Elevator " + (i + 1) + ": " + elevatorReport.toString());
      for (int j = 0; j < elevatorReport.getFloorRequests().length; j++) {
        if (elevatorReport.getCurrentFloor() == j) {
          elevatorLabels[j][i].setText("E" + (i + 1));
          elevatorLabels[j][i].setOpaque(true);
          elevatorLabels[j][i].setBackground(new Color(135, 206, 235));
          elevatorLabels[j][i].setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        } else {
          elevatorLabels[j][i].setText("");
          elevatorLabels[j][i].setOpaque(false);
          elevatorLabels[j][i].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }
      }
    }
    this.pack();
  }

  @Override
  public void activateRequests(int numFloors) {
    for (int i = 0; i < numFloors; i++) {
      chooseStartFloor.addItem(i);
      chooseEndFloor.addItem(i);
    }
    AtomicReference<Integer> selectedStartFloor =
        new AtomicReference<>((Integer) chooseStartFloor.getSelectedItem());
    AtomicReference<Integer> selectedEndFloor =
        new AtomicReference<>((Integer) chooseEndFloor.getSelectedItem());
    chooseStartFloor.addActionListener(e -> selectedStartFloor.set(
        (Integer) chooseStartFloor.getSelectedItem()));
    chooseEndFloor.addActionListener(
        e -> selectedEndFloor.set((Integer) chooseEndFloor.getSelectedItem()));
    addRequestButtonListener = e -> controller.addRequest(
        selectedStartFloor.get(), selectedEndFloor.get());
    addRequestButton.addActionListener(addRequestButtonListener);
    stepButtonListener = e -> {
      // add a line; default step is 1;
      String input = addStepNumber.getText();
      if (input != null && !input.isEmpty()) {
        for (int i = 0; i < Integer.parseInt(input); i++) {
          controller.step();
          addStepNumber.setText("");
        }
      } else {
        controller.step();
      }
    };
    stepButton.addActionListener(stepButtonListener);
    this.pack();
  }

  @Override
  public void addRequestView(List<Request> upRequests, List<Request> downRequests) {
    upRequestsLabel.setText("Up Requests[" + upRequests.size() + "]: " + upRequests);
    downRequestsLabel.setText("Down Requests["  + downRequests.size() + "]: " + downRequests);
  }

  @Override
  public void disableRequests() {
    chooseStartFloor.removeAllItems();
    chooseEndFloor.removeAllItems();
    addRequestButton.removeActionListener(addRequestButtonListener);
  }

  @Override
  public void disableStep() {
    stepButton.removeActionListener(stepButtonListener);
  }

  /**
   * Creates the mid JPanel.
   *
   * @param floorNumber the number of floors in the building.
   * @param elevatorNumber the number of elevators in the building.
   * @return the mid JPanel.
   */
  private JPanel getjPanel(Integer floorNumber, Integer elevatorNumber) {
    JPanel midPanel = new JPanel(new GridLayout(floorNumber + 1,
        10, 5, 5));
    midPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
    for (int i = 0; i < floorNumber; i++) {
      JLabel floorLabel = new JLabel("F" + (floorNumber - i - 1));
      floorLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
      floorLabel.setHorizontalAlignment(JLabel.CENTER);
      midPanel.add(floorLabel);
      for (int j = 0; j < elevatorNumber; j++) {
        elevatorLabels[floorNumber - i - 1][j] = new JLabel("");
        elevatorLabels[floorNumber - i - 1][j].setBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1));
        elevatorLabels[floorNumber - i - 1][j].setHorizontalAlignment(JLabel.CENTER);
        midPanel.add(elevatorLabels[floorNumber - i - 1][j]);
        if (i == floorNumber - 1) {
          elevatorLabels[floorNumber - i - 1][j].setText("E" + (j + 1));
          elevatorLabels[floorNumber - i - 1][j].setOpaque(true);
          elevatorLabels[floorNumber - i - 1][j].setBackground(new Color(135, 206, 235));
          elevatorLabels[floorNumber - i - 1][j].setBorder(
              BorderFactory.createLineBorder(Color.RED, 2));
        }
      }
      for (int j = elevatorNumber; j < 9; j++) {
        JLabel emptyLabel = new JLabel("");
        emptyLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        midPanel.add(emptyLabel);
      }
    }
    JLabel emptyLabel = new JLabel("");
    emptyLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
    midPanel.add(emptyLabel);
    for (int i = 0; i < elevatorNumber; i++) {
      JLabel elevatorLabel = new JLabel("Elevator " + (i + 1));
      elevatorLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
      elevatorLabel.setHorizontalAlignment(JLabel.CENTER);
      midPanel.add(elevatorLabel);
    }
    for (int i = elevatorNumber; i < 9; i++) {
      JLabel emptyLabel1 = new JLabel("");
      emptyLabel1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
      midPanel.add(emptyLabel1);
    }
    return midPanel;
  }

  /**
   * Creates the up JPanel.
   *
   * @param elevatorNumber the number of elevators in the building.
   * @param elevatorReports the reports for the elevators in the building.
   * @return the up JPanel.
   */
  private JPanel getUpJpanel(Integer elevatorNumber, ElevatorReport[] elevatorReports) {
    JPanel upPanel = new JPanel(new GridLayout(elevatorNumber + 2,
        1, 5, 5));
    upPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
    upRequestsLabel = new JLabel("Up Requests[0]:");
    downRequestsLabel = new JLabel("Down Requests[0]:");
    upPanel.add(upRequestsLabel);
    upPanel.add(downRequestsLabel);
    elevatorRequestsLabels = new JLabel[elevatorNumber];
    for (int i = 0; i < elevatorNumber; i++) {
      elevatorRequestsLabels[i] = new JLabel("Elevator "
          + (i + 1) + ": " + elevatorReports[i].toString());
      upPanel.add(elevatorRequestsLabels[i]);
    }
    return upPanel;
  }
}
