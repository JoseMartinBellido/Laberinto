package app.labyrinth.model.utils;

/**
 * Static class with calculus utility methods
 */
public class MathUtils {

  /**
   * Private constructor that makes impossible instantiate an object of the class
   */
  private MathUtils() {}
  
  /**
   * Rounds a double number to have only 1 decimal digit
   * @param number Number to round
   */
  public static double roundDoubleToOneDecimalDigit(double number) {
    return Math.round(number * 10) / 10.0;
  }
  
}
