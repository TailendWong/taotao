package demo.manager.service.inter;

import demo.common.EasyUIPageDatasBean;
import demo.manager.pojo.TbItem;

/**
 * Created by 黄敏雅 on 2018/12/8 0008.
 */
public interface GoodsItemService {
    public EasyUIPageDatasBean<TbItem> showPageTbitems(int pageNum, int pageSize);
}
