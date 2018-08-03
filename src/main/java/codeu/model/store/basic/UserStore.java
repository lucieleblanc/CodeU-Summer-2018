// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.model.store.basic;

import codeu.model.data.User;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import static jdk.nashorn.internal.runtime.Debug.id;

/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class UserStore {

  /** Singleton instance of UserStore. */
  private static UserStore instance;

  /**
   * Returns the singleton instance of UserStore that should be shared between all servlet classes.
   * Do not call this function from a test; use getTestInstance() instead.
   */
  public static UserStore getInstance() {
    if (instance == null) {
      instance = new UserStore(PersistentStorageAgent.getInstance());
    }
    return instance;
  }

  /**
   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
   *place to store- space in database to store it - code change + database change
   focus on code change
   update existing user in userStore (add new field)
   * @param persistentStorageAgent a mock used for testing
   */
  public static UserStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
    return new UserStore(persistentStorageAgent);
  }

  /**
   * The PersistentStorageAgent responsible for loading Users from and saving Users to Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  /** The in-memory list of Users. */
  private List<User> users;
  private HashMap<UUID, User> userKeyMap;
  private HashMap<String, User> userNameMap;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private UserStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    users = new ArrayList<>();
    userKeyMap = new HashMap<>();
    userNameMap = new HashMap<>();
  }

  /** Access the current set of users known to the application. */
  public List<User> getAllUsers() {
    return users;
  }

  /**
   * Access the User object with the given name.
   *
   * @return null if username does not match any existing User.
   */
  public User getUser(String username) {
    if(userNameMap.containsKey(username)) {
      return userNameMap.get(username);
    }
    else {
      return null;
    }
  }

  /**
   * Access the User object with the given UUID.
   *
   * @return null if the UUID does not match any existing User.
   */
  public User getUser(UUID id) {
    if(userKeyMap.containsKey(id)) {
      return userKeyMap.get(id);
    }
    else {
      return null;
    }
  }

  /**
   * Add a new user to the current set of users known to the application. This should only be called
   * to add a new user, not to update an existing user.
   */
  public void addUser(User user) {
    users.add(user);
    userKeyMap.put(user.getId(), user);
    userNameMap.put(user.getName(), user);
    persistentStorageAgent.writeThrough(user);
  }

  /**
   * Update an existing User.
   */
  public void updateUser(User user) {
    persistentStorageAgent.writeThrough(user);
  }

  /**
   * Updates the bio of user with ID {@code id}.
   */
  public void updateBio(UUID id, String bio) {
    User userWithBio = getUser(id);
    userWithBio.setBio(bio);
    persistentStorageAgent.writeThrough(userWithBio);
  }

  /** Return true if the given username is known to the application. */
  public boolean isUserRegistered(String username) {
    if(userNameMap.containsKey(username)) {
      return true;
    }
    else {
      return false;
    }
  }

  /**
   * Sets the List of Users stored by this UserStore. This should only be called once, when the data
   * is loaded from Datastore.
   */
  public void setUsers(List<User> users) {
    this.users = users;
    for(User user: users) {
      userKeyMap.put(user.getId(), user);
      userNameMap.put(user.getName(), user);
    }
  }

  /**
   * @return the current number of users.
   */
  public int getNumUsers() {
    return this.users.size();
  }


  public List<String> getTopUsernames() {
    User oldest = users.get(0);
    User newest = users.get(0);
    for (User user : users) {
      if (user.getCreationTime().compareTo(oldest.getCreationTime()) < 0) {
        oldest = user;
      } else if (user.getCreationTime().compareTo(newest.getCreationTime()) > 0) {
        newest = user;
      }
    }
    List<String> topUsers = new ArrayList<>();
    topUsers.add(oldest.getName());
    topUsers.add(newest.getName());
    return topUsers;
  }

}


}