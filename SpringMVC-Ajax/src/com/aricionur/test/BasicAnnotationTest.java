package com.aricionur.test;


public class BasicAnnotationTest {

	@BeforeClass
    public static void runOnceBeforeClass() {
        System.out.println("@BeforeClass - runOnceBeforeClass");
    }
	
}
