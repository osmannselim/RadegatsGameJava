/**
 * Name: Osman Selim Yuksel
 * Date: 08.05.2023 (DD.MM.YYYY)
 * The Terrain class represents the terrain of the game.
 * It contains the matrix, visited list, height list and the inner and outer sizes.
 * It also contains the methods to print the matrix, calculate the score and modify the matrix.
 */
import java.text.DecimalFormat;

public class Terrain {
    private int[] dx = {-1, 1, 0, 0, -1, -1, 1, 1}; // 8 directions
    private int[] dy = {0, 0, -1, 1, -1, 1, -1, 1}; // 8 directions
    public int innerSize;  // inner
    public int outerSize;  // outer
    public int[][] matrix;
    public int[][] heightList; // 2D list indicating the lake height for every value in the matrix

    /**
     * constructor for the Terrain class
     * @param innerSize rows
     * @param outerSize columns
     * @param matrix matrix of heights
     */
    public Terrain(int innerSize, int outerSize, int[][]matrix) {
        this.innerSize = innerSize;
        this.outerSize = outerSize;
        this.matrix = matrix;
        heightList = new int[outerSize][innerSize];  // 2D list indicating the lake height for every value in the matrix
    }

    /**
     * prints the matrix
     * @param count the number of modifications made to the matrix
     */
    public void printMatrix(int count) {
        int c = 0; // counter for the first row
        for (int i = 0; i < outerSize; i++) { // for every row
            System.out.format("%3d", c++); // print the first rows number
            for (int j = 0; j < innerSize; j++) { // for every column
                int n = matrix[i][j];
                System.out.format("%3d", n); // print the value at the coordinate
            }
            System.out.println();
        }
        System.out.print("   ");
        int asciiValue = 97; // ascii value of "a"
        int quotient = innerSize / 26; // the number of times the alphabet has to be printed
        int remainder = innerSize % 26;

        if (quotient == 0) { // if the innerSize is less than 26
            for (int i = 0; i < remainder; i++) {
                System.out.format("%3c", asciiValue + i);
            }
        } else {
            for (int i = 0; i < 26; i++) {
                System.out.format("%3c", asciiValue + i);
            }
            quotient--;
            for (int i = 0; i < quotient; i++) {
                for (int j = 0; j < 26; j++) {
                    System.out.format("%2c", i + asciiValue);
                    System.out.format("%1c", j + asciiValue);
                }
            }
            for (int i = 0; i < remainder; i++) {
                System.out.format("%2c", quotient + asciiValue);
                System.out.format("%1c", i + asciiValue);
            }
        }
        System.out.println();
        if (!(count == 0))
            System.out.println("---------------");
        if (count < 10)
            System.out.format("Add stone %1d / 10 to coordinate: ", count + 1);
    }

    /**
     * calculates the score of the matrix
     * prints the matrix with the final score and labels
     */
    public void printFinal() {
        int c = 0;
        for (int i = 0; i < outerSize; i++) { // for every row
            System.out.format("%3d", c++); // print the first rows number
            for (int j = 0; j < innerSize; j++) {  // for every column
                if (Label.labelList[i][j] == null) { // if the coordinate is not labeled
                    int n = matrix[i][j]; // print the value at the coordinate
                    System.out.format("%3d", n);
                }
                else{ // if the coordinate is labeled
                    String s = Label.labelList[i][j];
                    System.out.format("%3s", s); // print the label
                }
            }
            System.out.println();
        }
        System.out.print("   ");
        int asciiValue = 97; // ascii value of "a"
        int quotient = innerSize / 26;    // the order of first letter of the label in the alphabet
        int remainder = innerSize % 26;   // the order of second letter of the label in the alphabet

        if (quotient == 0) {  // if quotient is 0, we only need to print one letter
            for (int i = 0; i < remainder; i++) {
                System.out.format("%3c", asciiValue + i);
            }
        } else {   // if quotient is not 0, we need to print firstly 26 single letters, then the double letters
            for (int i = 0; i < 26; i++) {
                System.out.format("%3c", asciiValue + i); // print the single letters
            }
            quotient--;
            for (int i = 0; i < quotient; i++) {  // print the double letters as many times as the quotient
                for (int j = 0; j < 26; j++) {
                    System.out.format("%2c", i + asciiValue);
                    System.out.format("%1c", j + asciiValue);
                }
            }
            for (int i = 0; i < remainder; i++) { // print the remaining double letters
                System.out.format("%2c", quotient + asciiValue);
                System.out.format("%1c", i + asciiValue);
            }
        }
        System.out.println();

        double score = 0;
        for (int i = 0; i<Label.scoreList.length; i++){ // for every element in the scoreList
            score += Math.sqrt(Label.scoreList[i]); // add the square root of the element to the score
        }

        DecimalFormat decimalFormat = new DecimalFormat("0.00"); // format the score to 2 decimal places
        String roundedNumber = decimalFormat.format(score); // round the score to 2 decimal places

        System.out.print("Final score: "+roundedNumber );
    }

    /**
     * increase the element in the matrix
     * @param x x coordinate
     * @param y y coordinate
     */
    public void modify (int x, int y){
        matrix[y][x]++;
    }

    /**
     * checks whether there is a lake in the element of a matrix.
     * traverses the adjacent elements of the matrix recursively.
     * @param ind1 row index
     * @param ind2 column index
     * @param max the variable to simulate the lake level
     * @param visitedList the list of visited coordinates
     * @return false if with this max value, the money leaks, true otherwise
     */
    public boolean DFS(int ind1, int ind2, int max, boolean[][] visitedList) {
        visitedList[ind1][ind2] = true;
        // check whether the element is on the edges of the terrain
        if (ind1 == 0 || ind1 == outerSize - 1 || ind2 == 0 || ind2 == innerSize - 1) {
            // check whether on this edge, money leaks or not
            if (max >= matrix[ind1][ind2]) {
                return false;  // if leaks, return false
            }else{
                return true;
            }
        }
        for (int i = 0; i < 8; i++) { // check all 8 neighbors
            int nx = ind1 + dx[i];
            int ny = ind2 + dy[i];
            if (matrix[nx][ny] <= max & ! visitedList[nx][ny]) {  // if the neighbor is lower and not visited
                if (! DFS(nx, ny, max, visitedList)) {   // if leaks, return false
                    return false;
                }
            }
        }
        return true;
    }

}
