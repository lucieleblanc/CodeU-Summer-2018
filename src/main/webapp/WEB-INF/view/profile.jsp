
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.data.Bio" %>
<%@ page import="codeu.model.data.Media" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%@ page import="codeu.model.data.Bio"%>
<%@ page import="codeu.model.data.Event"%>
<!DOCTYPE html>
<html>
<head>
  <title>Profile Page</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

<style>
  #feed {
    background-color: white;
    height: 500px;
    overflow-y: scroll
  }
</style>


<script>
    // scroll the feed div to the bottom
      function scrollFeed() {
        var feedDiv = document.getElementById('feed');
        feedDiv.scrollTop = feedDiv.scrollHeight;
      };
    </script>
  </head>
<body onload="feedChat()">

  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else{ %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
    <a href="/activity.jsp">Activity Feed</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
    <!--<% String user = (String)request.getSession().getAttribute("user");%>-->
    <a href="/profile/<%=user%>" >My Profile</a>  
    <%} else{%>
      <a href="/login"> My Profile</a>
    <% } %>  
      </nav>

<center>
 <h2>
<% if(request.getSession().getAttribute("user") != null){ %>
      <h1> <%= request.getSession().getAttribute("user")%> 's Profile Page</h1></center>
      <% } else{ %>
      <h2>To see your profile,<a href="/login"> login.</a></h2>
     <% } %>
  </h2>
<center>

<!--src fires a get request calling doGet() of the FileUploadServlet-->
<img src="FileUploadServlet" alt="Upload a profile image" width="500" height="333">


<body>
  <center>
      <form id = "form" method="POST" action="FileUploadServlet" enctype="multipart/form-data">
        <table border="0">
          <tr>
            <td>Title: </td>
            <td><input type="text" name="Title" size="50"/></td>
          </tr>
          <tr>
            <td>Portrait Photo: </td>
            <td><input type="file" name="photo" size="50"/></td>  
          </tr>
          <tr>
            <td colspan="2">
              <input type="submit" value="Save">
            </td>
          </tr>
        </table>
      </form>
  </center>
</body>


<form action="/profile/" method="POST">
      About <%= request.getSession().getAttribute("user")%>:
      <%
      // NOTE(fang): Not request.getSession().getAttribute(). 
      String bio = (String)request.getAttribute("bio");
      if (bio == null) {
        bio = "";
      }
      %>
      <textarea type="text" name="bio" value="<%= bio%>" placeholder="Tell us about yourself..." height="200" width="400"></textarea>
      <button type="submit">Submit</button>
</form></textarea>
<div>

</div>
<%List<Conversation> userConvos = (List<Conversation>) request.getAttribute("conversations");%>

<!--<%List<Conversation> conversations =
 (List<Conversation>) request.getAttribute("conversations");
%>-->
<div id="feed"> 
 <h2><%= request.getSession().getAttribute("user")%> 's Conversations:</h2>

    <%
    if(userConvos != null && !userConvos.isEmpty()) {
      for(Conversation userConvo : userConvos){
    %>
      <li><p><a href="/chat/<%= userConvo.getTitle() %>"> <%= userConvo.getTitle() %></a></p></li>
       
    <%
      }
    }
    %>

</center>


  <!--<div id="feed">
<center>
   
</div>
  </div></center>

-->
</body>
</html>
           

