package com.jhnu.system.permiss.exception;

public class UserException extends Exception{

	private static final long serialVersionUID = 1L;
	
	  /**
     * Creates a new UserException.
     */
    public UserException() {
        super();
    }

    /**
     * Constructs a new UserException.
     *
     * @param message the reason for the exception
     */
    public UserException(String message) {
        super("用户异常："+message);
    }

    /**
     * Constructs a new UserException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public UserException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new UserException.
     *
     * @param message the reason for the exception
     * @param cause   the underlying Throwable that caused this exception to be thrown.
     */
    public UserException(String message, Throwable cause) {
        super("用户异常："+message, cause);
    }

}
