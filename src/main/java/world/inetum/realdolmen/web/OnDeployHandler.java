package world.inetum.realdolmen.web;

import org.flywaydb.core.Flyway;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class OnDeployHandler implements ServletContextListener {

    @Resource(lookup = "java:app/jdbc/trm4j")
    private DataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Flyway.configure()
                .dataSource(dataSource)
                .load()
                .migrate();
    }
}
