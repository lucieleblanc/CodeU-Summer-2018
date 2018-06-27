
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
    <a href="/profile.jsp">Profile</a>
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
<%List<Conversation> conversations =
 (List<Conversation>) request.getAttribute("conversations");
%>
<%= (String)request.getAttribute("bio")%> 
<h2>About <%= request.getSession().getAttribute("user")%>: </h2>
<h2> Edit your Bio:</h2>
  <form action="/profile.jsp" method="POST">
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
<!--<% User user = User.from(user.getId)%>
<% Conversation id = Conversation.from(conversation.getOwnerId)%>-->
<!--<%if(conversations != null && !conversations.isEmpty()){ %>-->

  <% for(Conversation conversation : conversations){ %>
    <!-- <%if(conversation.getOwnerId() == request.getAttribute("id")){ %>-->
          <li><p><a href="/chat/<%= conversation.getTitle()%>">
          <%= conversation.getTitle() %> </li></a><p>;
      <!--<% } %>-->
    <% } %>
  <% } %>

</center>
_____________________________________________________________________________________________________________________________________________


  <h2><%= request.getSession().getAttribute("user")%> 's Conversations:</h2>

  <!--<div id="feed">
<center>
   
</div>
  </div></center>

-->
</body>
</html>
