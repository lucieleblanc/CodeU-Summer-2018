package codeu.controller;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

  /*
   * TODO(VillanuevaK): Add a test for doPost if it is implemented.
   */
public class ActivityServletTest{
  private ActivityServlet activityServlet;
  private HttpServletRequest mockRequest;
  private HttpSession mockSession;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;

  @Before
  public void setup() {
    activityServlet = new ActivityServlet();

    mockRequest = Mockito
      .mock(HttpServletRequest.class);
    mockResponse = Mockito
      .mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito
      .mock(RequestDispatcher.class);
    Mockito.when(mockRequest
    	.getRequestDispatcher("/WEB-INF/view/activity.jsp"))
        .thenReturn(mockRequestDispatcher);

  }

  /**
   * Test doGet() in the ActivityServlet to make sure it correctly 
   * passes the list of event to the activity.jsp file.
   */
  @Test
  public void testDoGet() throws IOException, ServletException {
    /* Create the fake list of events */
    Conversation fakeConversation = new Conversation(
      UUID.randomUUID(), UUID.randomUUID(), 
      "test_conversation", Instant.now());

    Message fakeMessage = new Message(
      UUID.randomUUID(), UUID.randomUUID(), 
      UUID.randomUUID(), "test_message", Instant.now());

    User fakeUser = new User(
      UUID.randomUUID(), "test_user", 
      "test_user_PHash", Instant.now(), 
      "test_user_bio");

    /* 
     * Use the doGet function in the activity servlet with the 
     * parameters mockRequest and mockResponse (both initialized 
     * in setup()). 
     */
    activityServlet.doGet(mockRequest, mockResponse);

    /* 
     * NOTE: Essentially when we use verify we are saying
     * "make sure that this function actually did this"
     */


    /*
     * Verify that we our we forwarded the request to 
     * the activity.jsp file
     */
    Mockito.verify(mockRequestDispatcher)
      .forward(mockRequest, mockResponse);
  }

}