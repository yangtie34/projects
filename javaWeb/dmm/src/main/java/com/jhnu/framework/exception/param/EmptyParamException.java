package com.jhnu.framework.exception.param;

public class EmptyParamException extends ParamException{

	private static final long serialVersionUID = -4735094282252057112L;

	public EmptyParamException() {
        super();
    }

    public EmptyParamException(String message) {
        super("空参数异常："+message);
    }

    public EmptyParamException(Throwable cause) {
        super(cause);
    }

    public EmptyParamException(String message, Throwable cause) {
        super("空参数异常："+message, cause);
    }

}
