package com.tim.crwodfunding.util;

/**
 * 用于统一项目中所有 Ajax 请求的返回值类型
 * @param <T>
 */
public class ResultEntity<T> {
    public final static String SUCCESS = "SUCCESS";
    public final static String FAILED = "FAILED";
    /**
     * 用来封装当前请求处理的结果是成功还是失败
     */
    private String result;
    /**
     * 请求处理失败时返回的错误消息
     */
    private String message;
    /**
     * 要返回的数据
     */
    private T data;

    public ResultEntity() {
    }

    public ResultEntity(String result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    /**
     * 请求处理成功且不需要返回数据
     * @return
     */
    public static <E> ResultEntity<E> successWithoutData(){
        return new ResultEntity<>(SUCCESS, null, null);
    }

    /**
     *请求处理成功且需要返回数据
     * @param data 要返回的数据
     * @param <E> 返回的数据类型
     * @return
     */
    public static <E> ResultEntity<E> successWithData(E data){
        return new ResultEntity<>(SUCCESS, null, data);
    }

    /**
     *请求处理失败后使用的工具方法
     * @param message 失败时返回的错误消息
     * @return
     */
    public static <E> ResultEntity<E> failed(String message) {
        return new ResultEntity<>(FAILED, message, null);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "result='" + result + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
