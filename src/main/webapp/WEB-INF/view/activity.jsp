
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.data.Media" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Event" %>

<%
List<Event> events = (List<Event>) request.getAttribute("events");
%>


<!DOCTYPE html>
<html>
<head>
  <title>Activity Feed</title>
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
    <img src="menu-button.png" id="navItemToggle" onClick="toggleMenu()" />
    <a id="navTitle" href="/">CodeU Chat App</a>
    
  <div class="navItems hidden">
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else{ %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
    <a href="/activity.jsp">Activity Feed</a>
     <% if(request.getSession().getAttribute("user") != null){ %>
    <% String user = (String)request.getSession().getAttribute("user");%>
    <a href="/profile/<%=user%>" >My Profile</a>  
    <%} else{%>
      <a href="/login"> My Profile</a>
    <% } %>
  </div>

  </nav>

<div id="feed">
  <ul>
    <%
        for (Event event : events) {
          if(event instanceof Message) {
              Message m = (Message) event;
      %>
              <li><%=m.toString()%></li>
       <% } else if(event instanceof Media) { 
              Media m = (Media) event;
        %>
              <li><%=m.toString()%></li>
        <% } else if(event instanceof User) { 
              User u = (User) event;
        %>
              <li><%=u.toString()%> 
              <a href="/profileView/<%=u.getName()%>"><%=u.getName()%></a>
              joined
              </li>
        <% } else if(event instanceof Conversation) { 
              Conversation c = (Conversation) event;
        %>
              <li><%=c.toString()%></li>
              <a href="/chat/<%=c.getTitle()%>">
              <%= c.getTitle()%></li>
              </a>
       <% } %>
      <% } %>
  </ul>
</div>

  <div id="container">

    <% if(request.getAttribute("error") != null){ %>
        <h2 class="error"><%= request.getAttribute("error") %></h2>
    <% } %>

    <h1>Look at all this activity!</h1>

  </div>
</body>
</html>
