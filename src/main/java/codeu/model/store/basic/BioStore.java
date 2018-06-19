
package codeu.model.store.basic;

import codeu.model.data.Bio;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class BioStore {

  /** Singleton instance of UserStore. */
  private static BioStore instance;

  /**
   * Returns the singleton instance of UserStore that should be shared between all servlet classes.
   * Do not call this function from a test; use getTestInstance() instead.
   */
  public static BioStore getInstance() {
    if (instance == null) {
      instance = new BioStore(PersistentStorageAgent.getInstance());
    }
    return instance;
  }

  /**
   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
   *
   * @param persistentStorageAgent a mock used for testing
   */
  public static BioStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
    return new BioStore(persistentStorageAgent);
  }

  /**
   * The PersistentStorageAgent responsible for loading Bios from and saving Bios to Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  /** The in-memory list of Users. */
  private List<Bio> bios;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private BioStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    bios = new ArrayList<>();
  }


public List<Bio> getAllBios() {
    return bios;
  }
  /**
   * Access the Bio object with the given name.
   *
   * @return null if biog does not match any existing Bio.
   */
  public Bio getBio(String biog) {
    // This approach will be pretty slow if we have many users.
    for (Bio bio : bios) {
      if (bio.getBio().equals(biog)) {
        return bio;
      }
    }
    return null;
  }

  /**
   * Access the User object with the given UUID.
   *
   * @return null if the UUID does not match any existing User.
   */
  public Bio getBio(UUID id) {
    for (Bio bio : bios) {
      if (bio.getId().equals(id)) {
        return bio;
      }
    }
    return null;
  }

  /**
   * Add a new user to the current set of users known to the application. This should only be called
   * to add a new user, not to update an existing user.
   */
 /**public void addBio(Bio bio) {
    bios.add(bio);
    persistentStorageAgent.writeThrough(bio);
  }

  /**
   * Update an existing User.
   */
  /**public void updateBio(Bio bio) {
    persistentStorageAgent.writeThrough(bio);
  }

  /** Return true if the given username is known to the application. */
  public boolean isUserRegistered(String biog) {
    for (Bio bio : bios) {
      if (bio.getBio().equals(biog)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Sets the List of Users stored by this UserStore. This should only be called once, when the data
   * is loaded from Datastore.
   */
  public void setBios(List<Bio> bios) {
    this.bios = bios;
  }
}
