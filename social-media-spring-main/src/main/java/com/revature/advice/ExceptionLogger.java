package com.revature.advice;

import com.revature.exceptions.InvalidInputException;
import com.revature.exceptions.NotLoggedInException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

// This catches all errors, expected and otherwise, and prints/sends the cause.
// Most of the class is devoted to custom-error messages for testing purposes.
// handleExceptions handles the responses for errors in the back-end.
@ControllerAdvice
public class ExceptionLogger {

    // Ansi values to color the text/background of the terminal output
    public static final String ANSI_RESET = "\u001B[0m"; // So the following is standard output.

    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    // ---------------------------------------------------------------------------------------------------------------//

    // Main feature of this class. This formats and prints the exception in the console window
    public static void log(Exception e){
        log(e, "default", "default");
    }

    public static void log(Exception e, String textColor){
        log(e, textColor, "default");
    }

    public static void log(Exception e, String testColor, String backgroundColor){

        // Color the text/background (persists through all lines)
        System.out.println(name2ansiBackground(backgroundColor) + name2ansiText(testColor));

        // Print the type of error
        System.out.println( e.getClass().getCanonicalName() );

        // Print the error message
        System.out.println( "\t" + e.getMessage() );

        // Print the stack-trace
        System.out.println("\nStack Trace: ");
        int len = e.getStackTrace().length;
        if (len < 13) { // 13 = 2*_5_ + 3

            for (StackTraceElement elem : e.getStackTrace()) {
                System.out.println("\t" + elem.toString());
            }

        } else {
            for (int i=0; i<5; i++) { // First five
                System.out.println("\t" + e.getStackTrace()[i].toString());
            }

            System.out.println("\t...\n\n\t...");

            for (int i=len-5; i<len; i++) { // Last five
                System.out.println("\t" + e.getStackTrace()[i].toString());
            }
        }

        //Reset the text/background to terminal default
        System.out.println("\n" + ANSI_RESET);
    }

    // ---------------------------------------------------------------------------------------------------------------//

    // Handles the cross-cutting concern of the Spring application (exception handling)
    // If we get a 'good' error (i.e. flagging user errors like providing the wrong password on login) the exception is
    // printed in green.
    // If we get a 'bad' error (i.e. unexpected problems like SQL errors during normal operation) the exception is
    // logged in red with a black background.
    @ExceptionHandler
    public final ResponseEntity handleExceptions(Exception e, WebRequest request){
        // To pass on later
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status;

        // Individualized handling
        // "Expected errors" are printed in green, i.e. when someone isn't logged in or the input is faulty
        // "Unexpected errors" are printed in red with a black background, i.e. our code not working.
        if (e instanceof InvalidInputException){
            status = HttpStatus.BAD_REQUEST;
            this.log(e, "green");

        } else if (e instanceof NotLoggedInException){ // This one is returned here (just text in response)
            status = HttpStatus.UNAUTHORIZED;
            this.log(e, "green");
            String errorMessage = "You must be logged in to perform this action";

            return ResponseEntity.status(status).body(errorMessage);

        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            this.log(e, "red", "black");

            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, e, WebRequest.SCOPE_REQUEST);

        }

        // Create and return the response containing the exception and status (headers are empty).
        return new ResponseEntity<Exception>(e, headers, status);

    }

    // ---------------------------------------------------------------------------------------------------------------//

    // Return the correct ansi string given the color name. Unrecognized values return an empty string (nothing)

    // Text
    private static String name2ansiText(String color){
        switch ( color.toLowerCase() ) {
            case "black":
                return ANSI_BLACK;
            case "red":
                return ANSI_RED;
            case "yellow":
                return ANSI_YELLOW;
            case "green":
                return ANSI_GREEN;
            case "cyan":
                return ANSI_CYAN;
            case "blue":
                return ANSI_BLUE;
            case "purple":
                return ANSI_PURPLE;
            case "white":
                return ANSI_WHITE;
            default:
                return "";
        }
    }

    // Background
    private static String name2ansiBackground(String color){
        switch ( color.toLowerCase() ) {
            case "black":
                return ANSI_BLACK_BACKGROUND;
            case "red":
                return ANSI_RED_BACKGROUND;
            case "yellow":
                return ANSI_YELLOW_BACKGROUND;
            case "green":
                return ANSI_GREEN_BACKGROUND;
            case "cyan":
                return ANSI_CYAN_BACKGROUND;
            case "blue":
                return ANSI_BLUE_BACKGROUND;
            case "purple":
                return ANSI_PURPLE_BACKGROUND;
            case "white":
                return ANSI_WHITE_BACKGROUND;
            default:
                return "";
        }
    }

}
