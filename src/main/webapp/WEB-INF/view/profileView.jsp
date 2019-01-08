<%@ page import="java.io.*"%>
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

<%String username = (String) request.getAttribute("username");%>

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
<body onload="scrollFeed()">

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

<center><h1><%=username%>'s Profile Page</h1></center>
<center>

<%
  session.setAttribute("username", username);
%>
<!--src fires a get request calling doGet() of the FileUploadServlet-->
<img src="FileUploadServlet" alt="Upload a profile image" width="500" height="333">

</div>
<%List<Conversation> userConvos = (List<Conversation>) request.getAttribute("conversations");%>

<div id="feed"> 
 <h2><%=username%>'s Conversations:</h2>

    <%
    if(userConvos != null && !userConvos.isEmpty()) {
      for(Conversation userConvo : userConvos){
    %>
      <p><li><a href="/chat/<%= userConvo.getTitle() %>"> <%= userConvo.getTitle() %></a></li></p>
       
    <%
      }
    }
    %>

</center>

</body>
</html>