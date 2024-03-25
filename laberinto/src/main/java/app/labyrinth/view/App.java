package app.labyrinth.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;

import app.labyrinth.model.Map;

import javax.swing.JButton;
import java.awt.Font;
import java.nio.file.Path;
import java.util.List;

public class App {

  private static final Path MAP_PATH = Path.of("laberinto.txt");
  
  private JFrame frmLabryinth;
  private JTextArea txtAreaMap;
  private JButton btnNewButton;
  
  private Map map;

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
    frmLabryinth.setBounds(100, 100, 459, 721);
    frmLabryinth.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    txtAreaMap = new JTextArea();
    txtAreaMap.setEditable(false);
    txtAreaMap.setFont(new Font("Consolas", Font.PLAIN, 14));
    map = new Map(MAP_PATH);
    printMap();
    
    btnNewButton = new JButton("Iniciar");
    btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
    GroupLayout groupLayout = new GroupLayout(frmLabryinth.getContentPane());
    groupLayout.setHorizontalGroup(
      groupLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(groupLayout.createSequentialGroup()
          .addContainerGap()
          .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
            .addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
            .addComponent(txtAreaMap, GroupLayout.DEFAULT_SIZE, 810, Short.MAX_VALUE))
          .addContainerGap())
    );
    groupLayout.setVerticalGroup(
      groupLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(groupLayout.createSequentialGroup()
          .addContainerGap()
          .addComponent(txtAreaMap, GroupLayout.PREFERRED_SIZE, 579, GroupLayout.PREFERRED_SIZE)
          .addGap(18)
          .addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
          .addContainerGap(40, Short.MAX_VALUE))
    );
    frmLabryinth.getContentPane().setLayout(groupLayout);
  }

  /**
   * Prints the initial map in the textArea of the frame
   */
  private void printMap() {
    List<String> mapLines = map.getMap();
    
    txtAreaMap.setText("\n\n");
    
    mapLines.forEach(line -> txtAreaMap.setText(String.format("%s %s%n",
        txtAreaMap.getText(), line)));
    
  }
}
