package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import player.MIDIPlayer;
import View.MIDIPlayerView;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 11/22/13
 * Time: 11:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class MIDIPlayerController {
    private MIDIPlayerView _view;
    private MIDIPlayer _model;

    /**
     * Instantiates the controller with a model and view.
     * @param view
     * @param model
     */
    public MIDIPlayerController(MIDIPlayerView view, MIDIPlayer model) {
        _view = view;
        _model = model;

        setUpActionListeners();
    }

    /**
     * Sets up the controls for the application.
     */
    private void setUpActionListeners() {
        setUpPlayerListeners();
    }

    /**
     * Sets up the controls for the MIDI Player.
     */
    private void setUpPlayerListeners() {
        addFileOpenListener();
        addFileSaveListener();
        addPNGMusicListener();
        addPlayListener();
        addPauseListener();
        addStopListener();
        addBackListener();
        addForwardListener();
    }

    /**
     * Control for the open button.
     */
    private void addFileOpenListener() {
        _view.addFileOpenListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String filename = _view.getTextFieldText();
                    _view.setStatusFieldText("Opening " + filename + "...");
                    _model.open(filename);
                    _model.stop();
                    _view.setStatusFieldText("Opened " + filename);
                }
                catch(Exception exception) {
                    _view.displayMessageBox(exception.toString());
                    //exception.printStackTrace();
                }
            }
        });
    }

    /**
     * Control for the save button.
     */
    private void addFileSaveListener() {
        _view.addPlayerFileSaveListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String filename = _view.getTextFieldText();
                    _view.setStatusFieldText("Saving " + filename);
                    _model.save(filename);
                    _view.setStatusFieldText("Saved " + filename);
                }
                catch(Exception exception) {
                    _view.displayMessageBox(exception.toString());
                    //exception.printStackTrace();
                }
            }
        });
    }

    /**
     * Control for the PNGMusic button.
     */
    private void addPNGMusicListener() {
        _view.addPlayerPNGMusicListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BufferedImage image = _model.convertSequenceToPNG();
                    _view.setStatusFieldText("Converting to PNG...");
                    _view.displayImage(image);
                    _view.setStatusFieldText("Converted to PNG");
                }
                catch(Exception exception) {
                    //exception.printStackTrace();
                }
            }
        });
    }

    /**
     * Control for the play button.
     */
    private void addPlayListener() {
        _view.addPlayerPlayListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    _model.resume();
                    _view.setStatusFieldText("Playing");
                }
                catch(Exception exception) {
                    try {
                        _model.play();
                    }
                    catch(Exception exception2) {
                        _view.displayMessageBox(exception2.toString());
                        //exception2.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Control for the pause button.
     */
    private void addPauseListener() {
        _view.addPlayerPauseListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    _model.pause();
                    _view.setStatusFieldText("Paused");
                } catch (Exception exception) {
                    //Do nothing.
                }
            }
        });
    }

    /**
     * Control for the stop button.
     */
    private void addStopListener() {
        _view.addPlayerStopListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    _model.stop();
                    _view.setStatusFieldText("Stopped");
                } catch (Exception exception) {
                    //Do nothing.
                }
            }
        });
    }

    /**
     * Control for the back button.
     */
    private void addBackListener() {
        _view.addPlayerBackListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Doesn't do anything.
            }
        });
    }

    /**
     * Control for the forward button.
     */
    private void addForwardListener() {
        _view.addPlayerForwardListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Doesn't do anything.
            }
        });
    }
}
