package app.labyrinth.model;

/**
 * Element of the map where the game is played. It can be the player, the end of the map, an obstacle,
 *  a void space where the player can pass over o a space already traveled
 */
public enum Element {
  PLAYER('P'),
  OBSTACLE((char) Character.OTHER_SYMBOL),
  END('F'),
  VOID('\s'),
  TRAVELED('*');
  
  /**
   * Representation of the enum in the map
   */
  private char representation;
  
  /**
   * Constructor of the enum with the representation 
   * @param representation Representation of the element in the map
   */
  Element(char representation) {
    this.representation = representation;
  }

  /**
   * Gets the representation of the element
   * @return The representation of the element
   */
  public char getRepresentation() {
    return representation;
  }
  
  
}
