package controller.fiters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * filter for language
 */
@WebFilter(dispatcherTypes = {
        DispatcherType.REQUEST,
        DispatcherType.FORWARD,
        DispatcherType.INCLUDE
},urlPatterns = { "/*" })
public class LanguageFilter implements Filter {

    /**
     * {@inheritDoc}
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        String language = (String) session.getAttribute("lang");
        String languageParam = req.getParameter("lang");
        if(language!=languageParam && languageParam!=null){
            session.setAttribute("lang", languageParam);
        }
        chain.doFilter(request, response);
    }
}
