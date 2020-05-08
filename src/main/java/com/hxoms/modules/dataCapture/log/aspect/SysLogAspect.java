

package com.hxoms.modules.dataCapture.log.aspect;


import com.hxoms.modules.dataCapture.log.annotation.SysLog;
import com.hxoms.modules.dataCapture.entity.SysLogEntity;
import com.hxoms.modules.dataCapture.service.DataCaptureService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;


/**
 * @authore:wjf
 * @data 2020/5/6 11:44
 * @Description:
 ***/
@Aspect
@Component
public class SysLogAspect {

	@Autowired
	private DataCaptureService a01Service;

	@Pointcut("@annotation(com.hxoms.modules.dataCapture.log.annotation.SysLog)")
	public void logPointCut() {
		
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;

		//保存日志
		saveSysLog(point, time);

		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		SysLogEntity sysLog = new SysLogEntity();
		SysLog syslog = method.getAnnotation(SysLog.class);

		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");

		//请求的参数
		Object[] args = joinPoint.getArgs();
		if(syslog != null){

			//注解上的描述
			String tableName = args[0].toString().substring(0,3);
			int  updateListsize =0 ;
			if(args[1].getClass() == ArrayList.class){

			     updateListsize  =	((List)args[1]).size();

			}
			sysLog.setOperation("表"+tableName+syslog.value()+ "\n" + updateListsize +"条");
		}

		try{
			String params =args[0].toString();
			sysLog.setParams(params);
		}catch (Exception e){

		}

		// 获取request
//		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		//设置IP地址
		sysLog.setIp(getLocalIp());



		sysLog.setTime(time);
		sysLog.setCreateDate(new Date());
		//保存系统日志
		a01Service.insertSysLog(sysLog);
	}

	public String getLocalIp(){
		InetAddress inetAddress= null;
		try {
			inetAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		String ip=inetAddress.getHostAddress().toString();//获得本机Ip
		return ip;
	}

	public static String toString(Object[] a) {
		if (a == null)
			return "null";

		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0; ; i++) {
			b.append(String.valueOf(a[i]));
			if (i == iMax)
				return b.append(']').toString();
			b.append(", ");
		}
	}

}
