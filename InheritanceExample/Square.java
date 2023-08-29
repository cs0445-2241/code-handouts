public class Square {

  private final Integer DEFAULT_LENGTH = 10; //this is a constant
  private Integer sideLength; //inches
  private Integer area; //square inches
  private Integer perimeter; //inches

  //default constructor
  public Square(){
    this.sideLength = DEFAULT_LENGTH;
    //this is an eager approach; keep area and perimeter correct all the time
    computeArea();
    computePerimeter();
  }

  //parameterized constructor
  public Square(Integer sideLength){
    this.sideLength = sideLength;
    computeArea();
    computePerimeter();
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
    computeArea();
    computePerimeter();
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
    return area;
  }

  /**
    * @return the perimeter of the square
  **/
  public Integer getPerimeter(){
    return perimeter;
  }

  //Overriding toString of Object
  public String toString(){
    return "I'm a square of side length " + 
           sideLength + " inches, area " + 
           getArea() + " sq. inches and perimeter " + 
           getPerimeter() + " inches.";
  }

  public boolean equals(Object other){
    boolean result = false;
    if(other instanceof Square){
      Square s = (Square) other;
      result = sideLength.equals(s.sideLength) && area.equals(s.area) 
               && perimeter.equals(s.perimeter);
      //What happens if we use sideLength == s.sideLength?
    }
    return result;
  }

  //Make an independent copy of this Square object. To use this method, 
  //one can call Square anotherSquare = aSquare.clone(). 
  public Object clone(){
    Square copy = new Square(); 
    //The call below changes sideLength (area and perimeter change as well). 
    // This will cause the copy to point to
    //different objects than this
    copy.setSideLength(sideLength);
    return copy;
    //copy != this but copy.equals(this)
  }

  //Another way of copying objects is by using a Copy Constructor. To use it,
  //one can Square anotherSquare = new Square(aSquare)
  public Square(Square other){
    sideLength = other.sideLength;//This is shallow but is OK because Integer is immutable
    area = other.area;
    perimeter = other.perimeter;
  }
}
