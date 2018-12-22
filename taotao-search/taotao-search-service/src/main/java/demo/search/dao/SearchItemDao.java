package demo.search.dao;

import demo.manager.pojo.TbItem;
import org.apache.ibatis.annotations.Select;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by 黄敏雅 on 2018/12/17 0017.
 */
@Repository
public class SearchItemDao {
    @Resource
    private SolrServer httpSolrServer;
    public SolrResult queryTbItems(SolrQuery solrQuery) {
        try {
            QueryResponse queryResponse = httpSolrServer.query(solrQuery);
//            return queryResponse.getBeans(TbItem.class);
            SolrDocumentList results = queryResponse.getResults();
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
            SolrResult solrResult = new SolrResult();
            solrResult.setSolrDocuments(results);
            solrResult.setHighlighting(highlighting);
            return solrResult;
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
