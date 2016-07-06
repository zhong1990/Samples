package com.zhong.base.http;

/**
 * Created by zhong on 16/7/4.
 */
public class HttpResponse<T> {

    private boolean    error;
    private T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
