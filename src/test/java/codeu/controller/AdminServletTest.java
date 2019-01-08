
package codeu.controller;

import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AdminServletTest {

    private AdminServlet adminServlet;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private RequestDispatcher mockRequestDispatcher;

    @Before
    public void setup() {
        adminServlet = new AdminServlet();
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);
        mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
        Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/login.jsp"))
                .thenReturn(mockRequestDispatcher);
    }

    /*

    TODO: write POST test for allowed admins

    -at-Test
    public void testDoGet_NotAdmin() throws IOException, ServletException {

    }


    -at-Test
    public void testDoGet_IsAdmin() throws IOException, ServletException {

    }

    */

}
