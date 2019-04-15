package com.hjl.apigateway.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.hjl.apigateway.exception.RateLimitException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVLET_DETECTION_FILTER_ORDER;

/**
 * @ClassName RateFilter
 * @AUthor hanjialiang
 * @Date 2019-04-15 18:33
 * @Version 1.0
 * @Description 限流
 */
public class RateLimitFilter extends ZuulFilter {

    private static final RateLimiter RATE_LIMITER = RateLimiter.create(100);

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return SERVLET_DETECTION_FILTER_ORDER -1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        if(RATE_LIMITER.tryAcquire()){
            throw new RateLimitException();
        }
        return null;
    }
}
