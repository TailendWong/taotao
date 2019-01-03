package demo.shopcar.service;

import com.alibaba.fastjson.JSON;
import demo.manager.pojo.TbItem;
import demo.manager.service.inter.GoodsItemService;
import demo.shopcar.dao.ShopCarDao;
import demo.shopcar.inter.MQBean;
import demo.shopcar.inter.ShopCarBean;
import demo.shopcar.inter.ShopCarResult;
import demo.shopcar.inter.ShopcarService;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 黄敏雅 on 2018/12/22 0022.
 */
@Service
public class ShopcarServiceImpl implements ShopcarService {
    @Resource
    private ShopCarDao shopCarDao;
    @Resource
    private GoodsItemService goodsItemService;
    @Resource
    private DefaultMQProducer defaultMQProducer;
    @Value("${topic}")
    private String topic;
    @Value("${FDFS_URL}")
    private String FDFS_URL;
    @Override
    public boolean addToShopCar(String userid, TbItem tbItem, Integer num) {
        tbItem.setImage(FDFS_URL+tbItem.getImage().split(",")[0]);
        MQBean mqBean = new MQBean();
        mqBean.setAction(MQBean.MQAction.ADD);
        ShopCarBean shopCarBean = new ShopCarBean();
        shopCarBean.setUserid(userid);
        shopCarBean.setTbItem(tbItem);
        shopCarBean.setNum(num);
        mqBean.setShopCarBean(shopCarBean);
        String json = JSON.toJSONString(mqBean);
        //放入队列
        Message message = new Message(topic, "", userid + "-" + System.currentTimeMillis(), json.getBytes());
        SendResult result=null;
        try {
            result = defaultMQProducer.send(message);
            System.out.println("id:" + result.getMsgId()+"result = " + result);
            return result.getSendStatus()== SendStatus.SEND_OK;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delShopCarItem(String userid,Long itemid) {
        MQBean mqBean = new MQBean();
        mqBean.setAction(MQBean.MQAction.DELETE);
        ShopCarBean shopCarBean = new ShopCarBean();
        shopCarBean.setTbItem(getTbitemById(itemid));
        shopCarBean.setUserid(userid);
        mqBean.setShopCarBean(shopCarBean);
        String json = JSON.toJSONString(mqBean);
        Message message = new Message(topic,"",userid + "-" + System.currentTimeMillis(), json.getBytes());
        SendResult result=null;
        try {
            result = defaultMQProducer.send(message);
            return result.getSendStatus()==SendStatus.SEND_OK;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public TbItem getTbitemById(Long itemid) {
        TbItem itemDetail = goodsItemService.getItemDetail(itemid);
        return itemDetail;
    }

    @Override
    public boolean editShopcarItemNum(String userid,Long itemid, Integer num) {
        MQBean mqBean = new MQBean();
        mqBean.setAction(MQBean.MQAction.MODIFY_NUM);
        ShopCarBean shopCarBean = new ShopCarBean();
        shopCarBean.setTbItem(getTbitemById(itemid));
        shopCarBean.setUserid(userid);
        shopCarBean.setNum(num);
        mqBean.setShopCarBean(shopCarBean);
        String json = JSON.toJSONString(mqBean);
        Message message = new Message(topic,"",userid + "-" + System.currentTimeMillis(), json.getBytes());
        SendResult result=null;
        try {
            result = defaultMQProducer.send(message);
            return result.getSendStatus()==SendStatus.SEND_OK;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public ShopCarResult showShopCarItems(int page, int rows, String userid) {
        if (StringUtils.isBlank(userid)) {
            return null;
        }
        Long totalPrice = shopCarDao.queryTotalPrice(userid);
        int start=(page-1)*rows;
        int end=page*rows-1;
        List<ShopCarBean> shopCarBeans = shopCarDao.queryUserShopCarItems(userid, start, end);
        ShopCarResult shopCarResult = new ShopCarResult();
        shopCarResult.setBeans(shopCarBeans);
        shopCarResult.setPageNow(page);
        shopCarResult.setTotalPrice(totalPrice);
        long totalResults = shopCarDao.queryShopcarCounts(userid);
        shopCarResult.setTotalResults(totalResults);
        shopCarResult.setTotalPage((long) Math.ceil(totalResults*1.0/rows));
        return shopCarResult;
    }
}
