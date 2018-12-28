package demo.manager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import demo.common.EasyUIPageDatasBean;
import demo.common.EasyUIPicUploadBean;
import demo.common.EasyUITreeBean;
import demo.common.FastDFSClientUtil;
import demo.manager.pojo.TbItem;
import demo.manager.pojo.TbItemDesc;
import demo.manager.service.inter.CatService;
import demo.manager.service.inter.GoodsItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by 黄敏雅 on 2018/12/10 0010.
 */
@Controller
public class ManagerController {
    @Value("${FDFS_URL}")
    private String FDFS_URL;
    @Resource
    private CatService catService;
    @Resource
    private GoodsItemService goodsItemService;
    @RequestMapping("/{path}")
    public  String toMenuFunction(@PathVariable String path){
        System.out.println("path = " + path);
        return path;
    }
    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIPageDatasBean itemList(@RequestParam(required =false,defaultValue = "1")String page,
                           @RequestParam(required =false,defaultValue = "20")String rows){

        System.out.println("page = [" + page + "], rows = [" + rows + "]");
        EasyUIPageDatasBean<TbItem> items = goodsItemService.showPageTbitems(Integer.parseInt(page), Integer.parseInt(rows));
//        String jsonString = JSON.toJSONString(items);
//        return jsonString; 这是错误的，会受到视图解析器的影响
//        response.getWriter().write(jsonString); 这是正确的
        //返回json有两种方式，response写回   或者@responseBody
        return items;
    }

    @RequestMapping("/pic/upload")
    @ResponseBody
    public EasyUIPicUploadBean uploadPics(MultipartFile uploadFile){
        EasyUIPicUploadBean easyUIPicUploadBean=new EasyUIPicUploadBean();
        try {
        String originalFilename = uploadFile.getOriginalFilename();
        String expName = "";
        if (originalFilename.contains(".")) {
            expName = originalFilename.split("\\.")[1];
        }
        FastDFSClientUtil fastDFSClientUtil = new FastDFSClientUtil();
        byte[] picDatas=uploadFile.getBytes();
        String fdfsPath = FDFS_URL+fastDFSClientUtil.uploadFile(picDatas, expName);

        easyUIPicUploadBean.setError(0);
        easyUIPicUploadBean.setMessage("上传成功！");
        easyUIPicUploadBean.setUrl(fdfsPath);
        } catch (Exception e) {
            easyUIPicUploadBean.setError(1);
            easyUIPicUploadBean.setMessage("上传失败，请稍后重试！！");
            e.printStackTrace();
        }
        return easyUIPicUploadBean;

    }

    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeBean> loadItemCatTree(HttpServletRequest httpServletRequest,
                                                @RequestParam(required = false,defaultValue = "0") String id){
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            System.out.println("parameterNames = " + parameterNames.nextElement());
        }
        return catService.showCatItems(id);
    }
    @RequestMapping("/item/save")
    public void saveGood(TbItem tbItem, TbItemDesc itemDesc, HttpServletResponse response)  {
        try {
            goodsItemService.addItem(tbItem,itemDesc);
            response.getWriter().write("{\"status\":200}");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.getWriter().write("{\"status\":0}");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }
}
