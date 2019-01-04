package demo.order.consumer;

import com.alibaba.fastjson.JSON;
import demo.manager.pojo.TbOrderItem;
import demo.manager.pojo.TbOrderShipping;
import demo.order.dao.OrderDao;
import demo.order.inter.OrderBean;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 黄敏雅 on 2019/1/4 0004.
 */
public class OrderConsumer implements MessageListenerConcurrently {
    @Resource
    private OrderDao orderDao;
    @Override
    @Transactional(value = "id_txManager",propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,readOnly = false,rollbackFor = Exception.class)
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        //打印到console，查看消息
        MessageExt msg = list.get(0);//因为配置了每次只拉取一条消息，list(msg)只有一个消息
        System.out.println(msg.toString());
        String topic = msg.getTopic();
        System.out.println("topic = " + topic);
        byte[] body = msg.getBody();
        System.out.println("body:  " + new String(body));
        String keys = msg.getKeys();
        System.out.println("keys = " + keys);
        System.out.println(keys + "处理完毕");
        System.out.println("-----------------------------------------------");
        //处理消息
        try {
            OrderBean orderBean = JSON.parseObject(new String(body), OrderBean.class);
            List<TbOrderItem> orderItems = orderBean.getOrderItems();
            TbOrderShipping orderShipping = orderBean.getOrderShipping();
            orderDao.insertOrder(orderBean);
            orderDao.insertOrderItems(orderItems);
            orderDao.insertOrderShipping(orderShipping);
        } catch (Exception e) {
            e.printStackTrace();
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        //设置当前次消息的处理结果
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
