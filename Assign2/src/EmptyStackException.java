/**
 * 
 * @author Jin This class is called during a try-catch exception is raised for
 *         an EmptyStackException (The stack given has no data items in it) It
 *         also sends the String "The stack is empty" to its super classes as an
 *         appropriate response.
 *
 */
public class EmptyStackException extends Exception {

	public EmptyStackException() {
		super("The stack is empty");
	}
}
