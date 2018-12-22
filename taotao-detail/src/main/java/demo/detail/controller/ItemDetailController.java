package demo.detail.controller;

import demo.common.FreemarkerCacheUtil;
import demo.manager.pojo.TbItem;
import demo.manager.pojo.TbItemDesc;
import demo.manager.service.inter.GoodsItemService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 黄敏雅 on 2018/12/20 0020.
 */
@Controller
public class ItemDetailController {
    @Resource
    private FreemarkerCacheUtil freemarkerCacheUtil;
    @Resource
    private GoodsItemService goodsItemService;
    @Value("${FDFS_URL}")
    private String FDFS_URL;
    @RequestMapping("/item/{id}.html")
    public void showItemDetail(@PathVariable("id") String id, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        System.out.println("id = [" + id + "]");
        try {
            boolean b = freemarkerCacheUtil.processFtlCache("item.ftl", response.getWriter(), id);
            if (b) {
                System.out.println("缓存中返回"+id);
                return;
            }
            System.out.println("新生成"+id);
            System.out.println("调用service");
            long idlong = Long.parseLong(id);
            TbItem itemDetail = goodsItemService.getItemDetail(idlong);
            TbItemDesc itemDesc = goodsItemService.getItemDesc(idlong);
            String image = itemDetail.getImage();
            String[] images = image.split(",");
            for (int i = 0; i < images.length; i++) {
                images[i]=FDFS_URL+images[i];
            }
            freemarkerCacheUtil.addAttribute("item",itemDetail);
            freemarkerCacheUtil.addAttribute("images",images);
            freemarkerCacheUtil.addAttribute("itemDesc",itemDesc);
            freemarkerCacheUtil.processFtl("item.ftl",response.getWriter(),id);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
