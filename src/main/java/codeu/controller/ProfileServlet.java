
package codeu.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import java.io.IOException;

import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.util.List;
import java.util.UUID;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/* Servlet class responsible for the activity feed page. */
public class ProfileServlet extends HttpServlet {

    /* Store class that gives access to users. */
    private UserStore userStore;
    /* Store class that gives access to conversations. */
    private ConversationStore conversationStore;
    /* Store class that gives access to Messages. */
    private MessageStore messageStore;


    /**
     * Set up state for handling activity-related requests. This method is only called when
     * running in a server, not when running in a test.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        setUserStore(UserStore.getInstance());
        setConversationStore(ConversationStore.getInstance());
        setMessageStore(MessageStore.getInstance());
    }

    /**
     * Sets the UserStore used by this servlet. This function provides a common setup method for use
     * by the test framework or the servlet's init() function.
     */
    void setUserStore(UserStore userStore) {
        this.userStore = userStore;
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


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

        String username = (String) request.getSession().getAttribute("user");
        User user = userStore.getUser(username);
        if (user == null) {
          // NOTE(fang): Don't do response.sendRedirect("/profile.jsp");
          // here, which will cause infinite redirect.
          // Seems "/profile.jsp" reinvoke the servlet, whereas
          // "/WEB-INF/view/profile.jsp" simply renders the jsp file.
          //request.getRequestDispatcher("/WEB-INF/view/profile/").forward(request, response);
          return;
        }
        List<Conversation> userConvos = conversationStore.getConversationWithOwner(user.getId());
        request.setAttribute("conversations", userConvos);
        // TODO(lauren): Not all conversations, should be only conversations that belong
        // to the current user.
        //UUID ownerid = (UUID) request.getSession().getAttribute("owner");
        // Fang: not sure what you are trying to do with userUrl below,
        // commenting out because they are causing NPE in unit test.
        // String requestUrl = request.getRequestURI();
        // String userUrl = requestUrl.substring("/profile/".length());
        //request.getRequestDispatcher("/WEB-INF/view/profile/").forward(request, response);

        //List <Conversation> conversations = conversationStore.getAllConversations();
        //request.setAttribute("conversations", conversations);
        System.out.println("In doGet(), user.getBio(): "+ user.getBio());
        request.setAttribute("bio", user.getBio());
        request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {  
    String userProfile = (String) request.getSession().getAttribute("user");
        if(userProfile == null){
          response.sendRedirect("/login");
          return;
        }
        String requestUrl = request.getRequestURI();
        String userUrl = requestUrl.substring("/profile/".length());
        String username = (String) request.getSession().getAttribute("user");
        User user = userStore.getUser(username);
        String bio = request.getParameter("bio");
        System.out.println("In doPost(), bio got from JSP: " + bio);
        /*if (user == null) {
          request.getRequestDispatcher("/WEB-INF/view/profile/").forward(request, response);
          return;

        }*/
        System.out.println("Calling updateBio: " + bio);
        userStore.updateBio(user.getId(), bio);
        response.sendRedirect("/profile/" + userUrl);
    }
   /** @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        //which user is logged in
        //make a call to userStore to update bio for this user


        String bio = request.getParameter("bio");

        System.out.println("About Me: " + bio);

        response.sendRedirect("/profile.jsp");
    }**/
}
