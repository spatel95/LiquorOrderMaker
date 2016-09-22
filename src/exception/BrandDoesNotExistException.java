package exception;

public class BrandDoesNotExistException extends InterpreterException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -639512179604146119L;
	
	public BrandDoesNotExistException(){
		
	}
	
	public BrandDoesNotExistException(String s){
		super("Brand" +s+ "Does Not Exist");
	}
}
