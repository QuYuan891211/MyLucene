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
    private static Directory directory;
    private static Analyzer analyzer;
    private static IndexWriter.MaxFieldLength maxFieldLength;
    static {
        try{

            directory = FSDirectory.open(new File("D:/WebProjects/qy/IdeaProjects/MyLuence/LuceneDBDB"));
            analyzer = new StandardAnalyzer(Version.LUCENE_30);
            maxFieldLength = IndexWriter.MaxFieldLength.LIMITED;
        }catch (Exception e){
            throw new RuntimeException();
        }


    }
    public LuceneUtil() {};

    public static org.apache.lucene.document.Document javaBean2Document(Object object) throws Exception{
        Class clazz = object.getClass();
        java.lang.reflect.Field[] fields = clazz.getDeclaredFields();
        org.apache.lucene.document.Document document = new org.apache.lucene.document.Document();
        for (Field field: fields){
            field.setAccessible(true);
            String name = field.getName();
            String init = name.substring(0,1).toUpperCase();
            String methodName = "get" + init + name.substring(1);
            Method method = clazz.getDeclaredMethod(methodName,null);
            String value = method.invoke(object,null).toString();
            document.add(new org.apache.lucene.document.Field(name,value, org.apache.lucene.document.Field.Store.YES, org.apache.lucene.document.Field.Index.ANALYZED));
        }


        return document;
    }
    public static Object document2JavaBean(org.apache.lucene.document.Document document,Class clazz) throws Exception{
        Field[] fields = clazz.getDeclaredFields();
        Object object = new Object();
        for(Field field : fields){
            field.setAccessible(true);
            String name = field.getName();
            String value = document.get(name);
            BeanUtils.setProperty(object,name,value);
        }
        return object;
    }
}
