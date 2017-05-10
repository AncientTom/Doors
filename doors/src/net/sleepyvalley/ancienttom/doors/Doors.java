/* Doors.java */
package net.sleepyvalley.ancienttom.doors;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.mewin.WGRegionEvents.events.RegionEnterEvent;
import com.mewin.WGRegionEvents.events.RegionLeaveEvent;
import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

/**
 * @author AncientTom
 * Contact info: AncientTom@MC-Hosts.com
 *               Skype: AncientTom
 *               
 *               Version 1.0 beta
 */
public class Doors extends JavaPlugin implements Listener {
	
	private HashMap<ProtectedRegion, Integer> regionsList; // this hashmap stores any regions for which the event has been fired along with their entries integer.
	
	@Override
	public void onEnable() {
	    regionsList = new HashMap<>();
		getServer().getPluginManager().registerEvents(this, this);
        getWorldGuard();
	}

    private WorldGuardPlugin getWorldGuard() {
		Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
			try {
				throw new Exception();
			} catch (Exception e) {
				System.out.println("[Doors.getWorldGuard()] WorldGuard did not load!");
				e.printStackTrace();
			}
			return null;
        }
        return (WorldGuardPlugin) plugin;
    }
    
	@Override
	public void onDisable() {
	}

	@EventHandler
	public void onRegionEnter(RegionEnterEvent event) {
	    ProtectedRegion region = event.getRegion(); // is that the method??
		Player player = event.getPlayer();	// Make an instance of the player object that triggered the event
											// for instantiating the region that was triggered ph yhe player.
	    String regionID = event.getRegion().getId();
		if(!regionID.endsWith("door")) {
			return;
		}
		
	    if (!regionsList.containsKey(region)) { // the event hasn't been called for this event before
	        regionsList.put(region, 1); // put the region in the map and set the integer to 1
	    } else { // it has been called for this region before
	        regionsList.put(region, regionsList.get(region) + 1); // increase the integer
	    }

		manipulateDoor(regionID, player, true);

	}

	@EventHandler
	public void onRegionLeave(RegionLeaveEvent event) {
	    ProtectedRegion region = event.getRegion();
		Player player = event.getPlayer();	// Make an instance of the player object that triggered the event

		String regionID = event.getRegion().getId();
		if(!regionID.endsWith("door")) {
			return;
		}
		
	    if (!regionsList.containsKey(region)) { // the event hasn't been called for this event before
	    	return;		// exit handler because this region counter doesn't exist.
	    }
		regionID = event.getRegion().getId();
	    if (regionsList.get(region) > 0) {
	    	regionsList.put(region, regionsList.get(region) - 1);
    	} else {
	    	regionsList.put(region, 0);
	    }
	    if (regionsList.get(region) == 0) {
		  	manipulateDoor(regionID, player, false);
	    }
 	}
	
	private void manipulateDoor(String regionID, Player player, boolean doorFlag) {
		RegionContainer container = getWorldGuard().getRegionContainer();	//instantiate the world the player is in.
        RegionManager regions = container.get(player.getWorld());	//instantiate region triggered by player.
        int minX = (int) regions.getRegion(regionID).getMinimumPoint().getX();
        int level = (int) regions.getRegion(regionID).getMinimumPoint().getY();
        int maxX = (int) regions.getRegion(regionID).getMaximumPoint().getX();
        int minZ = (int) regions.getRegion(regionID).getMinimumPoint().getZ();                                          
        int maxZ = (int) regions.getRegion(regionID).getMaximumPoint().getZ();

        DoorOpen open = new DoorOpen();
		if(!open.doorOpen(player,minX,minZ,maxX,maxZ,level,doorFlag)) {
			System.out.println("[openDoor] Something went wrong with opening a door!");
		}

	}

}
