import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;

public class RPNStream {
	/* a Reverse Polish Notation calculator accepting stream input
	 * read values, one per line, separated by newlines (\n) from StdIn
	 * output is printed to StOut upon EOF (Ctrl-D)
	 * */	

	public static void main(String[] args) {
		// read RPN expressions, one line at a time, from StdIn
		// EOF (Ctrl-D) exits and returns contents of the stack
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Stack<Integer> st = new Stack<Integer>();
		while (true) {
			String valStr = null;
			try {
				valStr = br.readLine();
			} catch (IOException ioe) {
				System.err.println("IO error trying to reading input.");
				System.exit(1);
			}
			if (valStr == null) {
				// print contents of the stack to StdOut and exit
				printStack(st);
				System.exit(0);
			} else {
				// send trimmed value to calculate method to update stack
				String valStrTrimmed = valStr.trim();
				st = calculate(st, valStrTrimmed);
			}
		}
	}

	public static Stack<Integer> calculate(Stack<Integer> st, String val) {
		if (isInteger(val)) {
			// push integer value onto the stack
			st.push(Integer.valueOf(val));
		} else {
			// test for valid operation symbol
			String[] validOps = { "d", "p", "+", "-", "*", "/", "%" };
			if (Arrays.asList(validOps).contains(val)) {
				if (!st.empty()) {
					// every operation uses top value in the stack
					Integer a = st.pop();
					if (val.equals("d")) {
						// duplicate top value on the stack
						st.push(a);
						st.push(a);
					} else if (val.equals("p")) {
						// pop top value off the stack (uncomment to print)
						// System.out.println(a);
					} else if (!st.empty()) {
						// remaining binary arithmetic operations use next value
						// as well
						Integer b = st.pop();
						if (val.equals("+")) {
							st.push(b + a);
						} else if (val.equals("-")) {
							st.push(b - a);
						} else if (val.equals("*")) {
							st.push(b * a);
						} else if (val.equals("/")) {
							st.push(b / a);
						} else if (val.equals("%")) {
							st.push(b % a);
						}
					} else {
						// push one value back onto the stack
						st.push(a);
						printEmptyStack(val);
					}
				} else {
					printEmptyStack(val);
				}
			} else {
				printParseError(val);
			}
		}
		return st;
	}

	public static void printParseError(String val) {
		// element was neither value nor operation
		System.err.println("Error:  couldn't parse '" + val
				+ "' as an integer or operator.");
	}

	public static void printEmptyStack(String val) {
		// stack doesn't have enough values for operation
		System.err.println("Error:  insufficient input for '" + val
				+ "' operator.");
	}

	public static boolean isInteger(String val) {
		// tries to evaluate string as integer, returning boolean
		try {
			Integer.valueOf(val);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static void printStack(Stack<Integer> st) {
		// copy contents of stack into another stack in reverse
		Stack<Integer> stOut = new Stack<Integer>();
		while (!st.empty()) {
			stOut.push(st.pop());
		}
		// print contents of stack to StdOut (FIFO order)
		while (!stOut.empty()) {
			System.out.print("" + stOut.pop() + " ");
		}
		// trailing whitespace
		System.out.println();
	}
}
