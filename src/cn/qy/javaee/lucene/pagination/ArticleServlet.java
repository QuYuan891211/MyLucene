package cn.qy.javaee.lucene.pagination;

import cn.qy.javaee.lucene.entity.PageBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ArticleServlet")
public class ArticleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            request.setCharacterEncoding("UTF-8");
            Integer currPageNum = Integer.parseInt(request.getParameter("currPageNum"));
            String keyWords = request.getParameter("keyWords");
            ArticleSerive articleSerive = new ArticleSerive();
            PageBean pageBean = articleSerive.fy(keyWords,currPageNum);
            request.setAttribute("pageBean",pageBean);
            request.getRequestDispatcher("/list.jsp").forward(request,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
