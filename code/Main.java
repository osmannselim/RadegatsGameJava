/**
 * Name: Osman Selim Yuksel
 * Date: 08.05.2023 (DD.MM.YYYY)
 * This class's function is to read the input file, call printMatrix() method, create the terrain and label objects and run the program
 * This program takes a matrix from a file and modifies it according to the user input.
 * The user can add stones to the matrix and the program will modify the matrix according to the rules.
 * The program will print the matrix after every modification.
 * The program will print the final score after 10 modifications.
 * The program will print the matrix with the final score and labels.
 */

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        FileInputStream fis = new FileInputStream("input.txt"); // read the input file
        Scanner sc = new Scanner(fis);
        String colAndRow = sc.nextLine();
        int innerSize = Integer.parseInt(colAndRow.split(" ")[0]);   // take the first number as inner size
        int outerSize = Integer.parseInt(colAndRow.split(" ")[1]);   // take the second number as outer size
        int[][] matrix = new int[outerSize][];  // create a 2D list with the given sizes
        for (int i = 0; i < outerSize; i++) {  // for every row
            int[] innerList = new int[innerSize]; // create a list with the given inner size
            matrix[i] = innerList; // add the inner list to the matrix
        }
        // read the matrix from the file
        for (int i = 0; i < outerSize; i++) {
            String line = sc.nextLine();
            String[] splittedNums = line.split(" ");
            for (int j = 0; j < innerSize; j++) {
                matrix[i][j]=Integer.parseInt(splittedNums[j]); // assign the values to the matrix
            }
        }

        sc.close();

        // Create a terrain object with the given matrix
        Terrain terrain = new Terrain(innerSize,outerSize,matrix);
        terrain.printMatrix(0);

        Scanner inp = new Scanner(System.in); // create a scanner object to take input from the user

        int counter = 0;  // initialize the counter variable to count number of modifications
        while (counter < 10){ // loop until the counter is 10, which means 10 modifications are done
            String modification = inp.nextLine();   // take input from the user
            String coordinate = modification.split("\n")[0];

            if (! Modification.inputCheck(coordinate)){ // check whether the input is valid or not
                System.out.println("Not a valid step!"); // if not valid, print error message
                System.out.format("Add stone %1d / 10 to coordinate:", counter+1);
                continue;
            }

            if (Character.isDigit(coordinate.charAt(1))){   // if second character is a digit, this means only first character is alphabetic. So, input is like a2
                int asciiOfLetter = coordinate.charAt(0);   // take the ascii value of the alphabetic character
                int index = Integer.parseInt(coordinate.substring(1));   // take the remaining number part

                // check whether the modification is valid or not
                if (Modification.isValidModification(index,asciiOfLetter-97, innerSize,outerSize)){
                    counter++;  // increment the counter at each valid modification
                    terrain.modify(asciiOfLetter-97,index);  // we subtract 97 from ascii of letter since 97 is the ascii value of a. So asciOfLetter - 97 gives the index
                    terrain.printMatrix(counter);  // print the matrix
                }
                else{
                    System.out.println("Not a valid step!"); // if not valid, print error message
                    System.out.format("Add stone %1d / 10 to coordinate:", counter+1);
                }
            }
            else{
                int ascii1 = coordinate.charAt(0); // take the ascii value of the first alphabetic character
                int ascii2 = coordinate.charAt(1); // take the ascii value of the second alphabetic character
                int index = Integer.parseInt(coordinate.substring(2)); // take the numeric part
                int convertedAscii = (ascii1-96)*26 + ascii2-97;   // for "ac" converted ascii value is (97-96)*26 + (100-97) = 29, which is exactly the x index
                // check whether the modification is valid or not
                if (Modification.isValidModification(index,convertedAscii, innerSize,outerSize)) {
                    counter++; // increment the counter at each valid modification
                    terrain.modify(convertedAscii,index);  // modify the matrix
                    terrain.printMatrix(counter);  // print the matrix
                }
                // if modification is valid
                else{
                    System.out.println("Not a valid step!");
                    System.out.format("Add stone %1d / 10 to coordinate:", counter+1);
                }
            }
        }

        // loop through the matrix and find the height of each block
        for (int i = 1; i<outerSize; i++){
            for (int j = 1; j<innerSize; j++){
                int stone = matrix[i][j];  // stone is the height of the current element
                int max = stone;
                while (true){ // we will increment max until we find a height that is not valid
                    if (terrain.DFS(i,j,max,new boolean[outerSize][innerSize])){ // if DFS returns true, this means we can increment max
                        max++;
                    }
                    else {
                        break; // if DFS returns false, this means we cannot increment max anymore and the max value is found
                    }
                }

                if (max == stone) { // if max is equal to stone, this means we cannot flood this block
                    terrain.heightList[i][j] = 0;  // so, we set the height of this block to 0
                }
                else {
                    terrain.heightList[i][j] = max-matrix[i][j]; // else, we set the height of this block to max - stone
                }
            }
        }

        Label label = new Label(innerSize,outerSize,terrain.heightList);  // create a label object
        label.labelMatrix(); // label the matrix

        terrain.printFinal(); // print the final (labeled) matrix

    }
}
