package com.agungsetiawan.finalproject.config;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

/**
 *
 * @author awanlabs
 */
public class Initializer implements WebApplicationInitializer{
    
    @Override
    public void onStartup(ServletContext sc) throws ServletException {
        AnnotationConfigWebApplicationContext ctx=new AnnotationConfigWebApplicationContext();
        ctx.register(WebAppConfig.class);
        sc.addListener(new ContextLoaderListener(ctx));
        ctx.setServletContext(sc);
        
        FilterRegistration.Dynamic security = sc.addFilter("springSecurityFilterChain", new DelegatingFilterProxy());
        EnumSet<DispatcherType> securityDispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
        security.addMappingForUrlPatterns(securityDispatcherTypes, true, "/*");
        
        ServletRegistration.Dynamic servlet=sc.addServlet("dispatcher", new DispatcherServlet(ctx));
        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);
        
    }
    
}
