package demo.portal.controller;

import demo.manager.pojo.TbContent;
import demo.manager.service.inter.ContentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 黄敏雅 on 2018/12/15 0015.
 */
@Controller
public class PortalIndexController {
    @Value("${BIG_AD_ID}")
    private String bigADid;
    @Value("${BIG_AD_WIDTH}")
    private Integer bigWidth;
    @Value("${BIG_AD_HEIGHT}")
    private Integer bigHeight;
    @Resource
    private ContentService contentService;
    @RequestMapping("/getBigADs.json")
    @ResponseBody
    public List<BigADBean> getBigADs() {
        List<TbContent> tbContents = contentService.showCatContents("1", "4", bigADid);
        return BigADBean.genBeansFromContent(tbContents,bigWidth,bigHeight);
    }
}
