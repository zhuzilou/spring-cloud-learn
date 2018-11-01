package com.dxinfor.common.springcloudzuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * zuul不仅只是路由，并且还能过滤，做一些安全验证
 *
 * @author endless
 * @date 2018/10/30
 */
public class MyFilter extends ZuulFilter {
    private static final Logger LOG = LoggerFactory.getLogger(ZuulFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        LOG.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        Object accessToken = request.getParameter("token");
        if (null == accessToken) {
            LOG.warn("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty");
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
            return null;
        }
        LOG.info("OK");
        return null;
    }
}
