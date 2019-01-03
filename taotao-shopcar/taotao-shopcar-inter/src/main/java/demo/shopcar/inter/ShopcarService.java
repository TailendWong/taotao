package demo.shopcar.inter;

import demo.manager.pojo.TbItem;

/**
 * Created by 黄敏雅 on 2018/12/22 0022.
 */
public interface ShopcarService {
    //1.向购物车添加商品
    public boolean addToShopCar(String userid, TbItem tbItem,Integer num);

    //2.删除
    public boolean delShopCarItem(String userid,Long itemid);
    public TbItem getTbitemById(Long itemid);
    //3.修改数量
    public boolean editShopcarItemNum(String userid,Long itemid,Integer num);
    //4.列出购物车商品
    public ShopCarResult showShopCarItems(int page,int rows,String userid);


}
