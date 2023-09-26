public class Main {
  public static void main(String[] args){
    Square<Double> aSquare;
    //Double is type argument
    //Double is an actual type parameter

    ColoredSquare<Double> aColoredSquare;

    //ILLEGAL. This will give a bound mismatch compilation error
    //ColoredSquare<String> anotherColoredSquare;

    //aSquare = new Measurable(); //ILLEGAL
    aSquare = new Square<Double>(10.0);

    aColoredSquare = new ColoredSquare<Double>(20.0, "Blue");

    System.out.println(aSquare);
    System.out.println(aColoredSquare);
    Square<Double>[] shapes;

    @SuppressWarnings("unchecked")
    //This cast is OK because the array contains only null values
    //SuppressWarnings apply only to declaration statements, including declaring methods
    //temp = (Square<Double>[]) new Object[3]; is ILLEGAL; why?
    Square<Double>[] temp = (Square<Double>[]) new Square[3];
    shapes = temp;


    shapes[0] = aSquare;
    shapes[1] = aColoredSquare;
    shapes[2] = new ColoredSquare<Double>(2.0, "Gold");

    Measurable result = getMin(shapes);
    System.out.println(result);

    @SuppressWarnings("unchecked")
    //This cast is OK because the array contains only null values
    ColoredSquare<Double>[] csArray = (ColoredSquare<Double>[]) new ColoredSquare[3];
    csArray[0] = new ColoredSquare<Double>(5.0, "Blue");
    csArray[1] = aColoredSquare;
    csArray[2] = new ColoredSquare<Double>(2.0, "Gold");

    result = getMin(csArray);
    System.out.println(result);

  }

  public static <T extends Comparable<? super T>> T getMin(T[] items){
    //Without "? super T", can we call the method with a ColoredSquare[]? why?
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
