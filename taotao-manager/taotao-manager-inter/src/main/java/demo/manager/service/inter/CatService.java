package demo.manager.service.inter;

import demo.common.EasyUITreeBean;


import java.util.List;

/**
 * Created by 黄敏雅 on 2018/12/13 0013.
 */
public interface CatService {
    public List<EasyUITreeBean> showCatItems(String parentid);
}
