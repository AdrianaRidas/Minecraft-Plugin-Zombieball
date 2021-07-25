package me.nkleins.zombieball;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class SnowballListener implements Listener {
	private Main main;

	public SnowballListener(Main instance) {
		main = instance;
	}

	@EventHandler
	public boolean onHit(ProjectileHitEvent event) {
		if (event.getEntity() instanceof Snowball) {
			Player werfer = (Player) event.getEntity().getShooter();
			werfer.setCooldown(Material.SNOWBALL, 200);
			werfer.getInventory().addItem(main.snowball);
			if (event.getHitEntity() instanceof Player) {
				Player hit = (Player) event.getHitEntity();
				if (!main.hiddenList.contains(werfer.getDisplayName())) {
					werfer.setCooldown(Material.SNOWBALL, 50);
					werfer.getInventory().addItem(main.dirt);
					main.hitList.get(werfer).add(hit);
					main.hiddenList.add(hit.getDisplayName());
					hit.getInventory().clear();
					hit.getInventory().addItem(main.muchDirt);
					hit.sendMessage("You got hit and are a ghost now, have some dirt to grieve the living");
					hidePlayers(hit);
					rejoinGame(hit);
					if (main.hitList.get(werfer).size() == 1) {
						Bukkit.broadcastMessage("Player " + werfer.getDisplayName() + " hit 1 Player so far!");
					} else {
						Bukkit.broadcastMessage("Player " + werfer.getDisplayName() + " hit "
								+ main.hitList.get(werfer).size() + " Players so far!");
					}
					if (main.hitList.get(werfer).size() == main.activePlayers - 1) {
						Bukkit.broadcastMessage(
								werfer.getDisplayName() + " has won this game of Zombieball! Congratulations");
						finishGame(werfer);
					}
				} else {
					werfer.getInventory().clear();
					werfer.getInventory().addItem(main.muchDirt);
					werfer.sendMessage("You were not supposed to have that, here have some dirt instead!");
					
				}

				return true;

			}
			return true;
		}
		return false;
	}

	public void hidePlayers(Player hit) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (hit != p) {
				p.hidePlayer(main, hit);
			}
		}
	}

	public void rejoinGame(Player hit) {
		ArrayList<Player> rejoin = main.hitList.get(hit);
		for (Player p : Bukkit.getOnlinePlayers()) {
			for (Player p2 : rejoin) {
				p.showPlayer(main, p2);
			}

		}
		for (Player p2 : rejoin) {
			p2.getInventory().clear();
			p2.setCooldown(Material.SNOWBALL, 150);
			p2.getInventory().addItem(main.snowball);
			p2.getInventory().addItem(main.dirt);
			p2.sendMessage("You are now back in the Game! Good Luck!");
		}
		main.hitList.get(hit).clear();

	}

	public void finishGame(Player winner) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			for (Player p : main.hitList.get(winner)) {
				player.showPlayer(main, p);
			}
			player.teleport(new Location(player.getWorld(), 247.974, 5, -1846.340));
			player.getInventory().clear();
		}
		main.setVoteEnabled(true);
		main.hasVoted.clear();
		main.hiddenList.clear();
	}

}
