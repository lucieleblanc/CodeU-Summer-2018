<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.data.Event" %>
<%@ page import="codeu.model.store.basic.UserStore" %>

<% Conversation conversation = (Conversation) request.getAttribute("conversation"); %>

<!DOCTYPE html>
<html>
<head>
  <title><%= conversation.getTitle() %></title>
  <link rel="stylesheet" href="/css/main.css" type="text/css">

  <style>
    #chat {
      background-color: white;
      height: 500px;
      overflow-y: scroll
    }
  </style>

  <script>
    // scroll the chat div to the bottom
    function scrollChat() {
      var chatDiv = document.getElementById('chat');
      chatDiv.scrollTop = chatDiv.scrollHeight;
    };
  </script>
</head>
<body onload="scrollChat()">

  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
      <% if (request.getSession().getAttribute("user") != null) { %>
    <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else { %>
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

  <% List<Event> events = (List<Event>) request.getAttribute("events"); %>
  <div id="container">

    <h1><%= conversation.getTitle() %>
      <a href="" style="float: right">&#8635;</a></h1>

    <hr/>

    <div id="chat">
    <ul>
      <%
        for (Event event : events) {
          if(event.getEventType().toString() == "MESSAGE") {
            String author = UserStore.getInstance()
              .getUser(event.getAuthorIdForMessage()).getName();
      %>
            <li><strong><%= author %>:</strong> <%= event.getMessageContent() %></li>
       <% } else if(event.getEventType().toString() == "MEDIA") { %>
                <li><img src="FileUploadServlet?mediaId=<%=event.getMediaId()%>" 
                  alt="Upload a profile image" width="450" height="300"></li>
       <% } %>
      <% } %>
    </ul>
    </div>

    <hr/>

  <%
    String chatName = (String) request.getAttribute("chatName");
    session.setAttribute("chatName", chatName);
  %>

  <% if (request.getSession().getAttribute("user") != null) { %>
    <form action="/chat/<%= conversation.getTitle() %>" method="POST">
      <input type="text" name="message">
        <br/>
         <button type="submit">Send</button>
    </form>
    <form id = "form" method="POST" action="FileUploadServlet" enctype="multipart/form-data">
      <table border="0">
        <tr>
          <td>Upload Image: </td>
          <td><input type="file" name="photo" size="50"/></td>  
        </tr>
      <tr>
        <td colspan="2">
          <input type="submit" value="Send">
        </td>
      </tr>
      </table>
    </form>
    <% } else { %>
      <p><a href="/login">Login</a> to send a message.</p>
    <% } %>

    <hr/>

  </div>

</body>
</html>