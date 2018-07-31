
package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.time.ZoneId; 

import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.UserStore;
import java.awt.image.BufferedImage;

/** 
 *  This class abstracts any object so it can be a User, Conversation, or Message. 
 *  Made so a list can  easily hold and sort all types of objects at once.
 */
public class Event{

	/* Denotes the type of event this is. Conversation, Message, User, or Media. */
	public enum EventType{
    CONVERSATION, MESSAGE, USER, MEDIA;
  }
  private final EventType eventType;

	/* All the fields needed from the conversation class. */
	private final String titleOfConversation;
	private final UUID authorIdForConversation;
	private final Instant conversationCreationTime;
  private final UUID conversationId;

	/* All the fields needed from the message class. */
	private final Instant messageCreationTime;
  private final UUID authorIdForMessage;
  private final String conversationTitleOfMessage;
  private final UUID conversationIdForMessage;
  private final String messageContent;
  private final UUID messageId;

  /* All the fields needed from the user class. */
  private final Instant userCreationTime;
  private final String nameOfUser;
  private final UUID userId;

  /* All the fields needed from the media class. */
  private final UUID mediaId;
  private final UUID ownermediaId;
  private final Instant mediaCreationTime;
  private final String titleOfMedia;
  private final BufferedImage contentOfMedia;
  private final String contentTypeOfMedia;
  private final UUID conversationIdForMedia;

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
    eventType = EventType.CONVERSATION;

    userStore = UserStore.getInstance();
    conversationStore = ConversationStore.getInstance();

    titleOfConversation = conversation.getTitle();
    authorIdForConversation = conversation.getOwnerId();
    conversationCreationTime = conversation.getCreationTime();
    conversationId = conversation.getId();

    messageCreationTime = null;
    authorIdForMessage = null;
    conversationTitleOfMessage = null;
    conversationIdForMessage = null;
    messageContent = null;
    messageId = null;

    userCreationTime = null;
    nameOfUser = null;
    userId = null;

