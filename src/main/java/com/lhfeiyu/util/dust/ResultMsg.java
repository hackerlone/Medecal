package com.lhfeiyu.util.dust;

/** 处理结果返回类  */
public class ResultMsg {
    /** 处理成功标记  */
    private boolean success;
    /** 处理结果代码，对应不同的返回消息  */
    private int code;
    /** 返回的消息  */
    private String msg;

    public ResultMsg(){}
    public ResultMsg(boolean success,int code,String msg){
        this.success = success;
        this.code = code;
        this.setMsg(msg);
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
