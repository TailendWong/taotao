package demo.common;

import java.io.Serializable;

/**
 * Created by 黄敏雅 on 2018/12/13 0013.
 */
public class EasyUIPicUploadBean {
    private Integer error;
    private String url;
    private String message;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
