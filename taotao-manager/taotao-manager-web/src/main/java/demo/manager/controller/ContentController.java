package demo.manager.controller;

import demo.common.EasyUIPageDatasBean;
import demo.common.EasyUIResult;
import demo.common.EasyUITreeBean;
import demo.manager.pojo.TbContent;
import demo.manager.service.inter.ContentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 黄敏雅 on 2018/12/14 0014.
 */
@Controller
public class ContentController {
    @Value("${FDFS_URL}")
    private String FDFS_URL;
    @Resource
    private ContentService contentService;

    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeBean> showContentCats(@RequestParam(required = false,defaultValue = "0") String id) {
        return contentService.showContentCats(id);
    }

    @RequestMapping("/content/query/list")
    @ResponseBody
    public EasyUIPageDatasBean<TbContent> itemList(@RequestParam(required =false,defaultValue = "1")String page,
                                        @RequestParam(required =false,defaultValue = "10")String rows, String categoryId){

        System.out.println("page = [" + page + "], rows = [" + rows + "]");
        EasyUIPageDatasBean<TbContent> easyUIPageDatasBean = contentService.showPageContents(page, rows, categoryId);
        for (TbContent row : easyUIPageDatasBean.getRows()) {
            row.setPic(FDFS_URL+row.getPic());
            row.setPic2(FDFS_URL+row.getPic2());
        }
        return easyUIPageDatasBean;
    }
    @RequestMapping("/content/save")
    @ResponseBody
    public EasyUIResult addContent(TbContent tbContent){
        tbContent.setPic(tbContent.getPic().replace(FDFS_URL,""));
        tbContent.setPic2(tbContent.getPic2().replace(FDFS_URL,""));
        return contentService.insertContent(tbContent);
    }
}
