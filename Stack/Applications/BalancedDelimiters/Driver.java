/**
 * A driver that demonstrates the class BalanceChecker.
 * 
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 4.1
 */
public class Driver {
	public static void main(String[] args) {
		testBalance("a {b [c (d + e)/2 - f] + 1}"); // Seg. 5.10
		testBalance("[a {b / (c - d) + e/(f + g)} - h]"); // Question 3a (Chapter 5)
		testBalance("{a [b + (c + 2)/d ] + e) + f }"); // Question 3b
		testBalance("[a {b + [c (d+e) - f ] + g}"); // Question 3c

		System.out.println("\n\nDone.");
	} // end main

	public static void testBalance(String expression) {
		boolean isBalanced = BalanceChecker.checkBalance(expression);
		if (isBalanced)
			System.out.println(expression + " is balanced");
		else
			System.out.println(expression + " is not balanced");
	} // end testBalance
} // end Driver

/*
 * a {b [c (d + e)/2 - f] + 1} is balanced
 * [a {b / (c - d) + e/(f + g)} - h] is balanced
 * {a [b + (c + 2)/d ] + e) + f } is not balanced
 * [a {b + [c (d+e) - f ] + g} is not balanced
 * 
 * 
 * Done.
 */