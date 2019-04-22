package com.fnst.springboot01.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	@ExceptionHandler(value = Exception.class)
//	@ResponseBody
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", e);
		mav.addObject("url", req.getRequestURL());
		mav.setViewName("error");
		System.out.println("GlobalDefaultExceptionHandler.defaultErrorHandler()");
		return mav;
		// return "error";
	}
}
