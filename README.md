# Doors
This is my first plugin. It will open all wooden and iron doors within a doorway threshold for the first player that enters the threshold.
When the last player leaves the threshold, this oplugin will shut all doors that are within the threshold.

After you place your door(s), create a threshold;
  Stand on the first corner block of the threashold you are creating.
  It is suggested to set this block one block away from the first door in tour doorway.
  use //pos1 to mark this corner of the threshold.
  Move to the block that will be the opposit corner of your threshold.
  This would be a block away on the other side of the last door.
  use //pos2 to mark the opposite corner of the threshold.
  Then, use the command, "/rg create <nameofdoorway>door".
  This will create the threshold and the door(s) should now be working.
  When creating the threshold, you must use a unique name for each doorway with "door" appended to the door's name.
  This plugin only responds to region events that have the word "door" in their name. 
  Remember that this plugin will open any doors within any region that contains "door" within it's name.
