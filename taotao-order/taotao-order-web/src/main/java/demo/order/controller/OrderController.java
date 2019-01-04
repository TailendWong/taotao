package demo.order.controller;

import demo.manager.pojo.TbOrder;
import demo.order.inter.OrderBean;
import demo.order.inter.OrderService;
import demo.shopcar.inter.ShopCarResult;
import demo.shopcar.inter.ShopcarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * Created by 黄敏雅 on 2018/12/25 0025.
 */
@Controller
public class OrderController {
    @Resource
    private ShopcarService shopcarService;
    @Resource
    private OrderService orderService;
    @RequestMapping("/order/order-cart")
    public String showItems(@RequestParam(required = false,defaultValue = "1") int page,
                            @RequestParam(required = false,defaultValue = "10") int rows, Model model){
        ShopCarResult shopCarResult = shopcarService.showShopCarItems(page, rows, "dahuang1");
        model.addAttribute("cartList", shopCarResult.getBeans());
        model.addAttribute("totalPrice", shopCarResult.getTotalPrice());
        return "order-cart";
    }
    @RequestMapping("/order/create")
    public String createOrder(OrderBean orderBean){
        System.out.println("OrderController.createOrder");
        System.out.println("orderBean = [" + orderBean + "]");
        System.out.println("入库...");
        boolean b = orderService.createOrder(orderBean,"dahuang1");

        if (b) {
            System.out.println("发送消息成功 = " + b);
            return "success";
        }
        return "error/exception";
    }
}
