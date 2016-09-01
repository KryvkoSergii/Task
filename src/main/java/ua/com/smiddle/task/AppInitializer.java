package ua.com.smiddle.task;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ua.com.smiddle.task.configs.AppConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Added to ${PACKAGE_NAME} by A.Osadchuk on 08.04.2016 at 16:07.
 * Project: Manager
 */
public class AppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        //Creating Spring context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.getEnvironment().setActiveProfiles("production");
        rootContext.register(AppConfig.class);
        //Binding MVC context to IoC
        rootContext.setServletContext(servletContext);
        //Add listener for manage life cycle of IoC context
        servletContext.addListener(new ContextLoaderListener(rootContext));
        //MVC Servlet-dispatcher registration
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(rootContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}