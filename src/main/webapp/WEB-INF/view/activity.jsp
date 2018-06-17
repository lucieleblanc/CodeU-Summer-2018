
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%@ page import="codeu.model.store.basic.MessageStore" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.format.FormatStyle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.time.ZoneId" %>
<%
List<Conversation> conversations = (List<Conversation>) request.getAttribute("conversations");

MessageStore messageStore = (MessageStore) request.getAttribute("messageStore");

UserStore userStore = (UserStore) request.getAttribute("userStore");

List<User> users = (List<User>) request.getAttribute("users");
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
       /* Used to format the time since the getCreationTime() method returns an instance, not a string. */
      DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
        .withLocale( Locale.US )
        .withZone( ZoneId.systemDefault() );
       
       /* Get the titles of the conversations. */
      for (Conversation conversation : conversations) {
        String formatttedConversationCreationTime = formatter.format( conversation.getCreationTime() );
        String title = conversation.getTitle();
    %>
      <li><%= formatttedConversationCreationTime + ": " + userStore.getUser(conversation.getOwnerId()).getName() 
        + " created a new conversation: " + conversation.getTitle() %></li>  
      <%
        /* Get the messages of the conversations. */
        List<Message> messages = messageStore.getMessagesInConversation(conversation.getId());
        for (Message message : messages) {
      %>
          <%
            String formatttedMessageCreationTime = formatter.format( message.getCreationTime() );
          %>

          <li><%= formatttedMessageCreationTime + " PST: " + userStore.getUser(message.getAuthorId()).getName() 
            + " sent a message in " + title + ": " + "\"" + message.getContent() + "\"" %></li>  
    <%       
        }
      }
    %>
    <%  
      for(User user: users){

      String formatttedUserCreationTime = formatter.format( user.getCreationTime() );
    %>
        <li><%= formatttedUserCreationTime + " PST: " +user.getName() +" joined!" %></li>
    <%
      }     
    %>
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

