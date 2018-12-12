package demo.manager.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
}
