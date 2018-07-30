
package codeu.controller;

import codeu.model.data.Media;
import codeu.model.data.User;
import codeu.model.store.basic.MediaStore;
import codeu.model.store.basic.UserStore;
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
 * Mapped to the profile and profile view jsp files. The profile jsp file
 * allows you to access and edit your own profile. the profile view jsp file
 * allows others to view, but not edit another user's profile.
 */
@WebServlet({"profile/FileUploadServlet", "profileView/FileUploadServlet"})
/* Max file upload size is 16 MB. */
@MultipartConfig(maxFileSize = 16177215)
/* Servlet Class responsible for file uploads. */ 
public class FileUploadServlet extends HttpServlet {

	/* Store class that gives access to Media. */
    private MediaStore mediaStore;
    /* Store class that gives access to Users. */
    private UserStore userStore;


    /**
     * Set up state for handling activity-related requests. This method is only called when
     * running in a server, not when running in a test.
     */
    @Override
    public void init() throws ServletException {
      super.init();
      setMediaStore(MediaStore.getInstance());
      setUserStore(UserStore.getInstance());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

      String uri = request.getRequestURI();
      String mediaOwner = null;
      if(uri.equals("/profile/FileUploadServlet")) {
        mediaOwner = (String)request.getSession().getAttribute("user");
      }
      else if(uri.equals("/profileView/FileUploadServlet")) {
      	mediaOwner = (String)request.getSession().getAttribute("username");
      }

      Media picture = mediaStore.getProfilePicture(mediaOwner);

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

	    /* Title given by user in profile.jsp. */
        String mediaTitle = (String)request.getParameter("Title"); 
        /* Uploader. */
        String mediaOwner = (String)request.getSession().getAttribute("user");
        /* Input stream of the upload file. */
        InputStream inputStream = null;

		/* Get the file chosen by the user. */
		Part filePart = request.getPart("photo");
		
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
	      Media media = new Media(UUID.randomUUID(), user.getId(), 
	        mediaTitle, Instant.now(), content, contentType);
	      if(mediaStore.getProfilePicture(user.getName())!=null) {
	        mediaStore.getProfilePicture(user.getName()).setIsProfilePicture(false);
	      }
	      mediaStore.setProfilePicture(user.getName(), media);
	      mediaStore.addMedia(media);
	    }
        
        response.sendRedirect("/profile/"+user.getName()); 
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
}