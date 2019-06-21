package notemusic;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class GenerateMelodyListener
 *
 */
@WebListener
public class GenerateMelodyListener implements ServletContextListener, ServletRequestListener {

    /**
     * Default constructor. 
     */
    public GenerateMelodyListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
     */
    public void requestDestroyed(ServletRequestEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
     */
    public void requestInitialized(ServletRequestEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
        
    	ThreadPoolExecutor executor = (ThreadPoolExecutor) arg0
                .getServletContext().getAttribute("executor");
        executor.shutdown();
    	
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
    	InputStream inStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("notemusic.properties");
    	Properties props = new Properties();
    	try {
			props.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			props.put("docker_volume", "/opt");

		}
    	ServletContext ctx = arg0.getServletContext();
    	ctx.setAttribute("properties", props);
    	
//    	// create the thread pool
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 200, 50000L,
//                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(100));
//        arg0.getServletContext().setAttribute("executor",
//                executor);
    	
    	
    }
	
}
