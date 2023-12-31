/**
 * A driver that demonstrates the class Postfix.
 * 
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 4.1
 */
public class Driver {
	public static void main(String[] args) {
		System.out.println("Testing postfix expressions with\n" +
				"a = 2, b = 3, c = 4, d = 5, e = 6\n\n");

		testPostfix("a+b");
		testPostfix("(a + b) * c");
		testPostfix("a * b / (c - d)");
		testPostfix("a / b + (c - d)");
		testPostfix("a / b + c - d");
		testPostfix("a^b^c");
		testPostfix("(a^b)^c");
		testPostfix("a*(b/c+d)");

		System.out.println("Testing Question 6, Chapter 5:\n");
		testPostfix("(a+b)/(c-d)"); // Question 6a, Chapter 5
		testPostfix("a/(b-c)*d"); // Question 6b
		testPostfix("a-(b/(c-d)*e+f)^g"); // Question 6c
		testPostfix("(a-b*c)/(d*e^f*g+h)"); // Question 6d

		System.out.println("Testing Question 7, Chapter 5:\n");
		System.out.println("Q7a: ae+bd-/ : " + Postfix.evaluatePostfix("ae+bd-/") + "\n");
		System.out.println("Q7b: abc*d*- : " + Postfix.evaluatePostfix("abc*d*-") + "\n");
		System.out.println("Q7c: abc-/d* : " + Postfix.evaluatePostfix("abc-/d*") + "\n");
		System.out.println("Q7d: ebca^*+d- : " + Postfix.evaluatePostfix("ebca^*+d-") + "\n");
		System.out.println("\n\nDone.");
	} // end main

	public static void testPostfix(String infixExpression) {
		System.out.println("Infix:   " + infixExpression);
		String postfixExpression = Postfix.convertToPostfix(infixExpression);
		System.out.println("Postfix: " + postfixExpression);
		System.out.println("\n");
	} // end testPostfix
} // end Driver

/*
 * Testing postfix expressions with
 * a = 2, b = 3, c = 4, d = 5, e = 6
 * 
 * 
 * Infix: a+b
 * Postfix: ab+
 * 
 * 
 * Infix: (a + b) * c
 * Postfix: ab+c*
 * 
 * 
 * Infix: a * b / (c - d)
 * Postfix: ab*cd-/
 * 
 * 
 * Infix: a / b + (c - d)
 * Postfix: ab/cd-+
 * 
 * 
 * Infix: a / b + c - d
 * Postfix: ab/c+d-
 * 
 * 
 * Infix: a^b^c
 * Postfix: abc^^
 * 
 * 
 * Infix: (a^b)^c
 * Postfix: ab^c^
 * 
 * 
 * Infix: a*(b/c+d)
 * Postfix: abc/d+*
 * 
 * 
 * Testing Question 6, Chapter 5:
 * 
 * Infix: (a+b)/(c-d)
 * Postfix: ab+cd-/
 * 
 * 
 * Infix: a/(b-c)*d
 * Postfix: abc-/d*
 * 
 * 
 * Infix: a-(b/(c-d)*e+f)^g
 * Postfix: abcd-/e*f+g^-
 * 
 * 
 * Infix: (a-b*c)/(d*e^f*g+h)
 * Postfix: abc*-def^*g*h+/
 * 
 * 
 * Testing Question 7, Chapter 5:
 * 
 * Q7a: ae+bd-/ : -4.0
 * 
 * Q7b: abc*d*- : -58.0
 * 
 * Q7c: abc-/d* : -10.0
 * 
 * Q7d: ebca^*+d- : 49.0
 * 
 * 
 * 
 * Done.
 */