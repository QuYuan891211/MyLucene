package cn.qy.javaee.lucene.secondApp;

import cn.qy.javaee.lucene.entity.Article;
import cn.qy.javaee.lucene.util.LuceneUtil;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.junit.Test;

import javax.swing.text.Document;
import java.util.ArrayList;
import java.util.List;

public class SecondApp {
    @Test
    public void createIndexDB() throws Exception{
        Article article = new Article(2,"腾讯","腾讯的主席是马化腾");
        org.apache.lucene.document.Document document = LuceneUtil.javaBean2Document(article);
        IndexWriter writer = new IndexWriter(LuceneUtil.getDirectory(),LuceneUtil.getAnalyzer(),LuceneUtil.getMaxFieldLength());
        writer.addDocument(document);
        writer.close();
    }
    @Test
    public void findIndexDB() throws Exception{
        List<Article> articleList = new ArrayList<Article>();
        String keywords = "腾";
        QueryParser queryParser = new QueryParser(LuceneUtil.getVersion(),"content",LuceneUtil.getAnalyzer());
        Query query = queryParser.parse(keywords);
        IndexSearcher searcher = new IndexSearcher(LuceneUtil.getDirectory());
        TopDocs docs = searcher.search(query,10);
        for(ScoreDoc scoreDoc:docs.scoreDocs){
            int doc = scoreDoc.doc;
            org.apache.lucene.document.Document document = searcher.doc(doc);
            Article article = (Article) LuceneUtil.document2JavaBean(document,Article.class);
            articleList.add(article);
        }
        for(Article article:articleList){
            System.out.println(article.getId()+" " + article.getTitle() + " " + article.getContent());
        }
    }
}
