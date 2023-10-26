import java.io.File;
import java.util.Scanner;

public class WordSearch {
    
    private char[][] grid;
    private String wordToFind;

    public static void main(String[] args){
        if(args.length != 1){
            System.out.println("Usage: java WordSearch <fileName>");
        } else {
            WordSearch ws = new WordSearch(args[0], 10);
            ws.find("DATASTRUCTURES");
        }
    }

    public WordSearch(String fileName, int size){
        grid = new char[size][size];
        Scanner fileScan = null;
        try{
            fileScan = new Scanner(new File(fileName));
        }catch(Exception e){
            System.out.println("Error opening file " + e.getMessage());
        }
        if(fileScan != null){
            int row = 0;
            while(fileScan.hasNextLine()){
                String input = fileScan.nextLine();
                for(int i=0; i<size; i++){
                    grid[row][i] = Character.toLowerCase(
                            input.charAt(i));
                }
                row++;
            }
            fileScan.close();
        }
        
    }

    public boolean find(String word){
        boolean result = false;
        wordToFind = word.toUpperCase();

        //call solve
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid.length; j++){
                if(Character.toUpperCase(grid[i][j]) == wordToFind.charAt(0)) {
                    StringBuilder temp = new StringBuilder();
                    temp.append(Character.toUpperCase(grid[i][j]));
                    grid[i][j] = Character.toUpperCase(grid[i][j]);
                    if(solve(i, j, temp)){
                        result = true;
                        break;
                    }
                    grid[i][j] = Character.toLowerCase(grid[i][j]);
                }
            }
        }
        return result;
    }


    private boolean solve(int row, int col, StringBuilder current){
        boolean result = false;
        //for all possible options
        for(int direction=0; direction<8; direction++){ //8 directions
            //check if option is valid
            int nextRow = nextRow(row, direction);
            int nextCol = nextCol(col, direction);
            if(isValid(nextRow, nextCol)){
                current.append(Character.toUpperCase(grid[nextRow][nextCol]));
                grid[nextRow][nextCol] = 
                    Character.toUpperCase(grid[nextRow][nextCol]);
                if(current.toString().toUpperCase().equals(wordToFind)){
                    System.out.println("Word Found!");
                    printGrid();
                    result = true;
                   break;
                }
                if (isPrefix(current.toString(), wordToFind)){
                    if(solve(nextRow, nextCol, current)){
                        result = true;
                       break;
                    }
                }
                
                //delete last letter of current
                current.deleteCharAt(current.length()-1);
                grid[nextRow][nextCol] = 
                    Character.toLowerCase(grid[nextRow][nextCol]);

            }
        }
        return result;


    }

    private boolean isValid(int row, int col){
        boolean result = false;
        if((row >= 0) && (col >= 0) 
            && (row < grid.length) && (col < grid[0].length) 
            && Character.isLowerCase(grid[row][col])){
                result = true;
            }
        return result;
    }

    
     /*
     * directions:
     *  0: down
     *  1: down right
     *  2: right
     *  3: up right
     *  4: up
     *  5: up left
     *  6: left
     *  7: down left
     */
    private int nextRow(int row, int direction){
        int result = row;
        if(direction == 0 || direction == 1 || direction == 7){
            result = row + 1;
        }
        if(direction == 3 || direction == 4 || direction == 5){
            result = row - 1;
        }
        return result;
    }

    private int nextCol(int col, int direction){
        int result = col;
        if(direction == 5 || direction == 6 || direction == 7){
            result = col - 1;
        }
        if(direction == 1 || direction == 2 || direction == 3){
            result = col + 1;
        }
        return result;
    }

    private boolean isPrefix(String check, String against){
        boolean result = true;
        if(check.length() > against.length()){
            result = false;
        } else {
            for(int i=0; i<check.length(); i++){
                if(check.charAt(i) != against.charAt(i)){
                    result = false;
                    break;
                }
            }
        }       

        return result;

    }

    private void printGrid(){
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid.length; j++){
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    
}
