/**
 * Name: Osman Selim Yuksel
 * Student ID: 2021400105
 * Date: 08.05.2023 (DD.MM.YYYY)
 * The Modification class contains 2 static methods to check whether a modification is valid or not
 * and to check whether the input is in correct format or not
 * Due to the nature of the project, the methods are static and they are called from the main method
 * There is no need to create an object from this class
 */
public class Modification {

    /**
     * checks whether a modification is valid
     * modification will be done on this element matrix[indY][indX]
     * @param indY index of the inner list
     * @param indX index of element in the inner list
     * @param innerSize width of matrix
     * @param outerSize size of matrix
     * @return true if given modification is valid, else false
     */
    public static boolean isValidModification(int indY, int indX, int innerSize, int outerSize){
        boolean isValid = true;
        if (!(indY>=0 & indY<outerSize)){
            isValid = false;
        }
        if (!(indX>=0 & indX<innerSize)){
            isValid = false;
        }
        return isValid;
    }

    /**
     * checks whether the given input is valid or not
     * this is different from isValidModification method since it checks the format of the input
     * if input is in <letter(1 or 2)><digits> format, it is in valid format
     * but it may not be a valid modification, which is checked by isValidModification method later on
     * @param input input given by the user
     * @return true if input is valid, else false
     */
    public static boolean inputCheck(String input){
        // if given input's length is 1 or 0, input is not valid
        if (input.length() == 0 || input.length() == 1)
            return false;
        // if given input's length is 2, check whether it is in <letter><digit> format

        if (input.length() == 2) {
            char first = input.charAt(0);  // take the first char
            char sec = input.charAt(1);  // take the second char
            if (!(97 <= first && 122 >= first))  // if first char is not between 'a' and 'z' modification is not valid
                return false;
            else if(!Character.isDigit(sec)) { // if second char is not a digit, modification is not valid
                return false;
            }
            else{ // if first char is between 'a' and 'z' and second char is a digit, modification is valid
                return true;
            }
        }
        char[] charArray = new char[input.length()]; // if input's length is greater than 2, check whether it is in correct format
        charArray = input.toCharArray();

        char first = charArray[0]; // take the first char
        if (!(97 <= first && 122 >= first))  // if first char is not between 'a' and 'z' modification is not valid
            return false;

        char second = charArray[1];
        if (! Character.isDigit(second)) { // if second char is not a digit, it should be between 'a' and 'z'
            if (!(97 <= second && 122 >= second)) // if second char is not between 'a' and 'z' modification is not valid
                return false;
        }

        for (int i=2; i<input.length(); i++) {  // check whether the remaining part is a number
            char ch = charArray[i];
            if (!(Character.isDigit(ch))) // if one of the remaining chars is not a digit, modification is not valid
                return false;
        }
        return true; // if all the checks are passed, modification is valid, return true
    }

    /** No-arg constructor
     * For the sake of adhering to the java coding conventions
     */
    public Modification() { }
}
