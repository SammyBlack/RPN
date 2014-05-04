import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Stack;

public class RPN {
	/* a Reverse Polish Notation calculator
	 * read complete RPN expressions, one per line, separated by whitespace from StdIn
	 * output is printed to StOut
	 * EOF (Ctrl-D) to quit
	 * */	

	public static void main(String[] args) {
		// result of calculation printed after newline \n
		// until EOF
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			// disable to give calculator prompt
			// System.out.print(">>>  ");
			String valStr = null;
			try {
				valStr = br.readLine();
			} catch (IOException ioe) {
				System.err.println("IO error trying to reading input.");
				System.exit(1);
			}
			if (valStr == null) {
				// exit
				System.out.println();
				System.exit(0);
			} else {
				// send trimmed and parsed value array to calculate method
				String valStrTrimmed = valStr.trim();
				String delims = "[ ]+";
				String[] vals = valStrTrimmed.split(delims);
				calculate(vals);
			}
		}
	}

	public static void calculate(String[] vals) {
		Stack<Integer> st = new Stack<Integer>();
		// loop through input values
		for (int i = 0; i < vals.length; i++) {
			String val = vals[i];
			if (isInteger(val)) {
				// push integer value onto the stack
				st.push(Integer.valueOf(val));
			} else {
				String[] validOps = { "d", "p", "+", "-", "*", "/", "%" };
				if (Arrays.asList(validOps).contains(val)) {
					// valid operation symbol
					try {
						// every operation uses top value in the stack
						Integer a = st.pop();
						if (val.equals("d")) {
							// duplicate top value on the stack
							st.push(a);
							st.push(a);
						} else if (val.equals("p")) {
							// pop top value off the stack and print it
							System.out.println(a);
						} else {
							// remaining binary arithmetic operations use next
							// value as well
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
						}
					} catch (EmptyStackException eInsuff) {
						// stack doesn't have enough values for operation
						System.err.println("Error:  insufficient input for '"
								+ val + "' operator.");
						System.err.println();
						return;
					}
				} else {
					// element was neither value nor operation
					System.err.println("Error:  couldn't parse '" + val
							+ "' as an integer or operator.");
					System.err.println();
					return;
				}
			}
		}
		// print contents of the stack to StdOut
		printStack(st);
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
		System.out.println();
	}
}
