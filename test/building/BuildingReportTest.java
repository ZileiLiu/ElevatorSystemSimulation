package building;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import building.enums.Direction;
import building.enums.ElevatorSystemStatus;
import elevator.ElevatorReport;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import scanerzus.Request;

/**
 * This class is used to test the BuildingReport class.
 */
public class BuildingReportTest {
  BuildingReport report;

  /**
   * This method is used to set up the tests.
   */
  @Before
  public void setUp() {
    final ElevatorReport elevatorReport1 = new ElevatorReport(
        1,  // elevatorId
        1,  // currentFloor
        Direction.STOPPED,  // direction
        true,  // doorClosed
        new boolean[]{false, true, true},  // floorRequests
        0,  // doorOpenTimer
        0,  // endWaitTimer
        false,  // outOfService
        false  // isTakingRequests
    );
    final ElevatorReport elevatorReport2 = new ElevatorReport(
        2,  // elevatorId
        2,  // currentFloor
        Direction.UP,  // direction
        false,  // doorClosed
        new boolean[]{true, false, true},  // floorRequests
        1,  // doorOpenTimer
        1,  // endWaitTimer
        true,  // outOfService
        true  // isTakingRequests
    );
    List<Request> upRequest = new ArrayList<>();
    upRequest.add(new Request(0, 1));
    upRequest.add(new Request(0, 2));
    upRequest.add(new Request(0, 3));
    upRequest.add(new Request(5, 6));
    upRequest.add(new Request(5, 9));

    List<Request> downRequest = new ArrayList<>();
    downRequest.add(new Request(1, 0));
    downRequest.add(new Request(8, 5));
    downRequest.add(new Request(7, 3));
    downRequest.add(new Request(9, 6));
    report = new BuildingReport(
        10,  // numFloors
        5,  // numElevators
        10,  // elevatorCapacity
        new ElevatorReport[]{elevatorReport1, elevatorReport2},  // elevatorsReports
        upRequest,  // upRequests
        downRequest,  // downRequests
        ElevatorSystemStatus.stopping  // systemStatus
    );
  }

  /**
   * This method is used to test the getNumFloors method.
   */
  @Test
  public void getNumFloors() {
    assertEquals(10, report.getNumFloors());
  }

  /**
   * This method is used to test the getNumElevators method.
   */
  @Test
  public void getNumElevators() {
    assertEquals(5, report.getNumElevators());
  }

  /**
   * This method is used to test the getElevatorCapacity method.
   */
  @Test
  public void getElevatorCapacity() {
    assertEquals(10, report.getElevatorCapacity());
  }

  /**
   * This method is used to test the getElevatorReports method.
   */
  @Test
  public void getElevatorReports() {
    assertEquals(2, report.getElevatorReports().length);
    assertEquals(1, report.getElevatorReports()[0].getElevatorId());
    assertEquals(2, report.getElevatorReports()[1].getElevatorId());
    assertEquals(1, report.getElevatorReports()[0].getCurrentFloor());
    assertEquals(2, report.getElevatorReports()[1].getCurrentFloor());
    assertEquals(Direction.STOPPED, report.getElevatorReports()[0].getDirection());
    assertEquals(Direction.UP, report.getElevatorReports()[1].getDirection());
    assertTrue(report.getElevatorReports()[0].isDoorClosed());
    assertFalse(report.getElevatorReports()[1].isDoorClosed());
    assertArrayEquals(new boolean[]{false, true, true},
        report.getElevatorReports()[0].getFloorRequests());
    assertArrayEquals(new boolean[]{true, false, true},
        report.getElevatorReports()[1].getFloorRequests());
    assertEquals(0, report.getElevatorReports()[0].getDoorOpenTimer());
    assertEquals(1, report.getElevatorReports()[1].getDoorOpenTimer());
    assertEquals(0, report.getElevatorReports()[0].getEndWaitTimer());
    assertEquals(1, report.getElevatorReports()[1].getEndWaitTimer());
    assertFalse(report.getElevatorReports()[0].isOutOfService());
    assertTrue(report.getElevatorReports()[1].isOutOfService());
    assertFalse(report.getElevatorReports()[0].isTakingRequests());
    assertTrue(report.getElevatorReports()[1].isTakingRequests());
  }

  /**
   * This method is used to test the getUpRequests method.
   */
  @Test
  public void getUpRequests() {
    assertEquals(5, report.getUpRequests().size());
    assertEquals(0, report.getUpRequests().get(0).getStartFloor());
    assertEquals(1, report.getUpRequests().get(0).getEndFloor());
    assertEquals(0, report.getUpRequests().get(1).getStartFloor());
    assertEquals(2, report.getUpRequests().get(1).getEndFloor());
    assertEquals(0, report.getUpRequests().get(2).getStartFloor());
    assertEquals(3, report.getUpRequests().get(2).getEndFloor());
    assertEquals(5, report.getUpRequests().get(3).getStartFloor());
    assertEquals(6, report.getUpRequests().get(3).getEndFloor());
    assertEquals(5, report.getUpRequests().get(4).getStartFloor());
    assertEquals(9, report.getUpRequests().get(4).getEndFloor());
  }

  /**
   * This method is used to test the getDownRequests method.
   */
  @Test
  public void getDownRequests() {
    assertEquals(4, report.getDownRequests().size());
    assertEquals(1, report.getDownRequests().get(0).getStartFloor());
    assertEquals(0, report.getDownRequests().get(0).getEndFloor());
    assertEquals(8, report.getDownRequests().get(1).getStartFloor());
    assertEquals(5, report.getDownRequests().get(1).getEndFloor());
    assertEquals(7, report.getDownRequests().get(2).getStartFloor());
    assertEquals(3, report.getDownRequests().get(2).getEndFloor());
    assertEquals(9, report.getDownRequests().get(3).getStartFloor());
    assertEquals(6, report.getDownRequests().get(3).getEndFloor());
  }

  /**
   * This method is used to test the getSystemStatus method.
   */
  @Test
  public void testGetSystemStatus() {
    assertEquals(ElevatorSystemStatus.stopping, report.getSystemStatus());
  }

  /**
   * This method is used to test the generateLog method.
   */
  @Test
  public void testGenerateLog() {
    report.generateLog();
  }
}
