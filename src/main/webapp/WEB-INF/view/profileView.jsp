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

</div>
<%List<Conversation> userConvos = (List<Conversation>) request.getAttribute("conversations");%>

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

</body>
</html>