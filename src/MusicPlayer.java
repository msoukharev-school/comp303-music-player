import java.util.*;

/**
 * A class representing a Music Player App.
 * It contains a pool of songs that allow the user to traverse and add to the queue to play.
 */
public class MusicPlayer implements Iterable<Song> {

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

    public Playlist createPlaylist(String pName) {
    	assert pName != null;
    	return aPlaylists.putIfAbsent(pName, new Playlist(pName));
	}

	public Playlist getPlaylist(String pName) {
    	assert pName != null;
    	return aPlaylists.get(aPlaylists.get(pName));
	}

	/**
	 * 2.3
	 * @param pName
	 */
    public void enqueuePlaylist(String pName) {
    	assert pName != null;
    	if (aItems.containsKey(pName)) {
    		for (Playlistable item : aPlaylists.get(pName)) {
				if (item instanceof Song) {
					addItemToQueue(((Song) item).getName());
				} else if (item instanceof Playlist) {
					enqueuePlaylist(((Playlist) item).getName());
				}
			}
		}
	}

	/**
	 * 3.1
	 */
	PlayOrder aPlayOrder;

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
		List<Integer> aIndexList;

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
			aPlayOrder.update();
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
