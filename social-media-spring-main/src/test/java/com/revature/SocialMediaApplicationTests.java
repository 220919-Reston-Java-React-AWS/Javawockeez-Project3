package com.revature;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@SpringBootTest
class SocialMediaApplicationTests {

	SocialMediaApplication app = new SocialMediaApplication();

	@Test
	void contextLoads() {
	}

	@Test
	void doSomethingAfterStartup_DisplaysCorrectly(){
		String expectedString = "\n\t\t====== APPLICATION ACTIVE ======\n";
		String printlnEnding = "\r\n"; // It took me so long to figure this out

		// Direct console output to a place we can observe it in-program
		PrintStream originalOut = System.out;
		PrintStream originalErr = System.err;
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		ByteArrayOutputStream errContent = new ByteArrayOutputStream();

		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));

		// Actually run the method
		app.doSomethingAfterStartup();

		// Compare results
		Assertions.assertEquals(expectedString + printlnEnding, outContent.toString() );

		// Clean-up
		System.setOut(originalOut);
		System.setErr(originalErr);
	}

}
