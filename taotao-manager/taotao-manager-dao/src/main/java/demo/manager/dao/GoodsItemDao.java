package demo.manager.dao;

import demo.manager.pojo.TbItem;
import demo.manager.pojo.TbItemDesc;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 黄敏雅 on 2018/12/8 0008.
 */
@Repository
public interface GoodsItemDao {
    @Select("select * from tb_item")
    public List<TbItem> queryTbItems();
    @Select("select * from tb_item where id=#{id}")
    public TbItem queryItemDetail(long id);
    @Select("select * from tb_item_desc where item_id=#{id}")
    public TbItemDesc queryItemDesc(long id);

}
