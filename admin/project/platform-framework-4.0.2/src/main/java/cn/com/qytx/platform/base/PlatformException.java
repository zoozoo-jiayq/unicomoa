package cn.com.qytx.platform.base;

/**自定义平台异常对象
 * @author jiayongqiang
 *
 */
public class PlatformException extends RuntimeException {
    public PlatformException() {
    }

    public PlatformException(String message) {
        super(message);
    }

    public PlatformException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlatformException(Throwable cause) {
        super(cause);
    }


}
