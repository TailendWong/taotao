package demo.common;

import java.io.Serializable;

/**
 * Created by 黄敏雅 on 2018/12/13 0013.
 */
public class EasyUITreeBean implements Serializable {
    private long id;
    private String state;
    private String text;

    public static final String STATE_CLOSED="closed";
    public static final String STATE_OPEN="open";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
