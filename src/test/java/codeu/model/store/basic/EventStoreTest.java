package codeu.model.store.basic;

import codeu.model.data.Conversation;
import codeu.model.data.Event;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.persistence.PersistentStorageAgent;

import java.time.Instant;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class EventStoreTest {

  /*
   * We have two evenStores to test out two different ways to set the eventStore.
   */
  private EventStore eventStore;
  private PersistentStorageAgent mockPersistentStorageAgent;

  private final Conversation FAKECONVERSATION = new Conversation(
                               UUID.randomUUID(), UUID.randomUUID(), 
                               "test_conversation", Instant.now().plusSeconds(0));

  private final Message FAKEMESSAGE = new Message(
                          UUID.randomUUID(), UUID.randomUUID(), 
                          UUID.randomUUID(), "test_message", Instant.now().plusSeconds(1));

  private final User FAKEUSER = new User(
                      UUID.randomUUID(), "test_user", 
                      "test_user_PHash", Instant.now().plusSeconds(2), 
                      "test_user_bio");

  @Before
  public void setup() {
  	mockPersistentStorageAgent = Mockito.mock(PersistentStorageAgent.class);
  	eventStore  = EventStore.getTestInstance(mockPersistentStorageAgent); 

  	/* Create the fake list of events */
    
    final List<Event> fakeEventList = new ArrayList<>(Arrays.asList(
      new Event(FAKEUSER),                         //User Event
      new Event(FAKECONVERSATION),                //Conversation Event
      new Event(FAKEMESSAGE, "test_title")       //Message Event
      ));

    eventStore.setEvents(fakeEventList);
  }

  @Test
  public void testGetAllEventsSorted(){
    /* 
     * NOTE: Sorted order is FAKECONVERSATION, FAKEMESSAGE, and FAKEUSER. 
     * The normal order is FAKEUSER, FAKECONVERSATION, FAKEMESSAGE.
     */
    List<Event> events = eventStore.getAllEventsSorted();
    Assert.assertEquals(3, events.size());
    assertEquals(FAKECONVERSATION.getId(), events.get(0).getId());
    assertEquals(FAKEMESSAGE.getId(), events.get(1).getId());
    assertEquals(FAKEUSER.getId(), events.get(2).getId());
  }

}