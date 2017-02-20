package com.yiyun.framework.exception.handle;

public class HandleException extends Exception{

	private static final long serialVersionUID = -5616031045348279390L;

	public HandleException() {
        super();
    }

    public HandleException(String message) {
        super("业务处理异常："+message);
    }

    public HandleException(Throwable cause) {
        super(cause);
    }

    public HandleException(String message, Throwable cause) {
        super("业务处理异常："+message, cause);
    }

}
