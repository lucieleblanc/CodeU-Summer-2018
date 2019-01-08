
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

/* 
 * Servlet class responsible for the profile view page. 
 * Alows users to view, but not edit other user's profiles.
 */
public class ProfileViewServlet extends HttpServlet {

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

        String uri = request.getRequestURI();
        String username = uri.substring(uri.lastIndexOf('/')+1);
        User user = userStore.getUser(username);
        if (user == null) {
          return;
        }
        List<Conversation> userConvos = conversationStore.getConversationWithOwner(user.getId());
        request.setAttribute("conversations", userConvos);

        request.setAttribute("username", username);

        System.out.println("In doGet(), user.getBio(): "+ user.getBio());
        request.setAttribute("bio", user.getBio());
        request.getRequestDispatcher("/WEB-INF/view/profileView.jsp").forward(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {  
    }

}