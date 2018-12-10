package demo.manager.dao;

import demo.manager.pojo.TbItem;
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

}
