package demo.manager.service.inter;

import demo.common.EasyUIPageDatasBean;
import demo.manager.pojo.TbItem;
import demo.manager.pojo.TbItemDesc;

/**
 * Created by 黄敏雅 on 2018/12/8 0008.
 */
public interface GoodsItemService {
    public EasyUIPageDatasBean<TbItem> showPageTbitems(int pageNum, int pageSize);
    public TbItem getItemDetail(long id);
    public TbItemDesc getItemDesc(long id);
    public void addItem(TbItem tbItem,TbItemDesc tbItemDesc);
}
