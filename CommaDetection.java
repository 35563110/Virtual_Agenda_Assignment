/*
 * @author - Carl Wang
 * code associated for file reading: identifies which part of a string
 * is the due date, and which part of the string is the name of the task
 * 
* */

public class CommaDetection {
    public static void main (String[] args){
        String test = "Eat food/,/ tomorrow";
        String task = "";
        String due = "";

        int divider = test.indexOf("/,/"); // Crucial part of code

        test = test.trim(); // Removes leading and trailing spaces

        try{
            task = test.substring(0, divider);
            due = test.substring(divider+4, test.length());
        }
        catch (Exception e){
            task = "ERROR";
            due = "ERROR";
        }

        System.out.println(divider);
        System.out.println(test);
        System.out.println(task);
        System.out.println(due);
    }
}
