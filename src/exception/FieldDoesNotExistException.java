package exception;

public class FieldDoesNotExistException extends InterpreterException {

	private static final long serialVersionUID = 1238626493926332979L;

	public FieldDoesNotExistException() {

	}

	public FieldDoesNotExistException(String s) {
		super("Feild " + s + " Does Not Exist");
	}

}
