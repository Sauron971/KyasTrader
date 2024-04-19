package kyas.plugin;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class command implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		boolean b = false;
	    if(!(sender instanceof Player)) {
	        sender.sendMessage("Only players can run this command !");
	        b = false;
	    }
		
	    
	    Player p = (Player) sender;
	    Location loc = p.getLocation();
	    if(args.length == 0 || args[0].equalsIgnoreCase("metallurg")) {
	    	villager.spawnMetallurg(loc);
	    	b = true;
	    }
	    if(args.length == 0 || args[0].equalsIgnoreCase("metallurgStick")) {
	    	ItemStack item = new ItemStack(Material.BLAZE_ROD);
	    	ItemMeta metaitem = item.getItemMeta();
	    	metaitem.setDisplayName(ChatColor.GOLD + "Сохранение металурга");
	    	item.setItemMeta(metaitem);
	    	p.getInventory().addItem(item);
	    	b = true;
	    }
	    return b;
	}

}
