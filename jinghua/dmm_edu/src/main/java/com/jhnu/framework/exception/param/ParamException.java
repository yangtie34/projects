package com.jhnu.framework.exception.param;

public class ParamException extends Exception{

	private static final long serialVersionUID = 5291047235053933571L;

	public ParamException() {
        super();
    }

    public ParamException(String message) {
        super("参数异常："+message);
    }

    public ParamException(Throwable cause) {
        super(cause);
    }

    public ParamException(String message, Throwable cause) {
        super("参数异常："+message, cause);
    }

}
