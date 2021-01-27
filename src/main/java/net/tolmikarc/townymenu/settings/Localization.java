package net.tolmikarc.townymenu.settings;

import org.mineacademy.fo.settings.SimpleLocalization;

import java.util.Collection;


public class Localization extends SimpleLocalization {
	@Override
	protected int getConfigVersion() {
		return 2;
	}

	public static String CONFIRM;
	public static String CANCEL;
	public static String MENU_INFORMATION;

	private static void init() {
		pathPrefix(null);
		CONFIRM = getString("Confirm");
		CANCEL = getString("Cancel");
		MENU_INFORMATION = getString("Menu_Information");
	}

	public static class TownMenu {

		public static String MAIN_MENU_TITLE;
		public static String TOGGLE_MENU_BUTTON;
		public static Collection<String> TOGGLE_MENU_BUTTON_LORE;
		public static String RESIDENT_MENU_BUTTON;
		public static Collection<String> RESIDENT_MENU_BUTTON_LORE;
		public static String PERMISSIONS_MENU_BUTTON;
		public static Collection<String> PERMISSIONS_MENU_BUTTON_LORE;
		public static String ECONOMY_MENU_BUTTON;
		public static Collection<String> ECONOMY_MENU_BUTTON_LORE;
		public static String GENERAL_SETTINGS_MENU_BUTTON;
		public static Collection<String> GENERAL_SETTINGS_MENU_BUTTON_LORE;
		public static String INVITE_PLAYER_MENU_BUTTON;
		public static Collection<String> INVITE_PLAYER_MENU_BUTTON_LORE;
		public static String EXTRA_INFO_MENU_BUTTON;
		public static Collection<String> EXTRA_INFO_MENU_BUTTON_LORE;
		public static String PLOT_MENU_BUTTON;
		public static Collection<String> PLOT_MENU_BUTTON_LORE;

		private static void init() {
			pathPrefix("Town_Menu");

			MAIN_MENU_TITLE = getString("Main_Menu_Title");
			TOGGLE_MENU_BUTTON = getString("Toggle_Menu_Button");
			TOGGLE_MENU_BUTTON_LORE = getStringList("Toggle_Menu_Button_Lore");
			RESIDENT_MENU_BUTTON = getString("Resident_Menu_Button");
			RESIDENT_MENU_BUTTON_LORE = getStringList("Resident_Menu_Button_Lore");
			PERMISSIONS_MENU_BUTTON = getString("Permissions_Menu_Button");
			PERMISSIONS_MENU_BUTTON_LORE = getStringList("Permissions_Menu_Button_Lore");
			ECONOMY_MENU_BUTTON = getString("Economy_Menu_Button");
			ECONOMY_MENU_BUTTON_LORE = getStringList("Economy_Menu_Button_Lore");
			GENERAL_SETTINGS_MENU_BUTTON = getString("General_Settings_Menu_Button");
			GENERAL_SETTINGS_MENU_BUTTON_LORE = getStringList("General_Settings_Menu_Button_Lore");
			INVITE_PLAYER_MENU_BUTTON = getString("Invite_Player_Menu_Button");
			INVITE_PLAYER_MENU_BUTTON_LORE = getStringList("Invite_Player_Menu_Button_Lore");
			EXTRA_INFO_MENU_BUTTON = getString("Extra_Info_Menu_Button");
			EXTRA_INFO_MENU_BUTTON_LORE = getStringList("Extra_Info_Menu_Button_Lore");
			PLOT_MENU_BUTTON = getString("Plot_Menu_Button");
			PLOT_MENU_BUTTON_LORE = getStringList("Plot_Menu_Button_Lore");
		}

		public static class ToggleMenu {
			public static String MENU_TITLE;
			public static String[] INFO;
			public static String TOGGLE_OFF;
			public static String TOGGLE_ON;
			public static String FIRE;
			public static String MOBS;
			public static String EXPLODE;
			public static String PVP;
			public static String PUBLIC;
			public static String OPEN;
			public static String TAX_PERCENT;


