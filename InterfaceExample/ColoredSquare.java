public class ColoredSquare extends ISquare {
  private String color;

  public ColoredSquare(Integer sideLength){
    super(sideLength);
  }

  //Overloading
  public ColoredSquare(Integer sideLength, String color){
    //ILLEGAL because sideLength is private in Square
    //this.sideLength = sideLength;

    super(sideLength);//Calling the constructor of Square
    this.color = color;
  }

  public String toString(){
    return super.toString() + ". It has a " + color + " color.";
  }
}
