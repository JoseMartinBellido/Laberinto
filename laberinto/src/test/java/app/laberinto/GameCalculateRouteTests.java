package app.laberinto;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

import app.labyrinth.model.Coordinate;
import app.labyrinth.model.Element;
import app.labyrinth.model.Game;

/**
 * Tests for the calculateRoute method of the class Game
 */
class GameCalculateRouteTests {

  /**
   * Path of the testing map
   */
  private static final Path TESTING_MAP_PATH = Path
      .of("./src/test/resources/mapsfolder/testing-map.txt");
  
  /**
   * Checks the calculateRoute method works properly using a testing map
   */
  @Test
  void calculateRouteTest() {
    // Prepares the game with the specified path and calculates the route
    Game game = new Game(TESTING_MAP_PATH);
    game.calculateRoute();
    
    // Check the size and the elements
    List<Coordinate> route = game.getRoute();
    Coordinate playerCoordinate = game.getMap().getCoordinateOfElement(Element.PLAYER);
    Coordinate endCoordinate = game.getMap().getCoordinateOfElement(Element.END);
    assertEquals(6, route.size());
    
    assertTrue(route.contains(playerCoordinate));
    assertTrue(route.contains(new Coordinate(5, 1)));
    assertTrue(route.contains(new Coordinate(7, 1)));
    assertTrue(route.contains(endCoordinate));

    
    
  }

}
