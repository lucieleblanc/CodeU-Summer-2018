
package codeu.model.store.basic;

import codeu.model.data.Conversation;
import codeu.model.data.User;
import codeu.model.data.Message;
import codeu.model.data.Event;

import codeu.model.store.persistence.PersistentStorageAgent;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance. Can hold conversations, messages, and users all at once.
 */
public class EventStore{
  /** Singleton instance of EventStore. */
  private static EventStore instance;

  /**
   * Returns the singleton instance of EventStore that should be shared between all servlet
   * classes. Do not call this function from a test; use getTestInstance() instead.
   */
  public static EventStore getInstance() {
    if (instance == null) {
      instance = new EventStore(PersistentStorageAgent.getInstance());
    }
    return instance;
  }

  /**
   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
   *
   * @param persistentStorageAgent a mock used for testing
   */
  public static EventStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
    return new EventStore(persistentStorageAgent);
  }

  /**
   * The PersistentStorageAgent responsible for loading Conversations, Messages, and 
   * Users from and saving Conversations, Messages, and Users to Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  /** The in-memory list of Conversations. */
  private List<Conversation> conversations;

  /** The in-memory list of Messages. */
  private List<Message> messages;

  /** The in-memory list of Users. */
  private List<User> users;

  /** A list of events (users, messages, and convesations). */
  private List<Event> events;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private EventStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    conversations = new ArrayList<>();
    messages = new ArrayList<>();
    users = new ArrayList<>();
    events = new ArrayList<>();
  }

  /** Access the current set of events known to the application in chronological order. */
  public List<Event> getAllEventsSorted() {
  	/* Sort events based on the time that they were created. */
  	events.sort(Comparator.comparingLong(event -> event.getCreationTime()));
    return events;
  }

  /** Access the current set of events known to the application. */
  public List<Event> getAllEvents() {
    /* Sort events based on the time that they were created. */
    return events;
  }
  
  /** Set converations, messages, and users. */
  public void setEvents(List<User> users, List <Conversation> conversations, List <Message> messages){
    this.conversations = conversations;
    this.messages = messages;
    this.users = users;
    convertToEvents();
  }

  /** Set converations, messages, and users. */
  public void setEvents(List<Event> events){
    this.events = events;
  }
 
  /** Convert converations, messages, and users to event types. */
  private void convertToEvents(){
  	for(Conversation conversation: conversations){
    	events.add(new Event(conversation));
    }
    for(Message message: messages){
    	events.add(new Event(message));
    }
    for(User user: users){
    	events.add(new Event(user));
    }
  }
}