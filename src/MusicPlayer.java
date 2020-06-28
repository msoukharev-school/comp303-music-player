import java.util.*;

/**
 * A class representing a Music Player App.
 * It contains a pool of songs that allow the user to traverse and add to the queue to play.
 * Songs and playlists can be accessed and manipulated by their name identifier.
 * The client NEVER holds a reference to a song or a playlist.
 */
public class MusicPlayer implements Iterable<Song>, Device {

	private Map<String, Song> aItems = new LinkedHashMap<>(); //Predictable order of iteration
	private Queue aQueue = new Queue();
	private Map<String, Playlist> aPlaylists = new LinkedHashMap<>(); //stores playlists by their name identifiers
	/**
	 * PlayOrder holds a STRATEGY for traversing a queue
	 * Examples: order in which elements were added, shuffled order, etc.
	 */
	private PlayOrder aPlayOrder;
	public MusicPlayer(){}

	/**
	 * Allows iterating over the entire song library of the music player
	 */
    @Override
    public Iterator<Song> iterator() {
        return aItems.values().iterator();
    }

	/**
	 * Creates a playlist with a given name and stores it in the music player app
	 * @param pName name of the new playlist
	 * @pre pName != null
	 */
    public void createPlaylist(String pName) {
    	assert pName != null;
    	aPlaylists.putIfAbsent(pName, new Playlist(pName));
	}

	/**
	 * Adds a song to a playlist if both exist in the music player app
	 * @param pSongName name of the song to add
	 * @param pPlaylistName name of the playlist to store the song in
	 * @pre pSongName != null and pPlaylistName != null
	 */
	public void addSongToPlaylist(String pSongName, String pPlaylistName) {
    	assert pSongName != null && pPlaylistName != null;
    	if (aItems.containsKey(pSongName) && aPlaylists.containsKey(pPlaylistName)) {
			aPlaylists.get(pPlaylistName).add(aItems.get(pSongName));
		}
	}

	/**
	 * Adds a playlist to a playlist if both exist in the music player app
	 * @param pPlaylist name of the playlist to add (nested playlist)
	 * @param pDestinationPlaylist name of the nesting playlist
	 * @pre pPlaylist != null and pDestinationPlaylist != null
	 */

	public void addPlaylistToPlaylist(String pPlaylist, String pDestinationPlaylist) {
    	assert pPlaylist != null && pDestinationPlaylist != null;
    	if (aPlaylists.containsKey(pPlaylist) && aPlaylists.containsKey(pDestinationPlaylist)) {
			aPlaylists.get(pDestinationPlaylist).add(aPlaylists.get(pPlaylist));
		}
	}

	/**
	 * Adds a playlist to the player queue, if exists
	 * @param pName name of the playlist to add to a queue
	 * @pre pName != null
	 */
    public void enqueuePlaylist(String pName) {
    	assert pName != null;
    	if (aPlaylists.containsKey(pName)) {
    		for (Playlistable item : aPlaylists.get(pName)) {
    			try {
					if (item instanceof Song) {
						addItemToQueue(((Song) item).getName());
					} else if (item instanceof Playlist) {
						enqueuePlaylist(((Playlist) item).getName());
					}
				} catch (NullPointerException e) {}
			}
		}
	}

	/**
	 * Class OrderedPlay is a play order where songs in the queue are
	 * traversed in the same order the elements were added to the queue
	 */
	public class OrderedPlay implements PlayOrder {

		int aIndex = 0;

		public OrderedPlay() {
			aPlayOrder = this;
		}

		@Override
		public int getNext() throws IndexOutOfBoundsException {
			if (hasNext()) {
				return aIndex++;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public boolean hasNext() {
			return (aIndex < getQueueSize());
		}

		@Override
		public void update() {
			aIndex = 0;
		}
	}

	/**
	 * Class ShuffledPlay defines a play order where the songs in a queue
	 * are traversed in a non-repeating pseudo-random order
	 */
	public class ShuffledPlay implements PlayOrder {

		int aIndex;
		List<Integer> aIndexList = new ArrayList<>();

		public ShuffledPlay() {
			fillAndShuffle();
		}

		@Override
		public int getNext() throws IndexOutOfBoundsException {
			if (hasNext()) {
				return aIndexList.get(aIndex++);
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public boolean hasNext() {
			return (aIndex < aIndexList.size());
		}

		@Override
		public void update() {
			fillAndShuffle();
		}

		private void fillAndShuffle() {
			aIndex = 0;
			for (int i = 0; i < getQueueSize(); i++) {
				aIndexList.add(i);
			}
			Collections.shuffle(aIndexList);
		}
	}

	/**
	 * Sets up the play order of the music player app
	 * @param pPlayOrder a PlayOrder class implementing the queue traversal strategy
	 */
	public void setupPlayOrder(PlayOrder pPlayOrder) {
		aPlayOrder = pPlayOrder;
	}


	/**
	 * Plays the next song in the queue depending on the current aPlayOrder type: OrderedPlay or ShuffledPlay
	 */
	@Override
	public void next() {
		try{
			System.out.println("Now playing: " + aQueue.get(aPlayOrder.getNext()).toString());
		} catch (IndexOutOfBoundsException e) {
			System.out.println("END OF QUEUE");
		}
	}

	/**
	 * Add a single song to the music pool if a song with the same name is not already in the pool.
	 * @param pItem the song to be added in the pool
	 * @pre pItem !=null
	 */
	public void addItem(Song pItem) {
		assert pItem != null;
		aItems.putIfAbsent(pItem.getName(), pItem);
	}

	/**
	 * Add a single song to the queue if that song can be found in the music pool.
	 * @param pItemName the name of the song
	 * @pre pItemName !=null
	 */
	public void addItemToQueue(String pItemName) {
		assert pItemName!= null;
		if (aItems.containsKey(pItemName)) {
			aQueue.add(aItems.get(pItemName));
			try {
				aPlayOrder.update();
			} catch(NullPointerException e) {}
		}
	}

	/**
	 * Remove a single item from the queue
	 * @param pItemName the name of the song
	 * @pre pItemName != null
	 */
	public void removeItemFromQueue(String pItemName) {
		assert pItemName != null;
		if (aItems.containsKey(pItemName)) {
			if (aQueue.remove(aItems.get(pItemName)))
				try {
					aPlayOrder.update();
				} catch(NullPointerException e) {}
		}
	}

	/**
	 * Obtain the number of songs in the queue
	 * @return the number of songs in the queue
	 */
	public int getQueueSize() {
		return aQueue.size();
	}

}
