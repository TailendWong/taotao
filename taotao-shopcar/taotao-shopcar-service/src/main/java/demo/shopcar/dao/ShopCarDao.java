package demo.shopcar.dao;

import com.alibaba.fastjson.JSON;
import demo.manager.pojo.TbItem;
import demo.shopcar.inter.ShopCarBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 黄敏雅 on 2018/12/22 0022.
 */
@Repository
public class ShopCarDao {
    @Resource
    private RedisTemplate redisTemplate;
    @Value("${KEY_SHOPCAR_ZS}")
    private String KEY_SHOPCAR_ZS;
    @Value("${KEY_SHOPCAR_HASH}")
    private String KEY_SHOPCAR_HASH;
    @Value("${KEY_SHOPCAR_TP}")
    private String KEY_SHOPCAR_TP;

    public void addShopCarItem(ShopCarBean shopCarBean){
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        String zsKey=KEY_SHOPCAR_ZS+shopCarBean.getUserid();
        //商品序列
        zSetOperations.add(zsKey, JSON.toJSONString(shopCarBean.getTbItem()), System.currentTimeMillis());
        //商品数量
        HashOperations hashOperations = redisTemplate.opsForHash();
        String hsKey=KEY_SHOPCAR_HASH+shopCarBean.getUserid();
        hashOperations.increment(hsKey,shopCarBean.getTbItem().getId(),shopCarBean.getNum());
        //存总价格
        String tpKey=KEY_SHOPCAR_TP+shopCarBean.getUserid();
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.increment(tpKey,shopCarBean.getNum()*shopCarBean.getTbItem().getPrice());
    }

    public Long queryTotalPrice(String userid) {
        String tpKey=KEY_SHOPCAR_TP+userid;
        ValueOperations valueOperations = redisTemplate.opsForValue();
        return Long.parseLong(valueOperations.get(tpKey).toString());
    }

    public List<ShopCarBean> queryUserShopCarItems(String userid, int start, int end) {
        String zsKey=KEY_SHOPCAR_ZS+userid;
        String hsKey=KEY_SHOPCAR_HASH+userid;
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        HashOperations hashOperations = redisTemplate.opsForHash();
        Map nums = hashOperations.entries(hsKey);
        Set goods = zSetOperations.reverseRange(zsKey, start, end);
        List<ShopCarBean> shopCarBeans = new ArrayList<>();
        for (Object o : goods) {
            TbItem tbItem = JSON.parseObject(o.toString(), TbItem.class);
            Integer num = Integer.parseInt(nums.get(tbItem.getId()).toString());
            ShopCarBean shopCarBean = new ShopCarBean();
            shopCarBean.setTbItem(tbItem);
            shopCarBean.setNum(num);
            shopCarBean.setUserid(userid);
            shopCarBeans.add(shopCarBean);
        }
        return shopCarBeans;
    }

    public long queryShopcarCounts(String userid) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        String zsKey=KEY_SHOPCAR_ZS+userid;
        return zSetOperations.zCard(zsKey);
    }

}
