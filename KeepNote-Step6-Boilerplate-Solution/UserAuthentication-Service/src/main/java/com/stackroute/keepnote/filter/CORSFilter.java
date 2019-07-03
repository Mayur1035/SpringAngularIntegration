package com.stackroute.keepnote.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
@WebFilter(filterName = "Filter", urlPatterns =
{ "/*" }, initParams = {})
public class CORSFilter
    implements Filter
{
    @Override
    public void destroy()
    {
    }

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods",
				"POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers",
				"x-requested-with,Content-Type,Cache-Control,User-Agent,Referrer,Authorization");
		chain.doFilter(req, res);
	}

    @Override
    public void init( FilterConfig filterConfig )
    {
    }
}
