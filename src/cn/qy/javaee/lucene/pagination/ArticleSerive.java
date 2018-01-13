package cn.qy.javaee.lucene.pagination;

import cn.qy.javaee.lucene.entity.Article;
import cn.qy.javaee.lucene.entity.PageBean;

import java.util.List;

public class ArticleSerive {
    private ArticleDao articleDao = new ArticleDao();
    public PageBean fy (String keyWords, int currentNo) throws Exception{
        PageBean pageBean = new PageBean();
        pageBean.setCurrPageNo(currentNo);
        Integer start = (pageBean.getCurrPageNo()-1)*pageBean.getPageSize();
        pageBean.setAllObjNum(articleDao.getObjNum(keyWords));
        List<Article> articleList =  articleDao.getAllRecordWithPagination(keyWords,start,pageBean.getPageSize());
        pageBean.setArticleList(articleList);
        return pageBean;
    }
}
