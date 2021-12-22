package com.tim.crowdfunding.mvc.config;

import com.google.gson.Gson;
import com.tim.crwodfunding.constant.CrowdConstant;
import com.tim.crwodfunding.util.CrowdUtil;
import com.tim.crwodfunding.util.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@ControllerAdvice表示基于注解的异常处理器类
@ControllerAdvice
public class CrowdExceptionResolver {

    //@ExceptionHandler将异常与方法关联
    // 空指针异常
    @ExceptionHandler(NullPointerException.class)
    public ModelAndView resolveNullPointerException(NullPointerException exception, HttpServletRequest request,
                                                    HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName, exception, request, response);
    }

    // 创建通用方法
    private ModelAndView commonResolve(String viewName, Exception exception,
                                       HttpServletRequest request, HttpServletResponse response) throws IOException {
        //判断当前请求类型
        boolean judgeResult = CrowdUtil.judgeRequestType(request);
        //如果为Ajax请求
        if (judgeResult) {
            //创建 ResultEntity 对象
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
            //创建Gson对象
            Gson gson = new Gson();
            //将ResultEntity对象转换为JSON字符串
            String json = gson.toJson(resultEntity);
            //将JSON字符作为响应体返回给浏览器
            response.getWriter().write(json);
            // 上面已经通过原生response对象返回了响应，因此不再提供ModelAndView对象
            return null;
        }
        //如果不是Ajax请求，则创建ModelAndView对象
        ModelAndView modelAndView = new ModelAndView();
        //将Exception对象存入模型
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION, exception);
        //设置对应的视图名称
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
