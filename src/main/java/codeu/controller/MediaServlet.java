
package codeu.controller;

import codeu.model.data.Media;
import codeu.model.store.basic.MediaStore;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/images/*")
public class MediaServlet extends HttpServlet {
    /* Store class that gives access to Media. */
    private MediaStore mediaStore;

    /**
     * Set up state for handling activity-related requests. This method is only called when
     * running in a server, not when running in a test.
     */
    @Override
    public void init() throws ServletException {
      super.init();
      setMediaStore(MediaStore.getInstance());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
     
      UUID randomUUID = UUID.randomUUID();
      UUID parentUUID = UUID.randomUUID();

      // String stringBytes = "";
      // byte[] someBytes = stringBytes.getBytes(StandardCharsets.UTF_8);
      // Media picture = new Media(randomUUID, parentUUID,"Media Title", Instant.now(), someBytes);

      String imageName = request.getPathInfo().substring(1);

      byte[] content = mediaStore.getMediaWithId(randomUUID).getContent();
      response.setContentType(getServletContext().getMimeType(imageName));
  	  response.setContentLength(content.length);
  	  response.getOutputStream().write(content);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

  }



/**
 * Sets the MediaStore used by this servlet. This function provides a common setup method for
 * use by the test framework or the servlet's init() function.
 */
  void setMediaStore(MediaStore mediaStore) {
    this.mediaStore = mediaStore;
  } 
}