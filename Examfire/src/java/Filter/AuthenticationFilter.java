/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Dan
 */
public class AuthenticationFilter implements Filter {
    
    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession ses= ((HttpServletRequest) request).getSession(false);
        if(ses == null){
            request.getServletContext().getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
        }
        if(ses.getAttribute("user") == null){
            request.getServletContext().getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        
    }
    
    
}
