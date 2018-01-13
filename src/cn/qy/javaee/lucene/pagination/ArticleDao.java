package cn.qy.javaee.lucene.pagination;

import cn.qy.javaee.lucene.entity.Article;
import cn.qy.javaee.lucene.entity.PageBean;
import cn.qy.javaee.lucene.util.LuceneUtil;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ArticleDao {
    private PageBean pageBean = new PageBean();

    public Integer getObjNum(String word) throws Exception {
        String keywords = word;
        IndexSearcher searcher = new IndexSearcher(LuceneUtil.getDirectory());
        QueryParser queryParser = new QueryParser(LuceneUtil.getVersion(),"content",LuceneUtil.getAnalyzer());
        Query query = queryParser.parse(keywords);
        TopDocs topDocs = searcher.search(query,100000);
        pageBean.setAllObjNum(topDocs.totalHits);
        System.out.println("总数为："+topDocs.totalHits);
        searcher.close();
        return pageBean.getAllObjNum();
    }
    public List<Article> getAllRecordWithPagination(String words, Integer start, Integer size) throws Exception{
        List<Article> articleList = new ArrayList<Article>();
        IndexSearcher searcher = new IndexSearcher(LuceneUtil.getDirectory());
        QueryParser queryParser = new QueryParser(LuceneUtil.getVersion(),"content",LuceneUtil.getAnalyzer());
        Query query = queryParser.parse(words);
        TopDocs docs = searcher.search(query,100000);
        int middle = Math.min(start+size,docs.totalHits);
        for(int i = start;i<=middle;i++ ){
            ScoreDoc scoreDoc = docs.scoreDocs[i];
            Document document = searcher.doc(scoreDoc.doc);
            Article article =  (Article) LuceneUtil.document2JavaBean(document,Article.class);
            articleList.add(article);
        }
        return articleList;
    }
}
