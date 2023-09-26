/**
 * An interface of shapes that have an area and a perimeter
**/
public interface Measurable {
  public final static String DEFAULT_NAME = "MyShape";
  /**
    * @return the area of the shape
  **/
  public Double getArea();

  /**
    * @return the perimeter of the shape
  **/
  public Double getPerimeter();

}
