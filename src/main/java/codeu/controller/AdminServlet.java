package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/** Servlet class responsible for the admin page. */
public class AdminServlet extends HttpServlet {

  /** Store class that gives access to Conversations. */
  private ConversationStore conversationStore;

  /** Store class that gives access to Messages. */
  private MessageStore messageStore;

  /** Store class that gives access to Users. */
  private UserStore userStore;

  /** Store list of approved admins.
   * TODO: give users an isAdmin property and determine access that way instead.
   */
  private String[] adminUserNames = new String[]{"admin"};

  /** Set up state for handling chat requests. */
  @Override
  public void init() throws ServletException {
    super.init();
    setConversationStore(ConversationStore.getInstance());
    setMessageStore(MessageStore.getInstance());
    setUserStore(UserStore.getInstance());
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

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {
    String username = (String) request.getSession().getAttribute("user");

    if (username == null) {
      // user is not logged in, don't let them access admin page
      response.sendRedirect("/login");
      return;
    }

    User user = userStore.getUser(username);
    boolean isAdmin = userIsAdmin(user);
    System.out.println("\"" + user + "\"'s admin status is " + isAdmin);
    if (isAdmin) {
      // user was in approved list, so show admin page
      request.setAttribute("numUsers", userStore.getNumUsers());
      request.setAttribute("numConversations", conversationStore.getNumConversations());
      request.setAttribute("numMessages", messageStore.getNumMessages());
      List<String> topUsers = userStore.getTopUsernames();
      request.setAttribute("oldestUser", topUsers.get(0));
      request.setAttribute("newestUser", topUsers.get(1));
      request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
    } else {
      response.sendRedirect("/login");
    }

  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {
    return;
  }

  /** Determines whether a user has admin privileges
   *  by checking if their name is in a special list.
   *  Planning to change this later by adding an "admin"
   *  flag as an instance variable in the User class.
   */
  private boolean userIsAdmin(User user) {
    System.out.println("Checking privilege of user + \"" + user.getName() + "\"");
    if (user != null) {
      for (String adminName : adminUserNames) {
        if (user.getName().equalsIgnoreCase(adminName)) {
          return true;
        }
      }
    }
    return false;
  }

}