package app.labyrinth.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;

import app.labyrinth.controller.LabyrinthFileManager;
import app.labyrinth.model.Coordinate;
import app.labyrinth.model.Game;
import app.labyrinth.model.exceptions.GameException;

import javax.swing.JButton;
import java.awt.Font;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * Main Class of the App
 */
public class App {

  /**
   * Path of the map made with ASCII characters
   */
  private static final Path MAP_PATH_INITIAL = Path.of("labyrinth.txt");
  
  private static final Path MAP_PATH_SOLVED = Path.of("solved-labyrinth.txt");
  
  /**
   *  Game class which runs all the logical part of the app
   */
  private Game game;
  
  // WindowBuilder attributes
  private JFrame frmLabryinth;
  private JTextArea txtAreaMap;
  
  private JLabel lblTimeSpentCalculatingRoute;
  private JLabel lblTimeSpentSolvingLabyrinth;
  private JButton btnStart;


  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          App window = new App();
          window.frmLabryinth.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  public App() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frmLabryinth = new JFrame();
    frmLabryinth.setTitle("Labryinth");
    frmLabryinth.setBounds(100, 100, 434, 790);
    frmLabryinth.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    txtAreaMap = new JTextArea();
    txtAreaMap.setEditable(false);
    txtAreaMap.setFont(new Font("Consolas", Font.PLAIN, 14));
    
    // Button and event
    btnStart = new JButton("Start");
    // The btnStart is enabled from the beginning. It's only clickable if the initial map 
    // is printed correctly
    btnStart.setEnabled(false);
    btnStart.addActionListener(ev -> {
      try {
        executeTheGame();
        
      // In case an error occurs during the process, it shows an error popup to the user
      } catch (GameException e) {
        JOptionPane.showMessageDialog(frmLabryinth, "The specified route algorithm is not valid. "
            + "Please fix it and try again.", "Error during the game", JOptionPane.ERROR_MESSAGE);
      }
      
      // Disabling the button
      btnStart.setEnabled(false);
      
    });
    
    // Game
    try {
      game = new Game(MAP_PATH_INITIAL);
      // Printing the initial map in the textarea    
      printMap(MAP_PATH_INITIAL);
    
    } catch (Exception e) {
      JOptionPane.showMessageDialog(frmLabryinth, "A problem ocurred trying to start the App",
          "Fatal error", JOptionPane.ERROR_MESSAGE);
      frmLabryinth.dispose();
    }
    
    btnStart.setFont(new Font("Tahoma", Font.BOLD, 13));
    
    JLabel lblNewLabel = new JLabel("Time spent calculating the route:");
    lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
    
    JLabel lblNumberOfSteps = new JLabel("Time spent solving the labyrinth:");
    lblNumberOfSteps.setFont(new Font("Tahoma", Font.BOLD, 12));
    
    lblTimeSpentCalculatingRoute = new JLabel("               ");
    lblTimeSpentCalculatingRoute.setFont(new Font("Tahoma", Font.BOLD, 12));
    
    lblTimeSpentSolvingLabyrinth = new JLabel("               ");
    lblTimeSpentSolvingLabyrinth.setFont(new Font("Tahoma", Font.BOLD, 12));
    
    // Exit button and exit on click
    JButton btnExit = new JButton("Exit");
    btnExit.addActionListener(ev -> frmLabryinth.dispose());
    
