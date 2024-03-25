package app.laberinto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import app.labyrinth.model.Coordinate;

/**
 * Set of Coordinate class tests that verifies its correct functioning
 */
class CoordinateTests {

  /**
   * Verifies if the Coordinate equals method works correctly
   * @param x1 Horizontal position for the first coordinate
   * @param y1 Vertical position for the first coordinate
   * @param x2 Horizontal position for the second coordinate
   * @param y2 Vertical position for the second coordinate
   */
  @ParameterizedTest
  @CsvSource({
    "1, 1, 3, 3",
    "1, 5, 1, 5", 
    "8, 4, 8, 9",
    "1, 7, 5, 7"
  })
  void coordinateEqualsTest(int x1, int y1, int x2, int y2) {
    
    Coordinate c1 = new Coordinate(x1, y1);
    Coordinate c2 = new Coordinate(x2, y2);
    
    // If two both x and y are the same, the coordinates must be the same
    if (x1 == x2 && y1 == y2) {
      assertEquals(c1, c2);
    
    } else {
      assertNotEquals(c1, c2);
    }
  }
}
