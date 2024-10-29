package building;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import building.enums.ElevatorSystemStatus;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import scanerzus.Request;

/**
 * This class is used to test the Building class.
 */
public class BuildingTest {
  BuildingInterface building;

  /**
   * This method is used to set up the tests.
   */
  @Before
  public void setUpClass() {
    building = new Building(10, 2, 5);
  }

  /**
   * This method is used to test the constructor
   * will throw an IllegalArgumentException
   * if the number of floors is less than 3.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBuildingConstructorInvalidFloors() {
    new Building(2, 2, 5);
  }

  /**
   * This method is used to test the constructor
   * will throw an IllegalArgumentException
   * if the number of floors is greater than 30.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBuildingConstructorInvalidFloors2() {
    new Building(31, 2, 5);
  }

  /**
   * This method is used to test the constructor
   * will throw an IllegalArgumentException
   * if the number of elevators is less than 1.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBuildingConstructorInvalidElevators() {
    new Building(3, 0, 5);
  }

  /**
   * This method is used to test the constructor
   * will throw an IllegalArgumentException
   * if the elevator capacity is less than 3.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBuildingConstructorInvalidCapacity() {
    new Building(3, 2, 2);
  }

  /**
   * This method is used to test the constructor
   * will throw an IllegalArgumentException
   * if the elevator capacity is greater than 20.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBuildingConstructorInvalidCapacity2() {
    new Building(3, 2, 21);
  }

  /**
   * This method is used to test the constructor.
   */
  @Test
  public void testConstructor() {
    assertEquals(10, building.getElevatorSystemStatus().getNumFloors());
    assertEquals(2, building.getElevatorSystemStatus().getNumElevators());
    assertEquals(5, building.getElevatorSystemStatus().getElevatorCapacity());
    assertEquals(ElevatorSystemStatus.outOfService,
        building.getElevatorSystemStatus().getSystemStatus());
    assertEquals(new ArrayList<>(), building.getElevatorSystemStatus().getUpRequests());
    assertEquals(new ArrayList<>(), building.getElevatorSystemStatus().getDownRequests());
    assertEquals(2, building.getElevatorSystemStatus().getElevatorReports().length);
    assertEquals("Out of Service[Floor 0]",
        building.getElevatorSystemStatus().getElevatorReports()[0].toString());
    assertEquals("Out of Service[Floor 0]",
        building.getElevatorSystemStatus().getElevatorReports()[1].toString());
    assertTrue(building.getElevatorSystemStatus().getElevatorReports()[0].isOutOfService());
    assertTrue(building.getElevatorSystemStatus().getElevatorReports()[0].isDoorClosed());
    assertFalse(building.getElevatorSystemStatus().getElevatorReports()[0].isTakingRequests());
    assertEquals(0, building.getElevatorSystemStatus().getElevatorReports()[0].getCurrentFloor());
    assertEquals(0, building.getElevatorSystemStatus().getElevatorReports()[0].getDoorOpenTimer());
    assertEquals(0, building.getElevatorSystemStatus().getElevatorReports()[0].getEndWaitTimer());
    assertEquals(10,
        building.getElevatorSystemStatus().getElevatorReports()[0].getFloorRequests().length);
    assertEquals("-",
        building.getElevatorSystemStatus().getElevatorReports()[0].getDirection().toString());
    assertTrue(building.getElevatorSystemStatus().getElevatorReports()[1].isOutOfService());
    assertTrue(building.getElevatorSystemStatus().getElevatorReports()[1].isDoorClosed());
    assertFalse(building.getElevatorSystemStatus().getElevatorReports()[1].isTakingRequests());
    assertEquals(0, building.getElevatorSystemStatus().getElevatorReports()[1].getCurrentFloor());
    assertEquals(0, building.getElevatorSystemStatus().getElevatorReports()[1].getDoorOpenTimer());
    assertEquals(0, building.getElevatorSystemStatus().getElevatorReports()[1].getEndWaitTimer());
    assertEquals(10,
        building.getElevatorSystemStatus().getElevatorReports()[1].getFloorRequests().length);
    assertEquals("-",
        building.getElevatorSystemStatus().getElevatorReports()[1].getDirection().toString());
  }

