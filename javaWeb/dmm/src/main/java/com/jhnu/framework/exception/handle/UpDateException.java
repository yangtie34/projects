package com.jhnu.framework.exception.handle;

public class UpDateException extends HandleException{

	private static final long serialVersionUID = 1836038005349397822L;

	public UpDateException() {
        super();
    }

    public UpDateException(String message) {
        super("更新异常："+message);
    }

    public UpDateException(Throwable cause) {
        super(cause);
    }

    public UpDateException(String message, Throwable cause) {
        super("更新异常："+message, cause);
    }

}
