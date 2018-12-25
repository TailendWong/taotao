package demo.order.inter;

import demo.manager.pojo.TbItem;
import demo.manager.pojo.TbOrder;
import demo.manager.pojo.TbOrderShipping;

import java.util.List;

/**
 * Created by 黄敏雅 on 2018/12/25 0025.
 */
public class OrderBean extends TbOrder{
    private List<TbItem> orderItems;
    private TbOrderShipping orderShipping;

    public List<TbItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
