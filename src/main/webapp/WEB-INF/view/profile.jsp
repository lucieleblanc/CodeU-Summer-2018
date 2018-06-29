
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.data.Message" %>
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
______________________________________________________________________________________________________________________________________________


<h2>About <%= request.getSession().getAttribute("user")%>: </h2>
<%= (String)request.getAttribute("bio")%> 
<h2> Edit your Bio:</h2>
  <form action="/profile/" method="POST">
    <%
      // NOTE(fang): Not request.getSession().getAttribute(). 
      String bio = (String)request.getAttribute("bio");
      if (bio == null) {
        bio = "";
      }
      %>
      <input type="text" name="bio" value="<%= bio%>" >
      <button type="submit">Submit</button>
</form>
<div>

</div>
<%List<Conversation> conversations =
 (List<Conversation>) request.getAttribute("conversations");
%>
<div id="feed"> 
 <h2><%= request.getSession().getAttribute("user")%> 's Conversations:</h2>

    <%
    if(conversations != null && !conversations.isEmpty()) {
      for(Conversation conversation : conversations){
    %>
      <li><h3><a href="/chat/<%= conversation.getTitle() %>">
        <%= conversation.getTitle() %></a></h3></li>
    <%
      }
    }
    %>

</center>
_____________________________________________________________________________________________________________________________________________




  <!--<div id="feed">
<center>
   
</div>
  </div></center>

-->
</body>
</html>
