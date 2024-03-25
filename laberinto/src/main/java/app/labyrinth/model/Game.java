package app.labyrinth.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Complete game which defines the strategy to follow in the labrynth
 */
public class Game {
  
  /**
   * Initial velocity of the player in the beginning of the game. It represents the time spent to 
   * travel 1 square
   */
  private static final double INITIAL_VELOCITY = 1.0;
  
  /**
   * Map used for the game
   */
  private Map map;
  
  /**
   * List of coordinates with de stablished route to follow in the labyrinth
   */
  private List<Coordinate> route;
  
  /**
   * Time spent per square in the map
   */
  private double timeSpentPerSquare;
  
  
  /**
   * Constructor of the class which sets up the map for the game
   * @param mapPath Path of the txt file that contains the map made by ascii characters
   */
  public Game(Path mapPath) {
    // Initializes the map, the route and the stating velocity
    map = new Map(mapPath);
    route = new ArrayList<>();
    timeSpentPerSquare = 0;
  }
  
  
  /**
   * Gets the total time the player spents in the labyrinth with the selected route
   * @param route List of coordinates the player will travel to reach the end of the map
   * @return The time spent by the player to reach the objective
   * 
   * @throws GameException In case the route is empty or the route is wrong
   */
  public double getTotalTimeSpent(List<Coordinate> route) {
    
    // Check if the start and the end point are in the list
    if (!route.contains(map.getElementCoordinates(Element.END)) || 
        !route.contains(map.getElementCoordinates(Element.PLAYER))) {
      throw new GameException();
    }
    
    // If the player goes straight, he will go faster so the time per square reduces 0.1 up to 0.4.
    // 3 squares traveled to start going faster
    // If he encounters a turn, the velocity will reset to 1.0
    try {
      timeSpentPerSquare = INITIAL_VELOCITY;
      double timeSpent = INITIAL_VELOCITY * 2;
      
      for (int i = 2; i < route.size(); i++) {
        modifyVelocityIfGoesStraight(i);
        
        // Adds the time spent in this square
        timeSpent += timeSpentPerSquare;
      }
      
      return timeSpent;
      
    // In case there is an error with de route
    } catch (IndexOutOfBoundsException e) {
      throw new GameException();
    }
    
  }

  /**
   * Modifies the velocity of the player if he's going straigth for 3 squares in a row, or resets it
   * in case there is a turn in the way
   * @param routePosition Position of the player to check
   * 
   * @throws IndexOutOfBoundsException In case there's a problem running the List
   */
  private void modifyVelocityIfGoesStraight(int routePosition) {
    
    Coordinate first = route.get(routePosition - 2);
    Coordinate previous = route.get(routePosition - 1);
    Coordinate current = route.get(routePosition);
    
    // Check if the player is going straight because 1 coordinate is maintained
    if ((first.x() == previous.x() && previous.x() == current.x()) || 
        (first.y() == previous.y() && previous.y() == current.y())) {
      
      // Going straight reduces up to 0.4, so timeSpentPerSquare can't be lesser than 0.6
      timeSpentPerSquare -= (timeSpentPerSquare > 0.6) ? 0.1 : 0;
    
    // Resets if there is a turn recently
    } else {
      timeSpentPerSquare = INITIAL_VELOCITY;
    }    
  }
  
}
