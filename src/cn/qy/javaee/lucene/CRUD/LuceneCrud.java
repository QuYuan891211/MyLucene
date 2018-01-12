package cn.qy.javaee.lucene.CRUD;

import cn.qy.javaee.lucene.entity.Article;
import cn.qy.javaee.lucene.util.LuceneUtil;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LuceneCrud {
    @Test
    public void addIndexDB() throws Exception{
        Article article = new Article(3,"腾讯","腾讯的股价是800");
        Document document = LuceneUtil.javaBean2Document(article);
        IndexWriter indexWriter = new IndexWriter(LuceneUtil.getDirectory(),LuceneUtil.getAnalyzer(),LuceneUtil.getMaxFieldLength());
        indexWriter.addDocument(document);
        indexWriter.close();
    }
    @Test
    public void updateIndexDB() throws Exception{
        Integer id = 3;
        Article article = new Article(3,"腾讯","腾讯的股价是900");
        Term term = new Term("id",id.toString());
        Document document = LuceneUtil.javaBean2Document(article);
        IndexWriter indexWriter = new IndexWriter(LuceneUtil.getDirectory(),LuceneUtil.getAnalyzer(),LuceneUtil.getMaxFieldLength());
        indexWriter.updateDocument(term,document);
        indexWriter.close();
    }
@Test
    public void deleteIndexDB() throws Exception{
        Integer id = 3;
        IndexWriter writer = new IndexWriter(LuceneUtil.getDirectory(),LuceneUtil.getAnalyzer(),LuceneUtil.getMaxFieldLength());
        Term term = new Term("id",id.toString());
        writer.deleteDocuments(term);
        writer.close();
    }
@Test
    public void deleteAll() throws Exception{
        IndexWriter writer = new IndexWriter(LuceneUtil.getDirectory(),LuceneUtil.getAnalyzer(),LuceneUtil.getMaxFieldLength());
        writer.deleteAll();
        writer.close();
    }
    @Test
    public void findIndexDB() throws Exception{
        List<Article> articleList = new ArrayList<Article>();
        IndexSearcher searcher = new IndexSearcher(LuceneUtil.getDirectory());
        QueryParser queryParser = new QueryParser(LuceneUtil.getVersion(),"content",LuceneUtil.getAnalyzer());
        String keyWords = "腾";
        Query query = queryParser.parse(keyWords);
        TopDocs topDocs = searcher.search(query,100000);
        for(ScoreDoc scoreDoc : topDocs.scoreDocs){
            int doc = scoreDoc.doc;
            Document document = searcher.doc(doc);
            Article article = (Article) LuceneUtil.document2JavaBean(document,Article.class);
            articleList.add(article);
        }
        for(Article article:articleList){
            System.out.println(article.getId() + ":" + article.getTitle()+":" + article.getContent());
        }
        searcher.close();

    }
}
