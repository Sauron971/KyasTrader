package TraderKyas;

import net.minecraft.world.level.World;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import TraderKyas.config;

@SuppressWarnings("unused")
public final class Main extends JavaPlugin implements Listener {
	 public static final String PluginName = "TraderKyas";
	@Override
	public void onEnable() {
        /*config.init();*/
        date();
        getLogger().log(Level.INFO, ChatColor.GOLD + "Plugin KyasTrader enable!");
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
	}
	@Override
	public void onDisable() {
        getLogger().log(Level.INFO, ChatColor.GOLD + "Plugin KyasTrader enable!");
	}
	public void date() {
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
            public void run() {
        		SimpleDateFormat format = new SimpleDateFormat("hh.mm");
        		Date date = new Date();
        		String now_date = format.format(date);
        		float fdate = Float.parseFloat(now_date);
        		float ffrom = Float.parseFloat(getConfig().getString("time_from"));
        		float fto = Float.parseFloat(getConfig().getString("time_to"));
        		LocalDate now = LocalDate.now();
        		DayOfWeek day = now.getDayOfWeek();
        		String sday = day.name();
        		
        		double x, y, z;
        		x = getConfig().getInt("location.x");
    			y = getConfig().getInt("location.y");
    			z = getConfig().getInt("location.z");
    			Location loc = new Location(Bukkit.getWorld("world"), x,y,z);
        		
        		//проверяем время если нужное то спавним жителя
        		if(checkTrader(loc) == false) {
	        		if (sday.equals(getConfig().getString("DayOfWeek"))) {
	        		    if (ffrom <= fdate && fdate <= fto) {       
	        		    	spawnVillager();
	        		    }
	        		}
        		}
        		if(checkTrader(loc) == true) {
        		//удаляем жителя
        			if (sday.equals(getConfig().getString("DayOfWeek"))) {
        				if (fdate > fto) {
							for(Entity entity : loc.getWorld().getNearbyEntities(loc, 2.0D, 2.0D, 2.0D)) {
			        			if(entity.getCustomName().equals(ChatColor.GOLD + "" + ChatColor.BOLD + "Торговец репой")) {
			        				entity.remove();
			        				break;
			        			}
							}
        				}
        			}
        		}
            }
        }, 0L, 200L);
	}
	@EventHandler
    private void onSendMessage(final AsyncPlayerChatEvent e) {
        if (e.getPlayer().isOp() && e.getMessage().equals("***spawnTrader")) {
            e.setCancelled(true);
            new BukkitRunnable() {
                @Override
                public void run() {
                	spawnVillager();
                }        
            }.runTask(this);
            e.getPlayer().sendMessage("Trader spawn");
        }
    }
	public void spawnVillager() {
    	String wrld = getConfig().getString("world");
    	//коорднинаты жителя
    	double x, y, z;
    	x = getConfig().getInt("location.x");
    	y = getConfig().getInt("location.y");
    	z = getConfig().getInt("location.z");
    	Location loc = new Location(Bukkit.getWorld(wrld), x,y,z);
    	// спавн жителя с определенными торгами 
    	Entity villager = loc.getWorld().spawnEntity(loc, EntityType.WANDERING_TRADER);
    	//задаем имя и атрибуты жителю
    	villager.setCustomName(ChatColor.GOLD + "" + ChatColor.BOLD + "Торговец репой");
    	villager.setCustomNameVisible(true);
    	((Attributable) villager).getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.0D);
    	((Attributable) villager).getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(100.0D);
    	((LivingEntity) villager).setCollidable(false);
    	((LivingEntity) villager).setInvulnerable(true);
    	((LivingEntity) villager).setAI(false);
    	villager.addScoreboardTag("traderKyas");
    	//задем торги
    	//деньги для покупки
    	Random rand = new Random(); 
    	int b = rand.nextInt((45 - 10) + 1) + 10;
    	ItemStack moneyb = new ItemStack(Material.QUARTZ, b);
    	//деньги для продажи
    	int s = rand.nextInt((8 - 2) + 1) + 2;
    	ItemStack moneys = new ItemStack(Material.QUARTZ, b-s);
    	//репка
    	ItemStack turnip = new ItemStack(Material.BEETROOT, 1);
    	ItemMeta metaTurnip = turnip.getItemMeta();
    	metaTurnip.setCustomModelData(102);
    	metaTurnip.setDisplayName(ChatColor.RESET + "Репа");
    	turnip.setItemMeta(metaTurnip);
    	//торги
    	MerchantRecipe buy = new MerchantRecipe(turnip, 100);
    	buy.addIngredient(moneyb);
    	MerchantRecipe sold = new MerchantRecipe(moneys, 100);
    	sold.addIngredient(turnip);
        List<MerchantRecipe> recipes = new ArrayList<>();
       
		recipes.add(buy);
		recipes.add(sold);
		((Merchant) villager).setRecipes(recipes);
		
		sendMessageToAllPlayers(ChatColor.YELLOW + "" + ChatColor.BOLD + "Торговец репой " + ChatColor.GREEN + "прибыл в начальный город!");
	}
	private boolean checkTrader(Location loc) {
		boolean b = false;
		for(Entity entity : loc.getWorld().getNearbyEntities(loc, 2.0D, 2.0D, 2.0D)) {
			if (entity.getCustomName().equals(ChatColor.GOLD + "" + ChatColor.BOLD + "Торговец репой")) { 
				b = true;
				break;
			}else {
				b = false;
				
			}
		}
		return b;
	}
	private void sendMessageToAllPlayers(final String message) {
		   for (Player pl : Bukkit.getOnlinePlayers()) {
		       pl.sendMessage(message);
		   }
		   return;
		}
}