package com.nixsolutions.crudapp.config;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.EnumSet;

public class WebAppInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    public void onStartup(ServletContext container) {

        AnnotationConfigWebApplicationContext context
                = new AnnotationConfigWebApplicationContext();

        context.register(WebConfig.class);
        context.register(AppConfig.class);
        context.register(SecurityConfig.class);

        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new CXFServlet());
        FilterRegistration.Dynamic filter = container.addFilter("springSecurityFilterChain", new DelegatingFilterProxy());

        container.addListener(new ContextLoaderListener(context));
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false,"/*");
        dispatcher.addMapping("/*");
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { AppConfig.class, SecurityConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}