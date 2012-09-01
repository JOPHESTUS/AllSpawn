package us.jophest.allspawn;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class AllSpawn extends JavaPlugin {
	Logger log = Logger.getLogger("Minecraft");
	PluginDescriptionFile pdfFile;
	public static AllSpawn plugin;

	public void onEnable() {
		final File f = new File(getDataFolder(), "config.yml");
		if (!f.exists()){
			saveDefaultConfig();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		// TODO Auto-generated method stub

		if (command.getName().equalsIgnoreCase("allspawn")) {
			Player player = (Player) sender;
			if (sender.hasPermission("allspawn.s")) {
				if (args.length == 1){
					if (args[0].equalsIgnoreCase("set")){
						Location loc = player.getLocation();
						String location = (loc.getWorld().getName() + "|" + loc.getX() + "|" + loc.getY() + "|" + loc.getZ());
						this.getConfig().set("spawn_location", location);
						this.saveConfig();				
						player.sendMessage(ChatColor.GOLD + "Spawn point set!");
					}
				}
				if (args.length == 0){
					String location = this.getConfig().getString("spawn_location");
					String[] loc = location.split("\\|");
					World world = Bukkit.getWorld(loc[0]);
					Double x = Double.parseDouble(loc[1]);
					Double y = Double.parseDouble(loc[2]);
					Double z = Double.parseDouble(loc[3]);
					final Location finalloc = new Location(world, x, y, z);
				for (Player plr : Bukkit.getServer().getOnlinePlayers())
					if (!plr.hasPermission("allspawn.excluded")){
					plr.teleport(finalloc);
					}
				player.sendMessage(ChatColor.GOLD + "All players taken to spawn");
				}
				
				return true;
			}else {
				sender.sendMessage("You don't have permission to do that.");
			}
		}
				

		return super.onCommand(sender, command, label, args);

	}

	public void onDisable() {

		pdfFile = this.getDescription();
		this.log.info(pdfFile.getName() + " is now disabled!");
	}
}