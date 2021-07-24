package com.accumulate.interceptor;

import com.accumulate.annotation.AccessLimit;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;


public class RedisInterceptor implements HandlerInterceptor {
    @Autowired
    RedissonClient redissonClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Handler 是否为 HandlerMethod 实例
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 不限制
            if (!handlerMethod.hasMethodAnnotation(AccessLimit.class)) {
                return true;
            }

            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int maxCount = accessLimit.maxCount();
            int seconds = accessLimit.seconds();

            //获取要存储的key
            String key = "AccessLimit: " + request.getRequestURI();
            RBucket<Integer> bucket = redissonClient.getBucket(key);
            Integer count = bucket.get();
            if (count == null || count == 0) {
                count = 1;
                bucket.set(count, seconds, TimeUnit.SECONDS);
                return true;
            }
            if (count < maxCount) {
                count++;
                bucket.setAndKeepTTL(count);
                return true;
            }

            if (count >= maxCount) {
                System.out.println("接口已限制访问");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
