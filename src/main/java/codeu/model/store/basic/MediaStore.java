package codeu.model.store.basic;

import codeu.model.data.Media;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class MediaStore {

  /** Singleton instance of MediaStore. */
  private static MediaStore instance;

  /**
   * Returns the singleton instance of MediaStore that should be shared between all servlet
   * classes. Do not call this function from a test; use getTestInstance() instead.
   */
  public static MediaStore getInstance() {
    if (instance == null) {
      instance = new MediaStore(PersistentStorageAgent.getInstance());
    }
    return instance;
  }

  /**
   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
   *
   * @param persistentStorageAgent a mock used for testing
   */
  public static MediaStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
    return new MediaStore(persistentStorageAgent);
  }

  /**
   * The PersistentStorageAgent responsible for loading Media from and saving Media
   * to Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  /** The in-memory list of media. */
  private List<Media> media;
  private HashMap<UUID, Media> mediaIdMap;
  private HashMap<String, Media> mediaTitleMap;
  private HashMap<String, Media> profilePictureMap;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private MediaStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    media = new ArrayList<>();
    mediaIdMap = new HashMap<>();
    mediaTitleMap = new HashMap<>();
    profilePictureMap = new HashMap<>();
  }

  /** Access the current set of media known to the application. */
  public List<Media> getAllMedia() {
    return media;
  }

  /** Add a new media to the current set of media known to the application. */
  public void addMedia(Media singleMedia) throws java.io.IOException {
    media.add(singleMedia);
    mediaIdMap.put(
      singleMedia.getId(), singleMedia);
    mediaTitleMap.put(
      singleMedia.getTitle(), singleMedia);
    persistentStorageAgent.writeThrough(singleMedia);
  }

  /** Check whether a Media title is already known to the application. */
  public boolean isTitleTaken(String title) {
    if (mediaTitleMap.containsKey(title)) {
      return true;
    }
    else {
      return false;
    }
  }

  /** Find and return the media with the given title. */
  public Media getMediaWithTitle(String title) {
    if (mediaTitleMap.containsKey(title)) {
      return mediaTitleMap.get(title);
    }
    else {
      System.err.println("The media you were looking for could not be found." +
                         " No title's matched. Please check if the media actually" +
                         " exists on the admin page if on local server or the app engine. ");
      return null;
    }
  }

  /** Find and return the media with the given Id. */
  public Media getMediaWithId(UUID id) {
    if (mediaIdMap.containsKey(id)) {
      return mediaIdMap.get(id);
    }
    else {
      System.err.println("The media you were looking for could not be found." +
                         " No Id's matched. Please check if the media actually" +
                         " exists on the admin page if on local server or the app engine. ");
      return null;
    }
  }

  /** Sets the List of media stored by this mediatore. */
  public void setMedia(List<Media> media) {
    this.media = media;
    UserStore userStore = UserStore.getInstance();
    for(Media singleMedia: media) {
      mediaIdMap.put(
        singleMedia.getId(), singleMedia);
      mediaTitleMap.put(
        singleMedia.getTitle(), singleMedia);
      if(singleMedia.getIsProfilePicture() == true)
      {
        profilePictureMap.put(userStore.getUser(singleMedia.getOwnerId()).getName(), singleMedia);
      }
    }
  }

  public Media getProfilePicture(String user) {
    if(profilePictureMap.containsKey(user)) {
      return profilePictureMap.get(user);
    }
    else {
      return null;
    }
  }

  public Media getProfilePicture(UUID id) {jnkj
    if(mediaIdMap.containsKey(id)) {
      return mediaIdMap.get(id);
    }
    else {
      return null;
    }
  }

  public void setProfilePicture(String user, Media singleMedia) {
    profilePictureMap.put(user, singleMedia);
    singleMedia.setIsProfilePicture(true);
  }
}