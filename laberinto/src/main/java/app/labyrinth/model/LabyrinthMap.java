package app.labyrinth.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import app.labyrinth.controller.LabyrinthFileManager;
import app.labyrinth.model.exceptions.MapException;

/**
 * Map where the game is played. Basically, it consists in a labyrinth bordered and composed by walls, 
 * a player and the end (objective to reach)
 */
public class LabyrinthMap {
  
  /**
   * All the directions the player can possibly move in the labyrinth
   */
  private static final MovementDirection[] directions = {MovementDirection.UP, 
      MovementDirection.UP_LEFT, MovementDirection.UP_RIGHT, MovementDirection.RIGHT, 
      MovementDirection.LEFT, MovementDirection.DOWN, MovementDirection.DOWN_LEFT,  
      MovementDirection.DOWN_RIGHT};
  
  /**
   * The map represented by a bidimensional array
   */
  private Element[][] mapArray;
  
  
  /**
   * Constructor of the class. Creates a map using the path of a txt file with a labyrinth made by
   * ascii characters
   * @param mapPath Path of the txt file that contains the maze
   * 
   * @throws MapException In case there is a problem processing the map
   */
  public LabyrinthMap(Path mapPath) {
    
    // Sets the map using the txt file path
    setInitialMap(mapPath);
    
    // Send an ok message
    System.out.println("Map created correctly");
  }
  
  
  /**
   * Gets the labyrinth map array
   * @return The labyrinth map array
   */
  public Element[][] getMapArray() {
    return mapArray;
  }

  /**
   * Sets a labyrinth map array
   * @param mapArray Game map array to set
   */
  public void setMapArray(Element[][] mapArray) {
    this.mapArray = mapArray;
  }


  /**
   * Gets the element's position in the map (player or end coordinates)
   * @param searchedElement Element whose position is required. It should be the player or the end of
   * the map, given that they are the only two unique elements of the map 
   * @return The coordinates of the player if it's found
   * 
   * @throws MapException If the element is not found in the map or an element different from 
   * the player or the end is tried to be found
   */
  public Coordinate getCoordinateOfElement(Element searchedElement) {
    
    // The only permitted elements to be searched are the player and the end
    if (!searchedElement.equals(Element.END) && !searchedElement.equals(Element.PLAYER)) {
      throw new MapException();
    }
    
    // Gets through the array to find and retrieve the coordinates
    for (int i = 0; i < mapArray.length; i++) {
      Element[] row = mapArray[i];
      for (int j = 0; j < row.length; j++) {
        // If the element is found
        if (row[j].equals(searchedElement)){
          return new Coordinate(j, i);
        }
      }
    }
    // It should never get here. There is always a player and an end in the game
    throw new MapException();
  }
  
  /**
   * Gets the element of the map allocated on the given coordinate
   * @param coordinate Coordinate to check the element in
   * @return The element allocated on the given coordinate
   */
  public Element getElementAtCoordinate(Coordinate coordinate) {
    return mapArray[coordinate.y()][coordinate.x()];
  }
  
  /**
   * Gets all the surrounding elements of a given coordinate. 
   * @param coordinate Coordinate to look elements from around
   * @return A map which key is the movement direction and the value the element asociated
   */
  public Map<MovementDirection, Element> getSurroundingElements(Coordinate coordinate) {
    
    // It's not possible to throw an out of bounds exception, given that this method will be 
    // called only from the player to move and the maze is always bordered
    
    Map<MovementDirection, Element> surroundingElements = new EnumMap<>(MovementDirection.class);
    
    // Adds all the movement directions and the elements associated
    for (MovementDirection direction : directions) {
      surroundingElements.put(direction, 
          getElementAtCoordinate(new Coordinate(coordinate.x() + direction.getXMovement()
          , coordinate.y() + direction.getYMovement())));
    }
    
    return surroundingElements;
  }
  
  /**
   * Reads the map from a file and stablish all the required elements in the class
   * @param mapPath path of the txt file that contains the map
   *  
   * @throws MapException In case any problem occurs during the process
   */
  private void setInitialMap(Path mapPath){
    
    try {
      // Getting the column and row sizes to initialize the mapArray
      List<String> allLines = LabyrinthFileManager.readMapLines(mapPath);
      int rows = allLines.size();
      int columns = allLines.get(0).length();
      
      mapArray = new Element[rows][columns];
      
      // Reading all the lines to fill the array, partitioning every line into a char array
      for (int i = 0; i < rows; i++) {
        char[] elements = allLines.get(i).toCharArray();
        for (int j = 0; j < columns; j++) {
          
          // Determines the type of the element depending on the char
          mapArray[i][j] = switch (elements[j]) {
          case '\s' -> Element.VOID;
          case 'P' -> Element.PLAYER;
          case 'F' -> Element.END;
          case '*' -> Element.TRAVELLED;
          default -> Element.OBSTACLE;
          };  
        }
      }
      
    // In case of any problem during the process, a personalized exception is thrown  
    } catch (IOException | IndexOutOfBoundsException e) {
      throw new MapException();
    }
  }
  
  
  
}
