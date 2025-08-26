package me.cobrex.townymenu.settings;

import me.cobrex.townymenu.config.CommentedConfiguration;
import me.cobrex.townymenu.config.ConfigNodes;
import me.cobrex.townymenu.config.ConfigUtil;
import me.cobrex.townymenu.utils.MenuItem;
import me.cobrex.townymenu.utils.MessageFormatter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.function.Consumer;

public class Settings {

	private static CommentedConfiguration config;

	public enum ColorMode {
		MINIMESSAGE,
		LEGACY
	}

	public static void init(JavaPlugin plugin) {
		config = ConfigUtil.getConfig();
		load();

		MONEY_SYMBOL = config.getString("money_symbol", "$");

		String colorModeStr = config.getString("color_mode", "LEGACY").toUpperCase();
		try {
			COLOR_MODE = ColorMode.valueOf(colorModeStr);
//			Bukkit.getLogger().info("Settings.COLOR_MODE = " + Settings.COLOR_MODE);
		} catch (IllegalArgumentException ex) {
			Bukkit.getLogger().warning("[TownyMenu] Invalid color_mode '" + colorModeStr + "', defaulting to LEGACY.");
			COLOR_MODE = ColorMode.LEGACY;
//			Bukkit.getLogger().info("Settings.COLOR_MODE = " + Settings.COLOR_MODE);
		}
	}

	public static CommentedConfiguration getConfig() {
		return config;
	}

	public static String PREFIX;

	public static ColorMode COLOR_MODE;

	public static String MONEY_SYMBOL;
	public static boolean ECONOMY_ENABLED;
	public static String LOCALE;

	public static MenuItem BACK_BUTTON;
	public static int BACK_BUTTON_CMD;

	public static MenuItem MENU_PREVIOUS_BUTTON;
	public static int MENU_PREVIOUS_BUTTON_CMD;
	public static MenuItem MENU_NEXT_BUTTON;
	public static int MENU_NEXT_BUTTON_CMD;

	public static String CHUNK_VIEW_PARTICLE;

	public static MenuItem FILLER_JOIN_NATION_MENU;
	public static int FILLER_JOIN_NATION_MENU_CMD;
	public static MenuItem FILLER_NATION_TOGGLE;
	public static int FILLER_NATION_TOGGLE_CMD;

	public static MenuItem FILLER_NATION_EXTRA_INFO_MENU;
	public static int FILLER_NATION_EXTRA_INFO_MENU_CMD;

	public static MenuItem FILLER_JOIN_TOWN_MENU;
	public static int FILLER_JOIN_TOWN_MENU_CMD;

	public static MenuItem FILLER_TOWN_MENU;
	public static int FILLER_TOWN_MENU_CMD;
	public static MenuItem FILLER_TOWN_TOGGLE;
	public static int FILLER_TOWN_TOGGLE_CMD;
	public static MenuItem FILLER_TOWN_PERMS_MENU;
	public static int FILLER_TOWN_PERMS_MENU_CMD;
	public static MenuItem FILLER_TOWN_ECONOMY_MENU;
	public static int FILLER_TOWN_ECONOMY_MENU_CMD;
	public static MenuItem FILLER_TOWN_GENERAL_SETTINGS_MENU;
	public static int FILLER_TOWN_GENERAL_SETTINGS_MENU_CMD;
	public static MenuItem FILLER_TOWN_EXTRA_INFO_MENU;
	public static int FILLER_TOWN_EXTRA_INFO_MENU_CMD;

	public static MenuItem FILLER_PLOT_MENU;
	public static int FILLER_PLOT_MENU_CMD;
	public static MenuItem FILLER_PLOT_TOGGLE_MENU;
	public static int FILLER_PLOT_TOGGLE_MENU_CMD;
	public static MenuItem FILLER_PLOT_PERMS_MENU;
	public static int FILLER_PLOT_PERMS_MENU_CMD;
	public static MenuItem FILLER_PLOT_ADMIN_MENU;
	public static int FILLER_PLOT_ADMIN_MENU_CMD;

	public static MenuItem FIND_NATION_BUTTON;
	public static int FIND_NATION_BUTTON_SLOT;
	public static int FIND_NATION_BUTTON_CMD;
	public static MenuItem CREATE_NATION_BUTTON;
	public static int CREATE_NAITON_BUTTON_SLOT;
	public static int CREATE_NATION_BUTTON_CMD;

	public static MenuItem NATION_TOGGLE_MENU;
	public static int NATION_TOGGLE_MENU_CMD;
	public static MenuItem NATION_TOWN_LIST;
	public static int NATION_TOWN_LIST_CMD;
	public static MenuItem NATION_RESIDENT_MENU;
	public static int NATION_RESIDENT_MENU_CMD;
	public static MenuItem NATION_ECONOMY_MENU;
	public static int NATION_ECONOMY_MENU_CMD;
	public static MenuItem NATION_SETTINGS_MENU;
	public static int NATION_SETTINGS_MENU_CMD;
	public static MenuItem NATION_INVITE_TOWN_MENU;
	public static int NATION_INVITE_TOWN_MENU_CMD;
	public static MenuItem NATION_EXTRA_INFO;
	public static int NATION_EXTRA_INFO_CMD;

	public static MenuItem NATION_BALANCE;
	public static int NATION_BALANCE_CMD;
	public static MenuItem NATION_DEPOSIT;
	public static int NATION_DEPOSIT_CMD;
	public static MenuItem NATION_WITHDRAW;
	public static int NATION_WITHDRAW_CMD;
	public static MenuItem NATION_SET_TAX;
	public static int NATION_SET_TAX_CMD;

	public static MenuItem NATION_TOGGLE_PUBLIC;
	public static int NATION_TOGGLE_PUBLIC_CMD;
	public static MenuItem NATION_TOGGLE_OPEN;
	public static int NATION_TOGGLE_OPEN_CMD;
	public static MenuItem NATION_TOGGLE_TAX_PERCENTAGE;
	public static int NATION_TOGGLE_TAX_PERCENTAGE_CMD;

