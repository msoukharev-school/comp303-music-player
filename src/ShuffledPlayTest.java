import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShuffledPlayTest {

    private final MusicPlayer player = new MusicPlayer();
    private final Song[] SONGPOOL = {
            new Song("Song1", "A", 1),
            new Song("Song2", "A", 2),
            new Song("Song3", "A", 3),
            new Song("Song4", "A", 4),
            new Song("Song5", "A", 5),
            new Song("Song6", "A", 6),
    };

    @BeforeEach
    private void initialize() {
        /* Load songs into the app */
        for (Song s : SONGPOOL) {
            player.addItem(s);
        }
    }


}
