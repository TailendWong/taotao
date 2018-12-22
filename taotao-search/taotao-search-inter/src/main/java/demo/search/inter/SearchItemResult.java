package demo.search.inter;

import demo.manager.pojo.TbItem;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 黄敏雅 on 2018/12/19 0019.
 */
public class SearchItemResult implements Serializable{
    private String keyword;
    private int pageNow;
    private long totalResult;
    private long totalPage;
    private List<TbItem> tbItems;

    public SearchItemResult(String keyword, int pageNow, long totalResult, long totalPage, List<TbItem> tbItems) {
        this.keyword = keyword;
        this.pageNow = pageNow;
        this.totalResult = totalResult;
        this.totalPage = totalPage;
        this.tbItems = tbItems;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getPageNow() {
        return pageNow;
    }

    public void setPageNow(int pageNow) {
        this.pageNow = pageNow;
    }

    public long getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(long totalResult) {
        this.totalResult = totalResult;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public List<TbItem> getTbItems() {
        return tbItems;
    }

    public void setTbItems(List<TbItem> tbItems) {
        this.tbItems = tbItems;
    }
}
