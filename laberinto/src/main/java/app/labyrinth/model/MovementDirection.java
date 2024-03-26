package app.labyrinth.model;

/**
 * Movement direction the player can go to in the labyrinth. Associated with the X and Y axis 
 * interaction the movement will bring about to the position (Coordinates)
 */
public enum MovementDirection {
  UP(0, 1), 
  UP_RIGHT(1, 1),
  UP_LEFT(-1, 1),
  RIGHT(1, 0), 
  LEFT(-1, 0),
  DOWN(0, -1),
  DOWN_RIGHT(1, -1),
  DOWN_LEFT(-1, -1);
  
  /**
   * The quantity of squares the player moves in the X axis
   */
  private int xMovement;
  
  /**
   * The quantity of squares the player moves in the Y axis
   */
  private int yMovement;
  
  /**
   * Constructor of the class with all the movements done by the player due to the direction selected
   * @param xMovement The quantity of squares the player moves in the X axis
   * @param yMovement The quantity of squares the player moves in the Y axis
   */
  MovementDirection(int xMovement, int yMovement) {
    this.xMovement = xMovement;
    this.yMovement = yMovement;
  }

  /**
   * Gets the squares the player moves in the X axis
   * @return The squares the player moves in the X axis
   */
  public int getXMovement() {
    return xMovement;
  }

  /**
   * Gets the squares the player moves in the Y axis
   * @return The squares the player moves in the Y axis
   */
  public int getYMovement() {
    return yMovement;
  }

  
}
