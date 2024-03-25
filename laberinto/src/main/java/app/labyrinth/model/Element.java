package app.labyrinth.model;

/**
 * Element of the map where the game is played. It can be the player, the end of the map, an obstacle,
 *  a void space where the player can pass over o a space already traveled
 */
public enum Element {
  PLAYER,
  OBSTACLE,
  END,
  VOID,
  TRAVELED;

}
