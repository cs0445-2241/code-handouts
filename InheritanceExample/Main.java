public class Main {
  public static void main(String[] args){
    ColoredSquare aColoredSquare = new ColoredSquare(20, "Blue");
    aColoredSquare = new ColoredSquare(30, "Gold"); //What happens to the previous object?

    //OK because ColoredSquare is a Square
    Square aSquare = new ColoredSquare(15, "Green");

    //This should return false, not ClassCastException    
    //aSquare.equals(new String());
    
    //ILLEGAL because Square is not ColoredSquare
    //ColoredSquare aColoredSquare = new Square(10);

    //System.out.println(aColoredSquare); //Which toString is called?
    display(aColoredSquare);
 
    //System.out.println(aSquare); //Which toString is called?
    display(aSquare);

    Square copy = (Square) aSquare.clone();
    //System.out.println(copy); //Which toString is called?
    display(copy);


    //Let's create an array of Squares
    Square[] squares = new Square[5];
    squares[0] = new Square(10);
    squares[1] = new ColoredSquare(20, "Red");
    squares[2] = new Square();
    squares[3] = new ColoredSquare(20, "Blue");
    squares[4] = new Square(50);

    for(Square s : squares){
      System.out.println(s); //Which toString is called?
    }

  }

public static void display(Square s){
  System.out.println(s); //which toString is called?
}

}
