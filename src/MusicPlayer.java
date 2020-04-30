import java.util.*;

/**
 * A class representing a Music Player App.
 * It contains a pool of songs that allow the user to traverse and add to the queue to play.
 */
public class MusicPlayer implements Iterable<Song>, Device {

	/**
	 * 2.1
	 * @return
	 */
    @Override
    public Iterator<Song> iterator() {
        return aItems.values().iterator();
    }

	/**
	 * 2.2
	 */
	private Map<String, Playlist> aPlaylists = new LinkedHashMap<>();

    public void createPlaylist(String pName) {
    	assert pName != null;
    	aPlaylists.putIfAbsent(pName, new Playlist(pName));
	}

	public void addSongToPlaylist(String pSongName, String pPlaylistName) {
    	assert pSongName != null && pPlaylistName != null;
    	if (aItems.containsKey(pSongName) && aPlaylists.containsKey(pPlaylistName)) {
			aPlaylists.get(pPlaylistName).add(aItems.get(pSongName));
		}
	}

	public void addPlaylistToPlaylist(String pPlaylist, String pDestinationPlaylist) {
    	assert pPlaylist != null && pDestinationPlaylist != null;
    	if (aPlaylists.containsKey(pPlaylist) && aPlaylists.containsKey(pDestinationPlaylist)) {
			aPlaylists.get(pDestinationPlaylist).add(aPlaylists.get(pPlaylist));
		}
	}
	/**
	 * 2.3
	 * @param pName
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
	 * 3.1
	 */
	private PlayOrder aPlayOrder;

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
	 * 3.2
	 */
	public void setupPlayOrder(PlayOrder pPlayOrder) {
		aPlayOrder = pPlayOrder;
	}


	/**
	 * 4.1
	 */
	@Override
	public void next() {
		try{
			System.out.println("Now playing: " + aQueue.get(aPlayOrder.getNext()).toString());
		} catch (IndexOutOfBoundsException e) {
			System.out.println("END OF QUEUE");
		}
	}

    /*---------------------------- PROVIDED CODE ---------------------------------------*/
   private Map<String, Song> aItems = new LinkedHashMap<>(); // Make sure a predictable iteration order.
   private Queue aQueue = new Queue(); //

   MusicPlayer(){}

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
				aPlayOrder.update();
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
