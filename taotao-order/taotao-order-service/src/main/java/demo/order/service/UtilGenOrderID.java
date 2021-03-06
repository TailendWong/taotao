package demo.order.service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 黄敏雅 on 2018/12/25 0025.
 */
public class UtilGenOrderID {
    /*
    下单渠道1位+
    支付渠道1位+
    业务类型1位+
    时间信息4位+下单时间的Unix时间戳后8位（或是这8位数字加上随机码和随机规则进行随机后的数字）+
    用户user id后4位
     */
    private static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyMMddHHmmss");

    public static String genOrderID(String orderFrom, String paymentType, String orderType, String userid) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(orderFrom);
        stringBuilder.append(paymentType);
        stringBuilder.append(orderType);
        stringBuilder.append(simpleDateFormat.format(new Date()));
        stringBuilder.append(userid.substring(userid.length()-4));
        return stringBuilder.toString();
    }

}
