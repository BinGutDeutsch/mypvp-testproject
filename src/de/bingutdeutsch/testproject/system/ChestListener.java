package de.bingutdeutsch.testproject.system;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import de.bingutdeutsch.testproject.main.Main;

public class ChestListener implements Listener {

	@EventHandler
	public void onChestOpen(PlayerInteractEvent event) {
		Player p = event.getPlayer();

		if (event.getClickedBlock() == null) {
			return;
		}

		if (!event.getClickedBlock().getType().equals(Material.CHEST)) {
			return;
		}

		event.setCancelled(true);

		Chest clickedChest = (Chest) event.getClickedBlock().getState();
		Inventory openedChest = ChestHandler.getChestInventory(clickedChest);

		if (openedChest != null) {
			p.openInventory(openedChest);
			p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1f, 1f);
		} else {
			Inventory chestInventory = ChestHandler.createChestInventory();
			Main.getInstance().getChestlist().put(clickedChest, chestInventory);
			p.openInventory(ChestHandler.getChestInventory(clickedChest));
			p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1f, 1f);
		}

	}
}
