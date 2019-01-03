package demo.manager.service.inter;

import demo.manager.pojo.TbItem;
import demo.manager.pojo.TbItemDesc;

import java.io.Serializable;

/**
 * Created by 黄敏雅 on 2019/1/3 0003.
 */
public class MQBean implements Serializable{
    private TbItem goodBean;
    private TbItemDesc tbItemDesc;
    private MQAction action=MQBean.MQAction.ADD;
    public enum MQAction{
        ADD,DELETE,MODIFY_NUM
    }

    public TbItemDesc getTbItemDesc() {
        return tbItemDesc;
    }

    public void setTbItemDesc(TbItemDesc tbItemDesc) {
        this.tbItemDesc = tbItemDesc;
    }

    public TbItem getGoodBean() {
        return goodBean;
    }

    public void setGoodBean(TbItem goodBean) {
        this.goodBean = goodBean;
    }

    public MQAction getAction() {
        return action;
    }

    public void setAction(MQAction action) {
        this.action = action;
    }
}
