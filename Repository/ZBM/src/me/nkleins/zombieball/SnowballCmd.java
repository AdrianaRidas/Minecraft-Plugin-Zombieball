package me.nkleins.zombieball;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SnowballCmd implements CommandExecutor {
	private Main main;

	public SnowballCmd(Main instance) {
		main = instance;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
		if (label.equalsIgnoreCase("snowball")) {
			if (sender instanceof Player) {
					if (!main.voteEnabled && !main.hiddenList.contains(((Player) sender).getDisplayName())) {
						Player player = (Player) sender;
						player.setCooldown(Material.SNOWBALL, 300);
						player.getInventory().addItem(main.getSnowball());
						return true;
					} else {
						sender.sendMessage("You don't need a snowball right now");
					
				}
			}
		}
		return false;
	}
}
