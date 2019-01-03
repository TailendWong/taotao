package demo.manager.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import demo.common.EasyUIPageDatasBean;
import demo.manager.dao.GoodsItemDao;
import demo.manager.pojo.TbItem;
import demo.manager.pojo.TbItemDesc;
import demo.manager.service.inter.GoodsItemService;
import demo.manager.service.inter.MQBean;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by 黄敏雅 on 2018/12/8 0008.
 */
@Service
public class GoodsServiceImpl implements GoodsItemService {
    @Resource
    private DefaultMQProducer defaultMQProducer;
    @Value("${topic}")
    private String topic;
    @Resource
    private GoodsItemDao goodsItemDao;
    @Override
    public EasyUIPageDatasBean<TbItem> showPageTbitems(int pageNum, int pageSize) {
        System.out.println("pageNum = [" + pageNum + "], pageSize = [" + pageSize + "]");
        PageHelper.startPage(pageNum,pageSize);
        List<TbItem> tbItems = goodsItemDao.queryTbItems();
        PageInfo<TbItem> pageInfo = new PageInfo<>(tbItems);
        System.out.println(""+pageInfo.getSize()+""+pageInfo.getTotal());
        EasyUIPageDatasBean<TbItem> easyUIPageDatasBean = new EasyUIPageDatasBean<>();
        easyUIPageDatasBean.setRows(tbItems);
        easyUIPageDatasBean.setTotal(pageInfo.getTotal());
        return easyUIPageDatasBean;
    }

    @Override
    public TbItem getItemDetail(long id) {
        return goodsItemDao.queryItemDetail(id);
    }

    @Override
    public TbItemDesc getItemDesc(long id) {
        return goodsItemDao.queryItemDesc(id);
    }


   @Override
    public boolean addItem(TbItem tbItem, TbItemDesc tbItemDesc) {
            String formatTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            tbItem.setCreated(formatTime);
            tbItem.setUpdated(formatTime);
            tbItemDesc.setCreated(formatTime);
            tbItemDesc.setUpdated(formatTime);

            MQBean mqBean=new MQBean();
            mqBean.setAction(MQBean.MQAction.ADD);
            mqBean.setGoodBean(tbItem);
            mqBean.setTbItemDesc(tbItemDesc);
            String json= JSON.toJSONString(mqBean);
            Message message = new Message(topic,"","admin-"+System.currentTimeMillis(),json.getBytes());
            SendResult result=null;
        try {
            result=defaultMQProducer.send(message);
            return result.getSendStatus()== SendStatus.SEND_OK;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteGoodsItems(String ids) {

        MQBean mqBean=new MQBean();
        mqBean.setAction(MQBean.MQAction.DELETE);
        mqBean.setIds(ids);
        String json= JSON.toJSONString(mqBean);
        Message message = new Message(topic,"","admin-"+System.currentTimeMillis(),json.getBytes());
        SendResult result=null;
        try {
            result=defaultMQProducer.send(message);
            return result.getSendStatus()== SendStatus.SEND_OK;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateItem(TbItem tbItem, String desc, String id) {
        TbItem itemDetail = getItemDetail(Long.parseLong(id));
        String formatTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        tbItem.setUpdated(formatTime);
        tbItem.setCreated(itemDetail.getCreated());
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(Long.parseLong(id));
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setUpdated(formatTime);

        MQBean mqBean=new MQBean();
        mqBean.setAction(MQBean.MQAction.MODIFY);
        mqBean.setIds(itemDetail.getId());
        mqBean.setTbItemDesc(tbItemDesc);
        mqBean.setGoodBean(tbItem);
        String json= JSON.toJSONString(mqBean);
        Message message = new Message(topic,"","admin-"+System.currentTimeMillis(),json.getBytes());
        SendResult result=null;
        try {
            result=defaultMQProducer.send(message);
            return result.getSendStatus()== SendStatus.SEND_OK;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
