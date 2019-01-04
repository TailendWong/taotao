package demo.order.inter;

import demo.manager.pojo.TbItem;
import demo.manager.pojo.TbOrder;
import demo.manager.pojo.TbOrderItem;
import demo.manager.pojo.TbOrderShipping;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 黄敏雅 on 2018/12/25 0025.
 */
public class OrderBean extends TbOrder implements Serializable{
    private List<TbOrderItem> orderItems;
    private TbOrderShipping orderShipping;


    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
