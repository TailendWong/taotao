package demo.manager.dao;

import demo.manager.pojo.TbContent;
import demo.manager.pojo.TbContentCategory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 黄敏雅 on 2018/12/14 0014.
 */
@Repository
public interface ContentDao {
    @Select("select * from tb_content_category where parent_id=#{parentid}")
    public List<TbContentCategory> queryContentCatsByPid(long parentid);
    @Select("select * from tb_content where category_id=#{categoryId}")
    public List<TbContent> queryContents(long categoryId);
    @Insert("insert into tb_content values(null,#{categoryId},#{title},#{subTitle},#{titleDesc},#{url},#{pic},#{pic2},#{content},#{created},#{updated})")
    public void insertContent(TbContent tbContent);
}
