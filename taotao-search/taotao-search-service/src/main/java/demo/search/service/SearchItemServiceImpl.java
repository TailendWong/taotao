package demo.search.service;

import demo.manager.pojo.TbItem;
import demo.search.dao.SearchItemDao;
import demo.search.dao.SolrResult;
import demo.search.inter.SearchItemResult;
import demo.search.inter.SearchItemService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 黄敏雅 on 2018/12/17 0017.
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Value("${solr.itemsearch}")
    private String searchField;
    @Value("${FDFS_URL}")
    private String FDFS_URL;
    @Resource
    private SearchItemDao searchItemDao;
    @Override
    public SearchItemResult searchItems(String keyword, Map<String, Object> filters, int page, int rows) {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(searchField+":"+keyword);
        if (filters != null&&filters.size() > 0) {
            Set<Map.Entry<String, Object>> entries = filters.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                solrQuery.addFilterQuery(entry.getKey()+":\""+entry.getValue()+"\"");
            }
        }
        int start=(page-1)*rows;
        solrQuery.setStart(start);
        solrQuery.setRows(rows);
        solrQuery.setHighlightSimplePre("<span style='color:red; font-weight:bold'>");
        solrQuery.setHighlightSimplePost("</span>");
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("title");
        solrQuery.addHighlightField("sell_point");


        SolrResult solrResult=searchItemDao.queryTbItems(solrQuery);
        if (solrResult != null) {
            SolrDocumentList solrDocuments = solrResult.getSolrDocuments();
            Map<String, Map<String, List<String>>> highlighting = solrResult.getHighlighting();
            long numFound = solrDocuments.getNumFound();
            long pageTotal= (long) Math.ceil(numFound/rows);
            List<TbItem> tbItems=new ArrayList<>();
            for (SolrDocument solrDocument : solrDocuments) {
                TbItem tbItem = new TbItem();
                tbItems.add(tbItem);
                tbItem.setId(String.valueOf(solrDocument.getFieldValue("id")));

                //tbItem.setId((String) solrDocument.getFieldValue("id"));
                tbItem.setPrice((Long) solrDocument.getFieldValue("price"));
                tbItem.setNum((Integer) solrDocument.getFieldValue("num"));
                tbItem.setImage("http://s26.natfrp.org:19199/"+String.valueOf(solrDocument.getFieldValue("image")).split(",")[0]);
                //高亮,idHL为需要高亮的一条数据
                Map<String,List<String>> idHL=highlighting.get(tbItem.getId());
                if (idHL != null&&idHL.size()>0) {
                    List<String> titleHL = idHL.get("title");
                    List<String> sell_pointHL = idHL.get("sell_point");
                    if (titleHL != null&&titleHL.size()>0) {
                        tbItem.setTitle(titleHL.get(0));
                    }

                    if (sell_pointHL != null&&sell_pointHL.size()>0) {
                        tbItem.setSellPoint(sell_pointHL.get(0));
                    }
                }else {
                    tbItem.setTitle(String.valueOf(solrDocument.getFieldValue("title")));
                    tbItem.setSellPoint(String.valueOf(solrDocument.getFieldValue("sell_point")));
                }
            }
            SearchItemResult searchItemResult = new SearchItemResult(keyword, page, numFound, pageTotal, tbItems);
            return searchItemResult;

        }

        return null;
    }
}
