/**
 * Circle1 Object extends the GeometricObject1 class.
 * 
 * Matt Warman
 * 02/15/2015
 */

class Circle1 extends GeometricObject1 {
  protected double radius;

  // Default constructor
  public Circle1() {
    this(1.0, "white", 1.0);
  }

  // Construct circle with specified radius
  public Circle1(double radius) {
    super("white", 1.0);
    this.radius = radius;
  }

  // Construct a circle with specified radius, weight, and color
  public Circle1(double radius, String color, double weight) {
    super(color, weight);
    this.radius = radius;
  }

  // Getter method for radius
  public double getRadius() {
    return radius;
  }

  // Setter method for radius
  public void setRadius(double radius) {
    this.radius = radius;
  }

  // Implement the findArea method defined in GeometricObject
  public double findArea() {
    return radius*radius*Math.PI;
  }

  // Implement the findPerimeter method defined in GeometricObject
  public double findPerimeter() {
    return 2*radius*Math.PI;
  }

  // Override the equals() method defined in the Object class
  public boolean equals(Circle1 circle) {
    return this.radius == circle.getRadius();
  }

  // Override the toString() method defined in the Object class
  public String toString() {
    return "[Circle] radius = " + radius;
  }
  
  // MKW - Method compares a given circle to an inherited one
  public int compareTo(Circle1 o) {
    if (getRadius() > o.getRadius()) // MKW - if the given circle is smaller than the inherited one
      return 1;
    else if (getRadius() < o.getRadius()) // MKW - if the given circle is larger than the inherited one
      return -1;
    else // MKW - if the given circle is the same size as the inherited one
      return 0;
  }
}
