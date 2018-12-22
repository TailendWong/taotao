package demo.search.inter;

import demo.manager.pojo.TbItem;

import java.util.List;
import java.util.Map;

/**
 * Created by 黄敏雅 on 2018/12/17 0017.
 */
public interface SearchItemService {
    public SearchItemResult searchItems(String keyword, Map<String,Object> filters,int page,int rows);
}
