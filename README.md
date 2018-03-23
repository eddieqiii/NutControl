NutControl
=========
An Android app to control bluetooth serial devices.
# What does it do??
Right now, this app connects to a bluetooth serial device (previously paired) and sends ASCII-encoded integers from 0-65535 from a SeekBar, followed by a semicolon (no newline):
```
(int);
```
# Why?
All of the other Bluetooth serial control apps available on the Play Store seemed shifty, and I didn't want that kind of software on my phone. Additionally, my use case was very specific, and I decided making an app wouldn't be too hard.
# This code is terrible
I learned Java and Android app development as I went along. At least I can boast that 0% of this code was copied from Stack Overflow - forgive the spaghetti
# Will you update this??
Maybe
# How do I run it?
This repo contains the entire Android Studio project - just open it up and run it (min Android 6.0)