	public static MenuItem NATION_TOWN_KICK;
	public static int NATION_TOWN_KICK_CMD;

	public static MenuItem NATION_RESIDENT_RANK;
	public static int NATION_RESIDENT_RANK_CMD;
	public static MenuItem NATION_SET_KING;
	public static int NATION_SET_KING_CMD;

	public static MenuItem SET_NATION_SPAWN;
	public static int SET_NATION_SPAWN_CMD;
	public static MenuItem SET_NATION_BOARD;
	public static int SET_NATION_BOARD_CMD;
	public static MenuItem SET_NATION_NAME;
	public static int SET_NATION_NAME_CMD;

	public static MenuItem NATION_EXTRA_COMMANDS_1;
	public static int NATION_EXTRA_COMMANDS_1_CMD;
	public static MenuItem NATION_EXTRA_COMMANDS_2;
	public static int NATION_EXTRA_COMMANDS_2_CMD;

	public static MenuItem FIND_TOWN;
	public static MenuItem CREATE_TOWN;

	public static MenuItem TOGGLE_MENU;
	public static int TOGGLE_MENU_CMD;
	public static MenuItem RESIDENT_LIST;
	public static int RESIDENT_LIST_CMD;
	public static MenuItem PERMISSIONS_MENU;
	public static int PERMISSIONS_MENU_CMD;
	public static MenuItem ECONOMY_MENU;
	public static int ECONOMY_MENU_CMD;
	public static MenuItem PLOT_MENU;
	public static int PLOT_MENU_CMD;
	public static MenuItem SETTINGS_MENU;
	public static int SETTINGS_MENU_CMD;
	public static MenuItem INVITE_MENU;
	public static int INVITE_MENU_CMD;
	public static MenuItem EXTRA_INFO;
	public static int EXTRA_INFO_CMD;
	public static MenuItem TOWN_INFO_BUTTON;
	public static int TOWN_INFO_BUTTON_CMD;

	public static MenuItem TOGGLE_FIRE;
	public static int TOGGLE_FIRE_CMD;
	public static MenuItem TOGGLE_MOBS;
	public static int TOGGLE_MOBS_CMD;
	public static MenuItem TOGGLE_EXPLOSIONS;
	public static int TOGGLE_EXPLOSIONS_CMD;
	public static MenuItem TOGGLE_PVP;
	public static int TOGGLE_PVP_CMD;
	public static MenuItem TOGGLE_PUBLIC;
	public static int TOGGLE_PUBLIC_CMD;
	public static MenuItem TOGGLE_OPEN;
	public static int TOGGLE_OPEN_CMD;
	public static MenuItem TOGGLE_TAX_PERCENTAGE;
	public static int TOGGLE_TAX_PERCENTAGE_CMD;

	public static MenuItem RESIDENT_KICK;
	public static int RESIDENT_KICK_CMD;
	public static MenuItem RESIDENT_TITLE;
	public static int RESIDENT_TITLE_CMD;
	public static MenuItem RESIDENT_RANK;
	public static int RESIDENT_RANK_CMD;
	public static MenuItem RESIDENT_MAYOR;
	public static int RESIDENT_MAYOR_CMD;

	public static MenuItem BUILD;
	public static int BUILD_CMD;
	public static MenuItem BREAK;
	public static int BREAK_CMD;
	public static MenuItem ITEM_USE;
	public static int ITEM_USE_CMD;
	public static MenuItem SWITCH;
	public static int SWITCH_CMD;

	public static MenuItem RESIDENT_BUILD;
	public static int RESIDENT_BUILD_CMD;
	public static MenuItem RESIDENT_BREAK;
	public static int RESIDENT_BREAK_CMD;
	public static MenuItem RESIDENT_ITEM_USE;
	public static int RESIDENT_ITEM_USE_CMD;
	public static MenuItem RESIDENT_SWITCH;
	public static int RESIDENT_SWITCH_CMD;

	public static MenuItem NATION_BUILD;
	public static int NATION_BUILD_CMD;
	public static MenuItem NATION_BREAK;
	public static int NATION_BREAK_CMD;
	public static MenuItem NATION_ITEM_USE;
	public static int NATION_ITEM_USE_CMD;
	public static MenuItem NATION_SWITCH;
	public static int NATION_SWITCH_CMD;

	public static MenuItem ALLY_BUILD;
	public static int ALLY_BUILD_CMD;
	public static MenuItem ALLY_BREAK;
	public static int ALLY_BREAK_CMD;
	public static MenuItem ALLY_ITEM_USE;
	public static int ALLY_ITEM_USE_CMD;
	public static MenuItem ALLY_SWITCH;
	public static int ALLY_SWITCH_CMD;

	public static MenuItem OUTSIDER_BUILD;
	public static int OUTSIDER_BUILD_CMD;
	public static MenuItem OUTSIDER_BREAK;
	public static int OUTSIDER_BREAK_CMD;
	public static MenuItem OUTSIDER_ITEM_USE;
	public static int OUTSIDER_ITEM_USE_CMD;
	public static MenuItem OUTSIDER_SWITCH;
	public static int OUTSIDER_SWITCH_CMD;

	public static MenuItem RESET_ALL;
	public static int RESET_ALL_CMD;
	public static MenuItem ALL_ON;
	public static int ALL_ON_CMD;
	public static MenuItem RESET_TO_TOWN_PERM;
	public static int RESET_TO_TOWN_PERM_CMD;

	public static MenuItem TOWN_BALANCE;
	public static int TOWN_BALANCE_CMD;
	public static MenuItem DEPOSIT;
	public static int DEPOSIT_CMD;
	public static MenuItem WITHDRAW;
	public static int WITHDRAW_CMD;
	public static MenuItem SET_TAX;
	public static int SET_TAX_CMD;

