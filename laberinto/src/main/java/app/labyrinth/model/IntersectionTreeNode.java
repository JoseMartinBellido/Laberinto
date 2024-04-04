package app.labyrinth.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Tree Node made of coordinates made to keep the intersections of the labyrinth
 */
public class IntersectionTreeNode {
  
  private Coordinate coordinate;
  private List<IntersectionTreeNode> childNodes;
 
  public IntersectionTreeNode(Coordinate coordinate) {
    this.coordinate = coordinate;
    this.childNodes = new LinkedList<>();
  }
 
  public void addIntersectionChild(IntersectionTreeNode childNode) {
    this.childNodes.add(childNode);
  }
 
  public Coordinate getIntersection() {
    return coordinate;
  }
 
  public List<IntersectionTreeNode> getChildNodes() {
    return childNodes;
  }


}
