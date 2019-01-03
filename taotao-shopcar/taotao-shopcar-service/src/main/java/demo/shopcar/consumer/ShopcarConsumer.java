package demo.shopcar.consumer;

import com.alibaba.fastjson.JSON;
import demo.shopcar.dao.ShopCarDao;
import demo.shopcar.inter.MQBean;
import demo.shopcar.inter.ShopCarBean;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import javax.annotation.Resource;
import java.util.List;

public class ShopcarConsumer  implements MessageListenerConcurrently {
    @Resource
    private ShopCarDao shopCarDao;
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
        System.out.println("ConsumerLis.consumeMessage "+Thread.currentThread().getName());
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
            MQBean mqBean = JSON.parseObject(new String(body), MQBean.class);
            ShopCarBean shopCarBean = mqBean.getShopCarBean();
            switch (mqBean.getAction()){
                case ADD:
                    shopCarDao.addShopCarItem(shopCarBean);
                    break;
                case DELETE:
                    shopCarDao.delShopCarItem(shopCarBean.getTbItem());
                    break;
                case MODIFY_NUM:
                    shopCarDao.updateShopcarItemNum(shopCarBean.getTbItem(),shopCarBean.getNum());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        //设置当前次消息的处理结果
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}