	public static MenuItem SET_TOWN_SPAWN;
	public static int SET_TOWN_SPAWN_CMD;
	public static MenuItem SET_HOME_BLOCK;
	public static int SET_HOME_BLOCK_CMD;
	public static MenuItem SET_TOWN_BOARD;
	public static int SET_TOWN_BOARD_CMD;
	public static MenuItem SET_TOWN_NAME;
	public static int SET_TOWN_NAME_CMD;

	public static MenuItem TOWN_CLAIM_INFO;
	public static int TOWN_CLAIM_INFO_CMD;
	public static MenuItem EXTRA_COMMANDS;
	public static int EXTRA_COMMANDS_CMD;

	public static MenuItem PLOT_TOGGLE_MENU;
	public static int PLOT_TOGGLE_MENU_CMD;
	public static MenuItem PLOT_PERMISSIONS_MENU;
	public static int PLOT_PERMISSIONS_MENU_CMD;
	public static MenuItem PLOT_ADMIN_MENU;
	public static int PLOT_ADMIN_MENU_CMD;
	public static MenuItem PLOT_FRIEND_MENU;
	public static int PLOT_FRIEND_MENU_CMD;

	public static MenuItem PLOT_TOGGLE_FIRE;
	public static int PLOT_TOGGLE_FIRE_CMD;
	public static MenuItem PLOT_TOGGLE_MOBS;
	public static int PLOT_TOGGLE_MOBS_CMD;
	public static MenuItem PLOT_TOGGLE_EXPLOSIONS;
	public static int PLOT_TOGGLE_EXPLOSIONS_CMD;
	public static MenuItem PLOT_TOGGLE_PVP;
	public static int PLOT_TOGGLE_PVP_CMD;

	public static MenuItem PLOT_BUILD;
	public static int PLOT_BUILD_CMD;
	public static MenuItem PLOT_BREAK;
	public static int PLOT_BREAK_CMD;
	public static MenuItem PLOT_ITEM_USE;
	public static int PLOT_ITEM_USE_CMD;
	public static MenuItem PLOT_SWITCH;
	public static int PLOT_SWITCH_CMD;

	public static MenuItem PLOT_RESIDENT_BUILD;
	public static int PLOT_RESIDENT_BUILD_CMD;
	public static MenuItem PLOT_RESIDENT_BREAK;
	public static int PLOT_RESIDENT_BREAK_CMD;
	public static MenuItem PLOT_RESIDENT_ITEM_USE;
	public static int PLOT_RESIDENT_ITEM_USE_CMD;
	public static MenuItem PLOT_RESIDENT_SWITCH;
	public static int PLOT_RESIDENT_SWITCH_CMD;

	public static MenuItem PLOT_NATION_BUILD;
	public static int PLOT_NATION_BUILD_CMD;
	public static MenuItem PLOT_NATION_BREAK;
	public static int PLOT_NATION_BREAK_CMD;
	public static MenuItem PLOT_NATION_ITEM_USE;
	public static int PLOT_NATION_ITEM_USE_CMD;
	public static MenuItem PLOT_NATION_SWITCH;
	public static int PLOT_NATION_SWITCH_CMD;

	public static MenuItem PLOT_ALLY_BUILD;
	public static int PLOT_ALLY_BUILD_CMD;
	public static MenuItem PLOT_ALLY_BREAK;
	public static int PLOT_ALLY_BREAK_CMD;
	public static MenuItem PLOT_ALLY_ITEM_USE;
	public static int PLOT_ALLY_ITEM_USE_CMD;
	public static MenuItem PLOT_ALLY_SWITCH;
	public static int PLOT_ALLY_SWITCH_CMD;

	public static MenuItem PLOT_OUTSIDER_BUILD;
	public static int PLOT_OUTSIDER_BUILD_CMD;
	public static MenuItem PLOT_OUTSIDER_BREAK;
	public static int PLOT_OUTSIDER_BREAK_CMD;
	public static MenuItem PLOT_OUTSIDER_ITEM_USE;
	public static int PLOT_OUTSIDER_ITEM_USE_CMD;
	public static MenuItem PLOT_OUTSIDER_SWITCH;
	public static int PLOT_OUTSIDER_SWITCH_CMD;

	public static ItemStack PLOT_RESET_ALL;
	public static int PLOT_RESET_ALL_CMD;
	public static MenuItem PLOT_ALL_ON;
	public static int PLOT_ALL_ON_CMD;

	public static MenuItem PLOT_ADMIN_FOR_SALE;
	public static int PLOT_ADMIN_FOR_SALE_CMD;
	public static MenuItem PLOT_ADMIN_NOT_FOR_SALE;
	public static int PLOT_ADMIN_NOT_FOR_SALE_CMD;
	public static MenuItem PLOT_ADMIN_SET_PLOT_TYPE;
	public static int PLOT_ADMIN_SET_PLOT_TYPE_CMD;
	public static MenuItem PLOT_ADMIN_EVICT_RES;
	public static int PLOT_ADMIN_EVICT_RES_CMD;

