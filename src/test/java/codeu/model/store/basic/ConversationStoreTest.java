package codeu.model.store.basic;

import codeu.model.data.Conversation;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ConversationStoreTest {

  private ConversationStore conversationStore;
  private PersistentStorageAgent mockPersistentStorageAgent;
  private final UUID  CONVERSATION_ID = UUID.randomUUID();
  private final UUID CONVERSATION_USERID = UUID.randomUUID();
  private final Conversation CONVERSATION_ONE =
      new Conversation(
          CONVERSATION_ID, CONVERSATION_USERID, "conversation_one", Instant.ofEpochMilli(1000));
  private List<Conversation> conversationList; 

  @Before
  public void setup() {
    mockPersistentStorageAgent = Mockito.mock(PersistentStorageAgent.class);
    conversationStore = ConversationStore.getTestInstance(mockPersistentStorageAgent);

    conversationList = new ArrayList<>();
    conversationList.add(CONVERSATION_ONE);
    conversationStore.setConversations(conversationList);
  }

  @Test
  public void testGetConversationWithTitle_found() {
    Conversation resultConversation =
        conversationStore.getConversationWithTitle(CONVERSATION_ONE.getTitle());

    assertEquals(CONVERSATION_ONE, resultConversation);
  }

  @Test
  public void testGetConversationWithTitle_notFound() {
    Conversation resultConversation = conversationStore.getConversationWithTitle("unfound_title");

    Assert.assertNull(resultConversation);
  }

  @Test
  public void testGetConversationWithId_found() {
    Conversation resultConversation =
        conversationStore.getConversationWithId(CONVERSATION_ID);

    assertEquals(CONVERSATION_ONE, resultConversation);
  }
  
  @Test 
  public void testGetConversationWithOwner(){
    List<Conversation> fakeConvoList = 
        conversationStore.getConversationWithOwner(CONVERSATION_USERID);
  assertEquals(fakeConvoList, conversationList);
  }

  @Test
  public void testGetConversationWithId_notFound() {
    Conversation resultConversation = conversationStore.getConversationWithId(UUID.randomUUID());

    Assert.assertNull(resultConversation);
  }

  @Test
  public void testIsTitleTaken_true() {
    boolean isTitleTaken = conversationStore.isTitleTaken(CONVERSATION_ONE.getTitle());

    Assert.assertTrue(isTitleTaken);
  }

  @Test
  public void testIsTitleTaken_false() {
    boolean isTitleTaken = conversationStore.isTitleTaken("unfound_title");

    Assert.assertFalse(isTitleTaken);
  }

  @Test
  public void testAddConversation() {
    Conversation inputConversation =
        new Conversation(UUID.randomUUID(), UUID.randomUUID(), "test_conversation", Instant.now());

    conversationStore.addConversation(inputConversation);
    Conversation resultConversation =
        conversationStore.getConversationWithTitle("test_conversation");

    assertEquals(inputConversation, resultConversation);
    Mockito.verify(mockPersistentStorageAgent).writeThrough(inputConversation);
  }

  private void assertEquals(Conversation expectedConversation, Conversation actualConversation) {
    Assert.assertEquals(expectedConversation.getId(), actualConversation.getId());
    Assert.assertEquals(expectedConversation.getOwnerId(), actualConversation.getOwnerId());
    Assert.assertEquals(expectedConversation.getTitle(), actualConversation.getTitle());
    Assert.assertEquals(
        expectedConversation.getCreationTime(), actualConversation.getCreationTime());
  }
}
