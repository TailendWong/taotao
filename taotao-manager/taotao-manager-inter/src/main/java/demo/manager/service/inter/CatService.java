package demo.manager.service.inter;

import demo.common.AjaxResult;
import demo.common.EasyUITreeBean;
import demo.manager.pojo.TbContentCategory;


import java.util.List;

/**
 * Created by 黄敏雅 on 2018/12/13 0013.
 */
public interface CatService {
    public List<EasyUITreeBean> showCatItems(String parentid);
    public AjaxResult addContentCat(TbContentCategory cat);

    public void updateCat(Long id,String name);

    public void deleteCat(Long id);
}
