public class Square {

  private final Integer DEFAULT_LENGTH = 10; //this is a constant
  private Integer sideLength; //inches
  private Integer area; //square inches
  private Integer perimeter; //inches

  //default constructor
  public Square(){
    this.sideLength = DEFAULT_LENGTH;
  }

  //parameterized constructor
  public Square(Integer sideLength){
    this.sideLength = sideLength;
  }
  /**
    * compute the area of the square
  **/
  private void computeArea(){
    area = sideLength * sideLength;
  }

  /**
    * compute the perimeter of the square
  **/
  private void computePerimeter(){
    perimeter = sideLength * 4;
  }

  /**
    * set the side length of the square 
    * and update area and perimeter
  **/
  public void setSideLength(Integer sideLength){
    this.sideLength = sideLength;
  }

/**
  * @return the side length of the square
**/
  public Integer getSideLength(){
    return sideLength;
  }

  /**
    * @return the area of the square
  **/
  public Integer getArea(){
    computeArea(); //this is a lazy approach: update area only when needed
    return area;
  }

  /**
    * @return the perimeter of the square
  **/
  public Integer getPerimeter(){
    computePerimeter();
    return perimeter;
  }
}
