package com.jhnu.system.permiss.exception;

public class SaveUserException extends UserException{

	private static final long serialVersionUID = 1L;
	
	  /**
     * Creates a new UserException.
     */
    public SaveUserException() {
        super();
    }

    /**
     * Constructs a new UserException.
     *
     * @param message the reason for the exception
     */
    public SaveUserException(String message) {
        super("保存用户异常："+message);
    }

    /**
     * Constructs a new UserException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public SaveUserException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new UserException.
     *
     * @param message the reason for the exception
     * @param cause   the underlying Throwable that caused this exception to be thrown.
     */
    public SaveUserException(String message, Throwable cause) {
        super("保存用户异常："+message, cause);
    }

}
