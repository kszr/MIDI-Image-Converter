package controller;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;

import player.MIDIPlayer;
import view.MIDIPlayerView;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 11/22/13
 * Time: 11:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class MIDIPlayerController {
    private MIDIPlayerView view;
    private MIDIPlayer model;

    /**
     * Instantiates the controller with a model and view.
     * @param view
     * @param model
     */
    public MIDIPlayerController(MIDIPlayerView view, MIDIPlayer model) {
        this.view = view;
        this.model = model;

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
        addInstrumentListener();
    }

    /**
     * Control for the open button.
     */
    private void addFileOpenListener() {
        view.addFileOpenListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                	view.openFileChooser();
                	File file = view.getSelectedFile();
                	
                	//Return if the user canceled out of the window.
                	if(file == null)
                		return;
                	
                	String filename = file.getName();
                	model.open(view.getSelectedFile());
                    view.setStatusFieldText("Opening " + filename + "...");
                    model.stop();
                    view.setStatusFieldText("Opened " + filename);
                }
                catch(Exception exception) {
                	view.setStatusFieldText("Failed to open file");
                    view.displayMessageBox(exception.toString(), JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * Control for the save button.
     */
    private void addFileSaveListener() {
        view.addPlayerFileSaveListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                	File existingFile = view.getSelectedFile();
                	view.openFileSaver();
                	File newFile = view.getSelectedFile();
                	
                	//Return if the user canceled out of the window.
                	if(newFile == null)
                		return;
                	
                	String filename = newFile.getName();
                	if(existingFile == null ||
                			!existingFile.exists())
                		throw new Exception("No file loaded!");
                    view.setStatusFieldText("Saving " + filename);
                    model.save(newFile.getAbsolutePath());
                    view.setStatusFieldText("Saved " + filename);
                }
                catch(Exception exception) {
                	view.setStatusFieldText("Failed to save file");
                    view.displayMessageBox(exception.toString(), JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * Control for the PNGMusic button.
     */
    private void addPNGMusicListener() {
        view.addPlayerPNGMusicListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Image image = model.convertSequenceToPNG();
                    view.setStatusFieldText("Converting to image...");
                    view.displayImage(image);
                    view.setStatusFieldText("Converted to image");
                } catch(NullPointerException npe) {
                	view.setStatusFieldText("Failed to convert to image");
                	view.displayMessageBox(npe.toString(), JOptionPane.ERROR_MESSAGE);
                } catch(Exception exception) {
                	exception.printStackTrace();
                }
            }
        });
    }

    /**
     * Control for the play button.
     */
    private void addPlayListener() {
        view.addPlayerPlayListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    model.resume();
                    view.setStatusFieldText("Playing");
                }
                catch(Exception exception) {
                	//Generally this means the sequencer is closed, so try play() instead.
                    try {
                        model.play();
                        view.setStatusFieldText("Playing");
                    }
                    catch(Exception exception2) {
                        view.displayMessageBox(exception2.toString(), JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    /**
     * Control for the pause button.
     */
    private void addPauseListener() {
        view.addPlayerPauseListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    model.pause();
                    view.setStatusFieldText("Paused");
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
        view.addPlayerStopListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    model.stop();
                    view.setStatusFieldText("Stopped");
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
        view.addPlayerBackListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    model.setTickPosition(0);
                    view.setStatusFieldText("Back to start");
                } catch (Exception exception) {
                    //Do nothing.
                }
            }
        });
    }

    /**
     * Control for the forward button.
     */
    private void addForwardListener() {
        view.addPlayerForwardListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    model.setTickPosition(Long.MAX_VALUE);
                    view.setStatusFieldText("Reached end");
                } catch (Exception exception) {
                    //Do nothing.
                }
            }
        });
    }
    
    /**
     * Control for the "change instrument" button.
     */
    private void addInstrumentListener() {
    	view.addInstrumentListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					view.displayInstrumentBank(model.getInstrumentList());
					String instrument = view.getInstrument();
					
					//Return if the user canceled out of the window.
					if(instrument == null)
						return;
					model.changeInstrument(instrument);
					view.setStatusFieldText("Changed instrument to " + instrument);
				} catch(Exception exception) {
					view.setStatusFieldText("Failed to change instrument");
					view.displayMessageBox(exception.toString(), JOptionPane.ERROR_MESSAGE);
				}
			}
    		
    	});
    }
}
