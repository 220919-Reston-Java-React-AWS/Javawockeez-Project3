package com.revature;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@SpringBootTest
class SocialMediaApplicationTests {

	@Test
	void contextLoads() {
	}

//	@Test
//	void doSomethingAfterStartup_DisplaysCorrectly(){
//		PrintStream originalOut = System.out;
//		PrintStream originalErr = System.err;
//		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//		ByteArrayOutputStream errContent = new ByteArrayOutputStream();
//		SocialMediaApplication app = new SocialMediaApplication();
//
//		System.setOut(new PrintStream(outContent));
//		System.setErr(new PrintStream(errContent));
//
//		app.doSomethingAfterStartup();
//
//		Assertions.assertEquals("\n\t\t====== APPLICATION ACTIVE ======\n", outContent.toString());
//
//		System.setOut(originalOut);
//		System.setErr(originalErr);
//		System.out.println(outContent.toString());
//	}

}
