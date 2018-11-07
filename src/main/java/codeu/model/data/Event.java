
package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

/**  
 *  Made so a list can  easily hold and sort all types of objects at once.
 */
public interface Event{
 
  /** Returns the ID of this Conversation. */
  public UUID getId();

  /** Returns the creation time. */
  public Instant getCreationTime();

  public String toString();
}
