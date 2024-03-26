package app.labyrinth.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Class constituted by static methods for reading and writing a Labyrinth map from/into a file 
 */
public class LabyrinthFileManager {

  /**
   * Private constructor that makes impossible instantiate an object of the class
   */
  private LabyrinthFileManager() {}
  
  /**
   * Read the file and retrieve all the lines of the map in a list
   * @param mapPath Path of the file that contains the labyrinth map
   * @return A list containing all the lines of the map
   * 
   * @throws IOException In case any problem occurs during the process
   */
  public static List<String> readMapLines(Path mapPath) throws IOException{

      return Files.readAllLines(mapPath);

  }
  
  /**
   * Writes a labyrinth in a file. If the file already exists, it gets overwritten.
   * @param mapPath Path of the file to write
   * @param mapLines Lines that contains the ASCII symbols that represent the map
   * 
   * @throws IOException In case any problem occurs during the process
   */
  public static void writeMap(Path mapPath, List<String> mapLines) throws IOException {
    
    try (BufferedWriter writer = Files.newBufferedWriter(mapPath)){
      for (String line : mapLines) {
        writer.write(line);
      }
    }
    
  }
  
}
