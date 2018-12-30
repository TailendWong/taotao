package demo.common;

import java.io.Serializable;

/**
 * Created by 黄敏雅 on 2018/12/30 0030.
 */
public class AjaxResult implements Serializable{
    public static final int OK=200;
    public static final int Fail=0;
    private boolean success=false;
    private int status;
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
