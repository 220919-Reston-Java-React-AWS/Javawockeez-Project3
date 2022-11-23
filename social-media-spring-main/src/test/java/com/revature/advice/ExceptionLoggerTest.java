package com.revature.advice;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@SpringBootTest
public class ExceptionLoggerTest {

    PrintStream originalOut = System.out;
    PrintStream originalErr = System.err;

    ByteArrayOutputStream outContent;
    ByteArrayOutputStream errContent;

    @BeforeEach
    public void initTests(){
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void closeTests(){
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    void throwAfterNRecursions(Exception e, int n) throws Exception {
        if (n < 1){
            throw e;
        }
        throwAfterNRecursions(e, n-1);
    }

    @Test
    void log_throwException_EXPECT_itPrinted(){
        String testMessage = "Test";

        try{
           throw new Exception(testMessage);
        } catch (Exception e) {
            ExceptionLogger.log(e);

            String expectedSubstring = "\r\n" + e.getClass().getCanonicalName() + "\r\n\t" + testMessage;

            Assertions.assertEquals(testMessage, e.getMessage());
            Assertions.assertEquals(expectedSubstring, outContent.toString().substring(0, expectedSubstring.length()));
        }
    }

    @Test
    void log_throwRuntimeException_EXPECT_itPrinted(){
        String testMessage = "Test";

        try{
            throw new RuntimeException(testMessage);
        } catch (Exception e) {
            ExceptionLogger.log(e);

            String expectedSubstring = "\r\n" + e.getClass().getCanonicalName() + "\r\n\t" + testMessage;

            Assertions.assertEquals(testMessage, e.getMessage());
            Assertions.assertEquals(expectedSubstring, outContent.toString().substring(0, expectedSubstring.length()));
        }
    }

    @Test
    void log_throwException_Stack2_INPUT_GREEN_EXPECT_itPrinted(){
        String testMessage = "Test";
        String ANSI_GREEN = "\u001B[32m";

        try{
            throwAfterNRecursions(new ArithmeticException(testMessage), 2);
        } catch (Exception e) {
            e.printStackTrace(System.err);

            ExceptionLogger.log(e, "green");

            String expectedSubstring = ANSI_GREEN + "\r\n" + e.getClass().getCanonicalName() + "\r\n\t" + testMessage;

            Assertions.assertEquals(testMessage, e.getMessage());
            Assertions.assertEquals(expectedSubstring, outContent.toString().substring(0, expectedSubstring.length()));

//            System.setOut(originalOut);
//            System.out.println(outContent.toString());
//            System.out.println(errContent.toString());
//            for (StackTraceElement stack : e.getStackTrace() ){
//                System.out.println(stack.toString());
//            }
        }
    }
}
