package com.eyun.framework.exception.param;

public class FormatParamException extends ParamException {
	
	private static final long serialVersionUID = -3294913066956929526L;

	public FormatParamException() {
        super();
    }

    public FormatParamException(String message) {
        super("数据格式异常："+message);
    }

    public FormatParamException(Throwable cause) {
        super(cause);
    }

    public FormatParamException(String message, Throwable cause) {
        super("数据格式异常："+message, cause);
    }

}
