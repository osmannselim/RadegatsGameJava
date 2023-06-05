/**
 * Name: Osman Selim Yuksel
 * Student ID: 2021400105
 * Date: 08.05.2023 (DD.MM.YYYY)
 * The Label class represents the labels of the matrix which are letters.
 * It contains the matrix of labels, matrix of heights, inner and outer sizes and the score list.
 * matrix of labels is initialized as null and the score list is initialized as 0.
 * in this code, if matrix of labels is null, it means that this coordinate will not be labeled.
 */

public class Label {

    public static int[] scoreList;
    private int[] dx = {-1, 1, 0, 0, -1, -1, 1, 1};  // 8 directions
    private int[] dy = {0, 0, -1, 1, -1, 1, -1, 1};  // 8 directions
    public final int ascii = 65;  // ascii value of "A"
    public int innerSize; // number of columns
    public int outerSize;  // number of rows
    public static String[][] labelList; // matrix of labels
    public int[][] heightList; // matrix of heights

    /**
     * constructor for the Label class
     * @param innerSize columns
     * @param outerSize rows
     * @param heightList matrix of heights
     */
    public Label(int innerSize, int outerSize, int[][] heightList) {
        this.heightList = heightList;
        this.innerSize = innerSize;
        this.outerSize = outerSize;
        labelList = new String[outerSize][innerSize];
        for (int i = 0; i<outerSize; i++){ // for every row
            String[] temp = new String[innerSize];
            labelList[i] = temp; // initialize the labelList, initially all null
        }
        scoreList = new int[outerSize*innerSize]; // initialize the scoreList, initially all 0
    }

    /**
     * labels the matrix with letters
     */
    public void labelMatrix(){
        int ind1 = heightList.length;
        int ind2 = heightList[0].length;
        boolean[][] visited = new boolean[ind1][ind2]; // initialize the visited matrix, initially all false
        int counter = 0;  // counter for keeping track of which letter to use
        for (int i = 1; i<ind1; i++){
            for (int j = 1; j<ind2; j++) {
                if (heightList[i][j]!=0 && !visited[i][j]){ // if the coordinate is not visited and height is not 0, which means there is a lake and so it should be labeled
                    dfsLabel(i,j,visited, counter); // label the matrix
                    counter++;  // increase the counter
                }
            }
        }
    }

    /**
     * dfs method for labeling the matrix
     * traverses the matrix recursively until it reaches a 0 or a visited coordinate
     * @param ind1 row
     * @param ind2 column
     * @param visitedLst visited matrix
     * @param counter counter to be used for labeling.
     * For example, if counter is 0, the label will be "A", if counter is 1, the label will be "B" and so on.
     */
    public void dfsLabel(int ind1,int ind2, boolean[][] visitedLst, int counter){
        if (!visitedLst[ind1][ind2]){ // if the coordinate is not visited
            visitedLst[ind1][ind2] = true; // mark it as visited
            letterMatrix(ind1,ind2,counter); // label the matrix
            scoreList[counter] += heightList[ind1][ind2]; // add the height of the current element to the scoreList
            for (int i = 0; i < 8; i++) { // for every neighbor
                int nx = ind1 + dx[i];
                int ny = ind2 + dy[i];
                if (! visitedLst[nx][ny] && (heightList[nx][ny]!=0)){ // if the neighbor is not visited and height is not 0
                    dfsLabel(nx,ny,visitedLst,counter); // call dfsLabel recursively
                }
            }
        }
    }

    /**
     * labels the matrix with letters, updates the labelList
     * @param ind1 row
     * @param ind2 column
     * @param count counter for which letter to use
     */
    public void letterMatrix(int ind1, int ind2, int count){
        int quotient = count / 26;
        int remainder = count % 26;
        if (quotient==0){
            char c = (char)(remainder+ascii);         // should there be -1
            labelList[ind1][ind2] = String.valueOf(c);
        }
        else{
            char c1 = (char)(quotient+ascii-1);
            char c2 = (char)(remainder+ascii);      // should there be -1
            labelList[ind1][ind2] = String.valueOf(c1) + c2;
        }
    }
}
