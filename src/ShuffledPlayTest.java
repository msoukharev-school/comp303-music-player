import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class ShuffledPlayTest {

    final static Song[] SONGS = {
            new Song("Song1", "1", 1),
            new Song("Song2", "1", 1),
            new Song("Song3", "1", 1),
            new Song("Song4", "1", 1),
            new Song("Song5", "1", 1),
            new Song("Song6", "1", 1),
    };

    private MusicPlayer player;
    private PlayOrder playOrder;

    private void init() {
        player = new MusicPlayer();
        playOrder = player.new ShuffledPlay();
        player.setupPlayOrder(playOrder);

        for (Song s : SONGS) {
            player.addItem(s);
        }

        player.createPlaylist("pl1");
        player.addSongToPlaylist("Song6", "pl1");
        player.addSongToPlaylist("Song5", "pl1");
        player.addSongToPlaylist("Song4", "pl1");
        player.addSongToPlaylist("Song3", "pl1");
        player.addSongToPlaylist("Song2", "pl1");
        player.addSongToPlaylist("Song1", "pl1");
    }

    @Test
    public void test() {
        init();
        player.enqueuePlaylist("pl1");
        /* At this point we should have 6 songs in the queue: Song6, Song5, Song4, Song3, Song2, Song1 */
        ArrayList<Integer> sequence1 = new ArrayList<>();
        ArrayList<Integer> sequence2 = new ArrayList<>();

        while(playOrder.hasNext()) {
            sequence1.add(playOrder.getNext());
        }
        /* First shuffled sequence */

        playOrder.update();
        /* Issue update to the order, should reinitialize and reshuffle */

        while(playOrder.hasNext()) {
            sequence2.add(playOrder.getNext());
        }
        /* Second shuffled sequence */

        assertEquals(sequence1.size(), sequence2.size());
        /* The number of indices in both sequences should be the same */

        assertFalse(sequence1.equals(sequence2));
        /* The sequence should not be the same, with a probability of test failure 1/6! */

    }


}