			private static void init() {
				pathPrefix("Toggle_Menu");

				MENU_TITLE = getString("Menu_Title");
				INFO = getStringList("Info").toArray(new String[0]);
				TOGGLE_OFF = getString("Toggle_Off");
				TOGGLE_ON = getString("Toggle_On");
				FIRE = getString("Fire");
				MOBS = getString("Mobs");
				EXPLODE = getString("Explode");
				PVP = getString("PVP");
				PUBLIC = getString("Public");
				OPEN = getString("Open");
				TAX_PERCENT = getString("Tax_Percent");
			}
		}

		public static class ResidentMenu {

			public static String MENU_TITLE;
			public static String[] INFO;
			public static String ONLINE;
			public static String KICK;
			public static Collection<String> KICK_LORE;
			public static String TITLE;
			public static Collection<String> TITLE_LORE;
			public static String RANK;
			public static Collection<String> RANK_LORE;
			public static String MAYOR;
			public static Collection<String> MAYOR_LORE;

			private static void init() {
				pathPrefix("Resident_Menu");
				MENU_TITLE = getString("Menu_Title");
				INFO = getStringList("Info").toArray(new String[0]);
				ONLINE = getString("Online");
				KICK = getString("Kick");
				KICK_LORE = getStringList("Kick_Lore");
				TITLE = getString("Title");
				TITLE_LORE = getStringList("Title_Lore");
				RANK = getString("Rank");
				RANK_LORE = getStringList("Rank_Lore");
				MAYOR = getString("Mayor");
				MAYOR_LORE = getStringList("Mayor_Lore");

			}
		}

		public static class PlayerPermissionsMenu {

			public static String MENU_TITLE;
			public static String[] INFO;
			public static String TRUE_MSG;
			public static String FALSE_MSG;
			public static String CHANGE;
			public static String RESET;
			public static Collection<String> RESET_LORE;
			public static String ON;
			public static Collection<String> ON_LORE;
			public static String BUILD;
			public static Collection<String> BUILD_LORE;
			public static String BREAK;
			public static Collection<String> BREAK_LORE;
			public static String USE;
			public static Collection<String> USE_LORE;
			public static String SWITCH;
			public static Collection<String> SWITCH_LORE;
			public static String BUILD_RES;
			public static String BUILD_RES2;
			public static String BUILD_NATION;
			public static String BUILD_NATION2;
			public static String BUILD_ALLY;
			public static String BUILD_ALLY2;
			public static String BUILD_OUTSIDER;
			public static String BUILD_OUTSIDER2;
			public static String BREAK_RES;
			public static String BREAK_RES2;
			public static String BREAK_NATION;
			public static String BREAK_NATION2;
			public static String BREAK_ALLY;
			public static String BREAK_ALLY2;
			public static String BREAK_OUTSIDER;
			public static String BREAK_OUTSIDER2;
			public static String USE_RES;
			public static String USE_RES2;
			public static String USE_NATION;
			public static String USE_NATION2;
			public static String USE_ALLY;
			public static String USE_ALLY2;
			public static String USE_OUTSIDER;
			public static String USE_OUTSIDER2;
			public static String SWITCH_RES;
			public static String SWITCH_RES2;
			public static String SWITCH_NATION;
			public static String SWITCH_NATION2;
			public static String SWITCH_ALLY;
			public static String SWITCH_ALLY2;
			public static String SWITCH_OUTSIDER;
			public static String SWITCH_OUTSIDER2;


