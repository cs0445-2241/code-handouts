import java.util.Arrays;

final public class Backtracking {
  private static final int MAX_DIGITS = 4;
  private static final int BOARD_SIZE = 10;
  private static final int SUDOKU_SIZE = 9;

  public static void main(String[] args){
    //findPIN();
    eightQueens();
    //sudoku();
  }

  public static void findPIN(){
    solvePIN(0, "");
  }

  private static void solvePIN(int digitNo, String currentPIN){
    if(digitNo == MAX_DIGITS){
      System.out.println(currentPIN);
      System.out.println("----------------------");
      //System.exit(0); //one solution
      return; //all solutions
    }
    for(int i=0; i<10; i++){
      if(isValidPIN(digitNo, currentPIN, i)){
        currentPIN = currentPIN + i;
        solvePIN(digitNo+1, currentPIN);
        //deleting the last digit
        currentPIN = currentPIN.substring(0, currentPIN.length()-1);
      }
    }
  }

  private static boolean isValidPIN(int digitNo, String currentPIN, int i){
    boolean result = true;
    if(i == 0 && (currentPIN.indexOf('0') != -1)){
      result = false;
    }
    return result;
  }

  public static void eightQueens(){
    Character[][] board = new Character[BOARD_SIZE][BOARD_SIZE];
    for(int i=0; i<BOARD_SIZE; i++){
      for(int j=0; j<BOARD_SIZE; j++){
        board[i][j] = '-';
      }
    }
    solve8Queens(0, board);
  }

  private static void solve8Queens(int queenNo, Character[][] board){
    if(queenNo == BOARD_SIZE){
      printBoard(board);
      System.out.println("----------------------");

      // System.exit(0); //one solution
      return; //all solutions
    }
    for(int i=0; i < BOARD_SIZE; i++){
      if(isValidPlace(queenNo, i, board)){
        board[i][queenNo] = 'Q';
        solve8Queens(queenNo+1, board);
        board[i][queenNo] = '-';
      }
    }
  }

  private static <T> void printBoard(T[][] board){
    for(int i=0; i<board.length; i++){
      System.out.println(Arrays.toString(board[i]));
    }
  }

  private static boolean isValidPlace(int queenNo, int row, Character[][] board){
    for(int i=0; i<queenNo; i++){
      if(board[row][i] == 'Q'){
        return false;
      }
    }

    for(int i=0; i<row; i++){
      if(board[i][queenNo] == 'Q'){
        return false;
      }
    }

    int i = row;
    int j = queenNo;
    while(i >0 && j > 0){
      i--;
      j--;
      if(board[i][j] == 'Q'){
        return false;
      }
    }

    i = row;
    j = queenNo;
    while(i < BOARD_SIZE-1 && j > 0){
      i++;
      j--;
      if(board[i][j] == 'Q'){
        return false;
      }
    }
    return true;
  }

  public static void sudoku(){
    Integer[][] board = new Integer[SUDOKU_SIZE][SUDOKU_SIZE];
    for(int i=0; i<SUDOKU_SIZE; i++){
      for(int j=0; j<SUDOKU_SIZE; j++){
        board[i][j] = 0;
      }
    }
    solveSudoku(0, 0, board);
  }
  private static void solveSudoku(int row, int col, Integer[][] board){
    if(row == SUDOKU_SIZE){
      printBoard(board);
      System.out.println("----------------------");
      System.exit(0); //one solution
      //return; //all solutions
    }

    for(int i=1; i<10; i++){
      if(isValidSudoku(row, col, i, board)){
        board[row][col] = i;
        if(col == SUDOKU_SIZE - 1){
          solveSudoku(row+1, 0, board);
        } else {
          solveSudoku(row, col+1, board);
        }
        board[row][col] = 0;
      }
    }
  }

  private static boolean isValidSudoku(int row, int col, int num, Integer[][] board){
    if(board[row][col] != 0){
      if(board[row][col] == num){
        return true;
      } else {
        return false;
      }
    }

    for(int i=0; i<col; i++){
      if(board[row][i] == num){
        return false;
      }
    }

    for(int i=0; i<row; i++){
      if(board[i][col] == num){
        return false;
      }
    }

    if(!checkBox(row - row%3, col - col%3, num, board)){
      return false;
    }

    return true;

  }

  private static boolean checkBox(int row, int col, int num, Integer[][] board){
    for(int i=0; i<3; i++){
      for(int j=0; j<3; j++){
        if(board[i+row][j+col] == num){
          return false;
        }
      }
    }
    return true;
  }
}
