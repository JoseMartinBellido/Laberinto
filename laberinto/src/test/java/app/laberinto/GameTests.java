package app.laberinto;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import app.labyrinth.model.Coordinate;
import app.labyrinth.model.Element;
import app.labyrinth.model.Game;
import app.labyrinth.model.LabyrinthMap;

class GameTests {
  
  /**
   * Mocked game to execute the getTotalTimeSpentTest
   */
  private Game game;
  
  /**
   * Mocked map to execute the game tests
   */
  private LabyrinthMap map;
  
  /**
   * Determined route for the labyrinth game
   */
  private List<Coordinate> route;
  
  @BeforeEach
  void prepareRoute() {
        
    // Mocking the map class and the getElementCoordinates methods
    map = Mockito.mock(LabyrinthMap.class);
    Mockito.when(map.getElementCoordinates(Element.PLAYER))
      .thenReturn(new Coordinate(1, 1));
    
    Mockito.when(map.getElementCoordinates(Element.END))
      .thenReturn(new Coordinate(6, 6));
    
    route = new ArrayList<>();
    route.add(new Coordinate(1, 1));
    route.add(new Coordinate(1, 2));
    route.add(new Coordinate(1, 3));
    route.add(new Coordinate(1, 4));
    route.add(new Coordinate(1, 5));
    route.add(new Coordinate(1, 6));
    route.add(new Coordinate(1, 7));
    route.add(new Coordinate(1, 8));
    route.add(new Coordinate(2, 8));
    route.add(new Coordinate(3, 8));
    route.add(new Coordinate(4, 8));
    route.add(new Coordinate(5, 8));
    route.add(new Coordinate(6, 8));
    route.add(new Coordinate(6, 9));
    route.add(new Coordinate(7, 9));
    route.add(new Coordinate(7, 10));
    
    // set up the game class
    game = new Game(Path.of("./src/test/resources/mapsfolder/testing-map.txt"));
    
    game.setMap(map);
    game.setRoute(route);
    
  }
  
  @Test
  void getTotalTimeSpentTest() {

    // Calculated beforehand
    double timeExpected = 13.6;
    assertEquals(timeExpected, game.getTotalTimeSpent());
    
    
  }

}