			private static void init() {
				pathPrefix("Player_Permission_Menu");
				MENU_TITLE = getString("Menu_Title");
				INFO = getStringList("Info").toArray(new String[0]);
				TRUE_MSG = getString("True_Msg");
				FALSE_MSG = getString("False_Msg");
				CHANGE = getString("Change");
				RESET = getString("Reset");
				RESET_LORE = getStringList("Reset_Lore");
				ON = getString("On_Msg");
				ON_LORE = getStringList("On_Msg_Lore");
				BUILD = getString("Build");
				BUILD_LORE = getStringList("Build_Lore");
				BREAK = getString("Break");
				BREAK_LORE = getStringList("Break_Lore");
				USE = getString("Use");
				USE_LORE = getStringList("Use_Lore");
				SWITCH = getString("Switch");
				SWITCH_LORE = getStringList("Switch_Lore");
				BUILD_RES = getString("Build_Res");
				BUILD_RES2 = getString("Build_Res2");
				BUILD_NATION = getString("Build_Nation");
				BUILD_NATION2 = getString("Build_Nation2");
				BUILD_ALLY = getString("Build_Ally");
				BUILD_ALLY2 = getString("Build_Ally2");
				BUILD_OUTSIDER = getString("Build_Outsider");
				BUILD_OUTSIDER2 = getString("Build_Outsider2");
				BREAK_RES = getString("Break_Res");
				BREAK_RES2 = getString("Break_Res2");
				BREAK_NATION = getString("Break_Nation");
				BREAK_NATION2 = getString("Break_Nation2");
				BREAK_ALLY = getString("Break_Ally");
				BREAK_ALLY2 = getString("Break_Ally2");
				BREAK_OUTSIDER = getString("Break_Outsider");
				BREAK_OUTSIDER2 = getString("Break_Outsider2");
				USE_RES = getString("Use_Res");
				USE_RES2 = getString("Use_Res2");
				USE_NATION = getString("Use_Nation");
				USE_NATION2 = getString("Use_Nation2");
				USE_ALLY = getString("Use_Ally");
				USE_ALLY2 = getString("Use_Ally2");
				USE_OUTSIDER = getString("Use_Outsider");
				USE_OUTSIDER2 = getString("Use_Outsider2");
				SWITCH_RES = getString("Switch_Res");
				SWITCH_RES2 = getString("Switch_Res2");
				SWITCH_NATION = getString("Switch_Nation");
				SWITCH_NATION2 = getString("Switch_Nation2");
				SWITCH_ALLY = getString("Switch_Ally");
				SWITCH_ALLY2 = getString("Switch_Ally2");
				SWITCH_OUTSIDER = getString("Switch_Outsider");
				SWITCH_OUTSIDER2 = getString("Switch_Outsider2");


			}
		}

		public static class EconomyMenu {

			public static String MENU_TITLE;
			public static String BALANCE;
			public static String UPKEEP;
			public static String WITHDRAW;
			public static Collection<String> WITHDRAW_LORE;
			public static String DEPOSIT;
			public static Collection<String> DEPOSIT_LORE;
			public static String TAX;
			public static String TAX_PERCENTAGE;
			public static String TAX_AMOUNT;

			private static void init() {
				pathPrefix("Economy_Menu");

				MENU_TITLE = getString("Menu_Title");
				BALANCE = getString("Balance");
				UPKEEP = getString("Upkeep");
				WITHDRAW = getString("Withdraw");
				WITHDRAW_LORE = getStringList("Withdraw_Lore");
				DEPOSIT = getString("Deposit");
				DEPOSIT_LORE = getStringList("Deposit_Lore");
				TAX = getString("Tax");
				TAX_PERCENTAGE = getString("Tax_Percentage");
				TAX_AMOUNT = getString("Tax_Amount");

			}

		}

		public static class GeneralSettingsMenu {
			public static String MENU_TITLE;
			public static String[] INFO;
			public static String SET_HOME_BLOCK_MSG;
			public static String SPAWN_REMINDER;
			public static String SET_HOME_BLOCK;
			public static Collection<String> SET_HOME_BLOCK_LORE;
			public static String SET_SPAWN_MSG;
			public static String SET_SPAWN;
			public static Collection<String> SET_SPAWN_LORE;
			public static String SET_NAME;
			public static Collection<String> SET_NAME_LORE;
			public static String SET_BOARD;
			public static Collection<String> SET_BOARD_LORE;

