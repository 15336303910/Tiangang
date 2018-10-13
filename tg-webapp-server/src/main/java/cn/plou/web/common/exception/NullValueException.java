package cn.plou.web.common.exception;

/**
 * 
 *
 * @Author : WangJiWei
 * @Date : 2017年11月29日上午8:44:46
 *
 * @Comments : 空值异常
 *
 */
public class NullValueException extends Exception {

    private static final long serialVersionUID = 1L;

    public NullValueException(String message, Throwable cause) {
	super(message, cause);
    }

    public NullValueException(String message) {
	super(message);
    }

    public NullValueException(Throwable cause) {
	super(cause);
    }

    public NullValueException() {
	super();
    }
}
