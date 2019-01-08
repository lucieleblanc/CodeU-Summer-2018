
package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.Event;
import codeu.model.data.Media;
import codeu.model.data.Message;
import codeu.model.data.User;

import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.MediaStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* Servlet class responsible for the activity feed page. */
public class ActivityServlet extends HttpServlet {
   
	/** Store class that gives access to Conversations. */
	private ConversationStore conversationStore;

	/** Store class that gives access to Messages. */
	private MessageStore messageStore;

	/** Store class that gives access to Users. */
	private UserStore userStore;

	/** Store class that gives access to Users. */
	private MediaStore mediaStore;

	/** Set up state for handling chat requests. */
	@Override
	public void init() throws ServletException {
		super.init();
		setConversationStore(ConversationStore.getInstance());
		setMessageStore(MessageStore.getInstance());
		setUserStore(UserStore.getInstance());
		setMediaStore(MediaStore.getInstance());
	}

 /**
   * Sets the ConversationStore used by this servlet. This function provides a common setup method
   * for use by the test framework or the servlet's init() function.
   */
  void setConversationStore(ConversationStore conversationStore) {
    this.conversationStore = conversationStore;
  }

  /**
   * Sets the MessageStore used by this servlet. This function provides a common setup method for
   * use by the test framework or the servlet's init() function.
   */
  void setMessageStore(MessageStore messageStore) {
    this.messageStore = messageStore;
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
   * Sets the MediaStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setMediaStore(MediaStore mediaStore) {
    this.mediaStore = mediaStore;
  }



	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {

       /* Get all events. */
		List<Event> events = new ArrayList<>();
		List<Message> messages = messageStore.getAllMessages();
	    List<Media> media = mediaStore.getAllMedia();
	    List<User> users = userStore.getAllUsers();
	    List<Conversation> conversations = conversationStore.getAllConversations();
	    for(Event m : messages) {
	      events.add(m);
	    }
	    for(Event m : media) {
	      events.add(m);
	    }
	    for(Event u : users) {
	      events.add(u);
	    }
	    for(Event c : conversations) {
	      events.add(c);
	    }
	    events.sort(Comparator.comparingLong(event -> event.getCreationTimeLong()));

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