			private static void init() {
				pathPrefix("General_Settings_Menu");

				MENU_TITLE = getString("Menu_Title");
				INFO = getStringList("Info").toArray(new String[0]);
				SET_HOME_BLOCK_MSG = getString("Set_Home_Block_Msg");
				SPAWN_REMINDER = getString("Spawn_Reminder");
				SET_HOME_BLOCK = getString("Set_Home_Block");
				SET_HOME_BLOCK_LORE = getStringList("Set_Home_Block_Lore");
				SET_SPAWN_MSG = getString("Set_Spawn_Msg");
				SET_SPAWN = getString("Set_Spawn");
				SET_SPAWN_LORE = getStringList("Set_Spawn_Lore");
				SET_NAME = getString("Set_Name");
				SET_NAME_LORE = getStringList("Set_Name_Lore");
				SET_BOARD = getString("Set_Board");
				SET_BOARD_LORE = getStringList("Set_Board_Lore");
			}

		}

		public static class ExtraInfoMenu {

			public static String CLAIMING;
			public static Collection<String> CLAIMING_LORE;
			public static String COMMANDS;
			public static Collection<String> COMMANDS_LORE;

			private static void init() {
				pathPrefix("Extra_Info_Menu");

				CLAIMING = getString("Claiming");
				CLAIMING_LORE = getStringList("Claiming_Lore");
				COMMANDS = getString("Commands");
				COMMANDS_LORE = getStringList("Commands_Lore");


			}

		}

	}

	public static class PlotMenu {

		public static String MAIN_MENU_TITLE;
		public static String TOGGLE_SETTINGS_MENU_BUTTON;
		public static String[] TOGGLE_SETTINGS_MENU_BUTTON_LORE;
		public static String PERMISSIONS_MENU_BUTTON;
		public static String[] PERMISSIONS_MENU_BUTTON_LORE;
		public static String PLOT_ADMIN_MENU_BUTTON;
		public static String[] PLOT_ADMIN_MENU_BUTTON_LORE;
		public static String FRIEND_MENU_BUTTON;
		public static String[] FRIEND_MENU_BUTTON_LORE;


		private static void init() {
			pathPrefix("Plot_Menu");
			MAIN_MENU_TITLE = getString("Main_Menu_Title");
			TOGGLE_SETTINGS_MENU_BUTTON = getString("Toggle_Settings_Menu_Button");
			TOGGLE_SETTINGS_MENU_BUTTON_LORE = getStringList("Toggle_Settings_Menu_Button_Lore").toArray(new String[0]);
			PERMISSIONS_MENU_BUTTON = getString("Permissions_Menu_Button");
			PERMISSIONS_MENU_BUTTON_LORE = getStringList("Permissions_Menu_Button_Lore").toArray(new String[0]);
			PLOT_ADMIN_MENU_BUTTON = getString("Plot_Admin_Menu_Button");
			PLOT_ADMIN_MENU_BUTTON_LORE = getStringList("Plot_Admin_Menu_Button_Lore").toArray(new String[0]);
			FRIEND_MENU_BUTTON = getString("Friend_Menu_Button");
			FRIEND_MENU_BUTTON_LORE = getStringList("Friend_Menu_Button_Lore").toArray(new String[0]);


		}

		public static class FriendMenu {

			public static String MENU_TITLE;
			public static String REMOVE;
			public static String ADD;


			private static void init() {
				pathPrefix("Friend_Menu");
				MENU_TITLE = getString("Menu_Title");
				REMOVE = getString("Remove");
				ADD = getString("Add");

			}

		}

		public static class ToggleMenu {
			public static String MENU_TITLE;
			public static String[] INFO;
			public static String TOGGLE_OFF;
			public static String TOGGLE_ON;
			public static String FIRE;
			public static String MOBS;
			public static String EXPLODE;
			public static String PVP;


			private static void init() {
				pathPrefix("Plot_Toggle_Menu");

				MENU_TITLE = getString("Menu_Title");
				INFO = getStringList("Info").toArray(new String[0]);
				TOGGLE_OFF = getString("Toggle_Off");
				TOGGLE_ON = getString("Toggle_On");
				FIRE = getString("Fire");
				MOBS = getString("Mobs");
				EXPLODE = getString("Explode");
				PVP = getString("PVP");
			}
		}

