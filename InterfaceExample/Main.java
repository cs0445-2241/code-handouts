public class Main {
  public static void main(String[] args){
    DSquare aSquare;
    ColoredSquare aColoredSquare;

    //aSquare = new Measurable(); //ILLEGAL
    aSquare = new DSquare(10.0);
    aColoredSquare = new ColoredSquare(20, "Blue");

    System.out.println(aSquare);
    System.out.println(aColoredSquare);
    ISquare[] shapes = new ISquare[3];

    shapes[0] = new ISquare(25);
    shapes[1] = aColoredSquare;
    shapes[2] = new ColoredSquare(2,  "Gold");

    //Cannot call getMin. Why?
    //Measurable result = getMin(shapes);
  }

  private static <T extends Comparable<T>> T getMin(T[] items){
    T result = null;
    if(items.length > 0){
      result = items[0];
      for(T m: items){
        if(m.compareTo(result) < 0){
          result = m;
        }
      }
    }
    return result;
  }

}
