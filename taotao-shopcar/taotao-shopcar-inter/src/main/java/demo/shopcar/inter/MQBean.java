package demo.shopcar.inter;

/**
 * Created by 黄敏雅 on 2018/12/22 0022.
 */
public class MQBean {
    private ShopCarBean shopCarBean;
    private MQAction action=MQBean.MQAction.ADD;
    public enum MQAction{
        ADD,DELETE,MODIFY_NUM
    }

    public ShopCarBean getShopCarBean() {
        return shopCarBean;
    }

    public void setShopCarBean(ShopCarBean shopCarBean) {
        this.shopCarBean = shopCarBean;
    }

    public MQAction getAction() {
        return action;
    }

    public void setAction(MQAction action) {
        this.action = action;
    }
}
