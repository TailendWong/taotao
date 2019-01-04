package demo.order.dao;

import demo.manager.pojo.TbOrderItem;
import demo.manager.pojo.TbOrderShipping;
import demo.order.inter.OrderBean;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 黄敏雅 on 2019/1/4 0004.
 */
@Repository
public interface OrderDao {
    @Insert("insert into tb_order values(#{orderId},#{payment},#{paymentType},#{postFee},#{status},#{createTime},#{updateTime},#{paymentTime},#{consignTime},#{endTime},#{closeTime},#{shippingName},#{shippingCode},#{userId},#{buyerMessage},#{buyerNick},#{buyerRate})")
    public void insertOrder(OrderBean orderBean);

    public void insertOrderItems(List<TbOrderItem> OrderItems);

    @Insert("insert into tb_order_shipping values(#{orderId},#{receiverName},#{receiverPhone},#{receiverMobile},#{receiverState},#{receiverCity},#{receiverDistrict},#{receiverAddress},#{receiverZip},#{created},#{updated})")
    public void insertOrderShipping(TbOrderShipping tbOrderShipping);

}
