package demo.manager.service;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import demo.common.EasyUIPageDatasBean;
import demo.common.EasyUIResult;
import demo.common.EasyUITreeBean;
import demo.manager.dao.ContentDao;
import demo.manager.pojo.TbContent;
import demo.manager.pojo.TbContentCategory;
import demo.manager.service.inter.ContentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by 黄敏雅 on 2018/12/13 0013.
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Value("${KEY_Content_items}")
    private String redisContentKey;
    @Value("${FDFS_URL}")
    private String picServerUrl;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private ContentDao contentDao;
    @Override
    public List<EasyUITreeBean> showContentCats(String parentid) {
        List<TbContentCategory> tbContentCategories = contentDao.queryContentCatsByPid(Long.parseLong(parentid));
        List<EasyUITreeBean> easyUITreeBeans=new ArrayList<>();
        if (tbContentCategories != null) {
            for (TbContentCategory tbContentCategory : tbContentCategories) {
                EasyUITreeBean easyUITreeBean=new EasyUITreeBean();
                easyUITreeBean.setId(tbContentCategory.getId());
                easyUITreeBean.setText(tbContentCategory.getName());
                if (tbContentCategory.getIsParent()) {
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
    public EasyUIPageDatasBean showPageContents(String pageNum, String pageSize, String categoryId) {
        System.out.println("pageNum = [" + pageNum + "], pageSize = [" + pageSize + "]");
        PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        List<TbContent> tbContents = contentDao.queryContents(Long.parseLong(categoryId));
        PageInfo<TbContent> pageInfo = new PageInfo<>(tbContents);
        System.out.println(""+pageInfo.getSize()+""+pageInfo.getTotal());
        EasyUIPageDatasBean<TbContent> easyUIPageDatasBean = new EasyUIPageDatasBean<>();
        easyUIPageDatasBean.setRows(tbContents);
        easyUIPageDatasBean.setTotal(pageInfo.getTotal());
        return easyUIPageDatasBean;
    }

    @Override
    public EasyUIResult insertContent(TbContent tbContent) {
        String key=redisContentKey+tbContent.getCategoryId();
        EasyUIResult easyUIResult=new EasyUIResult();
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        try {
            contentDao.insertContent(tbContent);
            easyUIResult.setStatus(EasyUIResult.OK);
            easyUIResult.setMessage("新增内容成功！"+tbContent.getTitle());
            redisTemplate.delete(key);
        } catch (Exception e) {
            e.printStackTrace();
            easyUIResult.setStatus(EasyUIResult.ERROR);
            easyUIResult.setMessage("新增内容失败！"+tbContent.getTitle());
        }
        return easyUIResult;
    }

    @Override
    public List<TbContent> showCatContents(String pageNum, String pageSize, String categoryId) {
        List<TbContent> tbContents;
        Object result=null;
        String key=redisContentKey+categoryId;
        ValueOperations valueOperations = null;

        try {
            valueOperations=redisTemplate.opsForValue();
            result = valueOperations.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result == null) {
            //去数据库查
            PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
            tbContents = contentDao.queryContents(Long.parseLong(categoryId));
            if (tbContents != null) {
                for (TbContent tbContent : tbContents) {
                    tbContent.setPic("http://s26.natfrp.org:19199/"+tbContent.getPic());
                    tbContent.setPic2("http://s26.natfrp.org:19199/"+tbContent.getPic2());
                }
            }
            //放缓存
            try {
                valueOperations.set(key,JSON.toJSONString(tbContents));
                redisTemplate.expire(key,1, TimeUnit.HOURS);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else {
            //解析
            tbContents = JSON.parseArray(result.toString(), TbContent.class);
        }

        return tbContents;
    }
}