    btnExit.setFont(new Font("Tahoma", Font.BOLD, 13));
    GroupLayout groupLayout = new GroupLayout(frmLabryinth.getContentPane());
    groupLayout.setHorizontalGroup(
      groupLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(groupLayout.createSequentialGroup()
          .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(groupLayout.createSequentialGroup()
              .addContainerGap()
              .addComponent(txtAreaMap, GroupLayout.DEFAULT_SIZE, 931, Short.MAX_VALUE))
            .addGroup(groupLayout.createSequentialGroup()
              .addGap(65)
              .addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
              .addGap(56)
              .addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)))
          .addContainerGap())
        .addGroup(groupLayout.createSequentialGroup()
          .addContainerGap()
          .addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
          .addGap(18)
          .addComponent(lblTimeSpentCalculatingRoute, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
          .addGap(562))
        .addGroup(groupLayout.createSequentialGroup()
          .addContainerGap()
          .addComponent(lblNumberOfSteps, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(lblTimeSpentSolvingLabyrinth, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
          .addGap(587))
    );
    groupLayout.setVerticalGroup(
      groupLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(groupLayout.createSequentialGroup()
          .addContainerGap()
          .addComponent(txtAreaMap, GroupLayout.PREFERRED_SIZE, 543, GroupLayout.PREFERRED_SIZE)
          .addGap(26)
          .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
            .addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
            .addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
          .addGap(26)
          .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
            .addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
            .addComponent(lblTimeSpentCalculatingRoute))
          .addGap(18)
          .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
            .addComponent(lblTimeSpentSolvingLabyrinth, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
            .addComponent(lblNumberOfSteps, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
          .addContainerGap(63, Short.MAX_VALUE))
    );
    frmLabryinth.getContentPane().setLayout(groupLayout);
  }

  /**
   * Executes all the game and prints every result in case all the algorithms are correct, or pops
   * a message to the user if something is wrong
   * 
   * @throws GameException In case it occurs a problem during the game process
   */
  private void executeTheGame() {
    
    // Calculation of the route
    game.calculateRoute();
    List<Coordinate> route = game.getRoute();
    
    // Saving and printing the new route
    saveMap(route);
    printMap(MAP_PATH_SOLVED);
    
    // Getting the results and printing
    lblTimeSpentCalculatingRoute.setText(
        String.format("%,d ns",game.getTimeSpentCalculatingRoute()));
    
    lblTimeSpentSolvingLabyrinth.setText(
        String.format("%,f s", game.getTotalTimeSpent()));
 
    System.out.println("Game executed without errors");
  }

  /**
   * Prints the given map in the textArea of the frame. Warns the user if it occurs a problem
   * during the process
   * @param mapPath Path of the map the method prints in the frame
   */
  private void printMap(Path mapPath) {
    // Gets all the lines of the map in a list and prints them
    List<String> mapLines;
    
    try {
      // Read all the lines of the path and print it in the textarea
      mapLines = LabyrinthFileManager.readMapLines(mapPath);
      
      txtAreaMap.setText("");
      
      mapLines.forEach(line -> txtAreaMap.setText(String.format("%s%s%n",
          txtAreaMap.getText(), line))); 
      
      // Sets the start button to enabled if the initial map loads correctly
      if (mapPath.equals(MAP_PATH_INITIAL)) {
        btnStart.setEnabled(true);
      }
    
    // In case of error
    } catch (IOException e) {
      JOptionPane.showMessageDialog(frmLabryinth, "It's not possible to read the file. A problem "
          + "occurred during the process.", "Problem reading the file", JOptionPane.ERROR_MESSAGE);
    }
  } 
  
  /**
   * Saves the map with a stablished route followed by the player. If the route is empty, keeps the
   * initial map.
   * @param route Route followed by the player trying to reach the end of the maze
   */
  private void saveMap(List<Coordinate> route) {
    
    List<String> mapLines;
    try {
      // Reads the original map to add the route and save it
      mapLines = LabyrinthFileManager.readMapLines(MAP_PATH_INITIAL);
      
      // Divides every line into a char array. If the coordinate is in the route, 
      // changes the character to '*' (Element travelled) 
      for (int i = 0; i < mapLines.size(); i++) {
        char[] line = mapLines.get(i).toCharArray();
        
        for (int j = 0; j < line.length; j++) {
          line[j] = (route.contains(new Coordinate(j, i))) ? '*' : line[j];
        }
        // Update the mapLines list to keep the solved labyrinth
        mapLines.remove(i);
        mapLines.add(i, String.valueOf(line) + "\n");
      }
      
      // Write the solved labyrinth permanently in the file
      LabyrinthFileManager.writeMap(MAP_PATH_SOLVED, mapLines);
    
    // In case of error
    } catch (IOException e) {
      JOptionPane.showMessageDialog(frmLabryinth, "It's not possible to write the file. A problem"
          + " occurred during the process.", "Problem writing the file", JOptionPane.ERROR_MESSAGE);
    }
   
  }
}
