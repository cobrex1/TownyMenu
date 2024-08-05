package me.cobrex.townymenu.join;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import me.cobrex.townymenu.utils.HeadDatabaseUtil;
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
import org.mineacademy.fo.menu.button.ButtonReturnBack;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JoinTownMenu extends Menu {

	private final Button openTownButton;
	private final Button createTownButton;

	private final static ItemStack DUMMY_BUTTON = ItemCreator.of(CompMaterial.fromString(String.valueOf(Settings.FILLER_JOIN_TOWN_MENU)), "")
			.modelData(Integer.valueOf(Settings.FILLER_JOIN_TOWN_MENU_CMD)).make();

	public JoinTownMenu(Resident resident, Player player) {

		ItemStack headItem = HeadDatabaseUtil.HeadDataUtil.createItem("hdb-10230");
		ItemStack materialItem = HeadDatabaseUtil.HeadDataUtil.createItem("SUNFLOWER");

		if (Settings.USE_FIXED_INVENTORY_SIZE) {
			setSize(9 * 6);
		} else {
			setSize(9);
		}

		setTitle(Localization.JoinCreateMenu.MAIN_MENU_TITLE);

		List<Town> towns = new ArrayList<>(TownyUniverse.getInstance().getTowns()).stream().filter(t -> t.isOpen()).collect(Collectors.toList());

		if (Material.getMaterial(Settings.FIND_TOWN).equals(Material.LEATHER_BOOTS)) {
			openTownButton = new ButtonMenu(new OpenTownMenu(towns), ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.FIND_TOWN)))
					.name(Localization.JoinCreateMenu.FIND_OPEN_TOWN)
					.color(Settings.NEUTRAL_BUTTON_COLOR)
					.modelData(Settings.FIND_TOWN_CMD));
		} else {
			openTownButton = new ButtonMenu(new OpenTownMenu(towns), ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.FIND_TOWN)))
					.name(Localization.JoinCreateMenu.FIND_OPEN_TOWN)
					.modelData(Settings.FIND_TOWN_CMD));
		}

		if (Material.getMaterial(Settings.CREATE_TOWN).equals(Material.LEATHER_BOOTS)) {
			createTownButton = new ButtonConversation(new CreateTownPrompt(player), ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.CREATE_TOWN)))
					.name(Localization.JoinCreateMenu.CLICK_CREATE_TOWN)
					.color(Settings.NEUTRAL_BUTTON_COLOR)
					.modelData(Settings.CREATE_TOWN_CMD));
		} else {
			createTownButton = new ButtonConversation(new CreateTownPrompt(player), ItemCreator.of(HeadDatabaseUtil.HeadDataUtil.createItem(String.valueOf(Settings.CREATE_TOWN)))
					.name(Localization.JoinCreateMenu.CLICK_CREATE_TOWN)
					.modelData(Settings.CREATE_TOWN_CMD));
		}

	}

	private void add(Town t) {
	}

	public class OpenTownMenu extends MenuPagged<Town> {

		protected OpenTownMenu(Iterable<Town> pages) {
			super(JoinTownMenu.this, pages);

			setTitle(Localization.JoinCreateMenu.JOIN_OPEN_TOWN);

			if (Settings.USE_FIXED_INVENTORY_SIZE) {
				setSize(9 * 6);
			}

			if (Settings.BACK_BUTTON.getMaterial().equals(Material.LEATHER_BOOTS)) {
				ButtonReturnBack.setItemStack(ItemCreator
						.of(Settings.BACK_BUTTON)
						.name(Localization.Back_Button.BACK_BUTTON_TITLE)
						.lore((List<String>) Localization.Back_Button.BACK_BUTTON_LORE)
						.color(Settings.NEUTRAL_BUTTON_COLOR)
						.modelData(Settings.BACK_BUTTON_CMD)
						.make());
			} else {
				ButtonReturnBack.setItemStack(ItemCreator
						.of(Settings.BACK_BUTTON)
						.name(Localization.Back_Button.BACK_BUTTON_TITLE)
						.lore((List<String>) Localization.Back_Button.BACK_BUTTON_LORE)
						.modelData(Settings.BACK_BUTTON_CMD)
						.make());
			}
		}

		@Override
		protected ItemStack convertToItemStack(Town item) {
			ItemStack itemSkull = new ItemStack(Material.PLAYER_HEAD, 1);
			SkullMeta skull = (SkullMeta) itemSkull.getItemMeta();
			skull.setDisplayName(ChatColor.translateAlternateColorCodes('&', Localization.JoinCreateMenu.TOWN_NAME + (item.getName())));
//			skull.setDisplayName(ChatColor.YELLOW + "" + (item.getName()));
			Player player = Bukkit.getPlayer(item.getMayor().getUUID());
			skull.setOwningPlayer(player);
			skull.setCustomModelData(Settings.RESIDENT_LIST_PLAYER_HEAD_CMD);
			List<String> lore = new ArrayList<>();
			lore.add("");
			lore.add(ChatColor.translateAlternateColorCodes('&', Localization.JoinCreateMenu.MAYOR + (item.getMayor())));
//			lore.add(ChatColor.WHITE + Localization.JoinCreateMenu.MAYOR + (item.getMayor()));
			lore.add("");
			lore.add(ChatColor.translateAlternateColorCodes('&', Localization.JoinCreateMenu.NUMBER_RESIDENTS + (item.getNumResidents())));
//			lore.add(ChatColor.WHITE + Localization.JoinCreateMenu.NUMBER_RESIDENTS + (item.getNumResidents()));
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
		if (!Settings.USE_FIXED_INVENTORY_SIZE) {
			if (slot == 2)
				return openTownButton.getItem();

			if (slot == 5)
				return createTownButton.getItem();
		} else {
			if (slot == 20)
				return openTownButton.getItem();

			if (slot == 24)
				return createTownButton.getItem();
		}


		return DUMMY_BUTTON;
	}
}

