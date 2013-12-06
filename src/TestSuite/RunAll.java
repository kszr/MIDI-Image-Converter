package TestSuite;

/**
 * Created with IntelliJ IDEA.
 * User: chattrj3
 * Date: 11/8/13
 * Time: 5:22 AM
 * To change this template use File | Settings | File Templates.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({MIDIPlayerTest.class, PNGMusicTest.class, ImageAndMusicToolsTest.class})
public class RunAll {
}
