package cn.qy.javaee.lucene.entity;

import java.util.ArrayList;
import java.util.List;

public class PageBean {
    private Integer allObjNum;
    private Integer allPageNum;
    private Integer pageNum;
    private Integer pageSize = 2;


    private List<Article> articleList = new ArrayList<Article>();
    public PageBean(){};

    public PageBean(Integer allObjNum, Integer allPageNum, Integer pageNum, Integer pageSize, Integer currPageNo) {
        this.allObjNum = allObjNum;
        this.allPageNum = allPageNum;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.currPageNo = currPageNo;
    }

    public Integer getAllObjNum() {
        return allObjNum;
    }

    public void setAllObjNum(Integer allObjNum) {
        this.allObjNum = allObjNum;
        if(this.allObjNum % this.pageSize == 0){
            this.allPageNum = this.allObjNum / this.pageSize;
        }else{
            this.allPageNum = this.allObjNum / this.pageSize + 1;
        }
    }

    public Integer getAllPageNum() {
        return allPageNum;
    }

    public void setAllPageNum(Integer allPageNum) {
        this.allPageNum = allPageNum;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrPageNo() {
        return currPageNo;
    }

    public void setCurrPageNo(Integer currPageNo) {
        this.currPageNo = currPageNo;
    }

    private Integer currPageNo;
    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}
