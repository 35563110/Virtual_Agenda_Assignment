public class CommaDetection {
    public static void main (String[] args){
        String test = "Eat, Tomorrow";
        String task = "";
        String due = "";

        int divider = test.indexOf(","); // Crucial part of code

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