package demo.manager.consumer;

import com.alibaba.fastjson.JSON;
import demo.common.FreemarkerCacheUtil;
import demo.manager.dao.GoodsItemDao;
import demo.manager.pojo.TbItem;
import demo.manager.pojo.TbItemDesc;
import demo.manager.service.inter.MQBean;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 黄敏雅 on 2019/1/3 0003.
 */
public class ManagerConsumer implements MessageListenerConcurrently {
    @Resource
    private FreemarkerCacheUtil freemarkerCacheUtil;
    @Value("${FDFS_URL}")
    private String FDFS_URL;
    @Resource
    private GoodsItemDao goodsItemDao;
    @Override
    @Transactional(value = "id_txManager",propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,readOnly = false,rollbackFor = Exception.class)
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
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
            switch (mqBean.getAction()){
                case ADD:
                    TbItem tbItem = mqBean.getGoodBean();
                    goodsItemDao.insertItem(tbItem);
                    TbItemDesc tbItemDesc = mqBean.getTbItemDesc();
                    String tbItemId = tbItem.getId();
                    tbItemDesc.setItemId(Long.parseLong(tbItemId));
                    goodsItemDao.insertItemDesc(tbItemDesc);

                    System.out.println("生成新商品ftl文件"+tbItemId);
                    String image = tbItem.getImage();
                    String[] images = image.split(",");
                    for (int i = 0; i < images.length; i++) {
                        images[i]=FDFS_URL+images[i];
                    }
                    freemarkerCacheUtil.addAttribute("item",tbItem);
                    freemarkerCacheUtil.addAttribute("images",images);
                    freemarkerCacheUtil.addAttribute("itemDesc",tbItemDesc);
                    freemarkerCacheUtil.genFtl("item.ftl",tbItemId);
                    break;
                case DELETE:
                    break;
                case MODIFY_NUM:
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