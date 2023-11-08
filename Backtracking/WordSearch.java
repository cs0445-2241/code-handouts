import java.io.File;
import java.util.Scanner;

public class WordSearch {

    private char[][] grid;
    private String wordToFind;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java WordSearch <fileName> <word to find>");
        } else {
            WordSearch ws = new WordSearch(args[0]);
            if (ws.find(args[1])) {
                System.out.println("Word Found!");
                System.out.println("===========");
                ws.printGrid();
            } else {
                System.out.println("Word not found!");
            }
        }
    }

    public WordSearch(String fileName) {
        Scanner fileScan = null;
        try {
            fileScan = new Scanner(new File(fileName));
        } catch (Exception e) {
            System.out.println("Error opening file " + e.getMessage());
        }
        if (fileScan != null) {
            int row = 0;
            int size = Integer.parseInt(fileScan.nextLine());
            System.out.println("Found a " + size + "x" + size + " puzzle.");
            grid = new char[size][size];

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    char c = fileScan.next().charAt(0);
                    grid[i][j] = Character.toLowerCase(c);
                }
            }

            fileScan.close();
        }

    }

    public boolean find(String word) {
        boolean result = false;
        wordToFind = word.toLowerCase();

        // for all possible choices for the first decision
        for (int i = 0; i < grid.length && !result; i++) {
            for (int j = 0; j < grid.length && !result; j++) {
                // if matching letter
                if (grid[i][j] == wordToFind.charAt(0)) {
                    // mark cell as used
                    grid[i][j] = Character.toUpperCase(grid[i][j]);
                    // if last letter, return true
                    if (word.length() == 1) {
                        result = true;
                        break;
                    } else {
                        // else make a call to solve starting from next decision
                        if (solve(i, j, word, 1)) {
                            result = true;
                            break;
                        }
                    }
                    // unmark cell
                    grid[i][j] = Character.toLowerCase(grid[i][j]);
                }
            }
        }
        return result;
    }

    private boolean solve(int row, int col, String wordToFind, int letterNo) {
        boolean result = false;
        // for all possible neighbors
        for (int direction = 0; direction < 8; direction++) { // 8 directions
            // if inside the grid and hasn’t been used and == word[letter no.] {
            int nextRow = nextRow(row, direction);
            int nextCol = nextCol(col, direction);
            if (isValid(nextRow, nextCol) && wordToFind.charAt(letterNo) == grid[nextRow][nextCol]) {
                // mark neighbor as used
                grid[nextRow][nextCol] = Character.toUpperCase(grid[nextRow][nextCol]);
                // if last letter, print solution and return true
                if (letterNo == wordToFind.length() - 1) {
                    result = true;
                    break;
                } else {
                    // else make a recursive call on neighbor with letterNo + 1
                    if (solve(nextRow, nextCol, wordToFind, letterNo + 1)) {
                        result = true;
                        break;
                    }
                }
                // unmark the neighbor
                grid[nextRow][nextCol] = Character.toLowerCase(grid[nextRow][nextCol]);
            }
        }
        return result;
    }

    private boolean isValid(int row, int col) {
        boolean result = false;
        if ((row >= 0) && (col >= 0)
                && (row < grid.length) && (col < grid[0].length)
                && Character.isLowerCase(grid[row][col])) {
            result = true;
        }
        return result;
    }

    /*
     * directions:
     * 0: down
     * 1: down right
     * 2: right
     * 3: up right
     * 4: up
     * 5: up left
     * 6: left
     * 7: down left
     */
    private int nextRow(int row, int direction) {
        int result = row;
        if (direction == 0 || direction == 1 || direction == 7) {
            result = row + 1;
        }
        if (direction == 3 || direction == 4 || direction == 5) {
            result = row - 1;
        }
        return result;
    }

    private int nextCol(int col, int direction) {
        int result = col;
        if (direction == 5 || direction == 6 || direction == 7) {
            result = col - 1;
        }
        if (direction == 1 || direction == 2 || direction == 3) {
            result = col + 1;
        }
        return result;
    }

    private void printGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if(Character.isUpperCase(grid[i][j]))
                    //print found letters in purple
                    System.out.print("\u001B[35m" + grid[i][j] + " ");
                else
                    //rest in white
                    System.out.print("\u001B[37m" + grid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

}
