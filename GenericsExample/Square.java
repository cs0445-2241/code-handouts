//Square<T> is a generic type
//T is a (formal) type parameter
//Square<Double> is a parameterized type
//Square is a raw type <== DON'T USE RAW TYPES
public class Square<T extends Number>
              implements Measurable, Comparable<Square<T>>{

  private T sideLength;

  //parameterized constructor
  public Square(T sideLength){
    this.sideLength = sideLength;
  }
  /**
    * @return the area of the shape
  **/
  public Double getArea(){
    return sideLength.doubleValue() *
              sideLength.doubleValue();
  }

  /**
    * @return the perimeter of the shape
  **/
  public Double getPerimeter(){
    return sideLength.doubleValue() * 4;
  }

  //Overriding
  public String toString(){
    return "A square of area: " + getArea() +
           " and perimeter: " + getPerimeter();
  }

  public int compareTo(Square<T> other){
    return getArea().compareTo(other.getArea());
  }
}
