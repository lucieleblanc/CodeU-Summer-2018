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
<!DOCTYPE html>
<html>
<head>
  <title>CodeU Chat App</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

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

  <div id="container">
      <h1>About the CodeU Chat App</h1>
      <p>
        Welcome to the app! Please explore this website.
      </p>

      <p>
        You can create an account, put up a profile page, create conversations, upload images, and see what all the other users are doing on the activity feed!
      </p>

      <p>
        All the data was manually handled with a minimal use of API's as to get a sense of how data is transferred from the servlet layer down through to the Google App Engine.
      </p>
  </div>
</body>
</html>