		public static class PlayerPermissionsMenu {

			public static String MENU_TITLE;
			public static String[] INFO;
			public static String TRUE_MSG;
			public static String FALSE_MSG;
			public static String CHANGE;
			public static String RESET;
			public static Collection<String> RESET_LORE;
			public static String ON;
			public static Collection<String> ON_LORE;
			public static String BUILD;
			public static Collection<String> BUILD_LORE;
			public static String BREAK;
			public static Collection<String> BREAK_LORE;
			public static String USE;
			public static Collection<String> USE_LORE;
			public static String SWITCH;
			public static Collection<String> SWITCH_LORE;
			public static String BUILD_RES;
			public static String BUILD_RES2;
			public static String BUILD_NATION;
			public static String BUILD_NATION2;
			public static String BUILD_ALLY;
			public static String BUILD_ALLY2;
			public static String BUILD_OUTSIDER;
			public static String BUILD_OUTSIDER2;
			public static String BREAK_RES;
			public static String BREAK_RES2;
			public static String BREAK_NATION;
			public static String BREAK_NATION2;
			public static String BREAK_ALLY;
			public static String BREAK_ALLY2;
			public static String BREAK_OUTSIDER;
			public static String BREAK_OUTSIDER2;
			public static String USE_RES;
			public static String USE_RES2;
			public static String USE_NATION;
			public static String USE_NATION2;
			public static String USE_ALLY;
			public static String USE_ALLY2;
			public static String USE_OUTSIDER;
			public static String USE_OUTSIDER2;
			public static String SWITCH_RES;
			public static String SWITCH_RES2;
			public static String SWITCH_NATION;
			public static String SWITCH_NATION2;
			public static String SWITCH_ALLY;
			public static String SWITCH_ALLY2;
			public static String SWITCH_OUTSIDER;
			public static String SWITCH_OUTSIDER2;


			private static void init() {
				pathPrefix("Plot_Player_Permission_Menu");
				MENU_TITLE = getString("Menu_Title");
				INFO = getStringList("Info").toArray(new String[0]);
				TRUE_MSG = getString("True_Msg");
				FALSE_MSG = getString("False_Msg");
				CHANGE = getString("Change");
				RESET = getString("Reset");
				RESET_LORE = getStringList("Reset_Lore");
				ON = getString("On_Msg");
				ON_LORE = getStringList("On_Msg_Lore");
				BUILD = getString("Build");
				BUILD_LORE = getStringList("Build_Lore");
				BREAK = getString("Break");
				BREAK_LORE = getStringList("Break_Lore");
				USE = getString("Use");
				USE_LORE = getStringList("Use_Lore");
				SWITCH = getString("Switch");
				SWITCH_LORE = getStringList("Switch_Lore");
				BUILD_RES = getString("Build_Res");
				BUILD_RES2 = getString("Build_Res2");
				BUILD_NATION = getString("Build_Nation");
				BUILD_NATION2 = getString("Build_Nation2");
				BUILD_ALLY = getString("Build_Ally");
				BUILD_ALLY2 = getString("Build_Ally2");
				BUILD_OUTSIDER = getString("Build_Outsider");
				BUILD_OUTSIDER2 = getString("Build_Outsider2");
				BREAK_RES = getString("Break_Res");
				BREAK_RES2 = getString("Break_Res2");
				BREAK_NATION = getString("Break_Nation");
				BREAK_NATION2 = getString("Break_Nation2");
				BREAK_ALLY = getString("Break_Ally");
				BREAK_ALLY2 = getString("Break_Ally2");
				BREAK_OUTSIDER = getString("Break_Outsider");
				BREAK_OUTSIDER2 = getString("Break_Outsider2");
				USE_RES = getString("Use_Res");
				USE_RES2 = getString("Use_Res");
				USE_NATION = getString("Use_Nation");
				USE_NATION2 = getString("Use_Nation2");
				USE_ALLY = getString("Use_Ally");
				USE_ALLY2 = getString("Use_Ally2");
				USE_OUTSIDER = getString("Use_Outsider");
				USE_OUTSIDER2 = getString("Use_Outsider2");
				SWITCH_RES = getString("Switch_Res");
				SWITCH_RES2 = getString("Switch_Res2");
				SWITCH_NATION = getString("Switch_Nation");
				SWITCH_NATION2 = getString("Switch_Nation2");
				SWITCH_ALLY = getString("Switch_Ally");
				SWITCH_ALLY2 = getString("Switch_Ally2");
				SWITCH_OUTSIDER = getString("Switch_Outsider");
				SWITCH_OUTSIDER2 = getString("Switch_Outsider2");


			}
		}

