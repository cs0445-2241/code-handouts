public class DSquare 
      implements Measurable {

  private Double sideLength;

  //parameterized constructor
  public DSquare(Double sideLength){
    this.sideLength = sideLength;
  }
  /**
    * @return the area of the shape
  **/
  public Double getArea(){
    return sideLength *
              sideLength;
  }

  /**
    * @return the perimeter of the shape
  **/
  public Double getPerimeter(){
    return sideLength * 4.0;
  }

  //Overriding
  public String toString(){
    return "A square of area: " + getArea() +
           " and perimeter: " + getPerimeter();
  }

  public int compareTo(Measurable other){
    return getArea().compareTo(other.getArea());
  }
}
