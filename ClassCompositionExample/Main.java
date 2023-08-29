public class Main {
  public static void main(String[] args){
    Square aSquare = new Square(10);
    System.out.println("The area of a square of side length " +
                        aSquare.getSideLength() + " inches is " + 
                        + aSquare.getArea() + " sq. inches and its perimeter is " +
                        aSquare.getPerimeter() + " inches.");
  }
}
