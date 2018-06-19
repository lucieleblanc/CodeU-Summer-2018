
package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User; 
import java.io.IOException;

import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.BioStore; /**have to go make this page (?) **/ 
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* Servlet class responsible for the activity feed page. */
public class ProfileServlet extends HttpServlet {
    
    /* Store class that gives access to users. */
    private UserStore userStore;
    /* Store class that gives access to conversations. */
    private ConversationStore conversationStore;
    /* Store class that gives access to Messages. */
    private MessageStore messageStore;
    /*adding store class that gives access to biography*/
    private BioStore bioStore; 
    
    
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
        setBioStore(BioStore.getInstance()); 
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

    /**
    *Attempting to make a new data store for the about me/ bio section
    */

    void setBioStore(BioStore bioStore){
        this.bioStore = bioStore;
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        
        /* Get all conversations. */
        List<Conversation> conversations = conversationStore.getAllConversations();
        // List<Bio> bios = bioStore.getAllBios(); 

        /* Make the conversations variable accesible to the jsp file. */
        request.setAttribute("conversations", conversations);

        
        request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
            String userBio = request.getParameter("bio");
       /** User.getBio(bio);**/
            System.out.println(userBio);

            response.sendRedirect("/profile.jsp");
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
