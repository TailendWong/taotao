package demo.manager.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import demo.common.EasyUIPageDatasBean;
import demo.manager.dao.GoodsItemDao;
import demo.manager.pojo.TbItem;
import demo.manager.pojo.TbItemDesc;
import demo.manager.service.inter.GoodsItemService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
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


    @Transactional(value = "id_txManager",propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,readOnly = false,rollbackFor = Exception.class)
    public void addItem(TbItem tbItem,TbItemDesc tbItemDesc) {
            String formatTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            tbItem.setCreated(formatTime);
            tbItem.setUpdated(formatTime);
            tbItemDesc.setCreated(formatTime);
            tbItemDesc.setUpdated(formatTime);
            goodsItemDao.insertItem(tbItem);
            tbItemDesc.setItemId(Long.parseLong(tbItem.getId()));
            goodsItemDao.insertItemDesc(tbItemDesc);
    }
}
