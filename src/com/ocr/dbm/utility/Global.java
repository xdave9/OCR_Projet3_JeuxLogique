package com.ocr.dbm.utility;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class Global {
    private Global() {}

    /**
     * Represent a new line independant of OS
     */
    public static final String NEW_LINE = System.getProperty("line.separator");

    public static final int MASTERMIND_MIN_AVAILABLE_NUMERALS = 4;
    public static final int MASTERMIND_MAX_AVAILABLE_NUMERALS = 10;

    /**
     * Ask user, in console, for an integer between a minimum and a maximum
     * @param p_question Question to ask to user
     * @param p_min Minimum value allowed
     * @param p_max Maximum value allowed
     * @return A number given by the user between p_min and p_max
     */
    public static int readInt(String p_question, int p_min, int p_max) {
        Scanner sc = new Scanner(System.in);
        int response;

        for(;;)
        {
            System.out.print(p_question);

            try {
                response = sc.nextInt();
                /**/
                if (p_min <= response && response <= p_max) break;
                /**/
            } catch (InputMismatchException e) {}

            System.out.println(String.format("please, choose a number between %d and %d", p_min, p_max));
        }

        return response;
    }

    /**
     * Ask user, in console, for a String matching a specified regex
     * @param p_question Question to ask to user
     * @param p_regex Regex for a valid answer
     * @return An answer matching specified regex
     */
    public static String readString(String p_question, String p_regex) {
        Scanner sc = new Scanner(System.in);
        String response;

        for(;;)
        {
            System.out.print(p_question);

            try {
                response = sc.nextLine();
                /**/
                if (response.matches(p_regex)) break;
                /**/
            } catch (InputMismatchException e) {}

            System.out.println("Invalid!");
        }

        return response;
    }

    /**
     * @param p_question Question to ask to user
     * @return An answer to the question
     */
    public static String readString(String p_question) {
        Scanner sc = new Scanner(System.in);
        String response = null;

        try {
            response = sc.nextLine();
        } catch (InputMismatchException e) {}

        return response;
    }

    /**
     * Make console wait for enter to be pressed
     */
    public static void waitForNewLine() {
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }
}
