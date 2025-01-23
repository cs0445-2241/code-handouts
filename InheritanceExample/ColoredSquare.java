public class ColoredSquare extends Square {
  private String color;
  private final String DEFAULT_COLOR = "Gold";

  public ColoredSquare(){
    //Call to super() is implicit
    this.color = DEFAULT_COLOR;
  }

  public ColoredSquare(Integer sideLength){
    super(sideLength);
    this.color = DEFAULT_COLOR;
    //What will happen if we call 
    //this(sideLength) instead?
  
  }

  public ColoredSquare(Integer sideLength, String color){
    //ILLEGAL because sideLength is private in Square
    //this.sideLength = sideLength;

    super(sideLength); //Calling the constructor of Square
    this.color = color;
  }

  @Override
  public String toString(){
    return super.toString() + " It has a " + color + " color.";
    //What will happen if we omit super?
  }

  public boolean equals(Object other){
    boolean result = false;
    ColoredSquare cs = (ColoredSquare) other;
      result = super.equals(other) && this.color.equals(cs.color);
    if(other instanceof ColoredSquare){
      
      //What happens if we use this.color == cs.color?
    }
    return result;
  }
}
