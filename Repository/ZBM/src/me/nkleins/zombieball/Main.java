package me.nkleins.zombieball;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	protected int voteCounter;
	protected boolean voteEnabled;
	public int activePlayers;
	public ItemStack snowball = new ItemStack(Material.SNOWBALL, 1);
	public ItemStack dirt = new ItemStack(Material.DIRT, 5);
	public ItemStack muchDirt = new ItemStack(Material.DIRT, 20);
	public HashMap<Player, ArrayList<Player>> hitList;
	public ArrayList<String> hiddenList = new ArrayList<String>();
	public ArrayList<String> hasVoted = new ArrayList<String>();

	public ItemStack getSnowball() {
		return snowball;
	}

	public void setSnowball(ItemStack snowball) {
		this.snowball = snowball;
	}

	public int getVoteCounter() {
		return voteCounter;
	}

	public void setVoteCounter(int voteCounter) {
		this.voteCounter = voteCounter;
	}

	public boolean isVoteEnabled() {
		return voteEnabled;
	}

	public void setVoteEnabled(boolean voteEnabled) {
		this.voteEnabled = voteEnabled;
	}

	public int getActivePlayers() {
		return activePlayers;
	}

	public void setActivePlayers(int activePlayers) {
		this.activePlayers = activePlayers;
	}

	public HashMap<Player, ArrayList<Player>> getHitList() {
		return hitList;
	}

	public void setHitList(HashMap<Player, ArrayList<Player>> hitList) {
		this.hitList = hitList;
	}

	@Override
	public void onEnable() {
		getServer().getPluginCommand("votestart").setExecutor(new VoteStart(this));
		getServer().getPluginCommand("snowball").setExecutor(new SnowballCmd(this));
		voteCounter = 0;
		voteEnabled = true;
		getServer().getPluginManager().registerEvents(new SnowballListener(this), this);
	}

	@Override
	public void onDisable() {

	}

}
