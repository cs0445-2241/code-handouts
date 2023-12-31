/**
 * A class that evaluates an infix expression.
 * Based on pseudocode in Segment 5.21.
 * 
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 4.0
 */
public class Infix {
	public static double evaluateInfix(String infix) {
		Double operandOne, operandTwo, result;
		Character top;
		char topOperator;
		StackInterface<Character> operatorStack = new ArrayStack<>();
		StackInterface<Double> valueStack = new ArrayStack<>();

		int characterCount = infix.length();

		for (int index = 0; index < characterCount; index++) {
			char nextCharacter = infix.charAt(index);

			switch (nextCharacter) {
				case 'a':
				case 'b':
				case 'c':
				case 'd':
				case 'e':
					valueStack.push(valueOf(nextCharacter));
					break;

				case '(':
				case '^':
					operatorStack.push(nextCharacter);
					break;

				case ')': // Stack is not empty if infix expression is valid
					top = operatorStack.pop();
					topOperator = top.charValue();

					while (topOperator != '(') {
						operandTwo = valueStack.pop();
						operandOne = valueStack.pop();
						result = compute(operandOne, operandTwo, topOperator);
						valueStack.push(result);
						top = operatorStack.pop();
						topOperator = top.charValue();
					} // end while
					break;

				case '+':
				case '-':
				case '*':
				case '/':
					boolean done = false;
					while (!operatorStack.isEmpty() && !done) {
						top = operatorStack.peek();
						topOperator = top.charValue();

						if (precedence(nextCharacter) <= precedence(topOperator)) {
							operatorStack.pop();
							operandTwo = valueStack.pop();
							operandOne = valueStack.pop();
							result = compute(operandOne, operandTwo, topOperator);
							valueStack.push(result);
						} else
							done = true;
					} // end while

					operatorStack.push(nextCharacter);
					break;

				default:
					break; // Ignore unexpected characters
			} // end switch
		} // end for

		while (!operatorStack.isEmpty()) {
			top = operatorStack.pop();
			topOperator = top.charValue();
			operandTwo = valueStack.pop();
			operandOne = valueStack.pop();
			result = compute(operandOne, operandTwo, topOperator);
			valueStack.push(result);
		} // end while

		result = valueStack.peek();
		return result.doubleValue();
	} // end evaluateInfix

	private static int precedence(char operator) {
		switch (operator) {
			case '(':
			case ')':
				return 0;
			case '+':
			case '-':
				return 1;
			case '*':
			case '/':
				return 2;
			case '^':
				return 3;
		} // end switch

		return -1;
	} // end precedence

	private static double valueOf(char variable) {
		switch (variable) {
			case 'a':
				return 2.0;
			case 'b':
				return 3.0;
			case 'c':
				return 4.0;
			case 'd':
				return 5.0;
			case 'e':
				return 6.0;
		} // end switch

		return 0;
	} // end valueOf

	private static Double compute(Double operandOne, Double operandTwo, char operator) {
		double result;

		switch (operator) {
			case '+':
				result = operandOne.doubleValue() + operandTwo.doubleValue();
				break;

			case '-':
				result = operandOne.doubleValue() - operandTwo.doubleValue();
				break;

			case '*':
				result = operandOne.doubleValue() * operandTwo.doubleValue();
				break;

			case '/':
				result = operandOne.doubleValue() / operandTwo.doubleValue();
				break;

			case '^':
				result = Math.pow(operandOne.doubleValue(), operandTwo.doubleValue());
				break;

			default: // Unexpected character
				result = 0;
				break;
		} // end switch

		return result;
	} // end compute
} // end Infix