  /**
   * This method is used to test the startElevatorSystem method.
   */
  @Test
  public void testStartElevatorSystem() {
    assertTrue(building.startElevatorSystem());
    assertEquals(ElevatorSystemStatus.running,
        building.getElevatorSystemStatus().getSystemStatus());
    assertFalse(building.startElevatorSystem());
    assertEquals("Waiting[Floor 0, Time 5]",
        building.getElevatorSystemStatus().getElevatorReports()[0].toString());
    assertEquals("Waiting[Floor 0, Time 5]",
        building.getElevatorSystemStatus().getElevatorReports()[1].toString());
    assertTrue(building.getElevatorSystemStatus().getElevatorReports()[0].isTakingRequests());
    assertTrue(building.getElevatorSystemStatus().getElevatorReports()[1].isTakingRequests());
    assertTrue(building.getElevatorSystemStatus().getElevatorReports()[0].isDoorClosed());
    assertTrue(building.getElevatorSystemStatus().getElevatorReports()[1].isDoorClosed());
    assertFalse(building.getElevatorSystemStatus().getElevatorReports()[0].isOutOfService());
    assertFalse(building.getElevatorSystemStatus().getElevatorReports()[1].isOutOfService());
    assertEquals(0, building.getElevatorSystemStatus().getElevatorReports()[0].getDoorOpenTimer());
    assertEquals(0, building.getElevatorSystemStatus().getElevatorReports()[1].getDoorOpenTimer());
    assertEquals(5, building.getElevatorSystemStatus().getElevatorReports()[0].getEndWaitTimer());
    assertEquals(5, building.getElevatorSystemStatus().getElevatorReports()[1].getEndWaitTimer());
  }

  /**
   * This method is used to test the startElevatorSystem method
   * will throw an IllegalStateException if the system is stopping.
   */
  @Test(expected = IllegalStateException.class)
  public void testStartElevatorSystemInvalid() {
    building.stopElevatorSystem();
    building.startElevatorSystem();
  }

  /**
   * This method is used to test the stop elevator system method.
   */
  @Test
  public void testStopElevatorSystem() {
    assertTrue(building.startElevatorSystem());
    building.stopElevatorSystem();
    assertEquals(ElevatorSystemStatus.stopping,
        building.getElevatorSystemStatus().getSystemStatus());
    assertEquals("Out of Service[Floor 0]",
        building.getElevatorSystemStatus().getElevatorReports()[0].toString());
    assertEquals("Out of Service[Floor 0]",
        building.getElevatorSystemStatus().getElevatorReports()[1].toString());
    assertFalse(building.getElevatorSystemStatus().getElevatorReports()[0].isTakingRequests());
    assertFalse(building.getElevatorSystemStatus().getElevatorReports()[1].isTakingRequests());
    assertTrue(building.getElevatorSystemStatus().getElevatorReports()[0].isDoorClosed());
    assertTrue(building.getElevatorSystemStatus().getElevatorReports()[1].isDoorClosed());
    assertTrue(building.getElevatorSystemStatus().getElevatorReports()[0].isOutOfService());
    assertTrue(building.getElevatorSystemStatus().getElevatorReports()[1].isOutOfService());
    assertEquals(0, building.getElevatorSystemStatus().getElevatorReports()[0].getDoorOpenTimer());
    assertEquals(0, building.getElevatorSystemStatus().getElevatorReports()[1].getDoorOpenTimer());
    assertEquals(0, building.getElevatorSystemStatus().getElevatorReports()[0].getEndWaitTimer());
    assertEquals(0, building.getElevatorSystemStatus().getElevatorReports()[1].getEndWaitTimer());
  }

