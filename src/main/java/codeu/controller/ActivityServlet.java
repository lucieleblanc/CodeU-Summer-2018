
package codeu.controller;

import codeu.model.data.Event;
import java.io.IOException;

import codeu.model.store.basic.EventStore;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* Servlet class responsible for the activity feed page. */
public class ActivityServlet extends HttpServlet {
        
	/* Store class that gives access to Events. */
	private EventStore eventStore;


	/**
	  * Set up state for handling activity-related requests. This method is only called when
	  * running in a server, not when running in a test.
	  */
	@Override
	public void init() throws ServletException {
	  super.init();
	  setEventStore(EventStore.getInstance());
	}

	/**
	 * Sets the EventStore used by this servlet. This function provides a common setup method for
	 * use by the test framework or the servlet's init() function.
	 */
	void setEventStore(EventStore eventStore){
		this.eventStore = eventStore;
	}


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {

       /* Get all events. */
	   List <Event> events = eventStore.getAllEventsSorted();

	   /* Make the events list accesible to the activity.jsp file. */
	   request.setAttribute("events", events);

       /* Forward the request to the activity.jsp file. */
	   request.getRequestDispatcher("/WEB-INF/view/activity.jsp").forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
	}
}