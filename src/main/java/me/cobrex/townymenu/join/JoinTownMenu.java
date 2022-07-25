package me.cobrex.townymenu.join;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.MenuPagged;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.button.ButtonConversation;
import org.mineacademy.fo.menu.button.ButtonMenu;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class JoinTownMenu extends Menu {


	private final Button openTownButton;
	private final Button createTownButton;

	private final static ItemStack DUMMY_BUTTON = ItemCreator.of(CompMaterial.GRAY_STAINED_GLASS_PANE, "").make();


	public JoinTownMenu(Resident resident, Player player) {

		setSize(9);
		setTitle("Join or Create a Town");

		List<Town> towns = new ArrayList<>(TownyUniverse.getInstance().getTowns()).stream().filter(t -> t.isOpen()).collect(Collectors.toList());

//		List<Town> towns = new ArrayList<>(TownyUniverse.getInstance().getTowns());
//		LagCatcher.start("load-open towns");
//		towns.stream().filter(Government::isOpen).forEach(this::add);

//		System.out.println(towns);

		openTownButton = new ButtonMenu(new OpenTownMenu(towns), CompMaterial.LEVER, "Find an open Town");

		createTownButton = new ButtonConversation(new CreateTownPrompt(player), ItemCreator.of(CompMaterial.PLAYER_HEAD, "Click to create a town"));

	}

	private void add(Town t) {
	}

	//@Override
	public class OpenTownMenu extends MenuPagged<Town> {

		protected OpenTownMenu(Iterable<Town> pages) {
			super(JoinTownMenu.this, pages);
			setTitle("&aJoin an Open Town");
		}

			@Override
			protected ItemStack convertToItemStack(Town item) {
				ItemStack itemSkull = new ItemStack(Material.PLAYER_HEAD, 1);
				SkullMeta skull = (SkullMeta) itemSkull.getItemMeta();
				skull.setDisplayName(ChatColor.YELLOW + "" + (item.getName()));
				Player player = Bukkit.getPlayer(item.getMayor().getUUID());
				skull.setOwningPlayer(player);
				List<String> lore = new ArrayList<>();
				lore.add("");
				lore.add(ChatColor.WHITE + "Mayor: " + (item.getMayor()));
				lore.add(ChatColor.WHITE + "Number of Residence: " + (item.getNumResidents()));
				skull.setLore(lore);
				itemSkull.setItemMeta(skull);
				return itemSkull;

			}

			@Override
			protected void onPageClick(Player player, Town item, ClickType click) {
				player.closeInventory();
				player.performCommand("t join " + item.getName());

			}

		}
	@Override
	public ItemStack getItemAt(int slot) {
		if (slot == 1)
			return openTownButton.getItem();

		if (slot == 3)
			return createTownButton.getItem();

		return null;
	}

}

