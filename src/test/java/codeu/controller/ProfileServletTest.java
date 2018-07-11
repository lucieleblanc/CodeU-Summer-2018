
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

public class ProfileServletTest {

  private ProfileServlet profileServlet;
  private HttpServletRequest mockRequest;
  private HttpSession mockSession;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private ConversationStore mockConversationStore;
  private UserStore mockUserStore;
  private ConversationStore conversationStore;
  private UserStore userStore;

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
  }

  @Test 
  public void testGetConversationWithOwner() throws IOException, ServletException {
   List<Conversation> fakeConvoList = new ArrayList<>();
    fakeConvoList.add(
      new Conversation(UUID.randomUUID(), UUID.randomUUID(), "test_convo", Instant.now()));

    String username = (String) mockRequest.getSession().getAttribute("user");
    User user = mockUserStore.getUser(username);
    Mockito.when(mockConversationStore.getConversationWithOwner(user.getId()))
    	.thenReturn(fakeConvoList);

    profileServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("conversations", fakeConvoList);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }
}