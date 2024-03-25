package app.labyrinth.model;

import java.nio.file.Path;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import app.labyrinth.model.exeptions.GameException;

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
   * Maximum velocity the player can achieve going straight line
   */
  private static final double MAX_VELOCITY = 0.6;
  
  /**
   * Acceleration gained while going in a straight line for 1 square
   */
  private static final double STRAIGHT_ACCELERATION = 0.1;
  
  /**
   * Map used for the game
   */
  private LabyrinthMap map;
  
  /**
   * List of coordinates with de stablished route to follow in the labyrinth
   */
  private List<Coordinate> route;
  
  /**
   * Time spent per square in the map
   */
  private double timeSpentPerSquare;
  
  /**
   * Time spent calculating the route
   */
  private long timeSpentCalculatingRoute;
  
  /**
   * Number of movements after doing a turn
   */
  private int movementsAfterATurn;
  
  
  /**
   * Constructor of the class which sets up the map for the game
   * @param mapPath Path of the txt file that contains the map made by ascii characters
   * 
   * @throws MapException In case there is a problem processing the map
   */
  public Game(Path mapPath) {
    // Initializes the map, the route and the stating velocity
    map = new LabyrinthMap(mapPath);
    route = new ArrayList<>();
    timeSpentPerSquare = 0;
  }
  
  /**
   * Gets the complete route from the beginning to the end of the labyrinth
   * @return The complete route to exit the labyrinth
   */
  public List<Coordinate> getRoute() {
    return route;
  }
  
  /**
   * Gets the total time spent while calculating the route
   * @return The total time spent while calculating the route in ns (nanoseconds)
   */
  public long getTimeSpentCalculatingRoute() {
    return timeSpentCalculatingRoute;
  }
  
  /**
   * Gets the map of the game
   * @return The map of the game
   */
  public LabyrinthMap getMap() {
    return map;
  }
  
  
  // -- Methods created only to make tests work --
  
  /**
   * Sets a new map
   * @param map Map to introduce in the game
   */
  public void setMap(LabyrinthMap map) {
    this.map = map;
  }

  /**
   * Sets a new Route to follow in the game
   * @param route Route to follow in the game
   */
  public void setRoute(List<Coordinate> route) {
    this.route = route;
  }
  
  // --  --

  /**
   * Calculates the most effective route from the beginning of the labyrinth to the end, 
   * stablishing the time required to achieve it
   */
  public void calculateRoute() {
    
    // Time at the beggining of the algorithm
    LocalTime startTime = LocalTime.now();
    
    //  ------------------------- INSERT HERE THE CODE TO RUN IN THE GAME --------------------
    
    
    
    
    //  ------------------------- END OF THE CODE TO RUN IN THE GAME -------------------------
    
    // Time at the end of the algorithm and time calculation
    LocalTime endTime = LocalTime.now();
    timeSpentCalculatingRoute = endTime.toNanoOfDay() - startTime.toNanoOfDay();
    
    System.out.println("Route calculated correctly");
    
  }
  
  /**
   * Gets the total time the player spents in the labyrinth with the selected route
   * @param route List of coordinates the player will travel to reach the end of the map
   * @return The time spent by the player to reach the objective
   * 
   * @throws GameException In case the route is empty or the route is wrong
   */
  public double getTotalTimeSpent() {
    
    // Checks that the route has the player initial coordinates and the end coordinates
    if (!route.contains(map.getElementCoordinates(Element.PLAYER)) || 
        !route.contains(map.getElementCoordinates(Element.END))) {
      throw new GameException();
    }
    
    // If the player goes straight, he will go faster so the time per square reduces 0.1 up to 0.4.
    // 2 squares traveled to start going faster. It applies in the third one
    // If he encounters a turn, the velocity will reset to 1.0
    try {
      timeSpentPerSquare = INITIAL_VELOCITY;
      double timeSpent = INITIAL_VELOCITY * 2;
      
      movementsAfterATurn = 2;
      
      for (int i = 2; i < route.size(); i++) {  
        // Add a movement. If it's a turn, it resets in the next line
        movementsAfterATurn++;
        
        modifyVelocityIfGoesStraight(i);
        
        // This method avoids precision problems
        timeSpentPerSquare = roundDoubleToOneDecimalDigit(timeSpentPerSquare);
        
        // Adds the time spent in this square
        timeSpent += timeSpentPerSquare;
        timeSpent = roundDoubleToOneDecimalDigit(timeSpent);
      }
      
      System.out.println("Total time spent calculated correctly");
      
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
    
    // Check if the player makes a turn
    if ((first.x() == previous.x() && previous.x() == current.x()) || 
        (first.y() == previous.y() && previous.y() == current.y())) {
            
      if (movementsAfterATurn > 2) {
        // Going straight reduces up to 0.4, so timeSpentPerSquare can't be lesser than 0.6
        timeSpentPerSquare -= (timeSpentPerSquare > MAX_VELOCITY) ? STRAIGHT_ACCELERATION : 0;
      }
    
    // Resets if there is a turn recently
    } else {
      timeSpentPerSquare = INITIAL_VELOCITY;
      movementsAfterATurn = 1;
    }    
  }
  
  /**
   * Rounds a double number to have only 1 decimal digit
   * @param number Number to round
   */
  private double roundDoubleToOneDecimalDigit(double number) {
    return Math.round(number * 10) / 10.0;
  }
  
}
