package app.labyrinth.model;

import java.nio.file.Path;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.labyrinth.model.exceptions.GameException;
import app.labyrinth.model.utils.MathUtils;

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
   * Acceleration gained while going in a straight line for 1 square.
   */
  private static final double STRAIGHT_ACCELERATION = 0.1;
  
  /**
   * Map used for the game
   */
  private LabyrinthMap map;
  
  /**
   * Final list of coordinates with de stablished route to follow in the labyrinth
   */
  private List<Coordinate> route;
  
  /**
   * Time spent per square in the map. It will start at the INITIAL_VELOCITY
   */
  private double timeSpentPerSquare;
  
  /**
   * Time spent calculating the route
   */
  private long timeSpentCalculatingRoute;
  
  /**
   * Number of movements after doing a turn. It will count the number of squares in the same line
   * to apply the acceleration in the formula.
   */
  private int movementsAfterATurn;
  
  /**
   * Players coordinates in the game
   */
  private Coordinate playerCoordinate;
  
  
  // ------------------------- Personal implementation -------------------------
  
  /**
   * Map of intersection coordinates associated with a list of unexplored ways found in the
   * labyrinth.
   * For example, if the intersection connects with 3 different ways, 1 of them already explored, 
   * the 2 remaining coordinates to go will be kept in the associated coordinate list
   */
  private Map<Coordinate, List<Coordinate>> intersections;
  
  /**
   * List of visited coordinates that are not intersections the player is not going 
   * to revisit in order to find the end of the labyrinth
   */
  private List<Coordinate> recognition;
  
  // ---------------------------------------------------------------------------
  
  /**
   * Constructor of the class which sets up the map for the game
   * @param mapPath Path of the txt file that contains the map made by ascii characters
   * 
   * @throws MapException In case there is a problem processing the map
   */
  public Game(Path mapPath) {
    // Initializes the map, the routes (final route and recognition route) and the starting velocity
    map = new LabyrinthMap(mapPath);
    route = new ArrayList<>();
    recognition = new ArrayList<>();
    timeSpentPerSquare = 0;
  }
  
  /**
   * Gets the complete route from the beginning to the end of the maze
   * @return The complete route to exit the maze
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
  
  
  // -- Methods created only to make tests work. Not a good practice but helpful to check functioning --
  
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
   * Gets the total time the player spents in the labyrinth with the selected route
   * @param route List of coordinates the player will travel to reach the end of the map
   * @return The time spent by the player to reach the objective
   * 
   * @throws GameException In case the route is empty/wrong
   */
  public double getTotalTimeSpent() {
    
    // Checks that the route has the player initial coordinates and the end coordinates
    if (!route.contains(map.getCoordinateOfElement(Element.PLAYER)) || 
        !route.contains(map.getCoordinateOfElement(Element.END))) {
      throw new GameException();
    }
    
    // If the player goes straight, he will go faster so the time per square reduces 0.1 up to 0.4.
    // 2 squares traveled to start going faster. It applies in the third one
    // If he makes a turn, the velocity will reset to 1.0
    try {
      // Set up the variables. Obviously, the first 2 travelled squares are in the same line
      timeSpentPerSquare = INITIAL_VELOCITY;
      double timeSpent = INITIAL_VELOCITY * 2;
      
      movementsAfterATurn = 2;
      
      for (int i = 2; i < route.size(); i++) {  
        // Add a movement. If it's a turn, it resets in the next line
        movementsAfterATurn++;
        
        modifyVelocityIfGoesStraight(i);
        
        // This method avoids precision problems
        timeSpentPerSquare = MathUtils.roundDoubleToOneDecimalDigit(timeSpentPerSquare);
        
        // Adds the time spent in this square and rounds it to 1 decimal digit
        timeSpent += timeSpentPerSquare;
        timeSpent = MathUtils.roundDoubleToOneDecimalDigit(timeSpent);
      }
      
      System.out.println("Total time spent calculated correctly");
      
      return timeSpent;
      
    // In case there is an error with de route
    } catch (IndexOutOfBoundsException e) {
      throw new GameException();
    }
    
  }
  
  /**
   * Calculates the most effective route from the beginning of the labyrinth to the end, 
   * stablishing the time required to achieve it
   */
  public void calculateRoute() {
    
    // Time at the beggining of the algorithm
    LocalTime startTime = LocalTime.now();
    
    //  ------------------------- INSERT HERE THE CODE TO RUN IN THE GAME --------------------
    
    // My personal strategy is: try to go straight way. if it's possible, go.
    // If it's not, find the way to go as directly as possible calculating the squares of distance
    
    playerCoordinate = map.getCoordinateOfElement(Element.PLAYER);
    Coordinate endCoordinate = map.getCoordinateOfElement(Element.END);
    
    // Map of intersections in case it's needed to go backwards because a dead end is found.
    intersections = new HashMap<>();
    
    // The route starts with the player coordinates in the beginning
    route.add(playerCoordinate);
    recognition.add(playerCoordinate);
    
    while (!route.contains(endCoordinate)) {
      moveEfficiently();  
    }
    
    //  ------------------------- END OF THE CODE TO RUN IN THE GAME -------------------------
    
    // Time at the end of the algorithm and time calculation
    LocalTime endTime = LocalTime.now();
    timeSpentCalculatingRoute = endTime.toNanoOfDay() - startTime.toNanoOfDay();
    
    System.out.println("Route calculated correctly");
    
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
    
    // Check if the player doesn't make a turn
    if ((first.x() == previous.x() && previous.x() == current.x()) || 
        (first.y() == previous.y() && previous.y() == current.y())) {
            
      if (movementsAfterATurn > 2) {
        // Going straight reduces up to 0.4, so timeSpentPerSquare can't be lesser than 0.6 (MAX)
        timeSpentPerSquare -= (timeSpentPerSquare > MAX_VELOCITY) ? STRAIGHT_ACCELERATION : 0;
      }
    
    // Resets if the player makes a turn
    } else {
      timeSpentPerSquare = INITIAL_VELOCITY;
      movementsAfterATurn = 1;
    }    
  }
  
  // ------------------------- Personal implementation -------------------------
  
  /**
   * Moves the player as efficiently as possible through the labyrinth
   */
  private void moveEfficiently() {
    
    // Checks the possible coordinates to go for the player
    List<Coordinate> coordinatesToGo = getPossibleCoordinatesToGo();
    
    // Check if it's the end of the labyrinth
    if (coordinatesToGo.contains(map.getCoordinateOfElement(Element.END))) {
      route.add(map.getCoordinateOfElement(Element.END));
    
    } else {
      // If it's an unsaved intersection, adds it to the map with all the possible unexplored ways
      if (coordinatesToGo.size() > 1 && !intersections.keySet().contains(playerCoordinate)) {
        intersections.put(playerCoordinate, coordinatesToGo);
      }
      
      // Determine which one to choose if the list is not empty and adds it to the route lists
      if (!coordinatesToGo.isEmpty()) {
        playerCoordinate = getOptimalWay(coordinatesToGo);
        route.add(playerCoordinate);
        recognition.add(playerCoordinate);
      
      // If the list is empty, the player gets back to the last intersection
      } else {
        // The player's coordinates will reset to the last intersection and repeats the process
        // until a way to move on is found
        playerCoordinate = moveToLastIntersection();
        moveEfficiently();
      }
    }
  }

  /**
   * Gets the possible coordinates to go for the player
   * @return A list with the possible coordinates to go. It could be an empty list if there is no way
   * to go without going back
   */
  private List<Coordinate> getPossibleCoordinatesToGo() {
    List<Coordinate> coordinatesToGo = new ArrayList<>();
    
    // Check the surrounding coordinates to go
    Map<MovementDirection, Element> surroundingElements = map.getSurroundingElements(playerCoordinate);
    
    // Check for a non-Obstacle direction to move on where the player has not been
    coordinatesToGo.addAll(surroundingElements.keySet()
        .stream()
        // Filters by no obstacle
        .filter(direction -> !surroundingElements.get(direction).equals(Element.OBSTACLE))
        // Maps to the coordinates asociated
        .map(direction -> new Coordinate(playerCoordinate.x() + direction.getXMovement(), 
            playerCoordinate.y() + direction.getYMovement()))
        // Filters -> not have passed through this way
        .filter(coordinate -> !recognition.contains(coordinate))
        .toList());
    
    return coordinatesToGo;
  }
  
  /**
   * Gets the optimal coordinates to proceed
   * @param coordinatesToGo List of coordinates on which to decide the most optimal to reach the end
   * of the labyrinth. It can't be empty 
   * @return The optimal coordinate to proceed in the map
   */
  private Coordinate getOptimalWay(List<Coordinate> coordinatesToGo) {
    
    // Gets the a distances list to the end of the labyrinth from the coordinate candidates to go
    List<Integer> distances = coordinatesToGo.stream()
        .map(this::getDirectDistanceToEnd)
        .toList();
    
    // Gets the minimum distance and, with that, checks the closest coordinate to go
    int minDistance = distances.stream()
        .reduce(distances.get(0), (num1, num2) -> (num1 < num2) ? num1 : num2);
        
    return coordinatesToGo.get(distances.indexOf(minDistance));    
  }
  
  /**
   * Gets to the last intersection.
   * Updates the intersections map (discarding this path) and the final route
   * @return The last intersection coordinate
   */
  private Coordinate moveToLastIntersection() {
    
    // Search in the route the last intersection starting from the end of the route
    int routeSize = route.size();
    for (int i = routeSize - 2; i > 0; i--) {
      if (intersections.keySet().contains(route.get(i))) {
        
        // Get the last intersection and the first coordinate to the dead end way
        Coordinate lastIntersection = route.get(i);
        Coordinate deadEnd = route.get(i + 1);
        
        // Update the intersections map, knowing this way leads to dead end
        List<Coordinate> possibleWaysFromLastIntersection = intersections.get(lastIntersection);
        possibleWaysFromLastIntersection.remove(deadEnd);
        
        intersections.put(lastIntersection, possibleWaysFromLastIntersection);
        
        // Update the route, removing all the way from the intersection (not included)
        List<Coordinate> deadEndWay = route.subList(i + 1, routeSize);
        route.removeAll(deadEndWay);
        
        return lastIntersection;
      }
    }
    
    // If there is no intersection in the way back, it means there is no possible result of winning
    throw new GameException();
  }
  
  /**
   * Gets the distance going straight way from the given coordinate to the end's coordinate
   * @param coordinate Coordinate of the labyrinth to go from
   * @param endCoordinate End's coordinate in the labyrinth
   * @return The quantity of squares between the two both elements if it would be able to go in 
   * a straight line
   */
  private int getDirectDistanceToEnd(Coordinate coordinate) {
    // Check the end's coordinate
    Coordinate endCoordinate = map.getCoordinateOfElement(Element.END);
    
    // distance = ||x2| - |x1|| + ||y2| - |y1||
    return Math.abs(Math.abs(endCoordinate.y()) - Math.abs(coordinate.y())) 
        + Math.abs(Math.abs(endCoordinate.x()) - Math.abs(coordinate.x()));
  }

  // ---------------------------------------------------------------------------
  
}
