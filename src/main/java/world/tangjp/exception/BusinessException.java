package world.tangjp.exception;

public class BusinessException extends RuntimeException {
    private final int code;

    // 构造方法
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    // 获取状态码
    public int getCode() {
        return code;
    }
}