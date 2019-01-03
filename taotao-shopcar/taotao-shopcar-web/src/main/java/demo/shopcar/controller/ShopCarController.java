package demo.shopcar.controller;

import demo.manager.service.inter.GoodsItemService;
import demo.shopcar.inter.ShopCarResult;
import demo.shopcar.inter.ShopcarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * Created by 黄敏雅 on 2018/12/22 0022.
 */
@Controller
public class ShopCarController {
    @Resource
    private GoodsItemService goodsItemService;
    @Resource
    private ShopcarService shopcarService;
    @RequestMapping("/cart/add/{itemid}/{num}")
    public String addShopCar(@PathVariable Long itemid,@PathVariable Integer num){
        System.out.println("itemid = [" + itemid + "], num = [" + num + "]");
        boolean b = shopcarService.addToShopCar("dahuang1", goodsItemService.getItemDetail(itemid), num);
        if (b) {
            return "cartSuccess";
        }else {
            return "cartFail";
        }
    }
    @RequestMapping("/cart/cart")
    public String showShopCar(@RequestParam(required = false,defaultValue = "1")int page, @RequestParam(required = false,defaultValue = "10")int rows, Model model){
        ShopCarResult shopCarResult = shopcarService.showShopCarItems(page, rows, "dahuang1");
        model.addAttribute("shopCarResult",shopCarResult);
        return "cart";
    }

    @RequestMapping("/cart/delete/{tbItemid}")
    public String deleteCartItem(@PathVariable("tbItemid") Long tbitemid){
        System.out.println("tbitemid = [" + tbitemid + "]");
        shopcarService.delShopCarItem(tbitemid);
            return "forward:/cart/cart.html";
    }

    @RequestMapping("/cart/update/num/{itemId}/{num}")
    public String updateCartItem(@PathVariable("num") Integer num,@PathVariable("itemId") Long itemId ) {
        System.out.println("num = [" + num + "], itemId = [" + itemId + "]");
        shopcarService.editShopcarItemNum(itemId,num);
        return "forward:/cart/cart.html";
    }
}
