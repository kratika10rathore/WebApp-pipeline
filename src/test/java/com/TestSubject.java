package com;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;


@RunWith(JUnitPlatform.class)
public class TestSubject {
	Subject subj = new Subject();
	
	
	@Test
	public void testSubjectEquals() {
	   System.out.println("Hello! Welcome to Maths world..");
	   Assertions.assertEquals(subj.Maths(), "Hello! Welcome to Maths world..");
	}
	@Test
	public void testSubjectFalse() {
	   
	   Assertions.assertFalse(false, subj.Maths());
	}
	@Test
	public void testSubjectNotEquals() {
	   
	   Assertions.assertNotEquals("hello", subj.Maths(), "expected and actual strings are different");
	}
	
	@Test
	public void testSubjectNotNull() {
	   
	   Assertions.assertNotNull(subj.Maths(), "string is not null");
	}
	@Test
	public void testSubjectNotSame() {
	   
	   Assertions.assertNotSame("Hello world", subj.Maths(), "expected and actual strings are different");
	}
	
	@Test
	public void testSubject1Equals() {
	   System.out.println("Welcome to Science world");
	   Assertions.assertEquals(subj.Science(), "Welcome to Science world");
	}
	@Test
	public void testSubject1False() {
	   
	   Assertions.assertFalse(false, subj.Science());
	}
	@Test
	public void testSubject1NotEquals() {
	   
	   Assertions.assertNotEquals("hello", subj.Science(), "expected and actual strings are different");
	}
	
	@Test
	public void testSubject1NotNull() {
	   
	   Assertions.assertNotNull(subj.Science(), "string is not null");
	}
	@Test
	public void testSubject1NotSame() {
	   
	   Assertions.assertNotSame("Hello world", subj.Science(), "expected and actual strings are different");
	}
	
	
}
	