  /**
   * This method is used to test the stop elevator system method.
   */
  @Test
  public void testAddRequest() {
    assertTrue(building.startElevatorSystem());
    assertTrue(building.addRequest(new Request(8, 2)));
    assertEquals(0, building.getElevatorSystemStatus().getUpRequests().size());
    assertEquals(1, building.getElevatorSystemStatus().getDownRequests().size());
    assertEquals(8, building.getElevatorSystemStatus().getDownRequests().get(0).getStartFloor());
    assertEquals(2, building.getElevatorSystemStatus().getDownRequests().get(0).getEndFloor());
    assertFalse(building.addRequest(new Request(2, 2)));
    assertFalse(building.addRequest(new Request(8, 11)));
    assertFalse(building.addRequest(new Request(8, -2)));
    assertFalse(building.addRequest(new Request(-8, 2)));
    assertFalse(building.addRequest(new Request(11, 2)));
    assertTrue(building.addRequest(new Request(1, 7)));
    assertTrue(building.addRequest(new Request(1, 2)));
    assertTrue(building.addRequest(new Request(3, 8)));
    assertEquals(3, building.getElevatorSystemStatus().getUpRequests().size());
    assertEquals(1, building.getElevatorSystemStatus().getDownRequests().size());
    assertEquals(1, building.getElevatorSystemStatus().getUpRequests().get(0).getStartFloor());
    assertEquals(7, building.getElevatorSystemStatus().getUpRequests().get(0).getEndFloor());
    assertEquals(1, building.getElevatorSystemStatus().getUpRequests().get(1).getStartFloor());
    assertEquals(2, building.getElevatorSystemStatus().getUpRequests().get(1).getEndFloor());
    assertEquals(3, building.getElevatorSystemStatus().getUpRequests().get(2).getStartFloor());
    assertEquals(8, building.getElevatorSystemStatus().getUpRequests().get(2).getEndFloor());
  }

  /**
   * This method is used to test the addRequest method
   * will throw an IllegalStateException if the system is out of service.
   */
  @Test(expected = IllegalStateException.class)
  public void testAddRequestInvalid() {
    assertEquals(ElevatorSystemStatus.outOfService,
        building.getElevatorSystemStatus().getSystemStatus());
    building.addRequest(new Request(1, 2));
  }

