package me.nkleins.zombieball;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class VoteStart implements CommandExecutor {
	private Main main;

	public VoteStart(Main instance) {
		main = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
		if (label.equalsIgnoreCase("votestart")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;			
				int onlinePlayers = main.getServer().getOnlinePlayers().toArray().length;
				// if(onlinePlayers==1) {
				// Bukkit.broadcastMessage("You can't start a game by yourself! If you want to
				// take a look at the Arenas, try /warp");
				// return true;
				// }
				if (main.voteEnabled && !main.hasVoted.contains(player.getDisplayName())) {
					main.hasVoted.add(player.getDisplayName());
					main.voteCounter++;
					if (main.voteCounter < onlinePlayers) {
						int missingVotes = onlinePlayers - main.voteCounter;
						if (missingVotes == 1) {
							Bukkit.broadcastMessage(
									player.getName() + " has voted for the Game to start, 1 Player left!");
						} else {
							Bukkit.broadcastMessage(player.getName() + " has voted for the Game to start, "
									+ missingVotes + " Players left!");
						}
					}
					if (main.voteCounter == onlinePlayers) {
						Bukkit.broadcastMessage("Vote succesfull - Game is starting!");
						main.setActivePlayers(onlinePlayers);
						main.voteEnabled = false;
						main.voteCounter = 0;
						startGame(player.getWorld());
						return true;
					}

				} else {
					sender.sendMessage("A Game is already in Progress or you voted already");
				}
			} else {
				sender.sendMessage("Console Operator can't join vote!");
				return true;

			}
		}
		return false;

	}

	public void startGame(World welt) {
		fillList();
		Random rand = new Random();
		Location arena;
		switch (rand.nextInt(11) + 1) {
		case 1:
			arena = new Location(welt, 229, 5, 443);
			Bukkit.broadcastMessage("Act smart in the academy.");
			break;
		case 2:
			arena = new Location(welt, 389.123, 5, -1125.276);
			Bukkit.broadcastMessage("You are playing in the castle, my lord.");
			break;
		case 3:
			arena = new Location(welt, 132.85, 8, 773.69);
			Bukkit.broadcastMessage("Put your boots on, you are going in the canyon.");
			break;
		case 4:
			arena = new Location(welt, 364.262, 14, -177.785);
			Bukkit.broadcastMessage("You are playing in greece. Yamaz!");
			break;
		case 5:
			arena = new Location(welt, 215.70, 15, -34.71);
			Bukkit.broadcastMessage("You are playing in the mushroom forest. Tod is with you.");
			break;
		case 6:
			arena = new Location(welt, 923.60, 10, -2118.89);
			Bukkit.broadcastMessage("you gonna play with the penguins. It's cold there!");
			break;
		case 7:
			arena = new Location(welt, 316.17, 16, 727.01);
			Bukkit.broadcastMessage("Arr! Go and play with pirates around you!");
			break;
		case 8:
			arena = new Location(welt, 91.37, 7, -505.80);
			Bukkit.broadcastMessage("In the ruin it's scary. You go there to play!");
			break;
		case 9:
			arena = new Location(welt, 602.60, 51, 623.52);
			Bukkit.broadcastMessage("Hold your breath. You are going under water!");
		case 10:
			arena = new Location(welt, 693.35, 14, -1495.01);
			Bukkit.broadcastMessage("You are going to play in the forest. Search for the bread crumbs!");
			break;
		case 11:
			arena = new Location(welt, 794.51, 26, 311.37);
			Bukkit.broadcastMessage("Oh look! You are going in space!");
			break;
		default:
			arena = new Location(welt, 247.974, 5, -1846.340);
			Bukkit.broadcastMessage("There you go stupid. Try it again.");
		}

		for (Player player : main.getServer().getOnlinePlayers()) {
			player.setGameMode(GameMode.SURVIVAL);
			player.teleport(arena);
			player.getInventory().clear();
			player.setCooldown(Material.SNOWBALL, 300);
			player.getInventory().addItem(main.snowball);
			player.getInventory().addItem(main.dirt);

		}
	}

	public void fillList() {
		HashMap<Player, ArrayList<Player>> players = new HashMap<Player, ArrayList<Player>>();
		for (Player p : main.getServer().getOnlinePlayers()) {
			ArrayList<Player> liste = new ArrayList<Player>();
			players.put(p, liste);
		}
		main.setHitList(players);
	}

}
