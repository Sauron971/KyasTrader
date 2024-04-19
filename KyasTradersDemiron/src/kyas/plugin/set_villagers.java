package kyas.plugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class set_villagers {
	
	@EventHandler
	public void onInteract(Action action, ItemStack item, PlayerInteractEvent event) {
		Player who = event.getPlayer();
		ItemStack mainHand = who.getInventory().getItemInMainHand();
		who.sendMessage("Ты кликнул");
		if(mainHand.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Сохранение металурга"))
			if(action.equals(Action.RIGHT_CLICK_AIR)) {
				event.setCancelled(true);
				who.sendMessage("Ты кликнул по воздуху");
			}
			if(action.equals(Action.RIGHT_CLICK_BLOCK)) {
				event.setCancelled(true);
				who.sendMessage("Ты кликнул по блоку");
			}
	}
	
}
