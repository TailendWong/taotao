package demo.manager.service.inter;

import demo.common.EasyUIPageDatasBean;
import demo.common.EasyUIResult;
import demo.common.EasyUITreeBean;
import demo.manager.pojo.TbContent;

import java.util.List;

/**
 * Created by 黄敏雅 on 2018/12/13 0013.
 */
public interface ContentService {
    public List<EasyUITreeBean> showContentCats(String parentid);

    public EasyUIPageDatasBean showPageContents(String pageNum,String pageSize,String categoryId);

    public EasyUIResult insertContent(TbContent tbContent);

    public List<TbContent> showCatContents(String pageNum,String pageSize,String categoryId);
}
