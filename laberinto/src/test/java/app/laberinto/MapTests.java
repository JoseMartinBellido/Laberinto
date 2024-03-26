package app.laberinto;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import app.labyrinth.controller.LabyrinthFileManager;
import app.labyrinth.model.Coordinate;
import app.labyrinth.model.Element;
import app.labyrinth.model.LabyrinthMap;
import app.labyrinth.model.MovementDirection;
import app.labyrinth.model.exceptions.MapException;

/**
 * Set of Map class tests that verifies its correct functioning
 */
class MapTests {
  
  /**
   * Path of the testing map
   */
  private static final Path TESTING_MAP_PATH = Path
      .of("./src/test/resources/mapsfolder/testing-map.txt");

  /**
   * Proves that the constructor doesn't create a map with a non-existent file.
   * It must throw a MapException
   */
  @Test
  void creatingMapErrorTest() {
    assertThrowsExactly(MapException.class, () -> new LabyrinthMap(Path.of("InventedMapFilePath.txt")));
  }

  /**
   * Proves that the constructor creates correctly a map with a existent file.
   * It must throw no exception
   */
  @Test
  void correctMapCreationTest() {
    assertDoesNotThrow(() -> new LabyrinthMap(TESTING_MAP_PATH));
  }
  
  /**
   * Checks if the size of the given map for testing is correct
   * @throws IOException In case an error occurs during the process
   */
  @Test
  void mapSizeTest() throws IOException {
    
    // Checks the read lines given the map
    assertEquals(3, LabyrinthFileManager.readMapLines(TESTING_MAP_PATH).size());
    assertEquals(11, LabyrinthFileManager.readMapLines(TESTING_MAP_PATH).get(0).length());
  }
  
  /**
   * Checks, given the coordinate of an element, if it corresponds to map's element
   * @param x Horizontal coordinate of the element
   * @param y Vertical coordinate of the element
   * @param elementStr String representation of the element
   */
  @ParameterizedTest
  @CsvFileSource(resources = "/mapsfolder/elements-positioning.csv", numLinesToSkip = 1, 
    delimiter = ',')
  void mapElementsInspectionTest(int x, int y, String elementStr) {
    LabyrinthMap map = new LabyrinthMap(TESTING_MAP_PATH);
    
    // Checks the coordinates in the csv file
    Element expected = map.getElementAtCoordinate(new Coordinate(x, y));
    assertEquals(expected, Element.valueOf(elementStr));
    
  }
  
  /**
   * Checks if the method getElementCoordinates works correctly with elements PLAYER and 
   * END
   * @param x Horizontal coordinate of the element
   * @param y Vertical coordinate of the element
   * @param elementStr String representation of the element
   */
  @ParameterizedTest
  @CsvFileSource(resources = "/mapsfolder/elements-positioning.csv", numLinesToSkip = 10, 
  delimiter = ',')
  void getElementCoordinatesTest(int x, int y, String elementStr) {
    LabyrinthMap map = new LabyrinthMap(TESTING_MAP_PATH);
    
    // Checks the element coordinates of the player and the end
    Element element = Element.valueOf(elementStr);
    
    assertEquals(new Coordinate(x, y), map.getCoordinateOfElement(element));
  }
  
  
  @Test
  void getSurroundingElementsTest() {
    LabyrinthMap map = new LabyrinthMap(TESTING_MAP_PATH);
    
    // Getting the player to look around
    Coordinate player = map.getCoordinateOfElement(Element.PLAYER);
    Map<MovementDirection, Element> surroundingElements = map.getSurroundingElements(player);
    
    // Check all the elements
    // UP
    assertEquals(Element.OBSTACLE, surroundingElements.get(MovementDirection.UP));
    assertEquals(Element.OBSTACLE, surroundingElements.get(MovementDirection.UP_RIGHT));
    assertEquals(Element.OBSTACLE, surroundingElements.get(MovementDirection.UP_LEFT));
    // DOWN
    assertEquals(Element.OBSTACLE, surroundingElements.get(MovementDirection.DOWN));
    assertEquals(Element.OBSTACLE, surroundingElements.get(MovementDirection.DOWN_LEFT));
    assertEquals(Element.OBSTACLE, surroundingElements.get(MovementDirection.DOWN_RIGHT));
    // RIGHT and LEFT
    assertEquals(Element.VOID, surroundingElements.get(MovementDirection.RIGHT));
    assertEquals(Element.TRAVELLED, surroundingElements.get(MovementDirection.LEFT));
  }
  
}