	public static void load() {

		String raw = String.valueOf(config.get("economy")).trim();
		ECONOMY_ENABLED = raw.equalsIgnoreCase("true");
//		System.out.println("[DEBUG Settings] Raw config value: " + Settings.getConfig().get("economy"));
//		System.out.println("[DEBUG Settings] ECONOMY_ENABLED: " + Settings.ECONOMY_ENABLED);
		Object economyValue = config.get("economy");
//		System.out.println("[DEBUG] economyValue class: " + economyValue.getClass());
		PREFIX = config.getString("prefix", "&b&lTM &7// ");

		COLOR_MODE = ColorMode.valueOf(config.getString("color_mode", "LEGACY").toUpperCase());
//		Bukkit.getLogger().info("[DEBUG Settings380] Settings.COLOR_MODE = " + Settings.COLOR_MODE);

		MONEY_SYMBOL = config.getString("money_symbol", "$");
		ECONOMY_ENABLED = config.getBoolean("economy");
		LOCALE = config.getString("locale", "en");

		BACK_BUTTON = loadItem("back_button", Material.BARRIER);
		BACK_BUTTON_CMD = config.getInt("back_button_custommodeldata", 0);

		MENU_PREVIOUS_BUTTON = loadItem("menu_previous_page");
		MENU_PREVIOUS_BUTTON_CMD = config.getInt("menu_previous_page_custommodeldata", 0);
		MENU_NEXT_BUTTON = loadItem("menu_next_page");
		MENU_NEXT_BUTTON_CMD = config.getInt("menu_next_page_customemodeldata", 0);

		CHUNK_VIEW_PARTICLE = ConfigUtil.getString(ConfigNodes.CHUNK_VIEW_PARTICLE);

		FILLER_JOIN_NATION_MENU = loadItem("filler_join_nation_menu.item", Material.BARRIER);
		FILLER_JOIN_NATION_MENU_CMD = config.getInt("filler_join_nation_menu.custommodeldata", 0);
		FILLER_NATION_TOGGLE = loadItem("filler_nation_toggle.item", Material.BARRIER);
		FILLER_NATION_TOGGLE_CMD = config.getInt("filler_nation_toggle.custommodeldata");

		FILLER_NATION_EXTRA_INFO_MENU = loadItem("filler_nation_extra_info_menu.item", Material.BARRIER);
		FILLER_NATION_EXTRA_INFO_MENU_CMD = config.getInt("filler_nation_extra_info_menu.custommodeldata");

		FILLER_JOIN_TOWN_MENU = loadItem("filler_join_town_menu.item", Material.BARRIER);
		FILLER_JOIN_TOWN_MENU_CMD = config.getInt("filler_join_town_menu.custommodeldata");

		FILLER_TOWN_MENU = loadItem("filler_town_menu.item", Material.BARRIER);
		FILLER_TOWN_MENU_CMD = config.getInt("filler_town_menu.custommodeldata");
		FILLER_TOWN_TOGGLE = loadItem("filler_town_toggle.item", Material.BARRIER);
		FILLER_TOWN_TOGGLE_CMD = config.getInt("filler_town_toggle.custommodeldata");
		FILLER_TOWN_PERMS_MENU = loadItem("filler_town_perms_menu.item", Material.BARRIER);
		FILLER_TOWN_PERMS_MENU_CMD = config.getInt("filler_town_perms_menu.custommodeldata");
		FILLER_TOWN_ECONOMY_MENU = loadItem("filler_economy_menu.item", Material.BARRIER);
		FILLER_TOWN_ECONOMY_MENU_CMD = config.getInt("filler_economy_menu.custommodeldata");
		FILLER_TOWN_GENERAL_SETTINGS_MENU = loadItem("filler_town_general_settings_menu.item", Material.BARRIER);
		FILLER_TOWN_GENERAL_SETTINGS_MENU_CMD = config.getInt("filler_town_general_settings_menu.custommodeldata");
		FILLER_TOWN_EXTRA_INFO_MENU = loadItem("filler_extra_info_menu.item", Material.BARRIER);
		FILLER_TOWN_EXTRA_INFO_MENU_CMD = config.getInt("filler_extra_info_menu.custommodeldata");

		FILLER_PLOT_MENU = loadItem("filler_plot_menu.item", Material.BARRIER);
		FILLER_PLOT_MENU_CMD = config.getInt("filler_plot_menu.custommodeldata");
		FILLER_PLOT_TOGGLE_MENU = loadItem("filler_plot_toggle_menu.item", Material.BARRIER);
		FILLER_PLOT_TOGGLE_MENU_CMD = config.getInt("filler_plot_toggle_menu.custommodeldata");
		FILLER_PLOT_PERMS_MENU = loadItem("filler_plot_perms_menu.item", Material.BARRIER);
		FILLER_PLOT_PERMS_MENU_CMD = config.getInt("filler_plot_perms_menu.custommodeldata");
		FILLER_PLOT_ADMIN_MENU = loadItem("filler_plot_admin_menu.item", Material.BARRIER);
		FILLER_PLOT_ADMIN_MENU_CMD = config.getInt("filler_plot_admin_menu.custommodeldata");

		FIND_NATION_BUTTON = loadItem("find_nation_button.item", Material.BARRIER);
		FIND_NATION_BUTTON_CMD = config.getInt("find_nation_button.custommodeldata", 0);
		FIND_NATION_BUTTON_SLOT = config.getInt("find_nation_button.slot");
		CREATE_NATION_BUTTON = loadItem("create_nation_button.item", Material.BARRIER);
		CREATE_NAITON_BUTTON_SLOT = config.getInt("create_nation_button.slot");
		CREATE_NATION_BUTTON_CMD = config.getInt("create_nation_button.custommodeldata", 0);

		NATION_TOGGLE_MENU = loadItem("nation_toggle.item", Material.BARRIER);
		NATION_TOGGLE_MENU_CMD = config.getInt("nation_toggle.custommodeldata");
		NATION_TOWN_LIST = loadItem("nation_town_list.item", Material.BARRIER);
		NATION_TOWN_LIST_CMD = config.getInt("nation_town_list.custommodeldata");
		NATION_RESIDENT_MENU = loadItem("nation_resident_menu.item", Material.BARRIER);
		NATION_RESIDENT_MENU_CMD = config.getInt("nation_resident_menu.custommodeldata");
		NATION_ECONOMY_MENU = loadItem("nation_economy_menu.item", Material.BARRIER);
		NATION_ECONOMY_MENU_CMD = config.getInt("nation_economy_menu.custommodeldata");
		NATION_SETTINGS_MENU = loadItem("nation_settings_menu.item", Material.BARRIER);
		NATION_SETTINGS_MENU_CMD = config.getInt("nation_settings_menu.custommodeldata");
		NATION_INVITE_TOWN_MENU = loadItem("nation_invite_town.item", Material.BARRIER);
		NATION_INVITE_TOWN_MENU_CMD = config.getInt("nation_invite_town.custommodeldata");
		NATION_EXTRA_INFO = loadItem("nation_extra_info.item", Material.BARRIER);
		NATION_EXTRA_INFO_CMD = config.getInt("nation_extra_info.custommodeldata");

		NATION_BALANCE = loadItem("nation_balance.item", Material.BARRIER);
		NATION_BALANCE_CMD = config.getInt("nation_balance.custommodeldata");
		NATION_DEPOSIT = loadItem("nation_deposit.item", Material.BARRIER);
		NATION_DEPOSIT_CMD = config.getInt("nation_deposit.custommodeldata");
		NATION_WITHDRAW = loadItem("nation_withdraw.item", Material.BARRIER);
		NATION_WITHDRAW_CMD = config.getInt("nation_withdraw.custommodeldata");
		NATION_SET_TAX = loadItem("nation_set_tax.item", Material.BARRIER);
		NATION_SET_TAX_CMD = config.getInt("nation_set_tax.custommodeldata");

		NATION_TOWN_KICK = loadItem("nation_town_kick.item", Material.BARRIER);
		NATION_TOWN_KICK_CMD = config.getInt("nation_town_kick.custommodeldata");

		NATION_RESIDENT_RANK = loadItem("nation_resident_rank.item", Material.BARRIER);
		NATION_RESIDENT_RANK_CMD = config.getInt("nation_resident_rank.custommodeldata");
		NATION_SET_KING = loadItem("nation_set_king.item", Material.BARRIER);
		NATION_SET_KING_CMD = config.getInt("nation_set_king.custommodeldata");

		NATION_TOGGLE_PUBLIC = loadItem("nation_toggle_public.item", Material.BARRIER);
		NATION_TOGGLE_PUBLIC_CMD = config.getInt("nation_toggle_public.custommodeldata");
		NATION_TOGGLE_OPEN = loadItem("nation_toggle_open.item", Material.BARRIER);
		NATION_TOGGLE_OPEN_CMD = config.getInt("nation_toggle_open.custommodeldata");
		NATION_TOGGLE_TAX_PERCENTAGE = loadItem("nation_toggle_tax_percentage.item", Material.BARRIER);
		NATION_TOGGLE_TAX_PERCENTAGE_CMD = config.getInt("nation_toggle_tax_percentage.custommodeldata");

		SET_NATION_SPAWN = loadItem("set_nation_spawn.item", Material.BARRIER);
		SET_NATION_SPAWN_CMD = config.getInt("set_nation_spawn.custommodeldata");
		SET_NATION_BOARD = loadItem("set_nation_board.item", Material.BARRIER);
		SET_NATION_BOARD_CMD = config.getInt("set_nation_board.custommodeldata");
		SET_NATION_NAME = loadItem("set_nation_name.item", Material.BARRIER);
		SET_NATION_NAME_CMD = config.getInt("set_nation_name.custommodeldata");

		NATION_EXTRA_COMMANDS_1 = loadItem("nation_extra_commands_1.item", Material.BARRIER);
		NATION_EXTRA_COMMANDS_1_CMD = config.getInt("nation_extra_commands_1.custommodeldata");
		NATION_EXTRA_COMMANDS_2 = loadItem("nation_extra_commands_2.item", Material.BARRIER);
		NATION_EXTRA_COMMANDS_2_CMD = config.getInt("nation_extra_commands_2.custommodeldata");

		FIND_TOWN = loadItem("find_town", Material.BARRIER);
		CREATE_TOWN = loadItem("create_town", Material.BARRIER);

		TOGGLE_MENU = loadItem("toggle_menu.item", Material.BARRIER);
		TOGGLE_MENU_CMD = config.getInt("toggle_menu.custommodeldata");
		RESIDENT_LIST = loadItem("resident_list.item", Material.BARRIER);
		RESIDENT_LIST_CMD = config.getInt("resident_list.custommodeldata");
		PERMISSIONS_MENU = loadItem("permissions_menu.item", Material.BARRIER);
		PERMISSIONS_MENU_CMD = config.getInt("permissions_menu.custommodeldata");
		ECONOMY_MENU = loadItem("economy_menu.item", Material.BARRIER);
		ECONOMY_MENU_CMD = config.getInt("economy_menu.custommodeldata");
		PLOT_MENU = loadItem("plot_menu.item", Material.BARRIER);
		PLOT_MENU_CMD = config.getInt("plot_menu.custommodeldata");
		SETTINGS_MENU = loadItem("settings_menu.item", Material.BARRIER);
		SETTINGS_MENU_CMD = config.getInt("settings_menu.custommodeldata");
		INVITE_MENU = loadItem("invite_menu.item", Material.BARRIER);
		INVITE_MENU_CMD = config.getInt("invite_menu.custommodeldata");
		EXTRA_INFO = loadItem("extra_info.item", Material.BARRIER);
		EXTRA_INFO_CMD = config.getInt("extra_info.custommodeldata");
		TOWN_INFO_BUTTON = loadItem("town_info_icon.item", Material.BARRIER);
		TOWN_INFO_BUTTON_CMD = config.getInt("town_info_icon.custommodeldata");

		TOGGLE_FIRE = loadItem("toggle_fire.item", Material.BARRIER);
		TOGGLE_FIRE_CMD = config.getInt("toggle_fire.custommodeldata");
		TOGGLE_MOBS = loadItem("toggle_mobs.item", Material.BARRIER);
		TOGGLE_MOBS_CMD = config.getInt("toggle_fire.custommodeldata");
		TOGGLE_EXPLOSIONS = loadItem("toggle_explosions.item", Material.BARRIER);
		TOGGLE_EXPLOSIONS_CMD = config.getInt("toggle_explosions.custommodeldata");
		TOGGLE_PVP = loadItem("toggle_pvp.item", Material.BARRIER);
		TOGGLE_PVP_CMD = config.getInt("toggle_pvp.custommodeldata");
		TOGGLE_PUBLIC = loadItem("toggle_public.item", Material.BARRIER);
		TOGGLE_PUBLIC_CMD = config.getInt("toggle_public.custommodeldata");
		TOGGLE_OPEN = loadItem("toggle_open.item", Material.BARRIER);
		TOGGLE_OPEN_CMD = config.getInt("toggle_open.custommodeldata");
		TOGGLE_TAX_PERCENTAGE = loadItem("toggle_tax_percentage.item", Material.BARRIER);
		TOGGLE_TAX_PERCENTAGE_CMD = config.getInt("toggle_tax_percentage.custommodeldata");

		RESIDENT_KICK = loadItem("resident_kick.item", Material.BARRIER);
		RESIDENT_KICK_CMD = config.getInt("resident_kick.custommodeldata");
		RESIDENT_TITLE = loadItem("resident_title.item", Material.BARRIER);
		RESIDENT_TITLE_CMD = config.getInt("resident_title.custommodeldata");
		RESIDENT_RANK = loadItem("resident_rank.item", Material.BARRIER);
		RESIDENT_RANK_CMD = config.getInt("resident_rank.custommodeldata");
		RESIDENT_MAYOR = loadItem("resident_mayor.item", Material.BARRIER);
		RESIDENT_MAYOR_CMD = config.getInt("resident_mayor.custommodeldata");

		BUILD = loadItem("buildAndSet.item", Material.BARRIER);
		BUILD_CMD = config.getInt("buildAndSet.custommodeldata");
		BREAK = loadItem("break.item", Material.BARRIER);
		BREAK_CMD = config.getInt("break.custommodeldata");
		ITEM_USE = loadItem("item_use.item", Material.BARRIER);
		ITEM_USE_CMD = config.getInt("item_use.custommodeldata");
		SWITCH = loadItem("switch.item", Material.BARRIER);
		SWITCH_CMD = config.getInt("switch.custommodeldata");

		RESIDENT_BUILD = loadItem("resident_build.item", Material.BARRIER);
		RESIDENT_BUILD_CMD = config.getInt("resident_build.custommodeldata");
		RESIDENT_BREAK = loadItem("resident_break.item", Material.BARRIER);
		RESIDENT_BREAK_CMD = config.getInt("resident_break.custommodeldata");
		RESIDENT_ITEM_USE = loadItem("resident_item_use.item", Material.BARRIER);
		RESIDENT_ITEM_USE_CMD = config.getInt("resident_item_use.custommodeldata");
		RESIDENT_SWITCH = loadItem("resident_switch.item", Material.BARRIER);
		RESIDENT_SWITCH_CMD = config.getInt("resident_switch.custommodeldata");

		NATION_BUILD = loadItem("nation_build.item", Material.BARRIER);
		NATION_BUILD_CMD = config.getInt("nation_build.custommodeldata");
		NATION_BREAK = loadItem("nation_break.item", Material.BARRIER);
		NATION_BREAK_CMD = config.getInt("nation_break.custommodeldata");
		NATION_ITEM_USE = loadItem("nation_item_use.item", Material.BARRIER);
		NATION_ITEM_USE_CMD = config.getInt("nation_item_use.customodeldata");
		NATION_SWITCH = loadItem("nation_switch.item", Material.BARRIER);
		NATION_SWITCH_CMD = config.getInt("nation_switch.custommodeldata");

		ALLY_BUILD = loadItem("ally_build.item", Material.BARRIER);
		ALLY_BUILD_CMD = config.getInt("ally.buildAndSet.custommodeldata");
		ALLY_BREAK = loadItem("ally_break.item", Material.BARRIER);
		ALLY_BREAK_CMD = config.getInt("ally_break.custommodeldata");
		ALLY_ITEM_USE = loadItem("ally_item_use.item", Material.BARRIER);
		ALLY_ITEM_USE_CMD = config.getInt("ally_item_use.custommodeldata");
		ALLY_SWITCH = loadItem("ally_switch.item", Material.BARRIER);
		ALLY_SWITCH_CMD = config.getInt("ally_switch.custommodeldata");

		OUTSIDER_BUILD = loadItem("outsider_build.item", Material.BARRIER);
		OUTSIDER_BUILD_CMD = config.getInt("outsider_build.custommodeldata");
		OUTSIDER_BREAK = loadItem("outsider_break.item", Material.BARRIER);
		OUTSIDER_BREAK_CMD = config.getInt("outsider_break.custommodeldata");
		OUTSIDER_ITEM_USE = loadItem("outsider_item_use.item", Material.BARRIER);
		OUTSIDER_ITEM_USE_CMD = config.getInt("outsider_item_use.custommodeldata");
		OUTSIDER_SWITCH = loadItem("outsider_switch.item", Material.BARRIER);
		OUTSIDER_SWITCH_CMD = config.getInt("outsider_switch.custommodeldata");

		RESET_ALL = loadItem("reset_all.item", Material.BARRIER);
		RESET_ALL_CMD = config.getInt("reset_all.custommodeldata");
		ALL_ON = loadItem("all_on.item", Material.BARRIER);
		ALL_ON_CMD = config.getInt("all_on.custommodeldata");
		RESET_TO_TOWN_PERM = loadItem("reset_to_town_perm.item", Material.BARRIER);
		RESET_TO_TOWN_PERM_CMD = config.getInt("reset_to_town_perm.custommodeldata");

		TOWN_BALANCE = loadItem("town_balance.item", Material.BARRIER);
		TOWN_BALANCE_CMD = config.getInt("town_balance.custommodeldata");
		DEPOSIT = loadItem("deposit.item", Material.BARRIER);
		DEPOSIT_CMD = config.getInt("deposit.custommodeldata");
		WITHDRAW = loadItem("withdraw.item", Material.BARRIER);
		WITHDRAW_CMD = config.getInt("withdraw.custommodeldata");
		SET_TAX = loadItem("set_tax.item", Material.BARRIER);
		SET_TAX_CMD = config.getInt("set_tax.custommodeldata");

		SET_TOWN_SPAWN = loadItem("set_town_spawn.item", Material.BARRIER);
		SET_TOWN_SPAWN_CMD = config.getInt("set_town_spawn.custommodeldata");
		SET_HOME_BLOCK = loadItem("set_home_block.item", Material.BARRIER);
		SET_HOME_BLOCK_CMD = config.getInt("set_home_block.custommodeldata");
		SET_TOWN_BOARD = loadItem("set_town_board.item", Material.BARRIER);
		SET_TOWN_BOARD_CMD = config.getInt("set_town_board.custommodeldata");
		SET_TOWN_NAME = loadItem("set_town_name.item", Material.BARRIER);
		SET_TOWN_NAME_CMD = config.getInt("set_town_name.custommodeldata");

		TOWN_CLAIM_INFO = loadItem("town_claim_info.item", Material.BARRIER);
		TOWN_CLAIM_INFO_CMD = config.getInt("town_claim_info.custommodeldata");
		EXTRA_COMMANDS = loadItem("extra_commands.item", Material.BARRIER);
		EXTRA_COMMANDS_CMD = config.getInt("extra_commands.custommodeldata");

		PLOT_TOGGLE_MENU = loadItem("plot_toggle_menu.item", Material.BARRIER);
		PLOT_TOGGLE_MENU_CMD = config.getInt("plot_toggle_menu.custommodeldata");
		PLOT_PERMISSIONS_MENU = loadItem("plot_permissions_menu.item", Material.BARRIER);
		PLOT_PERMISSIONS_MENU_CMD = config.getInt("plot_permissions_menu.custommodeldata");
		PLOT_ADMIN_MENU = loadItem("plot_admin_menu.item", Material.BARRIER);
		PLOT_ADMIN_MENU_CMD = config.getInt("plot_admin_menu.custommodeldata");
		PLOT_FRIEND_MENU = loadItem("plot_friend_menu.item", Material.BARRIER);
		PLOT_FRIEND_MENU_CMD = config.getInt("plot_friend_menu.custommodeldata");

		PLOT_TOGGLE_FIRE = loadItem("plot_toggle_fire.item", Material.BARRIER);
		PLOT_TOGGLE_FIRE_CMD = config.getInt("plot_toggle_fire.custommodeldata");
		PLOT_TOGGLE_MOBS = loadItem("plot_toggle_mobs.item", Material.BARRIER);
		PLOT_TOGGLE_MOBS_CMD = config.getInt("plot_toggle_mobs.custommodeldata");
		PLOT_TOGGLE_EXPLOSIONS = loadItem("plot_toggle_explosions.item", Material.BARRIER);
		PLOT_TOGGLE_EXPLOSIONS_CMD = config.getInt("plot_toggle_explosions.custommodeldata");
		PLOT_TOGGLE_PVP = loadItem("plot_toggle_pvp.item", Material.BARRIER);
		PLOT_TOGGLE_PVP_CMD = config.getInt("plot_toggle_pvp.custommodeldata");

		PLOT_BUILD = loadItem("plot_build.item", Material.BARRIER);
		PLOT_BUILD_CMD = config.getInt("plot_build.custommodeldata");
		PLOT_BREAK = loadItem("plot_break.item", Material.BARRIER);
		PLOT_BREAK_CMD = config.getInt("plot_break.custommodeldata");
		PLOT_ITEM_USE = loadItem("plot_item_use.item", Material.BARRIER);
		PLOT_ITEM_USE_CMD = config.getInt("plot_item_use.custommodeldata");
		PLOT_SWITCH = loadItem("plot_switch.item", Material.BARRIER);
		PLOT_SWITCH_CMD = config.getInt("plot_switch.custommodeldata");

		PLOT_RESIDENT_BUILD = loadItem("plot_resident_build.item", Material.BARRIER);
		PLOT_RESIDENT_BUILD_CMD = config.getInt("plot_resident_build.custommodeldata");
		PLOT_RESIDENT_BREAK = loadItem("plot_resident_break.item", Material.BARRIER);
		PLOT_RESIDENT_BREAK_CMD = config.getInt("plot_resident_break.custommodeldata");
		PLOT_RESIDENT_ITEM_USE = loadItem("plot_resident_item_use.item", Material.BARRIER);
		PLOT_RESIDENT_ITEM_USE_CMD = config.getInt("plot_resident_item_use.custommodeldata");
		PLOT_RESIDENT_SWITCH = loadItem("plot_resident_switch.tem", Material.BARRIER);
		PLOT_RESIDENT_SWITCH_CMD = config.getInt("plot_resident_switch.custommodeldata");

		PLOT_NATION_BUILD = loadItem("plot_nation_build.item", Material.BARRIER);
		PLOT_NATION_BUILD_CMD = config.getInt("plot_nation_build.custommodeldata");
		PLOT_NATION_BREAK = loadItem("plot_nation_break.item", Material.BARRIER);
		PLOT_NATION_BREAK_CMD = config.getInt("plot_nation_break.custommodelDdta");
		PLOT_NATION_ITEM_USE = loadItem("plot_nation_item_use.item", Material.BARRIER);
		PLOT_NATION_ITEM_USE_CMD = config.getInt("plot_nation_item_use.custommodeldata");
		PLOT_NATION_SWITCH = loadItem("plot_nation_switch.item", Material.BARRIER);
		PLOT_NATION_SWITCH_CMD = config.getInt("plot_nation_switch.custommodeldata");

		PLOT_ALLY_BUILD = loadItem("plot_ally_build.item", Material.BARRIER);
		PLOT_ALLY_BUILD_CMD = config.getInt("plot_ally_build.custommodeldata");
		PLOT_ALLY_BREAK = loadItem("plot_ally_break.item", Material.BARRIER);
		PLOT_ALLY_BREAK_CMD = config.getInt("plot_ally_break.custommodeldata");
		PLOT_ALLY_ITEM_USE = loadItem("plot_ally_item_use.item", Material.BARRIER);
		PLOT_ALLY_ITEM_USE_CMD = config.getInt("plot_ally_item_use.custommodeldata");
		PLOT_ALLY_SWITCH = loadItem("plot_ally_switch.item", Material.BARRIER);
		PLOT_ALLY_SWITCH_CMD = config.getInt("plot_ally_switch.custommodeldata");

		PLOT_OUTSIDER_BUILD = loadItem("plot_outsider_build.item", Material.BARRIER);
		PLOT_OUTSIDER_BUILD_CMD = config.getInt("plot_outsider_build.custommodeldata");
		PLOT_OUTSIDER_BREAK = loadItem("plot_outsider_break.item", Material.BARRIER);
		PLOT_OUTSIDER_BREAK_CMD = config.getInt("plot_outsider_break.custommodeldata");
		PLOT_OUTSIDER_ITEM_USE = loadItem("plot_outsider_item_use.item", Material.BARRIER);
		PLOT_OUTSIDER_ITEM_USE_CMD = config.getInt("plot_outsider_item_use.custommodeldata");
		PLOT_OUTSIDER_SWITCH = loadItem("plot_outsider_switch.item", Material.BARRIER);
		PLOT_OUTSIDER_SWITCH_CMD = config.getInt("plot_outsider_switch.custommodeldata");

		PLOT_RESET_ALL = loadItemStack("plot_reset_all.item", Material.BARRIER);
		PLOT_RESET_ALL_CMD = config.getInt("plot_reset_all.custommodeldata");
		PLOT_ALL_ON = loadItem("plot_all_on.item", Material.BARRIER);
		PLOT_ALL_ON_CMD = config.getInt("plot_all_on.custommodeldata");

		PLOT_ADMIN_FOR_SALE = loadItem("plot_admin_for_sale.item", Material.BARRIER);
		PLOT_ADMIN_FOR_SALE_CMD = config.getInt("plot_admin_for_sale.custommodeldata");
		PLOT_ADMIN_NOT_FOR_SALE = loadItem("plot_admin_not_for_sale.item", Material.BARRIER);
		PLOT_ADMIN_NOT_FOR_SALE_CMD = config.getInt("plot_admin_not_for_sale.custommodeldata");
		PLOT_ADMIN_SET_PLOT_TYPE = loadItem("plot_admin_set_plot_type.item", Material.BARRIER);
		PLOT_ADMIN_SET_PLOT_TYPE_CMD = config.getInt("plot_admin_set_plot_type.custommodeldata");
		PLOT_ADMIN_EVICT_RES = loadItem("plot_admin_evict_res.item", Material.BARRIER);
		PLOT_ADMIN_EVICT_RES_CMD = config.getInt("plot_admin_evict_res.custommodeldata");
	}

