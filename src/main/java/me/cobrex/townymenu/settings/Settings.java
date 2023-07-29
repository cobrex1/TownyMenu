package me.cobrex.townymenu.settings;

import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.settings.SimpleSettings;

public final class Settings extends SimpleSettings {
	@Override
	protected int getConfigVersion() {
		return 1;
	}

	public static String MONEY_SYMBOL;
	public static Boolean ECONOMY_ENABLED;
	public static String LOCALE;

	public static CompMaterial CHUNK_VIEW;
	public static String CHUNK_VIEW_PARTICLE;

	public static String FIND_TOWN;
	public static String CREATE_TOWN;

	public static CompMaterial FILLER_JOIN_TOWN_MENU;

	public static CompMaterial FILLER_TOWN_MENU;
	public static CompMaterial FILLER_TOWN_TOGGLE;
	public static CompMaterial FILLER_TOWN_PERMS_MENU;
	public static CompMaterial FILLER_TOWN_ECONOMY_MENU;
	public static CompMaterial FILLER_TOWN_GENERAL_SETTINGS_MENU;
	public static CompMaterial FILLER_TOWN_EXTRA_INFO_MENU;

	public static CompMaterial FILLER_PLOT_MENU;
	public static CompMaterial FILLER_PLOT_TOGGLE_MENU;
	public static CompMaterial FILLER_PLOT_PERMS_MENU;
	public static CompMaterial FILLER_PLOT_ADMIN_MENU;

	public static CompMaterial FILLER_JOIN_NATION_MENU;

	public static String FIND_NATION_BUTTON;
	public static String CREATE_NATION_BUTTON;

	public static String NATION_TOGGLE_MENU;
	public static String NATION_TOWN_LIST;
	public static String NATION_RESIDENT_MENU;
	public static String NATION_ECONOMY_MENU;
	public static String NATION_SETTINGS_MENU;
	public static String NATION_INVITE_TOWN_MENU;
	public static String NATION_EXTRA_INFO;

	public static String NATION_BALANCE;
	public static String NATION_DEPOSIT;
	public static String NATION_WITHDRAW;
	public static String NATION_SET_TAX;

	public static String NATION_TOGGLE_PUBLIC;
	public static String NATION_TOGGLE_OPEN;
	public static String NATION_TOGGLE_TAX_PERCENTAGE;

	public static String NATION_SET_SPAWN;

	public static String NATION_TOWN_KICK;

	public static String NATION_RESIDENT_RANK;
	public static String NATION_SET_KING;

	public static String NATION_EXTRA_COMMANDS_1;
	public static String NATION_EXTRA_COMMANDS_2;

	public static String SET_NATION_SPAWN;
	public static String SET_NATION_BOARD;
	public static String SET_NATION_NAME;

	public static String TOGGLE_MENU;
	public static String RESIDENT_LIST;
	public static String PERMISSIONS_MENU;
	public static String ECONOMY_MENU;
	public static String PLOT_MENU;
	public static String SETTINGS_MENU;
	public static String INVITE_MENU;
	public static String EXTRA_INFO;

	public static String TOGGLE_FIRE;
	public static String TOGGLE_MOBS;
	public static String TOGGLE_EXPLOSIONS;
	public static String TOGGLE_PVP;
	public static String TOGGLE_PUBLIC;
	public static String TOGGLE_OPEN;
	public static String TOGGLE_TAX_PERCENTAGE;

	public static String RESIDENT_KICK;
	public static String RESIDENT_TITLE;
	public static String RESIDENT_RANK;
	public static String RESIDENT_MAYOR;

	public static String BUILD;
	public static String BREAK;
	public static String ITEM_USE;
	public static String SWITCH;

	public static String RESIDENT_BUILD;
	public static String RESIDENT_BREAK;
	public static String RESIDENT_ITEM_USE;
	public static String RESIDENT_SWITCH;

	public static String NATION_BUILD;
	public static String NATION_BREAK;
	public static String NATION_ITEM_USE;
	public static String NATION_SWITCH;

