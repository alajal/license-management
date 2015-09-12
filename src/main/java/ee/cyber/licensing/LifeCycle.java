package ee.cyber.licensing;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class LifeCycle implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //enne Servletide tekitamist
        //ühenduste lahtitegemine
        //andmebaasi seadistamine
        //emailide teavituste threadi töölepanemine
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //enne Servletide kinni minekut
        //ühendused katkestada
        //threadide lõpetamine
    }
}
