package de.bingutdeutsch.testproject.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemHandler {

	private final static Logger LOGGER = Logger.getLogger("Logger");
	private static Connection conn;

	public static ItemStack[] getItems(int numberOfItems) {
		LOGGER.setLevel(Level.ALL);

		ItemStack[] items = new ItemStack[numberOfItems];
		try {
			for (int i = 0; i < items.length; i++) {
				Random rn = new Random();
				int chance = rn.nextInt(100) + 1;

				PreparedStatement ps = ItemHandler.conn.prepareStatement(
						"SELECT material, max_amount FROM loot_table WHERE chance>=? ORDER BY RAND() LIMIT 1");
				ps.setInt(1, chance);
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					int max_amount = rs.getInt("max_amount");
					int amount = rn.nextInt(max_amount)+1;
					String material = rs.getString("material");
					Material m = Material.matchMaterial(material);
					ItemStack item = new ItemStack(m, amount);
					items[i] = item;
				}
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Statement Exception:", e);
		}
		return items;
	}

	public static void connect(String url, String user, String pass) {
		try {
			ItemHandler.conn = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Connecting Exception:", e);
		}
	}
}