	public static String ALLY_BUILD;
	public static String ALLY_BREAK;
	public static String ALLY_ITEM_USE;
	public static String ALLY_SWITCH;

	public static String OUTSIDER_BUILD;
	public static String OUTSIDER_BREAK;
	public static String OUTSIDER_ITEM_USE;
	public static String OUTSIDER_SWITCH;

	public static String RESET_ALL;
	public static String ALL_ON;

	public static String TOWN_BALANCE;
	public static String DEPOSIT;
	public static String WITHDRAW;
	public static String SET_TAX;

	public static String SET_TOWN_SPAWN;
	public static String SET_HOME_BLOCK;
	public static String SET_TOWN_BOARD;
	public static String SET_TOWN_NAME;

	public static String TOWN_CLAIM_INFO;
	public static String EXTRA_COMMANDS;

	public static String PLOT_TOGGLE_MENU;
	public static String PLOT_PERMISSIONS_MENU;
	public static String PLOT_ADMIN_MENU;
	public static String PLOT_FRIEND_MENU;

	public static String PLOT_TOGGLE_FIRE;
	public static String PLOT_TOGGLE_MOBS;
	public static String PLOT_TOGGLE_EXPLOSIONS;
	public static String PLOT_TOGGLE_PVP;

	public static String PLOT_BUILD;
	public static String PLOT_BREAK;
	public static String PLOT_ITEM_USE;
	public static String PLOT_SWITCH;

	public static String PLOT_RESIDENT_BUILD;
	public static String PLOT_RESIDENT_BREAK;
	public static String PLOT_RESIDENT_ITEM_USE;
	public static String PLOT_RESIDENT_SWITCH;

	public static String PLOT_NATION_BUILD;
	public static String PLOT_NATION_BREAK;
	public static String PLOT_NATION_ITEM_USE;
	public static String PLOT_NATION_SWITCH;

	public static String PLOT_ALLY_BUILD;
	public static String PLOT_ALLY_BREAK;
	public static String PLOT_ALLY_ITEM_USE;
	public static String PLOT_ALLY_SWITCH;

	public static String PLOT_OUTSIDER_BUILD;
	public static String PLOT_OUTSIDER_BREAK;
	public static String PLOT_OUTSIDER_ITEM_USE;
	public static String PLOT_OUTSIDER_SWITCH;

	public static String PLOT_RESET_ALL;
	public static String PLOT_ALL_ON;

	public static String PLOT_ADMIN_FOR_SALE;
	public static String PLOT_ADMIN_NOT_FOR_SALE;
	public static String PLOT_ADMIN_SET_PLOT_TYPE;
	public static String PLOT_ADMIN_EVICT_RES;

