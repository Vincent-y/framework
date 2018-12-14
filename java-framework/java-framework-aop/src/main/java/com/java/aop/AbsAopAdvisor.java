package com.java.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * <p>restful aop
 * <p>@author dragon
 * <p>2015年8月10日
 * <p>@version 1.0
 */
public abstract class AbsAopAdvisor {

	long startTime  = 0L;
	
	/**
	 * 对于要增强的方法，在方法之前执行
	 * @param joinPoint 连接点信息
	 */
	public void before(JoinPoint joinPoint){
		startTime = System.currentTimeMillis();
	}
	
	/**
	 * 对于要增强的方法，在方法之后执行
	 * @param joinPoint  连接点信息
	 */
	public void after(JoinPoint joinPoint){
		String excuteFunc   = "方法:" + joinPoint.getSignature() + "";
		String excuteTime   = (System.currentTimeMillis() - startTime) + "ms";
		System.out.println( excuteFunc + "--------执行时间------" + excuteTime);
	}
	
	
	/**
	 * 对于要增加的方法，方法返回结果后，对结果进行记录或者分析方法
	 * @param object target执行后返回的结果
	 */
	public void afterReturning(Object object){
		//logger.debug("返回对象:{}",object);
	};
	
	/**
	 * 异常处理
	 * @param exception
	 */
	public void handlerException(Throwable exception){
		//System.out.println("--------handlerException------");
	}
	
	
	 /**
	  * 声明环绕通知  处理异常
	  * @param pjp
	  * @return
	  * @throws Throwable
	  */
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {  
        Object object = pjp.proceed();  
        return object;
    }  
  
    enum LogType{
    	NORMAL,//正常
    	ERROR//错误
    }
}
