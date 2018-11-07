
package codeu.controller;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* Servlet class responsible for the activity feed page. */
public class ActivityServlet extends HttpServlet {
        
	/**
	  * Set up state for handling activity-related requests. This method is only called when
	  * running in a server, not when running in a test.
	  */
	@Override
	public void init() throws ServletException {
	  super.init();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {

       /* Get all events. */
//	   List <Event> events = eventStore.getAllEventsSorted();

	   /* Make the events list accesible to the activity.jsp file. */
//	   request.setAttribute("events", events);

       /* Forward the request to the activity.jsp file. */
	   request.getRequestDispatcher("/WEB-INF/view/activity.jsp").forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
	}
}