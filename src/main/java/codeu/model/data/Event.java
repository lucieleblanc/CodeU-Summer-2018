
package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.time.ZoneId; 

import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;

/** 
 *  This class abstracts any object so it can be a User, Conversation, or Message. 
 *  Made so a list can  easily hold and sort all types of objects at once.
 */
public class Event{

	/* Denotes the type of event this is. 'c' for conversation, 'm' for message, and 'u' for user. */
	private final Character eventType;

	/* All the fields needed from the conversation class. */
	private final String titleOfConversation;
	private final UUID authorIdForConversation;
	private final Instant conversationCreationTime;

	/* All the fields needed from the message class. */
	private final Instant messageCreationTime;
    private final UUID authorIdForMessage;
    private final String conversationTitleOfMessage;
    private final String messageContent;

    /* All the fields needed from the user class. */
    private final Instant userCreationTime;
    private final String nameOfUser;

    /* Needed to get the user given the ID from a conversation object. */
    private final UserStore userStore;
    /* Need to get the conversation given the ID from a message object. */
    private final ConversationStore conversationStore;
  
  /**
   * Constructs a new event using a Conversation object.
   *
   * @param conversation an object of type Conversation
   */
	public Event(Conversation conversation) {
      userStore = UserStore.getInstance();
      conversationStore = ConversationStore.getInstance();

      titleOfConversation = conversation.getTitle();
      authorIdForConversation = conversation.getOwnerId();
      conversationCreationTime = conversation.getCreationTime();
      eventType = 'c';

      messageCreationTime = null;
      authorIdForMessage = null;
      conversationTitleOfMessage = null;
      messageContent = null;

      userCreationTime = null;
      nameOfUser = null;

	}

  /**
   * Constructs a new event using a Message object.
   *
   * @param message an object of type Message
   */
	public Event(Message message) {
      userStore = UserStore.getInstance();
      conversationStore = ConversationStore.getInstance();

      titleOfConversation = null;
      authorIdForConversation = null;
      conversationCreationTime = null;

      messageCreationTime = message.getCreationTime();
      authorIdForMessage = message.getAuthorId();
      conversationTitleOfMessage = conversationStore.getConversationWithId(message.getConversationId()).getTitle(); //null error is here
      messageContent = message.getContent();
      eventType = 'm';

      userCreationTime = null;
      nameOfUser = null;
	}

  /**
   * Constructs an new event using a User object.
   *
   * @param user an object of type User
   */
    public Event(User user) {
      userStore = UserStore.getInstance();
      conversationStore = ConversationStore.getInstance();

      titleOfConversation = null;
      authorIdForConversation = null;
      conversationCreationTime = null;

      messageCreationTime = null;
      authorIdForMessage = null;
      conversationTitleOfMessage = null;
      messageContent = null;

      userCreationTime = user.getCreationTime();
      nameOfUser = user.getName();
      eventType = 'u';
	}

	/** Outputs a string based on the type given in the constructor. */
	public String toString() {

	  if(eventType == 'c') {
        return dateTime_ToString(conversationCreationTime) + ": "+ 
          userStore.getUser(authorIdForConversation).getName() + 
          " created a new conversation: " + titleOfConversation;
	  }
	  if(eventType == 'm') {
        return dateTime_ToString(messageCreationTime) + " PST: " + 
          userStore.getUser(authorIdForMessage).getName() + 
          " sent a message in " + conversationTitleOfMessage + 
          ": " + "\"" + messageContent + "\"";
	  }
	  if(eventType == 'u') {
	  	return dateTime_ToString(userCreationTime) + " PST: " + nameOfUser + " joined!";
	  }
	  else{
	  	System.err.println("This object has no event type. This is impossible. I don't know how you even caused this error");
	  	return null;
	  }
	}

    /** Formats time of type Instant into a string. */
	public String dateTime_ToString(Instant unformattedTime) {
	  DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
        .withLocale(Locale.US)
        .withZone(ZoneId.systemDefault());

      String formattedTime = formatter.format(unformattedTime);

      return formattedTime;
	}

	/** Returns the type of event that the object is. */
	public Character getEventType() { 
      return eventType;
	}

    /** 
     *  Returns seconds from the time Java was created 
     *  to the time the event was created as a Long. 
     */
	public Long getCreationTime() {
		if(eventType == 'u') {
			return userCreationTime.getEpochSecond();
		}
		if(eventType == 'm') {
			return messageCreationTime.getEpochSecond();
		}
		if(eventType == 'c') {
			return conversationCreationTime.getEpochSecond();
		}
		else {
			System.err.println("This object has no event type");
			return null;
		}
	}
}
