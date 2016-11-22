package com.jhnu.framework.exception.handle;

public class AddException extends HandleException{

	private static final long serialVersionUID = -3675717028521301093L;

	public AddException() {
        super();
    }

    public AddException(String message) {
        super("保存异常："+message);
    }

    public AddException(Throwable cause) {
        super(cause);
    }

    public AddException(String message, Throwable cause) {
        super("保存异常："+message, cause);
    }

}
