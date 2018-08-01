
package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.Event;
import codeu.model.data.Media;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MediaStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.EventStore;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


/* 
 * This servlet will load upon startup and map it to the URL /FileUploadServlet. 
 * Mapped to the profile, profile view, and chat jsp files. The profile jsp file
 * allows you to access and edit your own profile. the profile view jsp file
 * allows others to view, but not edit another user's profile. This is mapped 
 * to the chat jsp file inorder to be able to send images in conversations.
 */
@WebServlet({"profile/FileUploadServlet", "profileView/FileUploadServlet", "chat/FileUploadServlet"})
/* Max file upload size is 16 MB. */
@MultipartConfig(maxFileSize = 16177215)
/* Servlet Class responsible for file uploads. */ 
public class FileUploadServlet extends HttpServlet {

	/* Store class that gives access to Media. */
    private MediaStore mediaStore;
    /* Store class that gives access to Users. */
    private UserStore userStore;
    /* Store class that gives access to Events. */
    private EventStore eventStore;
    /* Store class that gives access to Conversations. */
    private ConversationStore conversationStore;


    /**
     * Set up state for handling activity-related requests. This method is only called when
     * running in a server, not when running in a test.
     */
    @Override
    public void init() throws ServletException {
      super.init();
      setMediaStore(MediaStore.getInstance());
      setUserStore(UserStore.getInstance());
      setEventStore(EventStore.getInstance());
      setConversationStore(ConversationStore.getInstance());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

      String uri = request.getRequestURI();
      String mediaOwner = null;
      Media picture = null;
      if(uri.equals("/profile/FileUploadServlet")) {
        mediaOwner = (String)request.getSession().getAttribute("user");
        picture = mediaStore.getProfilePicture(mediaOwner);
      }
      else if(uri.equals("/profileView/FileUploadServlet")) {
      	mediaOwner = (String)request.getSession().getAttribute("username");
      	picture = mediaStore.getProfilePicture(mediaOwner);
      }
      else if(uri.equals("/chat/FileUploadServlet")) {
      	UUID mediaId = (UUID)request.getSession().getAttribute("mediaId");
        picture = mediaStore.getProfilePicture(mediaId);
      }

      if (picture!=null) {
        BufferedImage bufferedImage = picture.getContent();
        response.setContentType(picture.getContentType());
        String mediaExtension = picture.getContentType().substring(
          picture.getContentType().indexOf('/')+1);
        ImageIO.write(bufferedImage, mediaExtension, response.getOutputStream());
      }
    }

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

      	String uri = request.getRequestURI();
	    
	    String mediaTitle = null;
	    if(!uri.equals("/chat/FileUploadServlet")) {
	      /* Title given by user in profile.jsp. */
          mediaTitle = (String)request.getParameter("Title"); 
        }

        /* Uploader. */
        String mediaOwner = (String)request.getSession().getAttribute("user");
        /* Input stream of the upload file. */
        InputStream inputStream = null;

		/* Get the file chosen by the user. */
		Part filePart = request.getPart("photo");

		/* Get the name of the chat this came from. */
		String chatName = null;
		if(uri.equals("/chat/FileUploadServlet")) {
		  chatName = (String)request.getSession().getAttribute("chatName");
		}
		
		/* Get the InputStream to store the file. */
	    if(filePart != null) {
	      System.out.println(filePart.getName());
	      System.out.println(filePart.getSize());
	      System.out.println(filePart.getContentType());
	      //Input stream of upload file. 
	      inputStream = filePart.getInputStream();
	    }
	    
	    User user = userStore.getUser(mediaOwner);
	    if(inputStream!=null) {
	      BufferedImage content = ImageIO.read(inputStream);
	      String contentType = filePart.getContentType();
	      UUID conversationId = null;
	      /* If this is uploaded from a conversation, get the conversation id. */
	      if(uri.equals("/chat/FileUploadServlet")) {
	      	Conversation conversation = conversationStore.getConversationWithTitle(chatName);
	      	conversationId = conversation.getId();
	      }
	      Media media = new Media(UUID.randomUUID(), user.getId(), 
	        mediaTitle, Instant.now(), content, contentType, conversationId);
	      if(mediaStore.getProfilePicture(user.getName())!=null) {
	        mediaStore.getProfilePicture(user.getName()).setIsProfilePicture(false);
	      }
	      if(uri.equals("/profile/FileUploadServlet")) {
	      	mediaStore.setProfilePicture(user.getName(), media);
	      }
	      if(uri.equals("/chat/FileUploadServlet")) {
	        eventStore.addEvent(new Event(media));
	       }
	      mediaStore.addMedia(media);
	    }
        
        /* Redirect to profile page if that's where you posted from. */
        if(uri.equals("/profile/FileUploadServlet")) {
          response.sendRedirect("/profile/"+user.getName()); 
        }
        /* Redirect to the chat you posted from. */
        if(uri.equals("/chat/FileUploadServlet")) {
          response.sendRedirect("/chat/"+chatName); 
        }
	}

	/**
	 * Sets the MediaStore used by this servlet. This function provides a common setup method for
	 * use by the test framework or the servlet's init() function.
	 */
	  void setMediaStore(MediaStore mediaStore) {
	    this.mediaStore = mediaStore;
	  } 

	/**
	 * Sets the UserStore used by this servlet. This function provides a common setup method for
	 * use by the test framework or the servlet's init() function.
	 */
	  void setUserStore(UserStore userStore) {
	    this.userStore = userStore;
	  }

	/**
	 * Sets the EventStore used by this servlet. This function provides a common setup method for
	 * use by the test framework or the servlet's init() function.
	 */
	  void setEventStore(EventStore eventStore) {
	    this.eventStore = eventStore;
	  }

	/**
	 * Sets the ConversationStore used by this servlet. This function provides a common setup method for
	 * use by the test framework or the servlet's init() function.
	 */
	  void setConversationStore(ConversationStore conversationStore) {
	    this.conversationStore = conversationStore;
	  } 
}