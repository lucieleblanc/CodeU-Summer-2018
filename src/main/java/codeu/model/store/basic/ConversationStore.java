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

import codeu.model.data.Conversation;
import codeu.model.data.User;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class ConversationStore {

  /** Singleton instance of ConversationStore. */
  private static ConversationStore instance;

  /**
   * Returns the singleton instance of ConversationStore that should be shared between all servlet
   * classes. Do not call this function from a test; use getTestInstance() instead.
   */
  public static ConversationStore getInstance() {
    if (instance == null) {
      instance = new ConversationStore(PersistentStorageAgent.getInstance());
    }
    return instance;
  }

  /**
   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
   *
   * @param persistentStorageAgent a mock used for testing
   */
  public static ConversationStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
    return new ConversationStore(persistentStorageAgent);
  }

  /**
   * The PersistentStorageAgent responsible for loading Conversations from and saving Conversations
   * to Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  /** The in-memory list of Conversations. */
  private List<Conversation> conversations;
  private HashMap<UUID, Conversation> conversationIdMap;
  private HashMap<String, Conversation> conversationTitleMap;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private ConversationStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    conversations = new ArrayList<>();
    conversationIdMap = new HashMap<>();
    conversationTitleMap = new HashMap<>();
  }

  /** Access the current set of conversations known to the application. */
  public List<Conversation> getAllConversations() {
    return conversations;
  }

//public List<ConversationSpec> getConversationWithId(){

//}
  /** Add a new conversation to the current set of conversations known to the application. */
  public void addConversation(Conversation conversation) {
    conversations.add(conversation);
    conversationIdMap.put(
      conversation.getId(), conversation);
    conversationTitleMap.put(
      conversation.getTitle(), conversation);
    persistentStorageAgent.writeThrough(conversation);
  }

  /** Check whether a Conversation title is already known to the application. */
  public boolean isTitleTaken(String title) {
    if (conversationTitleMap.containsKey(title)) {
      return true;
    }
    else {
      return false;
    }
  }

  /** Find and return the Conversation with the given title. */
  public Conversation getConversationWithTitle(String title) {
    if (conversationTitleMap.containsKey(title)) {
      return conversationTitleMap.get(title);
    }
    else {
      System.err.println("The conversation you were looking for could not be found." +
                         " No title's matched. Please check if the conversation actually" +
                         " exists on the admin page if on local server or the app engine. ");
      return null;
    }
  }
public List<Conversation> getConversationWithOwner(UUID userId){
  List<Conversation> userConvos = new ArrayList();//UUID curUserID = request.getSession().getAttribute("user"); (how does it know to get this from the user class vs the conversation class)
  for(Conversation conversation: conversations)
  {
    if(conversation.getOwnerId() == userId){
      userConvos.add(conversation);
    }
  }
  return userConvos;
}
  /** Find and return the Conversation with the given Id. */
  public Conversation getConversationWithId(UUID id){
    if (conversationIdMap.containsKey(id)) {
      return conversationIdMap.get(id);
    }
    else {
      System.err.println("The conversation you were looking for could not be found." +
                         " No Id's matched. Please check if the conversation actually" +
                         " exists on the admin page if on local server or the app engine. ");
      return null;
    }
  }

  /** Sets the List of Conversations stored by this ConversationStore. */
  public void setConversations(List<Conversation> conversations) {
    this.conversations = conversations;
    for(Conversation conversation: conversations) {
      conversationIdMap.put(
        conversation.getId(), conversation);
      conversationTitleMap.put(
        conversation.getTitle(), conversation);
    }
  }

  /**
   * Returns the current number of conversations.
   */
  public int getNumConversations() {
    return this.conversations.size();
  }
}

