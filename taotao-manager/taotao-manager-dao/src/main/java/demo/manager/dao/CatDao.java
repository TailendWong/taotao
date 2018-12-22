package demo.manager.dao;

import demo.common.EasyUITreeBean;
import demo.manager.pojo.TbItemCat;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 黄敏雅 on 2018/12/13 0013.
 */
@Repository
public interface CatDao {
    @Select("select * from tb_item_cat where parent_id=#{parentid}")
    public List<TbItemCat> queryChildCats(String parentid);
}
