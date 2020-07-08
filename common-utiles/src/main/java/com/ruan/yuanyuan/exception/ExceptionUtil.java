package com.ruan.yuanyuan.exception;

/**
 * User: ruanyuanyuan
 * Date: 2019-07-26
 * Time: 15:45
 * version:
 * Description: 异常工具类
 */
public class ExceptionUtil {

    private static final Integer SYSTEM=0;
    private static final Integer ORDER = 1;
    private static final Integer PRODUCT = 2;
    private static final Integer MESSAGE = 3;
    private static final Integer ORDER_PAY = 4;
    private static final Integer BUYER = 5;
    private static final Integer POWERETC = 6;
    private static final Integer USER = 7;

    public enum SystemExceptionEnum implements ExceptionInterface {
        SUCCESS(SYSTEM,"成功"),
        FAIL(SYSTEM-1,"失败"),
        NOT_TOKEN(SYSTEM+401,"Token不能为空"),
        SYSTEM_HYSTRIX(SYSTEM-99999,"接口不可用，进行接口降级"),
        UNAUTHORIZED(SYSTEM+401,"未授权"),
        ;
        private int code;
        private String message;

        SystemExceptionEnum(int code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public int getCode() {
            return code;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    public enum OrderExceptionEnum implements ExceptionInterface {

        ORDER_ID_NOT_NULL(ORDER + 1, "请选择订单"),
        ORDER_NAME_NOT_NULL(ORDER + 2, "订单名称不能为空"),
        ORDER_CREATE_FAIL(ORDER + 3, "创建订单失败"),
        ORDER_ORDER_SN_NOT_NULL(ORDER + 4, "订单编号不能为空"),
        ORDER_DETAIL_FAIL(ORDER + 5, "创建订单详情失败"),
        ORDER_PRODUCT_NOT_NULL(ORDER + 6, "请选择商品"),
        ORDER_NOT_EXITS(ORDER + 7, "未查询到该订单"),
        ORDER_UPDATE_FAIL(ORDER + 8, "修改订单失败");

        private int code;
        private String message;

        OrderExceptionEnum(int code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public int getCode() {
            return code;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    public enum ProductExceptionEnum implements ExceptionInterface {
        PRODUCT_ID_NOT_NULL(PRODUCT + 1, "请选择商品"),
        PRODUCT_NUM_NOT_NULL(PRODUCT + 2, "请填写商品数量");

        private int code;
        private String message;

        ProductExceptionEnum(int code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public int getCode() {
            return code;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    public enum MessageExceptionEnum implements ExceptionInterface {

        MESSAGE_ID_NOT_NULL(MESSAGE + 1, "消息ID不能为空"),
        MESSAGE_NOT_EXITS(MESSAGE + 2, "未查询到该条消息"),
        MESSAGE_UPDATE_FAIL(MESSAGE + 3, "消息修改失败");

        private int code;
        private String message;

        MessageExceptionEnum(int code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public int getCode() {
            return code;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    public enum OrderPayExceptionEnum implements ExceptionInterface {
        ORDER_PAY_CREATE_FAIL(ORDER_PAY + 1, "创建支付订单失败"),
        ORDER_PAY_FAIL(ORDER_PAY + 2, "支付失败"),
        ORDER_PAY_NOT_FOUND(ORDER_PAY+3,"未查询到支付订单")
        ;
        private int code;
        private String message;

        OrderPayExceptionEnum(int code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public int getCode() {
            return code;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }


    public enum BuyerExceptionEnum implements ExceptionInterface {
        BUYER_FIND_FAIL(BUYER + 1, "未查询到该用户"),
        BUYER_AMOUNT_DEDUCT_FAIL(BUYER + 2, "买家账户余额扣除失败");

        private int code;
        private String message;

        BuyerExceptionEnum(int code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public int getCode() {
            return code;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    public enum PowerEtcExceptionEnum implements ExceptionInterface {
        POWER_SAVE_FAIL(POWERETC + 1, "添加幂等消息失败"),

        ;

        private int code;
        private String message;

        PowerEtcExceptionEnum(int code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public int getCode() {
            return code;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    public enum UserExceptionEnum implements ExceptionInterface {
        POWER_SAVE_FAIL(USER + 1, "请输入用户信息"),
        USER_NOT_EXISTENT(USER+2,"用户不存在"),
        USER_NAME_NOT_NULL(USER+3,"请输入户名"),
        USER_PASSWORD_NOT_NULL(USER+4,"请输入密码"),
        USER_REMEMBER_NOT_NULL(USER+5,"请设置记住我"),
        USER_CODE_NOT_NULL(USER+6,"验证码不能为空"),
        USER_CODE_NOT_EQ(USER+7,"验证码不相等"),
        USER_LOGIN_PASSWORD_ERROR(USER+8,"密码错误"),
        USER_LOGIN_EXCESSIVE(USER+9,"用户登录失败次数过多"),
        USER_LOGIN_LOCKED(USER+10,"用户被锁定"),
        USER_LOGIN_DISABLE(USER+11,"用户被禁用"),
        USER_LOGIN_UNKNOWN(USER+12,"用户不存在"),
        USER_LOGIN_UNAUTHENTICATED(USER+13,"用户未授权"),
        USER_LOGIN_SUCCESS(USER+14,"登录成功"),
        USER_LOGIN_FAIL(USER+15,"登录失败"),
        USER_LOGOUT_SUCCESS(USER+16,"退出系统成功"),
        USER_ID_NOT_NULL(USER+17,"用户ID不能为空")

        ;

        private int code;
        private String message;

        UserExceptionEnum(int code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public int getCode() {
            return code;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }
}
