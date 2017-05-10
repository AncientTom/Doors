package net.sleepyvalley.ancienttom.doors;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.material.Door;

class DoorOpen {

	@SuppressWarnings("serial")
	class ErrOnCheck extends Exception {
		protected String errOnCheck() {
			return "Something went wrong with checking a block for a door!";
		}
	}
	@SuppressWarnings("serial")
	class ErrOnOpen extends Exception {
    	protected String errOnOpen() {
			return "Something went wrong with opening the dang door!";
    	}
	}
	
	protected boolean doorOpen(Player player, int startX, int startZ, int endX, int endZ, int level, boolean open) {
		World world = player.getWorld();
		int X = endX;
		while(startX <= X) {
			int Z = endZ;
			while(startZ <= Z) {
				if(checkForDoor(world,X,level,Z)) {
					openTheDangDoor(world,X,level,Z,open);
				}
				Z--;
			}
			X--;
		}
		return true;
	}
	
	private boolean checkForDoor(World world,int X, int Y, int Z) {
		// Check for a door at this position.
		Location location = new Location(world, X, Y, Z);
		if(location.getBlock().getType().name().contains("_DOOR")) {
			return true;
		}
		return false;
	}
	
    // Open doors
	private boolean openTheDangDoor(World world,int X,int Y,int Z, boolean open) {
		Location location = new Location(world, X, Y, Z);
		Block block = location.getBlock();
        BlockState state = block.getState();
        Door door = (Door) state.getData();
        door.setOpen(open);
        state.update();
        if(open) {
            world.playEffect(location,Effect.DOOR_TOGGLE,0);
        }else {
        	world.playEffect(location,Effect.DOOR_CLOSE,0);
        }
	    return true;
	}
	
}
	