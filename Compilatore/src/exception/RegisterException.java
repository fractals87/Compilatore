package exception;

public class RegisterException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RegisterException(String errorMessage) {
        super(errorMessage);
    }
}
