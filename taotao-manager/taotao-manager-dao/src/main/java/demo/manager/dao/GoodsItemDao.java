package demo.manager.dao;

import demo.manager.pojo.TbItem;
import demo.manager.pojo.TbItemDesc;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by 黄敏雅 on 2018/12/8 0008.
 */
@Repository
public interface GoodsItemDao {
    @Select("select * from tb_item where status<>3")
    public List<TbItem> queryTbItems();
    @Select("select * from tb_item where id=#{id}")
    public TbItem queryItemDetail(long id);
    @Select("select * from tb_item_desc where item_id=#{id}")
    public TbItemDesc queryItemDesc(long id);

    @Insert("insert into tb_item(title,sell_point,price,num,barcode,image,cid,created,updated) values(#{title},#{sellPoint},#{price},#{num},#{barcode},#{image},#{cid},#{created},#{updated})")
    @SelectKey(statement = "select last_insert_id()",keyProperty = "id",keyColumn = "id",before = false,resultType = String.class)
    public void insertItem(TbItem tbItem);
    @Insert("insert into tb_item_desc(item_id,item_desc,created,updated) values(#{itemId},#{itemDesc},#{created},#{updated})")
    public void insertItemDesc(TbItemDesc tbItemDesc);
    @Update("update tb_item set status=3 where id=#{id}")
    public void delGoodItems(String id);
    @Delete("delete from tb_item where id=#{id}")
    public boolean deleteItemById(String id);
    @Update("update tb_item_desc set item_desc=#{itemDesc},updated=#{updated} where item_id=#{itemId}")
    public boolean updateItemDesc(TbItemDesc tbItemDesc);
    @Update("update tb_item set id=#{nid} where id=#{oid}")
    public boolean updateItemId(@Param("oid") String oid,@Param("nid") String nid);
}
