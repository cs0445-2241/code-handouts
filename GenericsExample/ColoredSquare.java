public class ColoredSquare<T extends Number>
            extends Square<T> {
  private String color;

  public ColoredSquare(T sideLength){
    super(sideLength);
  }

  //Overloading
  public ColoredSquare(T sideLength, String color){
    //ILLEGAL because sideLength is private in Square
    //this.sideLength = sideLength;

    super(sideLength);//Calling the constructor of Square
    this.color = color;
  }

  public String toString(){
    return super.toString() + ". It has a " + color + " color.";
  }
}
