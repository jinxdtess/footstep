package org.jinxd.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class NormalCount {
     @Around("execution(* org.jinxd.service.BallService.think(..)")
     public void procee(ProceedingJoinPoint point) throws Throwable{
    	 Object[] args=point.getArgs();
    	 if(args!=null && args.length>0 && args[0].getClass() == Integer.class){
    		 Integer num=(Integer) args[0];
    	 }
    	 
     }
}
