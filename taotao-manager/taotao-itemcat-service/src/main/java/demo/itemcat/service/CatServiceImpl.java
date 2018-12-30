package demo.itemcat.service;

import demo.common.AjaxResult;
import demo.common.EasyUITreeBean;
import demo.manager.dao.CatDao;
import demo.manager.pojo.TbContentCategory;
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

    @Override
    public AjaxResult addContentCat(TbContentCategory cat) {
        AjaxResult ajaxResult = new AjaxResult();
        System.out.println("入库。。。");
        try {
            catDao.insertCat(cat);
            System.out.println("id==="+cat.getId());
            ajaxResult.setData(cat);
            ajaxResult.setSuccess(true);
            ajaxResult.setStatus(AjaxResult.OK);
        } catch (Exception e) {
            ajaxResult.setStatus(AjaxResult.Fail);
        }
        return ajaxResult;
    }

    @Override
    public void updateCat(Long id, String name) {
        catDao.updateCat(id,name);
    }

    @Override
    public void deleteCat(Long id) {
        catDao.deleteCat(id);
    }
}
