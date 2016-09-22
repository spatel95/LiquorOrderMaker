package exception;

public class NonExistentTableException extends InterpreterException {

	private static final long serialVersionUID = 8521161578186321742L;

	public NonExistentTableException() {

	}

	public NonExistentTableException(String s) {
		super(s);
	}

}
