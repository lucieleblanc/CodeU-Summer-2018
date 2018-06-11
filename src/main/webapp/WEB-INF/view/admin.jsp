
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.store.basic.UserStore" %>

<!DOCTYPE html>
<html>
<head>
  <title>Admin</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else{ %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
  </nav>

  <div id="container">
    <h1>Admin</h1>

    <p>This is the admin page.</p>

    <p>Here are some app statistics:</p>

    <ul>
        <li>Users: <%= request.getAttribute("numUsers") %></li>
        <li>Conversations: <%= request.getAttribute("numConversations") %></li>
        <li>Messages: <%= request.getAttribute("numMessages") %></li>
        <li>Etc</li>
    </ul>

  </div>
</body>
</html>