import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Playlist implements Playlistable, Iterable<Playlistable> {

    private String aName;
    private List<Playlistable> aItems = new ArrayList<>();

    public Playlist(String pName) {
        aName = pName;
    }

    public String getName() {
        return aName;
    }

    public void add(Playlistable pItem) {
        assert pItem != null;
        aItems.add(pItem);
    }

    @Override
    public Iterator<Playlistable> iterator() {
        return aItems.iterator();
    }
}
