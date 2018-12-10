package demo.manager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import demo.common.EasyUIPageDatasBean;
import demo.manager.pojo.TbItem;
import demo.manager.service.inter.GoodsItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by 黄敏雅 on 2018/12/10 0010.
 */
@Controller
public class ManagerController {
    @Resource
    private GoodsItemService goodsItemService;
    @RequestMapping("/{path}")
    public  String toMenuFunction(@PathVariable String path){
        System.out.println("path = " + path);
        return path;
    }
    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIPageDatasBean itemList(@RequestParam(required =false,defaultValue = "1")String pageNumber,
                           @RequestParam(required =false,defaultValue = "20")String pageSize){

        System.out.println("pageNumber = [" + pageNumber + "], pageSize = [" + pageSize + "]");
        EasyUIPageDatasBean<TbItem> items = goodsItemService.showPageTbitems(Integer.parseInt(pageNumber), Integer.parseInt(pageSize));
//        String jsonString = JSON.toJSONString(items);
//        return jsonString; 这是错误的，会受到视图解析器的影响
//        response.getWriter().write(jsonString); 这是正确的
        //返回json有两种方式，response写回   或者@responseBody
        return items;
    }


}
