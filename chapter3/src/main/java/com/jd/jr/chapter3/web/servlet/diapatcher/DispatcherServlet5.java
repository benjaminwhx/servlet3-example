package com.jd.jr.chapter3.web.servlet.diapatcher;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: 吴海旭
 * Date: 2016-11-10
 * Time: 下午5:15
 */
@WebServlet(name = "dispatchServlet5", urlPatterns = "/dispatch5", asyncSupported = true)
public class DispatcherServlet5 extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        if(req.getAttribute("ok") == null) {
            System.out.println("===before start async:" + req.isAsyncStarted());
            final AsyncContext asyncContext = req.startAsync();
            System.out.println("===after start async:" + req.isAsyncStarted());

            asyncContext.setTimeout(10L * 1000);
            asyncContext.addListener(new AsyncListener() {
                @Override
                public void onComplete(final AsyncEvent event) throws IOException {
                    System.out.println("=====async complete");
                }

                @Override
                public void onTimeout(final AsyncEvent event) throws IOException {
                    System.out.println("=====async timeout");
                }

                @Override
                public void onError(final AsyncEvent event) throws IOException {
                    System.out.println("=====async error");
                }

                @Override
                public void onStartAsync(final AsyncEvent event) throws IOException {
                }
            });

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3L * 1000);
                    } catch (InterruptedException e) {
                    }
                    req.setAttribute("ok", "true");
                    req.setAttribute("msg", "success");
                    asyncContext.dispatch();
                    System.out.println("===after dispatch before handle:" + req.isAsyncStarted());
                }
            }).start();

            return;

        } else {
            //可以继续开启异步并分派，在分派到的servlet中还可以继续开启异步流程
            AsyncContext asyncContext = req.startAsync();
            asyncContext.setTimeout(10L * 1000);
            asyncContext.addListener(new AsyncListener() {
                @Override
                public void onComplete(final AsyncEvent event) throws IOException {
                    System.out.println("=====async2 complete");
                }

                @Override
                public void onTimeout(final AsyncEvent event) throws IOException {
                    System.out.println("=====async2 timeout");
                }

                @Override
                public void onError(final AsyncEvent event) throws IOException {
                    System.out.println("=====async2 error");
                }

                @Override
                public void onStartAsync(final AsyncEvent event) throws IOException {
                }
            });

            resp.getWriter().write("hello ");
            //dispatch不会丢失之前的响应流，即保留这个响应流，接着在下一个servlet继续写入，这个不同于forward转发
            asyncContext.dispatch("/dispatch5");
        }


    }
}
