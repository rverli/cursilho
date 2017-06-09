package com.sicpa.thymeleaf.poc.aqualis.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoggingInterceptor implements HandlerInterceptor  {
	
	private static final String REQUEST_URL_LABEL = "Request URL::";
	private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		logger.info("---Before Method Execution---");
		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
		logger.info(REQUEST_URL_LABEL + request.getRequestURL().toString() + ":: Start Time=" + System.currentTimeMillis());
		return true;
	}
	@Override
	public void postHandle(	HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) throws Exception {
			logger.info("---method executed---");
			logger.info(REQUEST_URL_LABEL + request.getRequestURL().toString() + " Sent to Handler :: Current Time=" + System.currentTimeMillis());
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) throws Exception {
		
		logger.info("---Request Completed---");
		long startTime = (Long) request.getAttribute("startTime");
		logger.info(REQUEST_URL_LABEL + request.getRequestURL().toString() + ":: End Time=" + System.currentTimeMillis());
		logger.info(REQUEST_URL_LABEL + request.getRequestURL().toString() + ":: Time Taken=" + (System.currentTimeMillis() - startTime));
	}

}