	public static void setColorMode(ColorMode mode) {
//		Bukkit.getLogger().info("[DEBUG Settings693] COLOR_MODE changing from " + Settings.COLOR_MODE + " to " + mode);
		Settings.COLOR_MODE = mode;
		MessageFormatter.COLOR_MODE = mode;
	}

	private static ItemStack loadItemStack(String path, Material fallback) {
		String materialName = config.getString(path + ".item", fallback.name());
		Material material = Material.matchMaterial(materialName);
		if (material == null) {
			Bukkit.getLogger().warning("[TownyMenu] Invalid material '"
					+ materialName + "' at path " + path + ".item. Using fallback: "
					+ fallback.name());
			material = fallback;
		}

		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();

		if (config.contains(path + ".custommodeldata") && meta != null) {
			meta.setCustomModelData(config.getInt(path + ".custommodeldata"));
			item.setItemMeta(meta);
		}

		return item;
	}

	private static MenuItem loadItem(String path, Material fallback) {
		ItemStack item = loadItemStack(path, fallback);
		return new MenuItem(item, null);
	}

	private static MenuItem loadItem(String path) {
		return loadItem(path, Material.STONE);
	}

	public static MenuItem loadMenuItem(Player player, String path, Material fallback, String name, List<String> lore, Consumer<InventoryClickEvent> handler) {
		ItemStack item = loadItemStack(path, fallback);
//		System.out.println("[DEBUG Settings734] Back button material: " + item.getType());

		ItemMeta meta = item.getItemMeta();
		if (meta != null) {
			if (name != null) meta.setDisplayName(MessageFormatter.format(name, player));
			if (lore != null && !lore.isEmpty())
				meta.setLore(lore.stream().map(line -> MessageFormatter.format(line, player)).toList());
			item.setItemMeta(meta);
		}
//		System.out.println("[DEBUG Settings743] Back button name: " + meta.getDisplayName());
//		System.out.println("[DEBUG Settings744] Back button lore: " + meta.getLore());
		return new MenuItem(item, handler);
	}

	public static Material getChunkViewMaterial() {
		String raw = ConfigUtil.getConfig().getString("chunk_view", "REDSTONE_TORCH");
		try {
			return Material.valueOf(raw.toUpperCase());
		} catch (IllegalArgumentException e) {
			Bukkit.getLogger().warning("[TownyMenu] Invalid chunk_view material: " + raw + ", using REDSTONE_TORCH");
			return Material.REDSTONE_TORCH;
		}
	}
}
