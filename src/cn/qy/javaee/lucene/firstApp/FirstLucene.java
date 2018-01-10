package cn.qy.javaee.lucene.firstApp;

import cn.qy.javaee.lucene.entity.Article;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FirstLucene {
    @Test
    public void createIndexDB() throws Exception {
        Article article = new Article(1,"腾讯","腾讯计算机系统有限公司成立于1998年11月");
        Document document = new Document();
        document.add(new Field("id",article.getId().toString(), Field.Store.YES, Field.Index.ANALYZED));
        document.add(new Field("title",article.getTitle(), Field.Store.YES, Field.Index.ANALYZED));
        document.add(new Field("content",article.getContent(), Field.Store.YES, Field.Index.ANALYZED));

        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
        Directory directory = FSDirectory.open(new File("D:/WebProjects/qy/IdeaProjects/MyLuence/LuceneDBDB"));
        IndexWriter.MaxFieldLength maxFieldLength = IndexWriter.MaxFieldLength.LIMITED;
        IndexWriter indexWriter = new IndexWriter(directory,analyzer,maxFieldLength);
        indexWriter.addDocument(document);
        indexWriter.close();
    }
    @Test
    public void findIndexDB() throws Exception{
        List<Article> articleList = new ArrayList<Article>();
        String keyWords = "腾";
        Version version = Version.LUCENE_30;
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
        Directory directory = FSDirectory.open(new File("D:/WebProjects/qy/IdeaProjects/MyLuence/LuceneDBDB"));
        IndexWriter.MaxFieldLength maxFieldLength = IndexWriter.MaxFieldLength.LIMITED;
        IndexSearcher searcher = new IndexSearcher(directory);
        QueryParser queryParser = new QueryParser(version,"content",analyzer);
        Query query = queryParser.parse(keyWords);
        TopDocs topDocs = searcher.search(query,10);
        for(ScoreDoc scoreDoc:topDocs.scoreDocs){
            int doc = scoreDoc.doc;
            Document document = searcher.doc(doc);
            String id = document.get("id");
            String title = document.get("title");
            String content = document.get("content");
            Article article = new Article(Integer.parseInt(id),title,content);
            articleList.add(article);
        }
        for(Article article:articleList){
            System.out.println(article.getId()+" " + article.getTitle()+" " + article.getContent());
        }
    }
}
