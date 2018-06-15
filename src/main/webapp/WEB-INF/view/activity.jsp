
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%@ page import="codeu.model.store.basic.MessageStore" %>
<%
List<Conversation> conversations = (List<Conversation>) request.getAttribute("conversations");
%>
<%
MessageStore messageStore = (MessageStore) request.getAttribute("messageStore");
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
  </nav>

<div id="feed">
  <ul>
    <%
      /* Get the titles of the conversations. */
       for (Conversation conversation : conversations) {
         String title = conversation.getTitle();
    %>
       <li><strong><%= title %></strong></li>
       <%
            /* Get the messages of the conversations. */
         List<Message> messages = messageStore.getMessagesInConversation(conversation.getId());
         for (Message message : messages) {
       %>
           
           <li><%= message.getContent() %></li>  
    <%       
         }
      }
    %>
  </ul>
</div>

  <div id="container">

    <% if(request.getAttribute("error") != null){ %>
        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>
    
    <h1>This is the activity feed page, coming soon!</h1>

  </div>
</body>
</html>
