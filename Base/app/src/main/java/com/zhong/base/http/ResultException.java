package com.zhong.base.http;

/**
 * Created by zhong on 16/7/4.
 */
public class ResultException extends RuntimeException {

    public static final int USER_NOT_EXIST = 100;

    public ResultException(int resultCode) {
        this(getExceptionMessage(resultCode));
    }

    private ResultException(String detailMessage) {
        super(detailMessage);
    }


    private static String getExceptionMessage(int code){
        String message = "";
        switch (code) {
            case USER_NOT_EXIST:
                message = "该用户不存在";
                break;
            default:
                message = "未知错误";

        }
        return message;
    }
}

