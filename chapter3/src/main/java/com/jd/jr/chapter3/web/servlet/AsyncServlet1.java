package com.jd.jr.chapter3.web.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * User: 吴海旭
 * Date: 2016-11-06
 * Time: 下午4:22
 */
@WebServlet(name = "asyncServlet1", urlPatterns = "/async1", asyncSupported = true)
public class AsyncServlet1 extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        // http1.1默认keep-alive
        resp.setHeader("Connection", "Keep-Alive");
        resp.setContentType("text/html;charset=utf-8");

        PrintWriter out = resp.getWriter();
        out.write("hello async");
        out.write("<br/>");
        out.flush();

        // 1、开启异步
        final AsyncContext asyncContext = req.startAsync();
        // 2、不设置默认jetty是3秒
        asyncContext.setTimeout(10L * 1000);
        // 3、告诉容器分派一个新的线程执行异步任务
        // 这种方式的缺点就是可能和请求用同一个线程池，不推荐这种做法；从本质上讲和同步没啥区别（都要占用一个服务器线程）
        // 不过如果请求压力较小，可以使用这种方法（因为有超时设置，可以防止一直不响应）
        asyncContext.start(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3L * 1000);
                    PrintWriter out = asyncContext.getResponse().getWriter();
                    out.write("over");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    asyncContext.complete();
                }
            }
        });
        // 4、当前线程立即返回
    }
}