		public static class PlotAdminMenu {

			public static String MENU_TITLE;
			public static String FOR_SALE;
			public static String[] FOR_SALE_LORE;
			public static String NOT_FOR_SALE;
			public static String[] NOT_FOR_SALE_LORE;
			public static String SET_TYPE;
			public static String[] SET_TYPE_LORE;
			public static String EVICT;
			public static String[] EVICT_LORE;


			private static void init() {
				pathPrefix("Plot_Admin_Menu");
				MENU_TITLE = getString("Menu_Title");
				FOR_SALE = getString("For_Sale");
				FOR_SALE_LORE = getStringList("For_Sale_Lore").toArray(new String[0]);
				NOT_FOR_SALE = getString("Not_For_Sale");
				NOT_FOR_SALE_LORE = getStringList("Not_For_Sale_Lore").toArray(new String[0]);
				SET_TYPE = getString("Set_Type");
				SET_TYPE_LORE = getStringList("Set_Type_Lore").toArray(new String[0]);
				EVICT = getString("Evict");
				EVICT_LORE = getStringList("Evict_Lore").toArray(new String[0]);

			}


		}


	}


	public static class TownConversables {


		public static class Board {
			public static String PROMPT;
			public static String RESPONSE;

			private static void init() {
				pathPrefix("Town_Conversables.Board");
				PROMPT = getString("Prompt");
				RESPONSE = getString("Response");
			}
		}

		public static class Deposit {
			public static String PROMPT;
			public static String INVALID;
			public static String RESPONSE;

			private static void init() {
				pathPrefix("Town_Conversables.Deposit");
				PROMPT = getString("Prompt");
				INVALID = getString("Invalid");
				RESPONSE = getString("Response");
			}
		}

		public static class Mayor {
			public static String PROMPT;
			public static String RESPONSE;

			private static void init() {
				pathPrefix("Town_Conversables.Mayor");
				PROMPT = getString("Prompt");
				RESPONSE = getString("Response");
			}
		}

		public static class Kick {
			public static String PROMPT;
			public static String RESPONSE;

			private static void init() {
				pathPrefix("Town_Conversables.Kick");
				PROMPT = getString("Prompt");
				RESPONSE = getString("Response");
			}
		}

		public static class Name {
			public static String PROMPT;
			public static String INVALID;
			public static String RESPONSE;
			public static String RETURN;

			private static void init() {
				pathPrefix("Town_Conversables.Name");
				PROMPT = getString("Prompt");
				INVALID = getString("Invalid");
				RESPONSE = getString("Response");
				RETURN = getString("Return");
			}
		}

		public static class Rank {
			public static String PROMPT;
			public static String INVALID;
			public static String RESPONSE;
			public static String REMOVE;
			public static String REMOVED_ALL;

			private static void init() {
				pathPrefix("Town_Conversables.Rank");
				PROMPT = getString("Prompt");
				INVALID = getString("Invalid");
				RESPONSE = getString("Response");
				REMOVE = getString("Remove");
				REMOVED_ALL = getString("Removed_All");
			}
		}

		public static class Tax {
			public static String PROMPT;
			public static String INVALID_PERCENT;
			public static String INVALID_AMOUNT;
			public static String RESPONSE_AMOUNT;
			public static String RESPONSE_PERCENT;

