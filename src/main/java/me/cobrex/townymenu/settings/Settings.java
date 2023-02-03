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

	public static CompMaterial FIND_TOWN;
	public static CompMaterial CREATE_TOWN;

//	public static CompMaterial DUMMY_BUTTON;

	public static CompMaterial FILLER_JOIN_TOWN_MENU;

	public static CompMaterial FILLER_TOWN_MENU;
	public static CompMaterial FILLER_TOWN_TOGGLE;
	//	public static CompMaterial FILLER_TOWN_INVITE;
//	public static CompMaterial FILLER_RESIDENT_MENU;
	public static CompMaterial FILLER_TOWN_PERMS_MENU;
	public static CompMaterial FILLER_TOWN_ECONOMY_MENU;
	public static CompMaterial FILLER_TOWN_GENERAL_SETTINGS_MENU;
	public static CompMaterial FILLER_TOWN_EXTRA_INFO_MENU;

	public static CompMaterial FILLER_PLOT_MENU;
	//	public static CompMaterial FILLER_FRIENDS_LIST_MENU;
	public static CompMaterial FILLER_PLOT_TOGGLE_MENU;
	public static CompMaterial FILLER_PLOT_PERMS_MENU;
	public static CompMaterial FILLER_PLOT_ADMIN_MENU;

	public static CompMaterial TOGGLE_MENU;
	public static CompMaterial RESIDENT_LIST;
	public static CompMaterial PERMISSIONS_MENU;
	public static CompMaterial ECONOMY_MENU;
	public static CompMaterial PLOT_MENU;
	public static CompMaterial SETTINGS_MENU;
	public static CompMaterial INVITE_MENU;
	public static CompMaterial EXTRA_INFO;

	public static CompMaterial TOGGLE_FIRE;
	public static CompMaterial TOGGLE_MOBS;
	public static CompMaterial TOGGLE_EXPLOSIONS;
	public static CompMaterial TOGGLE_PVP;
	public static CompMaterial TOGGLE_PUBLIC;
	public static CompMaterial TOGGLE_OPEN;
	public static CompMaterial TOGGLE_TAX_PERCENTAGE;

	public static CompMaterial RESIDENT_KICK;
	public static CompMaterial RESIDENT_TITLE;
	public static CompMaterial RESIDENT_RANK;
	public static CompMaterial RESIDENT_MAYOR;

	public static CompMaterial BUILD;
	public static CompMaterial BREAK;
	public static CompMaterial ITEM_USE;
	public static CompMaterial SWITCH;

	public static CompMaterial RESIDENT_BUILD;
	public static CompMaterial RESIDENT_BREAK;
	public static CompMaterial RESIDENT_ITEM_USE;
	public static CompMaterial RESIDENT_SWITCH;

	public static CompMaterial NATION_BUILD;
	public static CompMaterial NATION_BREAK;
	public static CompMaterial NATION_ITEM_USE;
	public static CompMaterial NATION_SWITCH;

	public static CompMaterial ALLY_BUILD;
	public static CompMaterial ALLY_BREAK;
	public static CompMaterial ALLY_ITEM_USE;
	public static CompMaterial ALLY_SWITCH;

	public static CompMaterial OUTSIDER_BUILD;
	public static CompMaterial OUTSIDER_BREAK;
	public static CompMaterial OUTSIDER_ITEM_USE;
	public static CompMaterial OUTSIDER_SWITCH;

	public static CompMaterial RESET_ALL;
	public static CompMaterial ALL_ON;

	public static CompMaterial TOWN_BALANCE;
	public static CompMaterial DEPOSIT;
	public static CompMaterial WITHDRAW;
	public static CompMaterial SET_TAX;

	public static CompMaterial SET_TOWN_SPAWN;
	public static CompMaterial SET_HOME_BLOCK;
	public static CompMaterial SET_TOWN_BOARD;
	public static CompMaterial SET_TOWN_NAME;

	public static CompMaterial TOWN_CLAIM_INFO;
	public static CompMaterial EXTRA_COMMANDS;


	private static void init() {
		setPathPrefix(null);

		MONEY_SYMBOL = getString("Money_Symbol");
		ECONOMY_ENABLED = getBoolean("Economy");
		LOCALE = getString("Locale");

		FIND_TOWN = getMaterial("Find_Town");
		CREATE_TOWN = getMaterial("Create_Town");

//		DUMMY_BUTTON = getMaterial("Dummy_Button");

		FILLER_JOIN_TOWN_MENU = getMaterial("Filler_Join_Town_Menu");

		FILLER_TOWN_MENU = getMaterial("Filler_Town_Menu");
		FILLER_TOWN_TOGGLE = getMaterial("Filler_Town_Toggle");
//		FILLER_TOWN_INVITE = getMaterial("Filler_Town_Invite");
//		FILLER_RESIDENT_MENU = getMaterial("Filler_Resident_Menu");
		FILLER_TOWN_PERMS_MENU = getMaterial("Filler_Town_Perms_Menu");
		FILLER_TOWN_ECONOMY_MENU = getMaterial("Filler_Economy_Menu");
		FILLER_TOWN_GENERAL_SETTINGS_MENU = getMaterial("Filler_Town_General_Settings_Menu");
		FILLER_TOWN_EXTRA_INFO_MENU = getMaterial("Filler_Extra_Info_Menu");

		FILLER_PLOT_MENU = getMaterial("Filler_Plot_Menu");
//		FILLER_FRIENDS_LIST_MENU = getMaterial("Filler_Friends_List_Menu");
		FILLER_PLOT_TOGGLE_MENU = getMaterial("Filler_Plot_Toggle_Menu");
		FILLER_PLOT_PERMS_MENU = getMaterial("Filler_Plot_Perms_Menu");
		FILLER_PLOT_ADMIN_MENU = getMaterial("Filler_Plot_Admin_Menu");

		TOGGLE_MENU = getMaterial("Toggle_Menu");
		RESIDENT_LIST = getMaterial("Resident_List");
		PERMISSIONS_MENU = getMaterial("Permissions_Menu");
		ECONOMY_MENU = getMaterial("Economy_Menu");
		PLOT_MENU = getMaterial("Plot_Menu");
		SETTINGS_MENU = getMaterial("Settings_Menu");
		INVITE_MENU = getMaterial("Invite_Menu");
		EXTRA_INFO = getMaterial("Extra_Info");

		TOGGLE_FIRE = getMaterial("Toggle_Fire");
		TOGGLE_MOBS = getMaterial("Toggle_Mobs");
		TOGGLE_EXPLOSIONS = getMaterial("Toggle_Explosions");
		TOGGLE_PVP = getMaterial("Toggle_PVP");
		TOGGLE_PUBLIC = getMaterial("Toggle_Public");
		TOGGLE_OPEN = getMaterial("Toggle_Open");
		TOGGLE_TAX_PERCENTAGE = getMaterial("Toggle_Tax_Percentage");

		RESIDENT_KICK = getMaterial("Resident_Kick");
		RESIDENT_TITLE = getMaterial("Resident_Title");
		RESIDENT_RANK = getMaterial("Resident_Rank");
		RESIDENT_MAYOR = getMaterial("Resident_Mayor");

		BUILD = getMaterial("Build");
		BREAK = getMaterial("Break");
		ITEM_USE = getMaterial("Item_Use");
		SWITCH = getMaterial("Switch");

		RESIDENT_BUILD = getMaterial("Resident_Build");
		RESIDENT_BREAK = getMaterial("Resident_Break");
		RESIDENT_ITEM_USE = getMaterial("Resident_Item_Use");
		RESIDENT_SWITCH = getMaterial("Resident_Switch");

		NATION_BUILD = getMaterial("Nation_Build");
		NATION_BREAK = getMaterial("Nation_Break");
		NATION_ITEM_USE = getMaterial("Nation_Item_Use");
		NATION_SWITCH = getMaterial("Nation_Switch");

		ALLY_BUILD = getMaterial("Ally_Build");
		ALLY_BREAK = getMaterial("Ally_Break");
		ALLY_ITEM_USE = getMaterial("Ally_Item_Use");
		ALLY_SWITCH = getMaterial("Ally_Switch");

		OUTSIDER_BUILD = getMaterial("Outsider_Build");
		OUTSIDER_BREAK = getMaterial("Outsider_Break");
		OUTSIDER_ITEM_USE = getMaterial("Outsider_Item_Use");
		OUTSIDER_SWITCH = getMaterial("Outsider_Switch");

		RESET_ALL = getMaterial("Reset_All");
		ALL_ON = getMaterial("All_On");

		TOWN_BALANCE = getMaterial("Town_Balance");
		DEPOSIT = getMaterial("Deposit");
		WITHDRAW = getMaterial("Withdraw");
		SET_TAX = getMaterial("Set_Tax");

		SET_TOWN_SPAWN = getMaterial("Set_Town_Spawn");
		SET_HOME_BLOCK = getMaterial("Set_Home_Block");
		SET_TOWN_BOARD = getMaterial("Set_Town_Board");
		SET_TOWN_NAME = getMaterial("Set_Town_Name");

		TOWN_CLAIM_INFO = getMaterial("Town_Claim_Info");
		EXTRA_COMMANDS = getMaterial("Extra_Commands");

	}

}
