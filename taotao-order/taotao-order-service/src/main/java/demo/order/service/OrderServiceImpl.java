package demo.order.service;

import com.alibaba.fastjson.JSON;
import demo.manager.pojo.TbOrderItem;
import demo.manager.pojo.TbOrderShipping;
import demo.order.inter.OrderBean;
import demo.order.inter.OrderService;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by 黄敏雅 on 2019/1/4 0004.
 */
@Service
public class OrderServiceImpl implements OrderService{
    @Value("${topic}")
    private String topic;
    @Resource
    private DefaultMQProducer defaultMQProducer;
    @Override
    public boolean createOrder(OrderBean orderBean,String userid) {
        Date date = new Date();
        String orderID = UtilGenOrderID.genOrderID("1", "2", "3", "1008611");
        orderBean.setOrderId(orderID);
        orderBean.setStatus(1);
        orderBean.setUserId(userid);
        orderBean.setCreateTime(date);
        orderBean.setUpdateTime(date);

        List<TbOrderItem> orderItems = orderBean.getOrderItems();
        for (TbOrderItem orderItem : orderItems) {
            orderItem.setOrderId(orderID);
        }

        TbOrderShipping orderShipping = orderBean.getOrderShipping();
        orderShipping.setOrderId(orderID);
        orderShipping.setCreated(date);
        orderShipping.setUpdated(date);

        String json = JSON.toJSONString(orderBean);
        Message message = new Message(topic, "", userid+"-" + System.currentTimeMillis(), json.getBytes());
        SendResult sendResult=null;
        try {
            sendResult = defaultMQProducer.send(message);
            return sendResult.getSendStatus()== SendStatus.SEND_OK;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
