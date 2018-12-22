package demo.portal.controller;



import demo.manager.pojo.TbContent;

import java.util.ArrayList;
import java.util.List;

public class BigADBean {
    private String srcB;
    private Integer height;
    private String alt;
    private Integer width;
    private String src;
    private Integer widthB;
    private String href;
    private Integer heightB;


    public String getSrcB() {
        return srcB;
    }

    public void setSrcB(String srcB) {
        this.srcB = srcB;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Integer getWidthB() {
        return widthB;
    }

    public void setWidthB(Integer widthB) {
        this.widthB = widthB;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Integer getHeightB() {
        return heightB;
    }

    public void setHeightB(Integer heightB) {
        this.heightB = heightB;
    }

    public static List<BigADBean> genBeansFromContent(List<TbContent> contents, Integer width, Integer height){
        List<BigADBean> bigADBeans=new ArrayList<>();

        if (contents==null||contents.size()<=0) {
            return bigADBeans;
        }

        for (TbContent content : contents) {
            BigADBean bigADBean=new BigADBean();
            bigADBean.setSrc(content.getPic());
            bigADBean.setSrcB(content.getPic2());
            bigADBean.setAlt(content.getTitle());
            bigADBean.setHref(content.getUrl());
            bigADBean.setHeight(height);
            bigADBean.setHeightB(height);
            bigADBean.setWidth(width);
            bigADBean.setWidthB(width);

            bigADBeans.add(bigADBean);
        }

        return bigADBeans;


    }
}
