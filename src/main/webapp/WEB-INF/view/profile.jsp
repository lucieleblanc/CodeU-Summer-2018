
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%
List<Conversation> conversations = (List<Conversation>) request.getAttribute("conversations");
%>


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
    <a href="/profile.jsp">Profile</a>
  </nav>
  <h2>
<% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello, <%= request.getSession().getAttribute("user") %>! Welcome to your profile.</a>
    <% } else{ %>
      <h2>To see your profile,<a href="/login"> login.</a></h2>
    <% } %></h2>
    <h1>This is the profile page, coming soon!</h1>


  </div>
</body>
</html>