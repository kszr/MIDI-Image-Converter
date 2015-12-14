package application;

import audiovisual.LengthAudioVisual;
import player.MIDIPlayer;
import view.MIDIPlayerView;
import controller.MIDIPlayerController;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 11/22/13
 * Time: 1:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class Application {
    public static void main(String[] args) throws Exception {
        MIDIPlayerView view = new MIDIPlayerView();
        MIDIPlayer player = new MIDIPlayer(new LengthAudioVisual());
		MIDIPlayerController controller = new MIDIPlayerController(view, player);
    }
}
