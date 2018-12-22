package demo.search.dao;

import org.apache.solr.common.SolrDocumentList;

import java.util.List;
import java.util.Map;

/**
 * Created by 黄敏雅 on 2018/12/19 0019.
 */
public class SolrResult {
    private SolrDocumentList solrDocuments;
    private Map<String ,Map<String ,List<String>>> highlighting;

    public SolrDocumentList getSolrDocuments() {
        return solrDocuments;
    }

    public void setSolrDocuments(SolrDocumentList solrDocuments) {
        this.solrDocuments = solrDocuments;
    }

    public Map<String, Map<String, List<String>>> getHighlighting() {
        return highlighting;
    }

    public void setHighlighting(Map<String, Map<String, List<String>>> highlighting) {
        this.highlighting = highlighting;
    }
}
