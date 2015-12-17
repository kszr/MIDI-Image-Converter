# MIDI-Image-Converter

This was my final project for CS 242 Fall 2013 at the University of Illinois at Urbana-Champaign. The project proposal might prove interesting reading for anyone looking for more information as to how this came about.

The project is built on the model-view-controller pattern, and uses Swing for the GUI and ```javax.sound.midi``` for everything midi-related.

Expansions of various kinds are currently underway.

##Running the application
The main method is located in ```src/application/Application.java```.

##Current status
MIDI to Image and Image to MIDI conversion have both been implemented. Some comments below.

###Image to MIDI conversion
This was relatively easy to do, because it's easy to specify in advance that you want a certain number of tracks of a certain length.

###MIDI to Image conversion
This was harder, because it is difficult to predict what you will get in a MIDI file as far as number of tracks and their contents are concerned. The present implementation picks the three longest tracks, and uses them independently to set R, G, and B values in the resulting image value according to a certain mapping function.

Classes that define color-note and note-color mappings are implementations of an interface that has been provided, and it possible (with a little imagination, I suppose) to come up with arbitrarily complex mappings of this sort. Whether the resulting music and/or images are aesthetically pleasing is a different matter altogether. It might be desirable to implement symmetric mappings, so that an image that is converted to music which is then converted back into an image yields the same image as the original, but this is not mandatory.
