package codeu.model.data;

import codeu.model.data.Event.EventType;
import java.time.Instant;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

public class EventTest {

	private Event conversationEvent;
	private Event messageEvent;
	private Event userEvent;

	private final Instant TIME = Instant.now();
	private final UUID ID =  UUID.randomUUID();

	private final Conversation CONVERSATION = new Conversation(
	  ID, UUID.randomUUID(), "title_test", TIME);
	private final Message MESSAGE = new Message(
	  ID, UUID.randomUUID(), UUID.randomUUID(), 
	  "content_test", TIME);
	private final User USER = new User(
	  ID, "name_test", "password_test", TIME, "bio_test");

	@Before
	public void setup() {
	  conversationEvent = new Event(CONVERSATION);
	  messageEvent = new Event(MESSAGE, "words");
	  userEvent = new Event(USER);
	}

	@Test
	public void conversationConstructorTest() {
	  Assert.assertEquals(TIME.getEpochSecond(), 
	  	conversationEvent.getCreationTime());
	  Assert.assertEquals(EventType.CONVERSATION, 
	  	conversationEvent.getEventType());
	  Assert.assertEquals(ID, conversationEvent.getId());
	}

	@Test
	public void messageConstructorTest() {
	  Assert.assertEquals(TIME.getEpochSecond(), 
	  	messageEvent.getCreationTime());
	  Assert.assertEquals(EventType.MESSAGE, 
	  	messageEvent.getEventType());
	  Assert.assertEquals(ID, messageEvent.getId());
	}

	@Test
	public void userConstructorTest() {
	  Assert.assertEquals(TIME.getEpochSecond(), 
	  	userEvent.getCreationTime());
	  Assert.assertEquals(EventType.USER, userEvent.getEventType());
	  Assert.assertEquals(ID, userEvent.getId());
	}
}