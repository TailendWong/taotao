package demo.search.controller;

import demo.search.inter.SearchItemResult;
import demo.search.inter.SearchItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * Created by 黄敏雅 on 2018/12/19 0019.
 */
@Controller
public class SearchController {
    @Resource
    private SearchItemService searchItemService;
    @RequestMapping("search")
    public String searchItems(String q, @RequestParam(required = false,defaultValue = "1") Integer page, Model model) {
        SearchItemResult searchItemResult = searchItemService.searchItems(q, null, page, 10);
        model.addAttribute("result",searchItemResult);
        return "search";

    }

}
