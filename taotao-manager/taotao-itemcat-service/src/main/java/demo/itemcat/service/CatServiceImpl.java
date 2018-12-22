package demo.itemcat.service;

import demo.common.EasyUITreeBean;
import demo.manager.dao.CatDao;
import demo.manager.pojo.TbItemCat;
import demo.manager.service.inter.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 黄敏雅 on 2018/12/13 0013.
 */
@Service
public class CatServiceImpl implements CatService{
    @Resource
    private CatDao catDao;
    @Override
    public List<EasyUITreeBean> showCatItems(String parentid) {
        List<TbItemCat> tbItemCats = catDao.queryChildCats(parentid);
        List<EasyUITreeBean> easyUITreeBeans=new ArrayList<>();
        if (tbItemCats != null) {
            for (TbItemCat tbItemCat : tbItemCats) {
                EasyUITreeBean easyUITreeBean=new EasyUITreeBean();
                easyUITreeBean.setId(tbItemCat.getId());
                easyUITreeBean.setText(tbItemCat.getName());
                if (tbItemCat.getIsParent()) {
                    easyUITreeBean.setState(EasyUITreeBean.STATE_CLOSED);
                }else{
                    easyUITreeBean.setState(EasyUITreeBean.STATE_OPEN);
                }
                easyUITreeBeans.add(easyUITreeBean);
            }
        }
        return easyUITreeBeans;
    }
}
