package demo.shopcar.inter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 黄敏雅 on 2018/12/22 0022.
 */
public class ShopCarResult implements Serializable{
    private Integer pageNow;
    private Long totalPage;
    private Long totalResults;
    private List<ShopCarBean> beans;
    private Long totalPrice;

    public Integer getPageNow() {
        return pageNow;
    }

    public void setPageNow(Integer pageNow) {
        this.pageNow = pageNow;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

    public List<ShopCarBean> getBeans() {
        return beans;
    }

    public void setBeans(List<ShopCarBean> beans) {
        this.beans = beans;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }
}
