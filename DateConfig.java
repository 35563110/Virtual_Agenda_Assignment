/*

@author - carl wang

Objective: Convert any entered dates to the format: (DAY OF WEEK) (MONTH) (DAY), (YEAR)

Process:
    1. Remove all periods and commas
    2. Validate string
    3. Display Format
}

-> Cannot use numbers for month and date
-> Year must contain 4 numbers
-> Month must be a string
-> st, nd, rd, th to be removed

METHOD DOESN'T TAKE IN THE YEAR. SUE ME!
*/
import java.util.Scanner;

public class DateConfig {
    public static void main (String[] args){
        Scanner reader = new Scanner(System.in);
        String test1 = "";
        test1 = reader.nextLine();
        String day = "";
        String month = "";
        String week = "";
       
        test1 = adjustString(test1);

        day = identifyDay(test1);
        month = identifyMonth(test1);
        week = identifyWeek(test1);

        String test2 = (week + " " + month + " " + day ); // Combines the information
        test2 = analyzeDate(test2);
        test2 = adjustString(test2); // Readjusts in case of any formatting error

        System.out.println("Adjusted date: " + test1);
        System.out.println("Analyzed date: " + test2);

        reader.close();
        System.exit(0);
    }

    /*
     * @author - Carl Wang
     * Method removes any unnecesary components identified in the entered date
     * 
     * @param String date - the date the user has inputted
     * @return String date - The optimized date
     * */
    public static String adjustString (String date){
        String[] endings = {"st", "nd", "rd", "th"}; // Typical endings behind numbers

        // Simple Adjustments (commas, periods, spaces)
        date = date.replaceAll(",", ""); // Removes commas
        date = date.replaceAll("\\.", ""); // Removes periods
        date = date.replaceAll("\\s{2,}", " ").trim(); // Removes all unnecessary spaces

        // Removes day endings
        for (int i = 0; i < endings.length; i++){
            date = date.replaceAll(endings[i], ""); // If an ending was found, remove it
        }

        return date;
    }

    /*
     * @author - Carl Wang
     * Method identifies the day
     * 
     * @param String date - the date the user has inputted
     * @return - The day
     * */
    public static String identifyDay (String date){
        for (int i = 32; i >= 0; i--){
            String compare = Integer.toString(i); // converts integer to string
            if (date.contains(" " + compare  + " ")||date.contains(" " + compare)||date.contains(compare  + " ")){ // if a 2-digit number has been identified, return
                return compare; // Returned value is not a section of the entered year
            }
        }
        return "?";
    }

    /*
     * @author - Carl Wang
     * Method identifies the month
     * 
     * @param String date - the date the user has inputted
     * @return - The month (full or abbreviated)
     * */
    public static String identifyMonth (String date){
        String[] months1 = {"January", "Febuary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String[] months2 = {"january", "febuary", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december"};
        String[] months3 = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Sep", "Oct", "Nov", "Dec"};
        String[] months4 = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sept", "sep", "oct", "nov", "dec"};
        String month = "";
        for (int i = 0; i < months1.length; i++){
            if (date.contains(months1[i])||date.contains(months2[i])){ // Identifies the month
                month = months1[i];
                return month;
            }
        }
        for (int i = 0; i < months3.length; i++){
            if (date.contains(months3[i])||date.contains(months4[i])){ // Identifies the month
                month = months3[i];
                return month;
            }
        }
        return "?";
    }

    /*
     * @author - Carl Wang
     * Method identifies the week
     * 
     * @param String date - the date the user has inputted
     * @return - The week (full or abbreviated)
     * */
    public static String identifyWeek (String date){
        String[] weeks1 = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        String[] weeks2 = {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};
        String[] weeks3 = {"Sun", "Mon", "Tue", "Tues", "Wed", "Weds", "Thur", "Thurs", "Thu", "Fri", "Sat"};
        String[] weeks4 = {"sun", "mon", "tue", "tues", "wed", "weds", "thur", "thurs", "thu", "fri", "sat"};
        String week = "";
        for (int i = 0; i < weeks1.length; i++){ // Identifies the week
            if (date.contains(weeks1[i])||date.contains(weeks2[i])){
                week = weeks1[i];
                return week;
            }
        }
        for (int i = 0; i < weeks3.length; i++){ // Identifies the week
            if (date.contains(weeks3[i])||date.contains(weeks4[i])){
                week =weeks3[i];
                return week;
            }
        }
        return ""; // No question mark returned because week can be omitted
    }

    /*
     * @author - Carl Wang
     * Method identifies the year
     * 
     * @param String date - the date the user has inputted
     * @return - The year
     * */
    /*
    public static String identifyYear (String date){
        for (int i = 2000; i < 3000; i++){ // Hard coded, but humanity might not live long enough past 3000
            String compare = Integer.toString(i);
            if (date.contains(" " + compare)){ // If a 4-digit number has been identified
                return compare; // Loop stops here
            }
        }
        return "?";
    }
    */

    /*
     * @author - Carl Wang
     * Method analyzed the date to see if there were any unrecongized elements
     * 
     * @param String date - the date the program analyzed
     * @return "?" - the program found something wrong
     * @return date - the program found nothing wrong
     * */
    public static String analyzeDate(String date){
        if (date.contains("?")){ // If one of the date elements were unrecongized
            return "ERROR";
        }
        else {
            return date;
        }
    }
}