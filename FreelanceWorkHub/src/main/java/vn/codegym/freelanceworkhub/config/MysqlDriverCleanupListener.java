package vn.codegym.freelanceworkhub.config;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MysqlDriverCleanupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // No implementation needed
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            // Call the correct method to stop the MySQL Connector/J cleanup thread
            AbandonedConnectionCleanupThread.checkedShutdown();
            System.out.println("Successfully shut down MySQL AbandonedConnectionCleanupThread.");
        } catch (Exception e) {
            System.err.println("Error shutting down MySQL AbandonedConnectionCleanupThread: " + e.getMessage());
        }
    }
}