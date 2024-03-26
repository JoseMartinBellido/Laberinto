package app.labyrinth.model;

import java.util.Objects;

/**
 * Coordinate of an element of the map which indicates the horizontal and vertical position
 */
public record Coordinate(int x, int y) {

  // Redefined hashCode and equals methods to recognize if 2 coordinates are the same if two both
  // parts X and Y are equals
  
  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Coordinate other = (Coordinate) obj;
    return x == other.x && y == other.y;
  }

  
  
}
