import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Playlist class that can add and store songs and playlists
 */
public class Playlist implements Playlistable, Iterable<Playlistable> {

    private String aName;
    private List<Playlistable> aItems = new ArrayList<>();

    /**
     * Create a new Playlist object
     * @param pName name of the new playlist
     */
    public Playlist(String pName) {
        aName = pName;
    }

    /**
     * @return name of the playlist
     */
    public String getName() {
        return aName;
    }

    /**
     * Adds a song or a playlist to this playlist
     * @param pItem a song or a playlist to be added
     */
    public void add(Playlistable pItem) {
        assert pItem != null;
        aItems.add(pItem);
    }

    /**
     * @return iterator over the ArrayList that stores the songs and other playlists
     */
    @Override
    public Iterator<Playlistable> iterator() {
        return aItems.iterator();
    }
}