			private static void init() {
				pathPrefix("Town_Conversables.Tax");
				PROMPT = getString("Prompt");
				INVALID_AMOUNT = getString("Invalid_Amount");
				INVALID_PERCENT = getString("Invalid_Percent");
				RESPONSE_PERCENT = getString("Response_Percent");
				RESPONSE_AMOUNT = getString("Response_Amount");
			}
		}

		public static class Title {
			public static String PROMPT;
			public static String RESPONSE;

			private static void init() {
				pathPrefix("Town_Conversables.Title");
				PROMPT = getString("Prompt");
				RESPONSE = getString("Response");
			}
		}

		public static class Withdraw {
			public static String PROMPT;
			public static String INVALID;
			public static String RESPONSE;

			private static void init() {
				pathPrefix("Town_Conversables.Withdraw");
				PROMPT = getString("Prompt");
				INVALID = getString("Invalid");
				RESPONSE = getString("Response");
			}
		}


	}

	public static class PlotConversables {

		public static class Evict {
			public static String PROMPT;
			public static String INVALID;
			public static String RESPONSE;

			private static void init() {
				pathPrefix("Plot_Conversables.Evict");
				PROMPT = getString("Prompt");
				INVALID = getString("Invalid");
				RESPONSE = getString("Response");
			}
		}

		public static class ForSale {
			public static String PROMPT;
			public static String INVALID;
			public static String RESPONSE;

			private static void init() {
				pathPrefix("Plot_Conversables.For_Sale");
				PROMPT = getString("Prompt");
				INVALID = getString("Invalid");
				RESPONSE = getString("Response");
			}
		}

		public static class NotForSale {
			public static String PROMPT;
			public static String RESPONSE;

			private static void init() {
				pathPrefix("Plot_Conversables.Evict");
				PROMPT = getString("Prompt");
				RESPONSE = getString("Response");
			}
		}

		public static class SetType {
			public static String PROMPT;
			public static String INVALID;
			public static String RESPONSE;

			private static void init() {
				pathPrefix("Plot_Conversables.Set_Type");
				PROMPT = getString("Prompt");
				INVALID = getString("Invalid");
				RESPONSE = getString("Response");
			}
		}


	}


	public static class Error {

		public static String NO_PERMISSION;
		public static String NO_TOWN;
		public static String WAR_TIME;
		public static String MUST_BE_IN_OWN_PLOT;
		public static String MUST_BE_IN_TOWN;
		public static String MUST_BE_IN_BANK;
		public static String TOGGLE_PVP_OUTSIDERS;
		public static String TOGGLE_PVP_COOLDOWN;
		public static String CANNOT_SELECT_SELF;
		public static String CANNOT_SET_HOMEBLOCK;
		public static String CANNOT_SET_SPAWN;
		public static String CANNOT_CHANGE_NAME;
		public static String CANNOT_CHANGE_BOARD;

		private static void init() {
			pathPrefix("Error");

			NO_PERMISSION = getString("No_Permission");
			NO_TOWN = getString("No_Town");
			WAR_TIME = getString("War_Time");
			MUST_BE_IN_OWN_PLOT = getString("Must_Be_In_Own_Plot");
			MUST_BE_IN_TOWN = getString("Must_Be_In_Town");
			MUST_BE_IN_BANK = getString("Must_Be_In_Bank");
			TOGGLE_PVP_OUTSIDERS = getString("Toggle_PVP_Outsiders");
			TOGGLE_PVP_COOLDOWN = getString("Toggle_PVP_Cooldown");
			CANNOT_SELECT_SELF = getString("Cannot_Select_Self");
			CANNOT_SET_HOMEBLOCK = getString("Cannot_Set_Homeblock");
			CANNOT_SET_SPAWN = getString("Cannot_Set_Spawn");
			CANNOT_CHANGE_NAME = getString("Cannot_Change_Name");
			CANNOT_CHANGE_BOARD = getString("Cannot_Change_Board");

		}
	}

}
