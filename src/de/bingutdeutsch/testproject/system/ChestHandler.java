package de.bingutdeutsch.testproject.system;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.bingutdeutsch.testproject.main.Main;

public class ChestHandler {

	private final static Main MAIN = Main.getInstance();
	private final static int MAX_ITEMS = MAIN.getConfig().getInt("max_items");
	private final static int MIN_ITEMS = MAIN.getConfig().getInt("min_items");
	private final static int REFILL_TIME = MAIN.getConfig().getInt("refill_time");

	public static Inventory getChestInventory(Chest clickedChest) {
		if (MAIN.getChestlist().containsKey(clickedChest)) {
			return MAIN.getChestlist().get(clickedChest);
		}
		return null;
	}

	public static void resetChests() {
		MAIN.getChestlist().clear();
		Bukkit.broadcastMessage(Main.PREFIX + "Die Kisten wurden neu aufgefüllt.");
	}

	public static Inventory createChestInventory() {
		Random rn = new Random();
		int range = MAX_ITEMS - MIN_ITEMS + 1;
		int itemsInChest = rn.nextInt(range) + MIN_ITEMS;
		ItemStack[] items = ItemHandler.getItems(itemsInChest);
		Inventory inv = Bukkit.createInventory(null, 27, "§9InfiniteSG");
		for (int i = 0; i < items.length; i++) {
			inv.setItem(rn.nextInt(26) + 1, items[i]);
		}
		return inv;

	}

	public static void startChestRefill() {
		new BukkitRunnable() {
			@Override
			public void run() {
				ChestHandler.resetChests();
			}
		}.runTaskTimer(MAIN, 60 * 20 * REFILL_TIME, 60 * 20 * REFILL_TIME);
	}
}
