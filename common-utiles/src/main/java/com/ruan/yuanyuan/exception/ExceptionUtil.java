package com.ruan.yuanyuan.exception;

/**
 * User: ruanyuanyuan
 * Date: 2019-07-26
 * Time: 15:45
 * version:
 * Description: 异常工具类
 */
public class ExceptionUtil {

    private static final Integer ORDER = 1;
    private static final Integer PRODUCT = 2;
    private static final Integer MESSAGE = 3;
    private static final Integer ORDER_PAY = 4;
    private static final Integer BUYER = 5;
    private static final Integer POWERETC = 6;

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
        ORDER_PAY_FAIL(ORDER_PAY + 2, "支付失败");

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
}
