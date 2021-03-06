package com.jd.jr.chapter5.bigpipe.web.controller.bigpage;

import com.jd.jr.chapter5.bigpipe.BigPipeContext;
import com.jd.jr.chapter5.bigpipe.Pagelet;
import com.jd.jr.chapter5.bigpipe.PageletResult;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;

/**
 * User: 吴海旭
 * Date: 2016-11-12
 * Time: 下午6:39
 */
@Controller("bigpipe/part1")
public class Part1Pagelet implements Pagelet {

    @Override
    public PageletResult run(final BigPipeContext context, final HttpServletResponse response) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }

        context.put("msg", "这是第一段内容");

        return PageletResult.pageletResult("part1")
                .container("part1")
                .cssUrl("/static/bigpipe/part1.css")
                .jsUrl("/static/bigpipe/part1.js");
    }
}
