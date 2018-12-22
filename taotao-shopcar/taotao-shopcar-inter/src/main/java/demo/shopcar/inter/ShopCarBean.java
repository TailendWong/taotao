package demo.shopcar.inter;

import demo.manager.pojo.TbItem;

import java.io.Serializable;

/**
 * Created by 黄敏雅 on 2018/12/22 0022.
 */
public class ShopCarBean implements Serializable {
    private String userid;
    private TbItem tbItem;
    private Integer num;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public TbItem getTbItem() {
        return tbItem;
    }

    public void setTbItem(TbItem tbItem) {
        this.tbItem = tbItem;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
