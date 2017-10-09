package com.corpse.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.corpse.model.ErrorResp;
import com.corpse.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
@Component
public class Proxy {
	
	private static final Logger logger = LoggerFactory.getLogger(Proxy.class);
	private static final ObjectMapper mapper = new ObjectMapper();
	
	private String getCurrentUsername() {
		String username = "not found";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof User) {
			username = ((User) principal).getUsername();
		}
		else {
			logger.error("principal is not User");
		}
		return username;
	}
	
	@Pointcut("execution(* com.corpse.controller..*.*(..))")
	public void controller() {
	}
	
	@Before("controller()")
	public void logBefore(JoinPoint joinPoint) {
		String method = joinPoint.getSignature().getName();
		String username = getCurrentUsername();
		if(StringUtils.containsIgnoreCase(method, "object")) {
			String param = "";
			for(Object obj : joinPoint.getArgs()) {
				if(obj instanceof List) {
					try {
						param = mapper.writeValueAsString(obj);
					}
					catch(JsonProcessingException e) {
						e.printStackTrace();
						logger.error(Common.log(username, e.getMessage()));
					}
					break;
				}
			}
			
			logger.info(Common.log(
				username,
				"Method: " + joinPoint.getSignature().getName() + "\n" +
				"Class Name: " + joinPoint.getSignature().getDeclaringTypeName() + "\n" +
				"Arguments: " + param
			));
		}
		else if((StringUtils.containsIgnoreCase(method, "insert") || StringUtils.containsIgnoreCase(method, "update") ||
				StringUtils.containsIgnoreCase(method, "delete") || StringUtils.containsIgnoreCase(method, "getFile")) &&
				!StringUtils.containsIgnoreCase(method, "password")) {
			logger.info(Common.log(
				username,
				"Method: " + joinPoint.getSignature().getName() + "\n" +
				"Class Name: " + joinPoint.getSignature().getDeclaringTypeName() + "\n" +
				"Arguments: " + Arrays.toString(joinPoint.getArgs())
			));
		}
	}
	
	@Around("controller()")
	public Object changeException(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			return joinPoint.proceed();
		}
		catch(Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			e.printStackTrace();
			
			String regex = "hash_sync.+already.+exists";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(errors.getBuffer());
			int code = HttpServletResponse.SC_BAD_REQUEST;
			if(matcher.find()) {
				code = 4000;
			}

			RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
			HttpServletResponse response = ((ServletRequestAttributes) requestAttributes).getResponse();

			ErrorResp errorResp = new ErrorResp();
			errorResp.setCode(code);
			errorResp.setMessage(e.getMessage());
			logger.error(Common.log(getCurrentUsername(), e.getMessage()));
			
			String json = mapper.writer()
					.writeValueAsString(errorResp);
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setContentType("application/json;charset=UTF-8"); 
			PrintWriter pw = response.getWriter();
			pw.println(json);
			pw.close();
			errors.close();
			return null;
		}
	}
}
