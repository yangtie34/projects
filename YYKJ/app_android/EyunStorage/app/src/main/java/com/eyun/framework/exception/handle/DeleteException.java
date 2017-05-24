package com.eyun.framework.exception.handle;

public class DeleteException extends HandleException{

	private static final long serialVersionUID = -3412081990064175317L;

	public DeleteException() {
        super();
    }

    public DeleteException(String message) {
        super("删除异常："+message);
    }

    public DeleteException(Throwable cause) {
        super(cause);
    }

    public DeleteException(String message, Throwable cause) {
        super("删除异常："+message, cause);
    }

}
