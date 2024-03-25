package app.labyrinth.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;

import app.labyrinth.model.Coordinate;
import app.labyrinth.model.Game;
import app.labyrinth.model.exeptions.GameException;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.Frame;
import java.nio.file.Path;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class App {

  private static final Path MAP_PATH = Path.of("laberinto.txt");
  
  private JFrame frmLabryinth;
  private JTextArea txtAreaMap;
  
  // Game class which runs all the logical part of the app
  private Game game;
  private JLabel lblTimeSpentCalculatingRoute;
  private JLabel lblTimeSpentSolvingLabyrinth;


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
    frmLabryinth.setBounds(100, 100, 450, 770);
    frmLabryinth.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    txtAreaMap = new JTextArea();
    txtAreaMap.setEditable(false);
    txtAreaMap.setFont(new Font("Consolas", Font.PLAIN, 14));
    // Game
    game = new Game(MAP_PATH);
    // Printing the initial map in the textarea    
    printMap();
    
    // Button and event
    JButton btnStart = new JButton("Start");
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
              .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                  .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                    .addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNumberOfSteps, GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE))
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                    .addGroup(groupLayout.createSequentialGroup()
                      .addComponent(lblTimeSpentSolvingLabyrinth, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                      .addGap(577))
                    .addGroup(groupLayout.createSequentialGroup()
                      .addComponent(lblTimeSpentCalculatingRoute, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                      .addGap(552))))
                .addComponent(txtAreaMap, GroupLayout.DEFAULT_SIZE, 931, Short.MAX_VALUE)))
            .addGroup(groupLayout.createSequentialGroup()
              .addGap(62)
              .addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
              .addGap(58)
              .addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)))
          .addContainerGap())
    );
    groupLayout.setVerticalGroup(
      groupLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(groupLayout.createSequentialGroup()
          .addContainerGap()
          .addComponent(txtAreaMap, GroupLayout.PREFERRED_SIZE, 579, GroupLayout.PREFERRED_SIZE)
          .addGap(18)
          .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
            .addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
            .addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
          .addGap(18)
          .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
            .addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
            .addComponent(lblTimeSpentCalculatingRoute))
          .addPreferredGap(ComponentPlacement.RELATED)
          .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
            .addComponent(lblNumberOfSteps, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
            .addComponent(lblTimeSpentSolvingLabyrinth, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
          .addContainerGap(55, Short.MAX_VALUE))
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
    
    // Printing the new route
    printMap(route);
    
    // Getting the results and printing
    lblTimeSpentCalculatingRoute.setText(
        String.format("%,d ns",game.getTimeSpentCalculatingRoute()));
    
    lblTimeSpentSolvingLabyrinth.setText(
        String.format("%,f s", game.getTotalTimeSpent()));
 
    System.out.println("Game executed without errors");
  }

  /**
   * Prints the initial map in the textArea of the frame
   */
  private void printMap() {
    // Gets all the lines of the map in a list and prints them
    List<String> mapLines = game.getMap().getMapLines();
    
    txtAreaMap.setText("\n\n\s\s");
    
    mapLines.forEach(line -> txtAreaMap.setText(String.format("%s%s%n",
        txtAreaMap.getText(), line) + "\s\s")); 
  }
  
  /**
   * Prints the map with a stablished route followed by the player. If the route is empty, prints the
   * initial map
   * @param route Route to mark in the map
   */
  private void printMap(List<Coordinate> route) {
    
    List<String> mapLines = game.getMap().getMapLines();
    // Final String that represents the map
    
    StringBuilder mapStr = new StringBuilder("\n\n\s\s");
    // Divides every line into a char array and, if the coordinate is in the route, prints an '*'
    for (int i = 0; i < mapLines.size(); i++) {
      char[] line = mapLines.get(i).toCharArray();
      
      for (int j = 0; j < line.length; j++) {
        mapStr.append((route.contains(new Coordinate(i, j))) ? '*' : line[j]);
      }
      mapStr.append("\n\s\s");
      
    }

    txtAreaMap.setText(mapStr.toString());
  }
}
