package demo.manager.service;

import demo.common.EasyUIPageDatasBean;
import demo.manager.dao.GoodsItemDao;
import demo.manager.pojo.TbItem;
import demo.manager.service.inter.GoodsItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        List<TbItem> tbItems = goodsItemDao.queryTbItems();
        EasyUIPageDatasBean<TbItem> easyUIPageDatasBean = new EasyUIPageDatasBean<>();
        easyUIPageDatasBean.setRows(tbItems);
        easyUIPageDatasBean.setTotal(tbItems.size());
        return easyUIPageDatasBean;
    }
}
