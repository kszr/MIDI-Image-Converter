package Application;

import Player.MIDIPlayer;
import View.MIDIPlayerView;
import Controller.MIDIPlayerController;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 11/22/13
 * Time: 1:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class Application {
    public static void main(String[] args) throws Exception {
        MIDIPlayer player = new MIDIPlayer();
        MIDIPlayerView view = new MIDIPlayerView();
        MIDIPlayerController controller = new MIDIPlayerController(view, player);
    }
}