    mediaId = null;
    ownermediaId = null;
    mediaCreationTime = null;
    titleOfMedia = null;
    contentOfMedia = null;
    contentTypeOfMedia = null;
    conversationIdForMedia = null;

	}

  /**
   * Constructs a new event using a Message object.
   *
   * @param message an object of type Message
   */
	public Event(Message message) {
    eventType = EventType.MESSAGE;

    userStore = UserStore.getInstance();
    conversationStore = ConversationStore.getInstance();

    titleOfConversation = null;
    authorIdForConversation = null;
    conversationCreationTime = null;
    conversationId = null;

    messageCreationTime = message.getCreationTime();
    authorIdForMessage = message.getAuthorId();

    Boolean error = false;
    try {
      conversationStore.getConversationWithId(message
        .getConversationId()).getTitle();
    }
    catch (NullPointerException e) {
      System.err.println("_______________________________________________"  +
                         "\n CAUGHT EXCEPTION_____________________________" + 
                         " This message does not have a conversation"    + 
                         " it is associated with. If you are unit test," + 
                         " consider using the other constructor that takes" + 
                         " a message and a placeholder for the conversationTitleOfMessage," + 
                         " otherwise consider checking the admin page or"   + 
                         " appegine for to see if the conversation exists." + 
                         " _______________________________________________" +
                         "\n_______________________________________________");
      error = true;
    }
    if(error) {
      conversationTitleOfMessage = null;
      conversationIdForMessage = null;
    }
    else {
      conversationTitleOfMessage = conversationStore.getConversationWithId(
        message.getConversationId()).getTitle();
      conversationIdForMessage = message.getConversationId();
    }

    messageContent = message.getContent();
    messageId = message.getId();

    userCreationTime = null;
    nameOfUser = null;
    userId = null;

    mediaId = null;
    ownermediaId = null;
    mediaCreationTime = null;
    titleOfMedia = null;
    contentOfMedia = null;
    contentTypeOfMedia = null;
    conversationIdForMedia = null;
	}

  /**
   * Constructor used for testing. Supply a mock for message.
   *
   * @param message a mock message used for testing.
   * @param testTitle  a mock string used for testing (the conversation for the message doesn't 
   *                exist because it is a test. Would other wise give a nullptr exception).
   */
  public Event(Message message, String testTitle) {
    eventType = EventType.MESSAGE;

    userStore = UserStore.getInstance();
    conversationStore = ConversationStore.getInstance();

    titleOfConversation = null;
    authorIdForConversation = null;
    conversationCreationTime = null;
    conversationId = null;

    messageCreationTime = message.getCreationTime();
    authorIdForMessage = message.getAuthorId();
    conversationTitleOfMessage = testTitle;
    conversationIdForMessage = null;
    messageContent = message.getContent();
    messageId = message.getId();

    userCreationTime = null;
    nameOfUser = null;
    userId = null;

    mediaId = null;
    ownermediaId = null;
    mediaCreationTime = null;
    titleOfMedia = null;
    contentOfMedia = null;
    contentTypeOfMedia = null;
    conversationIdForMedia = null;
  }

  /**
   * Constructs an new event using a User object.
   *
   * @param user an object of type User
   */
  public Event(User user) {
    eventType = EventType.USER;

    userStore = UserStore.getInstance();
    conversationStore = ConversationStore.getInstance();

    titleOfConversation = null;
    authorIdForConversation = null;
    conversationCreationTime = null;
    conversationId = null;

    messageCreationTime = null;
    authorIdForMessage = null;
    conversationTitleOfMessage = null;
    conversationIdForMessage = null;
    messageContent = null;
    messageId = null;
    userCreationTime = user.getCreationTime();
    nameOfUser = user.getName();
    userId = user.getId();

    mediaId = null;
    ownermediaId = null;
    mediaCreationTime = null;
    titleOfMedia = null;
    contentOfMedia = null;
    contentTypeOfMedia = null;
    conversationIdForMedia = null;
  }

  /**
   * Constructs an new event using a Media object.
   *
   * @param media an object of type media
   */
  public Event(Media media) {
    eventType = EventType.MEDIA;

    userStore = UserStore.getInstance();
    conversationStore = ConversationStore.getInstance();

    titleOfConversation = null;
    authorIdForConversation = null;
    conversationCreationTime = null;
    conversationId = null;

    messageCreationTime = null;
    authorIdForMessage = null;
    conversationTitleOfMessage = null;
    conversationIdForMessage = null;
    messageContent = null;
    messageId = null;
    userCreationTime = null;
    nameOfUser = null;
    userId = null;

    mediaId = media.getId();
    ownermediaId = media.getOwnerId();
    mediaCreationTime = media.getCreationTime();
    titleOfMedia = media.getTitle();
    contentOfMedia = media.getContent();
    contentTypeOfMedia = media.getContentType();
    conversationIdForMedia = media.getConversationId();
  }

  public UUID getConversationIdForMessage() {
    return conversationIdForMessage;
  }

  public String getMessageContent() {
    return messageContent;
  }

  public UUID getAuthorIdForMessage() {
    return authorIdForMessage;
  }

  public String getNameOfUser() {
    return nameOfUser;
  }

	/** Outputs a string based on the type given in the constructor. */
	public String toString() {
	  if(eventType == EventType.CONVERSATION) {
        return toString(conversationCreationTime) + " PST: "+ 
          userStore.getUser(authorIdForConversation).getName() + 
          " created a new conversation: ";
	  }
	  if(eventType == EventType.MESSAGE) {
        return toString(messageCreationTime) + " PST: " + 
          userStore.getUser(authorIdForMessage).getName() + 
          " sent a message in " + conversationTitleOfMessage + 
          ": " + "\"" + messageContent + "\"";
	  }
	  if(eventType == EventType.USER) {
	  	return toString(userCreationTime) + " PST: ";
	  }
    if(eventType == EventType.MEDIA) {
      return "Fix this later. A user uploaded media though";
    }
	  else{
	  	System.err.println("This object has no event type. This is impossible. I don't know how you even caused this error");
	  	return null;
	  }
	}

  /** Formats time of type Instant into a string. */
	public String toString(Instant unformattedTime) {
	  DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
      .withLocale(Locale.US)
      .withZone(ZoneId.systemDefault());

    String formattedTime = formatter.format(unformattedTime);

    return formattedTime;
	}

  public String getTitleOfConversation() {
    return titleOfConversation;
  }

	/** Returns the type of event that the object is. */
	public EventType getEventType() { 
    return eventType;
	}

  /** 
   *  Returns seconds from the time Java was created 
   *  to the time the event was created as a Long. 
   */
	public long getCreationTime() {
		if(eventType == EventType.USER) {
			return userCreationTime.getEpochSecond();
		}
		if(eventType == EventType.MESSAGE) {
			return messageCreationTime.getEpochSecond();
		}
		if(eventType == EventType.CONVERSATION) {
			return conversationCreationTime.getEpochSecond();
		}
    if(eventType == EventType.MEDIA) {
      return mediaCreationTime.getEpochSecond();
    }
		else {
			System.err.println("This object has no event type");
			return 0;
		}
	}

  public UUID getId() {
    if(eventType == EventType.USER) {
      return userId;
    }
    if(eventType == EventType.MESSAGE) {
      return messageId;
    }
    if(eventType == EventType.CONVERSATION) {
      return conversationId;
    }
    if(eventType == EventType.MEDIA) {
      return mediaId;
    }
    else {
      System.err.println("This object has no event type");
      return null;
    }
  }
}

//make it so that event can hold media type in a similar way to message (conversation that it comes from is important)
  //make it actually save media as and event if uploaded from chat servlet

//save media as an event in file upload servlet in a similar way that messages are saved as events
//chat.jsp will loop thorugh events and check if event is message or media and post right one
//chat servlet just passes through event data

//for each correct image that we want, we call the file upload servlet and send it information as to which image we want
