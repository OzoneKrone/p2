package it.unisa.control;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageValidationFilter implements Filter {

    private static final List<String> ALLOWED_PAGES = Arrays.asList("Home.jsp", "Ps5.jsp", "XboxSeries.jsp", "Switch.jsp", "Ps4.jsp", "XboxOne.jsp");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String pageParam = httpRequest.getParameter("page");
        if (pageParam != null) {
            String normalizedPath = Paths.get(pageParam).normalize().toString();
            if (!ALLOWED_PAGES.contains(normalizedPath)) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/home?page=Home.jsp");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
    }
}
