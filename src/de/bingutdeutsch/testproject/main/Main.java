package de.bingutdeutsch.testproject.main;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.bingutdeutsch.testproject.system.ChestHandler;
import de.bingutdeutsch.testproject.system.ChestListener;
import de.bingutdeutsch.testproject.system.ItemHandler;

public class Main extends JavaPlugin {

	public static final String PREFIX = "§8» §9§lInfiniteSG §8┃ §7";
	private static Main instance;
	private HashMap<Chest, Inventory> chestlist = new HashMap<Chest, Inventory>();

	@Override
	public void onEnable() {
		setInstance(this);
		ChestHandler.startChestRefill();
		FileConfiguration config = Main.getInstance().getConfig();
		String user = config.getString("dbUser");
		String pass = config.getString("dbPassword");
		String host = config.getString("dbHost");
		String port = config.getString("dbPort");
		String dbname = config.getString("dbName");
		String url = String.format("jdbc:mysql://%s:%s/%s", host, port, dbname);
		ItemHandler.connect(url, user, pass);
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new ChestListener(), this);
	}

	public static Main getInstance() {
		return instance;
	}

	public static void setInstance(Main instance) {
		Main.instance = instance;
	}

	public HashMap<Chest, Inventory> getChestlist() {
		return chestlist;
	}

	public void setChestlist(HashMap<Chest, Inventory> chestlist) {
		this.chestlist = chestlist;
	}

}
