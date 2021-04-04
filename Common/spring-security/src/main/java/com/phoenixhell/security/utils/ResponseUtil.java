package com.phoenixhell.security.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixhell.utils.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtil {

    public static void out(HttpServletResponse response, CommonResult r) {
        /*
        ObjectMapper类(com.fasterxml.jackson.databind.ObjectMapper)是Jackson的主要类，
        它可以帮助我们快速的进行各个类型和Json类型的相互转换。
         */
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
//            1.内置对象out的类型是JspWriter；
//　response.getWrite()返回的类型是PrintWriter；
//　out和response.getWriter的类不一样，一个是JspWriter，另一个是java.io.PrintWriter。
//            2. 获取方式不同：
//　　JspWriter是JSP的内置对象，直接使用即可，对象名out是保留字，也只能通过out来调用其相关方法。
//　　此外还可以通过内置对象pageContext.getOut();获得；
//　　PrintWriter则是在用的时候需要通过内置对象response.getWriter();获得
//            3.执行原理不同:
//　　JspWriter相当于一个带缓存功能的printWriter，它不是直接将数据输出到页面，
//　　而是将数据刷新到response的缓冲区后再输出，
//　　response.getWriter直接输出数据（response.print()），所以（out.print）只能在其后输出。
//            4. JspWriter的print()方法会抛出IOException；而PrintWriter则不会5.out为jsp的内置对象，刷新jsp页面，自动初始化获得out对象，所以使用out对象是需要刷新页面的，
//            而response.getWriter()响应信息通过out对象输出到网页上，当响应结束时它自动被关闭，与jsp页面无关，无需刷新页面
//            6.out的print()方法和println()方法在缓冲区溢出并且没有自动刷新时候会产生ioexception，
//            而response.getWrite()方法的print和println中都是抑制ioexception异常的，不会有ioexception
//            out.println(""); 方法并不能也页面布局换行，只能领html代码换行，
//            要实现页面布局换行可以：out.println("</br>");
            mapper.writeValue(response.getWriter(), r);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
