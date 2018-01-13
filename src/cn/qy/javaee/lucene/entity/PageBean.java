package cn.qy.javaee.lucene.entity;

public class PageBean {
    private Integer allObjNum;
    private Integer allPageNum;
    private Integer pageNum;
    private Integer pageSize;

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

}
