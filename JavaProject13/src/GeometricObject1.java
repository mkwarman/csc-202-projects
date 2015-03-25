/**
 * Geometric Object implements the comparable class.
 * 
 * Matt Warman
 * 02/15/2015
 */

abstract class GeometricObject1 implements Comparable<GeometricObject1> {
  protected String color;
  protected double weight;

  // Default construct
  protected GeometricObject1() {
    color = "white";
    weight = 1.0;
  }

  // Construct a geometric object
  protected GeometricObject1(String color, double weight) {
    this.color = color;
    this.weight = weight;
  }

  // Getter method for color
  public String getColor() {
    return color;
  }

  // Setter method for color
  public void setColor(String color) {
    this.color = color;
  }

  // Getter method for weight
  public double getWeight() {
    return weight;
  }

  // Setter method for weight
  public void setWeight(double weight) {
    this.weight = weight;
  }

  // Abstract method
  public abstract double findArea();

  // Abstract method
  public abstract double findPerimeter();

  // MKW - Method compares a given GeometricObject to an inherited one
  public int compareTo(GeometricObject1 o) {
    if (getWeight() > o.getWeight()) // MKW - if the given object weighs less than the inherited one
      return 1;
    else if (getWeight() < o.getWeight()) // MKW - if the given object weighs more than the inherited one
      return -1;
    else // MKW - if the given object and the inherited one weighs the same
      return 0;
  }  
  
  // MKW - Method compares two circles and returns whichever is greater 
  public static Comparable<GeometricObject1> max(Circle1 o1, Circle1 o2) {
	    if (((Circle1) o1).getRadius() > ((Circle1) o2).getRadius()) // MKW - If the first object is larger than the second
	    {
	        return (Circle1) o1; // MKW - Return the first object
	    }
	    if (((Circle1) o1).getRadius() < ((Circle1) o2).getRadius()) // MKW - If the second object is larger than the first
	    {
	        return (Circle1) o2; // MKW - Return the second object
	    }
	    else // MKW - if the objects are equal in size
	    {
	        System.out.print("Objects are equal in value");
	    	return null;
	    }
  }
}
