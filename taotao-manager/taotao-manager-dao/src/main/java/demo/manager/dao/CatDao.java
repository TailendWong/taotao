package demo.manager.dao;

import demo.common.EasyUITreeBean;
import demo.manager.pojo.TbContentCategory;
import demo.manager.pojo.TbItemCat;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 黄敏雅 on 2018/12/13 0013.
 */
@Repository
public interface CatDao {
    @Select("select * from tb_item_cat where parent_id=#{parentid}")
    public List<TbItemCat> queryChildCats(String parentid);
    @Insert("insert into tb_content_category(parent_id,name,is_parent,created,updated) values(#{parentId},#{name},#{isParent},#{created},#{updated})")
    @SelectKey(statement = "select last_insert_id()",keyProperty = "id",keyColumn = "id",before = false,resultType = Long.class)
    public void insertCat(TbContentCategory cat);
    @Update("update tb_content_category set name=#{param2} where id=#{param1}")
    public void updateCat(long id,String name);
    @Update("update tb_content_category set status=0 where id=#{id}")
    public void deleteCat(long id);

}
