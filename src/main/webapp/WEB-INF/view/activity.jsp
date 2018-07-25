
<%@ page import="java.util.List" %>
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
      function feedChat() {
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
    <% String user = (String)request.getSession().getAttribute("user");%>
    <a href="/profile/<%=user%>" >My Profile</a>  
    <%} else{%>
      <a href="/login"> My Profile</a>
    <% } %>
  </nav>

<div id="feed">
  <ul>
    <% for(Event event: events){ %>
      <% if(event.getEventType().toString() == "USER") { %>
        <li><%=event.toString()%> 
            <a href="/profileView/<%=event.getNameOfUser()%>"><%=event.getNameOfUser()%></a>
            joined
        </li>
      <% } else { %>
        <li><%= event.toString() %>
        <% if(event.getEventType().toString() == "CONVERSATION") { %>
        <a href="/chat/<%=event.getTitleOfConversation()%>">
            <%= event.getTitleOfConversation()%></li>
        </a>
        <% } %> 
      <% } %>
    <% } %>
  </ul>
</div>

  <div id="container">

    <% if(request.getAttribute("error") != null){ %>
        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>
    
    <h1>Look at all this activity!</h1>

  </div>
</body>
</html>

