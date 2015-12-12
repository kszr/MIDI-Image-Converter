# MIDI-Image-Converter

This was my final project for CS 242 Fall 2013 at the University of Illinois at Urbana-Champaign. The project proposal might prove interesting reading for anyone looking for more information as to how this came about. 

Expansions of various kinds are currently underway.

##What it does (or claims to do)

One of the (ambitious) claims this project makes is to load image (png) files and convert them to music (midi), and vice versa.

##How well it does what it's supposed to do

Reasonably well, in my opinion, but for a smaller input set than is desirable. In particular, converting midis to images has proven a bit more onerous than anyone would like, owing to the sheer uncertainty of what you can expect from a midi file (number of tracks, number of extraneous tracks, etc.). At present this functionality is limited to files with three tracks, each of which is composed solely of semiquavers/sixteenth notes. (I forgot to check whether it accepts rests...)

On the other hand, converting images to midi seems to work just fine, if only because it's easy to specify in advance that you want a certain number of tracks in your midi file. The conversion itself is not particularly sophisticated at present, but it shouldn't be too difficult to solicit the help of people with more imagination than I have.

At present I'm working on refactoring the project a bit to make it more amenable to the expansions that I have in mind, which include:
* Overcoming the abovementioned limitations of midi files.
* Generating more sophisticated and/or interesting music from images (e.g., using other aspects of the image than just R, G, B values to create music).
* Possibly extending the list of acceptable file formats (e.g., exporting midi as mp3, etc.).
* Giving the user more flexibility in terms of modifying midi files (changing instruments, key, speed, etc.).

In addition to all this, I have found myself pondering deeply the profound philosophical question of why anyone would find a project like this remotely useful. As a novelty, perhaps; but that wears off in... five minutes, last I checked. Would it be more interesting to make a simple music player that generated interesting (if trippy) visualizations as the music played?