	private static void init() {
		setPathPrefix(null);

		MONEY_SYMBOL = getString("Money_Symbol");
		ECONOMY_ENABLED = getBoolean("Economy");
		LOCALE = getString("Locale");

		CHUNK_VIEW = getMaterial("Chunk_View");
		CHUNK_VIEW_PARTICLE = getString("Chunk_View_Particle");

		FILLER_JOIN_TOWN_MENU = getMaterial("Filler_Join_Town_Menu");

		FILLER_TOWN_MENU = getMaterial("Filler_Town_Menu");
		FILLER_TOWN_TOGGLE = getMaterial("Filler_Town_Toggle");
		FILLER_TOWN_PERMS_MENU = getMaterial("Filler_Town_Perms_Menu");
		FILLER_TOWN_ECONOMY_MENU = getMaterial("Filler_Economy_Menu");
		FILLER_TOWN_GENERAL_SETTINGS_MENU = getMaterial("Filler_Town_General_Settings_Menu");
		FILLER_TOWN_EXTRA_INFO_MENU = getMaterial("Filler_Extra_Info_Menu");

		FILLER_PLOT_MENU = getMaterial("Filler_Plot_Menu");
		FILLER_PLOT_TOGGLE_MENU = getMaterial("Filler_Plot_Toggle_Menu");
		FILLER_PLOT_PERMS_MENU = getMaterial("Filler_Plot_Perms_Menu");
		FILLER_PLOT_ADMIN_MENU = getMaterial("Filler_Plot_Admin_Menu");

		FILLER_JOIN_NATION_MENU = getMaterial("Filler_Join_Nation_Menu");

		FIND_NATION_BUTTON = getString("Find_Nation_Button");
		CREATE_NATION_BUTTON = getString("Create_Nation_Button");

		NATION_TOGGLE_MENU = getString("Nation_Toggle");
		NATION_TOWN_LIST = getString("Nation_Town_List");
		NATION_RESIDENT_MENU = getString("Nation_Resident_Menu");
		NATION_ECONOMY_MENU = getString("Nation_Economy_Menu");
		NATION_SETTINGS_MENU = getString("Nation_Settings_Menu");
		NATION_INVITE_TOWN_MENU = getString("Nation_Invite_Town");
		NATION_EXTRA_INFO = getString("Nation_Extra_Info");

		NATION_SET_SPAWN = getString("Nation_Set_Spawn");

		NATION_EXTRA_COMMANDS_1 = getString("Nation_Extra_Commands_1");
		NATION_EXTRA_COMMANDS_2 = getString("Nation_Extra_Commands_2");

		NATION_BALANCE = getString("Nation_Balance");
		NATION_DEPOSIT = getString("Nation_Deposit");
		NATION_WITHDRAW = getString("Nation_Withdraw");
		NATION_SET_TAX = getString("Nation_Set_Tax");

		NATION_TOWN_KICK = getString("Nation_Town_Kick");

		NATION_RESIDENT_RANK = getString("Resident_Rank");
		NATION_SET_KING = getString("Resident_Mayor");

		NATION_TOGGLE_PUBLIC = getString("Nation_Toggle_Public");
		NATION_TOGGLE_OPEN = getString("Nation_Toggle_Open");
		NATION_TOGGLE_TAX_PERCENTAGE = getString("Nation_Toggle_Tax_Percentage");

		SET_NATION_SPAWN = getString("Set_Nation_Spawn");
		SET_NATION_BOARD = getString("Set_Nation_Board");
		SET_NATION_NAME = getString("Set_Nation_Name");

		FIND_TOWN = getString("Find_Town");
		CREATE_TOWN = getString("Create_Town");

		TOGGLE_MENU = getString("Toggle_Menu");
		RESIDENT_LIST = getString("Resident_List");
		PERMISSIONS_MENU = getString("Permissions_Menu");
		ECONOMY_MENU = getString("Economy_Menu");
		PLOT_MENU = getString("Plot_Menu");
		SETTINGS_MENU = getString("Settings_Menu");
		INVITE_MENU = getString("Invite_Menu");
		EXTRA_INFO = getString("Extra_Info");

		TOGGLE_FIRE = getString("Toggle_Fire");
		TOGGLE_MOBS = getString("Toggle_Mobs");
		TOGGLE_EXPLOSIONS = getString("Toggle_Explosions");
		TOGGLE_PVP = getString("Toggle_PVP");
		TOGGLE_PUBLIC = getString("Toggle_Public");
		TOGGLE_OPEN = getString("Toggle_Open");
		TOGGLE_TAX_PERCENTAGE = getString("Toggle_Tax_Percentage");

		RESIDENT_KICK = getString("Resident_Kick");
		RESIDENT_TITLE = getString("Resident_Title");
		RESIDENT_RANK = getString("Resident_Rank");
		RESIDENT_MAYOR = getString("Resident_Mayor");

		BUILD = getString("Build");
		BREAK = getString("Break");
		ITEM_USE = getString("Item_Use");
		SWITCH = getString("Switch");

		RESIDENT_BUILD = getString("Resident_Build");
		RESIDENT_BREAK = getString("Resident_Break");
		RESIDENT_ITEM_USE = getString("Resident_Item_Use");
		RESIDENT_SWITCH = getString("Resident_Switch");

		NATION_BUILD = getString("Nation_Build");
		NATION_BREAK = getString("Nation_Break");
		NATION_ITEM_USE = getString("Nation_Item_Use");
		NATION_SWITCH = getString("Nation_Switch");

		ALLY_BUILD = getString("Ally_Build");
		ALLY_BREAK = getString("Ally_Break");
		ALLY_ITEM_USE = getString("Ally_Item_Use");
		ALLY_SWITCH = getString("Ally_Switch");

		OUTSIDER_BUILD = getString("Outsider_Build");
		OUTSIDER_BREAK = getString("Outsider_Break");
		OUTSIDER_ITEM_USE = getString("Outsider_Item_Use");
		OUTSIDER_SWITCH = getString("Outsider_Switch");

		RESET_ALL = getString("Reset_All");
		ALL_ON = getString("All_On");

		TOWN_BALANCE = getString("Town_Balance");
		DEPOSIT = getString("Deposit");
		WITHDRAW = getString("Withdraw");
		SET_TAX = getString("Set_Tax");

		SET_TOWN_SPAWN = getString("Set_Town_Spawn");
		SET_HOME_BLOCK = getString("Set_Home_Block");
		SET_TOWN_BOARD = getString("Set_Town_Board");
		SET_TOWN_NAME = getString("Set_Town_Name");

		TOWN_CLAIM_INFO = getString("Town_Claim_Info");
		EXTRA_COMMANDS = getString("Extra_Commands");

		PLOT_TOGGLE_MENU = getString("Plot_Toggle_Menu");
		PLOT_PERMISSIONS_MENU = getString("Plot_Permissions_Menu");
		PLOT_ADMIN_MENU = getString("Plot_Admin_Menu");
		PLOT_FRIEND_MENU = getString("Plot_Friend_Menu");

		PLOT_TOGGLE_FIRE = getString("Toggle_Fire");
		PLOT_TOGGLE_MOBS = getString("Toggle_Mobs");
		PLOT_TOGGLE_EXPLOSIONS = getString("Toggle_Explosions");
		PLOT_TOGGLE_PVP = getString("Toggle_PVP");

		PLOT_BUILD = getString("Plot_Build");
		PLOT_BREAK = getString("Plot_Break");
		PLOT_ITEM_USE = getString("Plot_Item_Use");
		PLOT_SWITCH = getString("Plot_Switch");

		PLOT_RESIDENT_BUILD = getString("Plot_Resident_Build");
		PLOT_RESIDENT_BREAK = getString("Plot_Resident_Break");
		PLOT_RESIDENT_ITEM_USE = getString("Plot_Resident_Item_Use");
		PLOT_RESIDENT_SWITCH = getString("Plot_Resident_Switch");

		PLOT_NATION_BUILD = getString("Plot_Nation_Build");
		PLOT_NATION_BREAK = getString("Plot_Nation_Break");
		PLOT_NATION_ITEM_USE = getString("Plot_Nation_Item_Use");
		PLOT_NATION_SWITCH = getString("Plot_Nation_Switch");

		PLOT_ALLY_BUILD = getString("Plot_Ally_Build");
		PLOT_ALLY_BREAK = getString("Plot_Ally_Break");
		PLOT_ALLY_ITEM_USE = getString("Plot_Ally_Item_Use");
		PLOT_ALLY_SWITCH = getString("Plot_Ally_Switch");

		PLOT_OUTSIDER_BUILD = getString("Plot_Outsider_Build");
		PLOT_OUTSIDER_BREAK = getString("Plot_Outsider_Break");
		PLOT_OUTSIDER_ITEM_USE = getString("Plot_Outsider_Item_Use");
		PLOT_OUTSIDER_SWITCH = getString("Plot_Outsider_Switch");

		PLOT_RESET_ALL = getString("Plot_Reset_All");
		PLOT_ALL_ON = getString("Plot_All_On");

		PLOT_ADMIN_FOR_SALE = getString("Plot_Admin_For_Sale");
		PLOT_ADMIN_NOT_FOR_SALE = getString("Plot_Admin_Not_For_Sale");
		PLOT_ADMIN_SET_PLOT_TYPE = getString("Plot_Admin_Set_Plot_Type");
		PLOT_ADMIN_EVICT_RES = getString("Plot_Admin_Evict_Res");
	}
}
