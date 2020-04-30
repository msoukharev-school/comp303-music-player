import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.*;

public class ShuffledOrderTest {

    final static Song[] SONGS = {
            new Song("Song1", "1", 1),
            new Song("Song2", "1", 1),
            new Song("Song3", "1", 1),
            new Song("Song4", "1", 1),
            new Song("Song5", "1", 1),
            new Song("Song6", "1", 1),
    };

    private MusicPlayer player;

    @BeforeEach
    public void init() {
        player = new MusicPlayer();
        for (Song s : SONGS) {
            player.addItem(s);
        }
    }

    @Test
    public void hasNextText() throws NoSuchFieldException {
        player.createPlaylist("pl1");
        player.addSongToPlaylist("Song3", "pl1");
        player.addSongToPlaylist("Song2", "pl1");
        player.addSongToPlaylist("Song1", "pl1");

        player.enqueuePlaylist("pl1");

        PlayOrder order = player.new ShuffledPlay();

        player.setupPlayOrder(order);

        




    }
}
