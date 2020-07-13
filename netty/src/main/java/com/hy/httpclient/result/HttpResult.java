package com.hy.httpclient.result;

public class HttpResult {
    private String url;

    private String msg;

    private boolean success;

    public HttpResult() {
    }

    public HttpResult(String url, String msg, boolean success) {
        this.url = url;
        this.msg = msg;
        this.success = success;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "url='" + url + '\'' +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                '}';
    }
}
