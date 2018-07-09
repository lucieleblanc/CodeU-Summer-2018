package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

/** Class representing a registered user. */
public class Bio {
  private final UUID id;
  private final String bio;
  private final Instant creationB;

  /**
   * Constructs a new User.
   *
   * @param id the ID of this User
   * @param bio the username of this User
   
   * @param creationB the creation time of this User
   */
  public Bio(UUID id, String bio, Instant creationB) {
    this.id = id;
    this.bio = bio;
    this.creationB = creationB;
  }

  /** Returns the ID of this User. */
  public UUID getId() {
    return id;
  }

  /** Returns the username of this User. */
  public String getBio() {
    return bio;
  }

  /** Returns the creation time of this User. */
  public Instant getCreationTimeB() {
    return creationB;
  }
}
