package app.labyrinth.model;

/**
 * Element of the map (labyrinth). It can be the player, the end of the map, an obstacle,
 *  a void space where the player can pass over o a space already travelled. It will be associated with 
 *  the character representation in the ASCII map.
 */
public enum Element {
  PLAYER(new char[]{'P'}),
  OBSTACLE(new char[]{'|', '-', '+'}),
  END(new char[]{'F'}),
  VOID(new char[]{'\s'}),
  TRAVELLED(new char[]{'*'});
  
  /**
   * Representation of the enum in the map
   */
  private char[] representation;
  
  /**
   * Constructor of the enum with the representation 
   * @param representation Representation of the element in the map
   */
  Element(char[] representation) {
    this.representation = representation;
  }

  /**
   * Gets the representation of the element
   * @return The representation of the element
   */
  public char[] getRepresentation() {
    return representation;
  }
  
  
}
