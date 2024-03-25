package app.labyrinth.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Map where the game is played. It consists in a labyrinth bordered and composed by walls, 
 * with a player and the end (objective to reach)
 */
public class Map {
  
  /**
   * The map representated by a bidimensional array
   */
  private Element[][] mapArray;
  
  /**
   * Path of the txt file that contains the map made by ascii characters
   */
  private Path mapPath;
  
  
  /**
   * Constructor of the class. Creates a map using the path of a txt file with a labyrinth made by
   * ascii characters
   * @param mapPath path of the txt file that contains the map
   */
  public Map(Path mapPath) {
    this.mapPath = mapPath;
    
    // Sets the map using the txt file path
    setInitialMap();
    
    // Logs an ok message
    System.out.println("Map created correctly");
  }
  
  
  /**
   * Read the file and retrieve all the lines of the map in a list
   * @return A list containing all the characters of the map
   * 
   * @throws MapException In case any problem occurs during the process
   */
  public List<String> getMap(){
    
    try {
      return Files.readAllLines(mapPath);
      
    } catch (IOException e) {
      throw new MapException();
    }
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
  public Coordinate getElementCoordinates(Element searchedElement) {
    
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
          return new Coordinate(i, j);
        }
      }
    }
    // It should never get here. There is always a player and an end in the game
    throw new MapException();
  }
  
  /**
   * Reads the map from a file and stablish all the required elements in the class
   *  
   * @throws MapException In case any problem occurs during the process
   */
  private void setInitialMap(){
    
    try {
      // Getting the column and row size to initialize the mapArray
      List<String> allLines = Files.readAllLines(mapPath);
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
          case '*' -> Element.PLAYER;
          case 'F' -> Element.END;
          default -> Element.OBSTACLE;
          };  
        }
      }
      
    // In case of any problem during the process, a personalized exception is thrown  
    } catch (IndexOutOfBoundsException | IOException e) {
      throw new MapException();
    }
  }
  
  
  
}
