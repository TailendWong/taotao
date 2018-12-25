package demo.pay;


import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;


import static demo.pay.AlipayConfig.*;

@Controller
public class PayController {

//    public void payOrder(OrderBean orderBean){
//
//    }
    @RequestMapping("payOrder")
    public void payOrder(HttpServletResponse httpResponse) throws Exception {
        String orderid=UtilGenOrderID.genOrderID("1", "2", "3", "1008611");

        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", app_id, merchant_private_key, "json", charset, alipay_public_key, sign_type); //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\""+orderid+"\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":0.01," +
                "    \"subject\":\"a二斤瓜子a\"," +
                "    \"body\":\"a二斤瓜子a\"" +
                "  }");//填充业务参数
        String form="";

        form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单

        httpResponse.setContentType("text/html;charset=utf-8");
        httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();

    }

}