  /**
   * This method is used to test the step method.
   */
  @Test
  public void testStep() {
    assertTrue(building.startElevatorSystem());
    building.addRequest(new Request(1, 2));
    building.addRequest(new Request(4, 2));
    building.addRequest(new Request(3, 1));
    building.step();
    assertEquals("[1|^|C  ]< --  1  2 -- -- -- -- -- -- -->",
        building.getElevatorSystemStatus().getElevatorReports()[0].toString());
    assertEquals("Waiting[Floor 0, Time 4]",
        building.getElevatorSystemStatus().getElevatorReports()[1].toString());
    assertEquals(0, building.getElevatorSystemStatus().getUpRequests().size());
    assertEquals(2, building.getElevatorSystemStatus().getDownRequests().size());
    building.step();
    assertEquals("[1|^|O 3]< -- --  2 -- -- -- -- -- -- -->",
        building.getElevatorSystemStatus().getElevatorReports()[0].toString());
    assertEquals("Waiting[Floor 0, Time 3]",
        building.getElevatorSystemStatus().getElevatorReports()[1].toString());
    building.step();
    assertEquals("[1|^|O 2]< -- --  2 -- -- -- -- -- -- -->",
        building.getElevatorSystemStatus().getElevatorReports()[0].toString());
    assertEquals("Waiting[Floor 0, Time 2]",
        building.getElevatorSystemStatus().getElevatorReports()[1].toString());
    building.step();
    assertEquals("[1|^|O 1]< -- --  2 -- -- -- -- -- -- -->",
        building.getElevatorSystemStatus().getElevatorReports()[0].toString());
    assertEquals("Waiting[Floor 0, Time 1]",
        building.getElevatorSystemStatus().getElevatorReports()[1].toString());
    building.step();
    assertEquals("[1|^|C  ]< -- --  2 -- -- -- -- -- -- -->",
        building.getElevatorSystemStatus().getElevatorReports()[0].toString());
    assertEquals("[0|^|C  ]< -- -- -- -- -- -- -- -- -- -->",
        building.getElevatorSystemStatus().getElevatorReports()[1].toString());
    building.step();
    assertEquals("[2|^|C  ]< -- --  2 -- -- -- -- -- -- -->",
        building.getElevatorSystemStatus().getElevatorReports()[0].toString());
    assertEquals("[1|^|C  ]< -- -- -- -- -- -- -- -- -- -->",
        building.getElevatorSystemStatus().getElevatorReports()[1].toString());
    building.step();
    assertEquals("[2|^|O 3]< -- -- -- -- -- -- -- -- -- -->",
        building.getElevatorSystemStatus().getElevatorReports()[0].toString());
    building.step();
    assertEquals("[2|^|O 2]< -- -- -- -- -- -- -- -- -- -->",
        building.getElevatorSystemStatus().getElevatorReports()[0].toString());
    building.step();
    assertEquals("[2|^|O 1]< -- -- -- -- -- -- -- -- -- -->",
        building.getElevatorSystemStatus().getElevatorReports()[0].toString());
    building.step();
    assertEquals("[2|^|C  ]< -- -- -- -- -- -- -- -- -- -->",
        building.getElevatorSystemStatus().getElevatorReports()[0].toString());
    building.stopElevatorSystem();
    assertEquals(ElevatorSystemStatus.stopping,
        building.getElevatorSystemStatus().getSystemStatus());
    building.step();
    assertEquals("[1|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        building.getElevatorSystemStatus().getElevatorReports()[0].toString());
    assertEquals("[4|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        building.getElevatorSystemStatus().getElevatorReports()[1].toString());
    for (int i = 0; i < 5; i++) {
      building.step();
    }
    assertEquals(ElevatorSystemStatus.outOfService,
        building.getElevatorSystemStatus().getSystemStatus());
    assertFalse(building.getElevatorSystemStatus().getElevatorReports()[0].isDoorClosed());
    building.startElevatorSystem();
    building.addRequest(new Request(5, 3));
    building.addRequest(new Request(4, 1));
    for (int i = 0; i < 16; i++) {
      building.step();
    }
    assertEquals("[8|v|C  ]< --  1 --  3  4  5 -- -- -- -->",
        building.getElevatorSystemStatus().getElevatorReports()[0].toString());
    assertEquals(0, building.getElevatorSystemStatus().getDownRequests().size());
  }

  /**
   * This method is used to test the stopElevator method.
   */
  @Test
  public void testStopElevator() {
    assertTrue(building.startElevatorSystem());
    building.stopElevator(0);
    assertEquals("Out of Service[Floor 0]",
        building.getElevatorSystemStatus().getElevatorReports()[0].toString());
    assertEquals("Waiting[Floor 0, Time 5]",
        building.getElevatorSystemStatus().getElevatorReports()[1].toString());
    building.stopElevator(1);
    assertEquals("Out of Service[Floor 0]",
        building.getElevatorSystemStatus().getElevatorReports()[1].toString());
  }

  @Test
  public void testOutCapacity() {
    assertTrue(building.startElevatorSystem());
    for (int i = 0; i < 20; i++) {
      building.addRequest(new Request(1, 2));
    }
    for (int i = 0; i < 20; i++) {
      building.addRequest(new Request(2, 1));
    }
    building.step();
    assertEquals("[1|^|C  ]< --  1  2 -- -- -- -- -- -- -->",
        building.getElevatorSystemStatus().getElevatorReports()[0].toString());
    assertEquals("[1|^|C  ]< --  1  2 -- -- -- -- -- -- -->",
        building.getElevatorSystemStatus().getElevatorReports()[1].toString());
    assertEquals(10, building.getElevatorSystemStatus().getUpRequests().size());
    assertEquals(20, building.getElevatorSystemStatus().getDownRequests().size());
  }
}
