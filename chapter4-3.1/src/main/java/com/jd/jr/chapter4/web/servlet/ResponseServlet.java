package com.jd.jr.chapter4.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: 吴海旭
 * Date: 2016-11-12
 * Time: 下午2:31
 */
@WebServlet(name = "responseServlet", urlPatterns = "/response")
public class ResponseServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write("haha");

        //servlet3.1明确阐明了它的作用：清空buffer中的所有数据、以及状态码、头字段。Servlet3.1也清空getServletOutputStream 或 getWriter调用的状态
        resp.reset();

        resp.setContentType("text/plain;charset=utf-8");
        resp.getWriter().write("哈哈");
    }
}
