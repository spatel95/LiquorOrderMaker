package exception;

public abstract class InterpreterException extends Exception {

	private static final long serialVersionUID = 7984873056802627488L;

	protected InterpreterException() {

	}

	protected InterpreterException(String s) {
		super(s);
	}

}
