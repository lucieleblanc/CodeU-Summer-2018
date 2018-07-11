
package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import java.util.List;
import java.util.ArrayList;

public class ProfileServletTest {

  private ProfileServlet profileServlet;
  private HttpServletRequest mockRequest;
  private HttpSession mockSession;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private ConversationStore mockConversationStore;
  private UserStore mockUserStore;
  private User fakeUser;

  @Before
  public void setup() {
    profileServlet = new ProfileServlet();

    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/profile.jsp"))
        .thenReturn(mockRequestDispatcher);


   	mockConversationStore = Mockito.mock(ConversationStore.class);
    profileServlet.setConversationStore(mockConversationStore);

    mockUserStore = Mockito.mock(UserStore.class);
    profileServlet.setUserStore(mockUserStore);

    // Mockito.when() below is because in ProfileServlet.doGet(), it will call
    // String username = (String) request.getSession().getAttribute("user");
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
    // fakeUser and Mockito.when() below is because in ProfileServlet.doGet(),
    // it would call User user = userStore.getUser(username);
    fakeUser =
        new User(
            UUID.randomUUID(),
            "test_username",
            "$2a$10$eDhncK/4cNH2KE.Y51AWpeL8/5znNBQLuAFlyJpSYNODR/SJQ/Fg6",
            Instant.now(),
            "test_bio");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(fakeUser);
  }

  @Test
  public void testGetConversationOwner () throws IOException, ServletException {
    // Linese below because in ProfileServlet.doGet(), it will do
    // List<Conversation> userConvos = conversationStore.getConversationWithOwner(user.getId());
    // request.setAttribute("conversations", userConvos);
    List<Conversation> fakeConvoHashmap = new ArrayList();
    fakeConvoHashmap.add(
      new Conversation(UUID.randomUUID(), UUID.randomUUID(), "test_convo", Instant.now()));
    Mockito.when(mockConversationStore.getConversationWithOwner(fakeUser.getId())).thenReturn(fakeConvoHashmap);

    profileServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("conversations", fakeConvoHashmap);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }
}
