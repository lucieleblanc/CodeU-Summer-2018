package codeu.model.data;

import java.awt.image.BufferedImage;
import java.time.Instant;
import java.util.UUID;

/**
 * Class representing media (a picture, video, or sound byte).
 * The media is uploaded by Users.
 */
public class Media implements Event{
  private final UUID id;
  private final UUID owner;
  private final Instant creation;
  private final String title;
  private final BufferedImage content;
  private final String contentType;
  private Boolean isProfilePicture;
  private final UUID conversationId;
  /**
   * Constructs a new Media object.
   *
   * @param id the ID of the Media
   * @param owner the ID of the User who uploaded this Media
   * @param title the title of this Media
   * @param creation the creation time of this Media
   * @param content the content of this media
   * @param contentType the content type of this media
   * @param conversationId the conversation id of this media
   */
  public Media(UUID id, UUID owner, String title, Instant creation, 
    BufferedImage content, String contentType, UUID conversationId) {
    this.id = id;
    this.owner = owner;
    this.title = title;
    this.creation = creation;
    this.content = content;
    this.contentType = contentType;
    this.isProfilePicture = false;
    this.conversationId = conversationId;
  }

  /** Returns the ID of this Media. */
  public UUID getId() { 
    return id;
  }

  /** Returns the ID of the User who created this Media. */
  public UUID getOwnerId() { //Conversation and Media (technically message)
    return owner;
  }

  /** Returns the title of this Media. */
  public String getTitle() { //Conversation and Media
    return title;
  }

  /** Returns the creation time of this Media. */
  public Instant getCreationTime() {
    return creation;
  }

  /** Returns the content of this Media. */
  public BufferedImage getContent() { //only this
    return content;
  }

  public String getContentType() { //only this
    return contentType;
  }

  public void setIsProfilePicture(Boolean bool) { //only this
    isProfilePicture = bool;
  }

  public Boolean getIsProfilePicture() { //only this
    return isProfilePicture;
  }

  public UUID getConversationId() { //message and media(media should probably be split into types where it was uploaded from)
    return conversationId;
  }

  public String toString() {
    return "Media to string in progress";
  }

  public Long getCreationTimeLong() {
    return creation.getEpochSecond();
  }
}