package cn.qy.javaee.lucene.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import javax.swing.text.Document;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class LuceneUtil {


    private  static Directory directory;
    private  static Analyzer analyzer;
    private  static IndexWriter.MaxFieldLength maxFieldLength;
    private static Version version;
    static {
        try{
            version = Version.LUCENE_30;
            maxFieldLength = IndexWriter.MaxFieldLength.LIMITED;
            directory = FSDirectory.open(new File("D:\\WebProjects\\qy\\IdeaProjects\\MyLuence\\LuceneDBDB"));
            analyzer = new StandardAnalyzer(version);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public static org.apache.lucene.document.Document javaBean2Document(Object object) throws Exception{
        org.apache.lucene.document.Document document = new org.apache.lucene.document.Document();
        Class clazz =  object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            String name = field.getName();
            String methodName = "get"+ name.substring(0,1).toUpperCase() + name.substring(1);
            Method method = clazz.getDeclaredMethod(methodName,null);
            String value = method.invoke(object,null).toString();
            document.add(new org.apache.lucene.document.Field(name,value, org.apache.lucene.document.Field.Store.YES, org.apache.lucene.document.Field.Index.ANALYZED));
        }
        return document;
    }
    public static Object document2JavaBean(org.apache.lucene.document.Document document,Class clazz) throws Exception{
        Object obj = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            String fieldName = field.getName();
            String fieldValue = document.get(fieldName);
            BeanUtils.setProperty(obj,fieldName,fieldValue);
        }
        return obj;
    }


    public static Directory getDirectory() {
        return directory;
    }

    public static Analyzer getAnalyzer() {
        return analyzer;
    }

    public static IndexWriter.MaxFieldLength getMaxFieldLength() {
        return maxFieldLength;
    }

    public static Version getVersion() {
        return version;
    }

}
