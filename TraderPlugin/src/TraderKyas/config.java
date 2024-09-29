package TraderKyas;

import java.io.File;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

//import TraderKyas.Main;


public class config {
    private static File file;
    private static FileConfiguration config;

    private static final String fileNameConfig = "config.yml";
    
    @SuppressWarnings("null")
	public static void init() {
    	// Получаем экземпляр класса плагина
    	Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(Main.PluginName);
    	
    	if (plugin == null) {
    		//создаем логи если что-то не так
    		plugin.getLogger().log(
    				Level.WARNING,
    				MessageFormat.format("Cannot get plugin {0}", Main.PluginName)
    		);
    		return;	
    	}
    	file = new File(plugin.getDataFolder(), fileNameConfig);
    	plugin.getLogger().log(Level.SEVERE, file.toString());
    	
    	//Не знаем есть ли файл, пытаемся создать его
    	//если файл есть file.createNewFile вернет false
    	
    	try {
    		if (file.createNewFile()) {
    			plugin.getLogger().log(
    				Level.INFO,	
    				MessageFormat.format("Config file {0} was created", fileNameConfig)
    			);
    		}
    	} catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, e.toString());
            return;
        }
    	reload();
    }
    public static FileConfiguration get() {
    	return config;
    }
    
    public static void reload() {
    	config = YamlConfiguration.loadConfiguration(file);
    }
}
