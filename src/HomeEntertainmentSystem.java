/**
 *
 * Class representing the client code of the MusicPlayer app.
 *
 */
public class HomeEntertainmentSystem {

    final static Song[] SONGPOOL = {
            new Song("Song0", "0", 0),
            new Song("Song1", "1", 1),
            new Song("Song2", "2", 2),
            new Song("Song3", "3", 3),
            new Song("Song4", "4", 4),
            new Song("Song5", "5", 5),
            new Song("Song6", "6", 6),
            new Song("Song7", "7", 7),
    };

    public static void main(String[] args) {
        final MusicPlayer kitchenMusicPlayer = new MusicPlayer();

        /* Load the songs to the player */
        for (Song s : SONGPOOL) {
            kitchenMusicPlayer.addItem(s);
        }

        kitchenMusicPlayer.createPlaylist("pl");
        kitchenMusicPlayer.createPlaylist("nested");

        kitchenMusicPlayer.addSongToPlaylist("Song0", "pl");
        kitchenMusicPlayer.addSongToPlaylist("Song1", "pl");
        kitchenMusicPlayer.addSongToPlaylist("Song2", "pl");

        kitchenMusicPlayer.addSongToPlaylist("Song3", "nested");
        kitchenMusicPlayer.addSongToPlaylist("Song4", "nested");
        kitchenMusicPlayer.addSongToPlaylist("Song5", "nested");

        kitchenMusicPlayer.addPlaylistToPlaylist("nested", "pl");

        kitchenMusicPlayer.addSongToPlaylist("not_in_the_pool","pl");

        kitchenMusicPlayer.enqueuePlaylist("pl");

        kitchenMusicPlayer.setupPlayOrder(kitchenMusicPlayer.new OrderedPlay());

        System.out.println("------- Songs will be played in order -------");

        Controller remote = new RemoteController(kitchenMusicPlayer);
        Controller voice = new VoiceController(kitchenMusicPlayer);

        Controller controller = remote;

        controller.next();
        controller.next();
        controller.next();

        System.out.println("------- I am going to use voice control now -------");
        controller = voice;

        controller.next();
        controller.next();
        controller.next();

        kitchenMusicPlayer.setupPlayOrder(kitchenMusicPlayer.new ShuffledPlay());
        System.out.println("------- I am going to switch to the shuffle order now -------");
        for(int i = 0; i < 10; i++) {
            controller.next();
        }

    }
}
