package kyas.plugin;

import java.util.List;
import java.util.logging.Level;
import kyas.plugin.set_villagers;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin implements Listener {
	 public static final String PluginName = "TraderKyas";
	@Override
	public void onEnable() {
       getLogger().log(Level.INFO, ChatColor.GOLD + "Plugin TradersDemiron enable!");
       saveDefaultConfig();
       getServer().getPluginManager().registerEvents(this, this);
       commands();
	}
	@Override
	public void onDisable() {
       getLogger().log(Level.INFO, ChatColor.GOLD + "Plugin TradersDemiron disable!");
	}
	public void commands() {
		getServer().getPluginCommand("trader").setExecutor(new command());
	}
	@EventHandler
	private void onClickEntity(PlayerInteractEntityEvent e) {
		Player who = e.getPlayer();
		ItemStack mainHand = who.getInventory().getItemInMainHand();
		Entity entity = e.getRightClicked();
		if (entity instanceof Villager) {
			if(mainHand.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Сохранение металурга")) {
				e.setCancelled(true);
				who.sendMessage("Ты кликнул по жителю");
				//Entity villager = e.getClickedEntity();
				List<MerchantRecipe> recipes = ((Merchant) entity).getRecipes();
				this.getConfig().set("metallurg", recipes);
				this.saveConfig();
			}
		}
		//who.sendMessage(mainHand.getItemMeta().getDisplayName().toString());
	}
}