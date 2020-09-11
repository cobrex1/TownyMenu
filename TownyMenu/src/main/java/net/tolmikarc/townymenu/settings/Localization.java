package net.tolmikarc.townymenu.settings;

import org.mineacademy.fo.settings.SimpleLocalization;


public class Localization extends SimpleLocalization {
	@Override
	protected int getConfigVersion() {
		return 1;
	}

	public static String CONFIRM;
	public static String CANCEL;

	private static void init() {
		pathPrefix(null);
		CONFIRM = getString("Confirm");
		CANCEL = getString("Cancel");
	}

	public static class TownMenu {

		public static String MAIN_MENU_TITLE;
		public static String TOGGLE_MENU_BUTTON;
		public static String TOGGLE_MENU_BUTTON_LORE1;
		public static String TOGGLE_MENU_BUTTON_LORE2;
		public static String RESIDENT_MENU_BUTTON;
		public static String RESIDENT_MENU_BUTTON_LORE1;
		public static String RESIDENT_MENU_BUTTON_LORE2;
		public static String PERMISSIONS_MENU_BUTTON;
		public static String PERMISSIONS_MENU_BUTTON_LORE1;
		public static String PERMISSIONS_MENU_BUTTON_LORE2;
		public static String PERMISSIONS_MENU_BUTTON_LORE3;
		public static String ECONOMY_MENU_BUTTON;
		public static String ECONOMY_MENU_BUTTON_LORE1;
		public static String GENERAL_SETTINGS_MENU_BUTTON;
		public static String GENERAL_SETTINGS_MENU_BUTTON_LORE1;
		public static String GENERAL_SETTINGS_MENU_BUTTON_LORE2;
		public static String INVITE_PLAYER_MENU_BUTTON;
		public static String INVITE_PLAYER_MENU_BUTTON_LORE1;
		public static String INVITE_PLAYER_MENU_BUTTON_LORE2;
		public static String EXTRA_INFO_MENU_BUTTON;
		public static String EXTRA_INFO_MENU_BUTTON_LORE1;
		public static String EXTRA_INFO_MENU_BUTTON_LORE2;
		public static String PLOT_MENU_BUTTON;
		public static String PLOT_MENU_BUTTON_LORE1;
		public static String PLOT_MENU_BUTTON_LORE2;

		private static void init() {
			pathPrefix("Town_Menu");

			MAIN_MENU_TITLE = getString("Main_Menu_Title");
			TOGGLE_MENU_BUTTON = getString("Toggle_Menu_Button");
			TOGGLE_MENU_BUTTON_LORE1 = getString("Toggle_Menu_Button_Lore1");
			TOGGLE_MENU_BUTTON_LORE2 = getString("Toggle_Menu_Button_Lore2");
			RESIDENT_MENU_BUTTON = getString("Resident_Menu_Button");
			RESIDENT_MENU_BUTTON_LORE1 = getString("Resident_Menu_Button_Lore1");
			RESIDENT_MENU_BUTTON_LORE2 = getString("Resident_Menu_Button_Lore2");
			PERMISSIONS_MENU_BUTTON = getString("Permissions_Menu_Button");
			PERMISSIONS_MENU_BUTTON_LORE1 = getString("Permissions_Menu_Button_Lore1");
			PERMISSIONS_MENU_BUTTON_LORE2 = getString("Permissions_Menu_Button_Lore2");
			PERMISSIONS_MENU_BUTTON_LORE3 = getString("Permissions_Menu_Button_Lore3");
			ECONOMY_MENU_BUTTON = getString("Economy_Menu_Button");
			ECONOMY_MENU_BUTTON_LORE1 = getString("Economy_Menu_Button_Lore1");
			GENERAL_SETTINGS_MENU_BUTTON = getString("General_Settings_Menu_Button");
			GENERAL_SETTINGS_MENU_BUTTON_LORE1 = getString("General_Settings_Menu_Button_Lore1");
			GENERAL_SETTINGS_MENU_BUTTON_LORE2 = getString("General_Settings_Menu_Button_Lore2");
			INVITE_PLAYER_MENU_BUTTON = getString("Invite_Player_Menu_Button");
			INVITE_PLAYER_MENU_BUTTON_LORE1 = getString("Invite_Player_Menu_Button_Lore1");
			INVITE_PLAYER_MENU_BUTTON_LORE2 = getString("Invite_Player_Menu_Button_Lore2");
			EXTRA_INFO_MENU_BUTTON = getString("Extra_Info_Menu_Button");
			EXTRA_INFO_MENU_BUTTON_LORE1 = getString("Extra_Info_Menu_Button_Lore1");
			EXTRA_INFO_MENU_BUTTON_LORE2 = getString("Extra_Info_Menu_Button_Lore2");
			PLOT_MENU_BUTTON = getString("Plot_Menu_Button");
			PLOT_MENU_BUTTON_LORE1 = getString("Plot_Menu_Button_Lore1");
			PLOT_MENU_BUTTON_LORE2 = getString("Plot_Menu_Button_Lore2");
		}

		public static class ToggleMenu {
			public static String MENU_TITLE;
			public static String INFO1;
			public static String INFO2;
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
				INFO1 = getString("Info1");
				INFO2 = getString("Info2");
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
			public static String INFO1;
			public static String INFO2;
			public static String ONLINE;
			public static String KICK;
			public static String KICK_LORE1;
			public static String KICK_LORE2;
			public static String TITLE;
			public static String TITLE_LORE1;
			public static String TITLE_LORE2;
			public static String RANK;
			public static String RANK_LORE1;
			public static String RANK_LORE2;
			public static String MAYOR;
			public static String MAYOR_LORE1;
			public static String MAYOR_LORE2;
			public static String MAYOR_LORE3;

			private static void init() {
				pathPrefix("Resident_Menu");
				MENU_TITLE = getString("Menu_Title");
				INFO1 = getString("Info1");
				INFO2 = getString("Info2");
				ONLINE = getString("Online");
				KICK = getString("Kick");
				KICK_LORE1 = getString("Kick_Lore1");
				KICK_LORE2 = getString("Kick_Lore2");
				TITLE = getString("Title");
				TITLE_LORE1 = getString("Title_Lore1");
				TITLE_LORE2 = getString("Title_Lore2");
				RANK = getString("Rank");
				RANK_LORE1 = getString("Rank_Lore1");
				RANK_LORE2 = getString("Rank_Lore2");
				MAYOR = getString("Mayor");
				MAYOR_LORE1 = getString("Mayor_Lore1");
				MAYOR_LORE2 = getString("Mayor_Lore2");
				MAYOR_LORE3 = getString("Mayor_Lore3");

			}
		}

		public static class PlayerPermissionsMenu {

			public static String MENU_TITLE;
			public static String INFO1;
			public static String INFO2;
			public static String INFO3;
			public static String TRUE_MSG;
			public static String FALSE_MSG;
			public static String CHANGE;
			public static String RESET;
			public static String RESET2;
			public static String RESET3;
			public static String ON;
			public static String ON2;
			public static String ON3;
			public static String BUILD;
			public static String BUILD_LORE1;
			public static String BUILD_LORE2;
			public static String BUILD_LORE3;
			public static String BREAK;
			public static String BREAK_LORE1;
			public static String BREAK_LORE2;
			public static String BREAK_LORE3;
			public static String USE;
			public static String USE_LORE1;
			public static String USE_LORE2;
			public static String USE_LORE3;
			public static String USE_LORE4;
			public static String SWITCH;
			public static String SWITCH_LORE1;
			public static String SWITCH_LORE2;
			public static String SWITCH_LORE3;
			public static String SWITCH_LORE4;
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
				INFO1 = getString("Info1");
				INFO2 = getString("Info2");
				INFO3 = getString("Info3");
				TRUE_MSG = getString("True_Msg");
				FALSE_MSG = getString("False_Msg");
				CHANGE = getString("Change");
				RESET = getString("Reset");
				RESET2 = getString("Reset2");
				RESET3 = getString("Reset3");
				ON = getString("On_Msg");
				ON2 = getString("On_Msg2");
				ON3 = getString("On_Msg3");
				BUILD = getString("Build");
				BUILD_LORE1 = getString("Build_Lore1");
				BUILD_LORE2 = getString("Build_Lore2");
				BUILD_LORE3 = getString("Build_Lore3");
				BREAK = getString("Break");
				BREAK_LORE1 = getString("Break_Lore1");
				BREAK_LORE2 = getString("Break_Lore2");
				BREAK_LORE3 = getString("Break_Lore3");
				USE = getString("Use");
				USE_LORE1 = getString("Use_Lore1");
				USE_LORE2 = getString("Use_Lore2");
				USE_LORE3 = getString("Use_Lore3");
				USE_LORE4 = getString("Use_Lore4");
				SWITCH = getString("Switch");
				SWITCH_LORE1 = getString("Switch_Lore1");
				SWITCH_LORE2 = getString("Switch_Lore2");
				SWITCH_LORE3 = getString("Switch_Lore3");
				SWITCH_LORE4 = getString("Switch_Lore4");
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

		public static class EconomyMenu {

			public static String MENU_TITLE;
			public static String BALANCE;
			public static String UPKEEP;
			public static String WITHDRAW;
			public static String WITHDRAW2;
			public static String WITHDRAW3;
			public static String DEPOSIT;
			public static String DEPOSIT2;
			public static String DEPOSIT3;
			public static String TAX;
			public static String TAX_PERCENTAGE;
			public static String TAX_AMOUNT;

			private static void init() {
				pathPrefix("Economy_Menu");

				MENU_TITLE = getString("Menu_Title");
				BALANCE = getString("Balance");
				UPKEEP = getString("Upkeep");
				WITHDRAW = getString("Withdraw");
				WITHDRAW2 = getString("Withdraw2");
				WITHDRAW3 = getString("Withdraw3");
				DEPOSIT = getString("Deposit");
				DEPOSIT2 = getString("Deposit2");
				DEPOSIT3 = getString("Deposit3");
				TAX = getString("Tax");
				TAX_PERCENTAGE = getString("Tax_Percentage");
				TAX_AMOUNT = getString("Tax_Amount");

			}

		}

		public static class GeneralSettingsMenu {
			public static String MENU_TITLE;
			public static String INFO1;
			public static String INFO2;
			public static String SET_HOME_BLOCK_MSG;
			public static String SPAWN_REMINDER;
			public static String SET_HOME_BLOCK;
			public static String SET_HOME_BLOCK2;
			public static String SET_HOME_BLOCK3;
			public static String SET_SPAWN_MSG;
			public static String SET_SPAWN;
			public static String SET_SPAWN2;
			public static String SET_SPAWN3;
			public static String SET_SPAWN4;
			public static String SET_SPAWN5;
			public static String SET_NAME;
			public static String SET_NAME2;
			public static String SET_NAME3;
			public static String SET_BOARD;
			public static String SET_BOARD2;
			public static String SET_BOARD3;

			private static void init() {
				pathPrefix("General_Settings_Menu");

				MENU_TITLE = getString("Menu_Title");
				INFO1 = getString("Info1");
				INFO2 = getString("Info2");
				SET_HOME_BLOCK_MSG = getString("Set_Home_Block_Msg");
				SPAWN_REMINDER = getString("Spawn_Reminder");
				SET_HOME_BLOCK = getString("Set_Home_Block");
				SET_HOME_BLOCK2 = getString("Set_Home_Block2");
				SET_HOME_BLOCK3 = getString("Set_Home_Block3");
				SET_SPAWN_MSG = getString("Set_Spawn_Msg");
				SET_SPAWN = getString("Set_Spawn");
				SET_SPAWN2 = getString("Set_Spawn2");
				SET_SPAWN3 = getString("Set_Spawn3");
				SET_SPAWN4 = getString("Set_Spawn4");
				SET_SPAWN5 = getString("Set_Spawn5");
				SET_NAME = getString("Set_Name");
				SET_NAME2 = getString("Set_Name2");
				SET_NAME3 = getString("Set_Name3");
				SET_BOARD = getString("Set_Board");
				SET_BOARD2 = getString("Set_Board2");
				SET_BOARD3 = getString("Set_Board3");


			}

		}

		public static class ExtraInfoMenu {

			public static String CLAIMING;
			public static String CLAIMING2;
			public static String CLAIMING3;
			public static String CLAIMING4;
			public static String CLAIMING5;
			public static String CLAIMING6;
			public static String CLAIMING7;
			public static String CLAIMING8;
			public static String COMMANDS;
			public static String COMMANDS2;
			public static String COMMANDS3;
			public static String COMMANDS4;
			public static String COMMANDS5;

			private static void init() {
				pathPrefix("Extra_Info_Menu");

				CLAIMING = getString("Claiming");
				CLAIMING2 = getString("Claiming2");
				CLAIMING3 = getString("Claiming3");
				CLAIMING4 = getString("Claiming4");
				CLAIMING5 = getString("Claiming5");
				CLAIMING6 = getString("Claiming6");
				CLAIMING7 = getString("Claiming7");
				CLAIMING8 = getString("Claiming8");
				COMMANDS = getString("Commands");
				COMMANDS2 = getString("Commands2");
				COMMANDS3 = getString("Commands3");
				COMMANDS4 = getString("Commands4");
				COMMANDS5 = getString("Commands5");


			}

		}

	}

	public static class PlotMenu {

		public static String MAIN_MENU_TITLE;
		public static String TOGGLE_SETTINGS_MENU_BUTTON;
		public static String TOGGLE_SETTINGS_MENU_BUTTON_LORE1;
		public static String TOGGLE_SETTINGS_MENU_BUTTON_LORE2;
		public static String PERMISSIONS_MENU_BUTTON;
		public static String PERMISSIONS_MENU_BUTTON_LORE1;
		public static String PERMISSIONS_MENU_BUTTON_LORE2;
		public static String PERMISSIONS_MENU_BUTTON_LORE3;
		public static String PLOT_ADMIN_MENU_BUTTON;
		public static String PLOT_ADMIN_MENU_BUTTON_LORE1;
		public static String PLOT_ADMIN_MENU_BUTTON_LORE2;
		public static String FRIEND_MENU_BUTTON;
		public static String FRIEND_MENU_BUTTON_LORE1;


		private static void init() {
			pathPrefix("Plot_Menu");
			MAIN_MENU_TITLE = getString("Main_Menu_Title");
			TOGGLE_SETTINGS_MENU_BUTTON = getString("Toggle_Settings_Menu_Button");
			TOGGLE_SETTINGS_MENU_BUTTON_LORE1 = getString("Toggle_Settings_Menu_Button_Lore1");
			TOGGLE_SETTINGS_MENU_BUTTON_LORE2 = getString("Toggle_Settings_Menu_Button_Lore2");
			PERMISSIONS_MENU_BUTTON = getString("Permissions_Menu_Button");
			PERMISSIONS_MENU_BUTTON_LORE1 = getString("Permissions_Menu_Button_Lore1");
			PERMISSIONS_MENU_BUTTON_LORE2 = getString("Permissions_Menu_Button_Lore2");
			PERMISSIONS_MENU_BUTTON_LORE3 = getString("Permissions_Menu_Button_Lore3");
			PLOT_ADMIN_MENU_BUTTON = getString("Plot_Admin_Menu_Button");
			PLOT_ADMIN_MENU_BUTTON_LORE1 = getString("Plot_Admin_Menu_Button_Lore1");
			PLOT_ADMIN_MENU_BUTTON_LORE2 = getString("Plot_Admin_Menu_Button_Lore2");
			FRIEND_MENU_BUTTON = getString("Friend_Menu_Button");
			FRIEND_MENU_BUTTON_LORE1 = getString("Friend_Menu_Button_Lore1");


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
			public static String INFO1;
			public static String INFO2;
			public static String TOGGLE_OFF;
			public static String TOGGLE_ON;
			public static String FIRE;
			public static String MOBS;
			public static String EXPLODE;
			public static String PVP;


			private static void init() {
				pathPrefix("Plot_Toggle_Menu");

				MENU_TITLE = getString("Menu_Title");
				INFO1 = getString("Info1");
				INFO2 = getString("Info2");
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
			public static String INFO1;
			public static String INFO2;
			public static String INFO3;
			public static String TRUE_MSG;
			public static String FALSE_MSG;
			public static String CHANGE;
			public static String RESET;
			public static String RESET2;
			public static String RESET3;
			public static String ON;
			public static String ON2;
			public static String ON3;
			public static String BUILD;
			public static String BUILD_LORE1;
			public static String BUILD_LORE2;
			public static String BUILD_LORE3;
			public static String BREAK;
			public static String BREAK_LORE1;
			public static String BREAK_LORE2;
			public static String BREAK_LORE3;
			public static String USE;
			public static String USE_LORE1;
			public static String USE_LORE2;
			public static String USE_LORE3;
			public static String USE_LORE4;
			public static String SWITCH;
			public static String SWITCH_LORE1;
			public static String SWITCH_LORE2;
			public static String SWITCH_LORE3;
			public static String SWITCH_LORE4;
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
				INFO1 = getString("Info1");
				INFO2 = getString("Info2");
				INFO3 = getString("Info3");
				TRUE_MSG = getString("True_Msg");
				FALSE_MSG = getString("False_Msg");
				CHANGE = getString("Change");
				RESET = getString("Reset");
				RESET2 = getString("Reset2");
				RESET3 = getString("Reset3");
				ON = getString("On_Msg");
				ON2 = getString("On_Msg2");
				ON3 = getString("On_Msg3");
				BUILD = getString("Build");
				BUILD_LORE1 = getString("Build_Lore1");
				BUILD_LORE2 = getString("Build_Lore2");
				BUILD_LORE3 = getString("Build_Lore3");
				BREAK = getString("Break");
				BREAK_LORE1 = getString("Break_Lore1");
				BREAK_LORE2 = getString("Break_Lore2");
				BREAK_LORE3 = getString("Break_Lore3");
				USE = getString("Use");
				USE_LORE1 = getString("Use_Lore1");
				USE_LORE2 = getString("Use_Lore2");
				USE_LORE3 = getString("Use_Lore3");
				USE_LORE4 = getString("Use_Lore4");
				SWITCH = getString("Switch");
				SWITCH_LORE1 = getString("Switch_Lore1");
				SWITCH_LORE2 = getString("Switch_Lore2");
				SWITCH_LORE3 = getString("Switch_Lore3");
				SWITCH_LORE4 = getString("Switch_Lore4");
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
			public static String FOR_SALE2;
			public static String FOR_SALE3;
			public static String FOR_SALE4;
			public static String NOT_FOR_SALE;
			public static String NOT_FOR_SALE2;
			public static String NOT_FOR_SALE3;
			public static String SET_TYPE;
			public static String SET_TYPE2;
			public static String SET_TYPE3;
			public static String EVICT;
			public static String EVICT2;
			public static String EVICT3;


			private static void init() {
				pathPrefix("Plot_Admin_Menu");
				MENU_TITLE = getString("Menu_Title");
				FOR_SALE = getString("For_Sale");
				FOR_SALE2 = getString("For_Sale2");
				FOR_SALE3 = getString("For_Sale3");
				FOR_SALE4 = getString("For_Sale4");
				NOT_FOR_SALE = getString("Not_For_Sale");
				NOT_FOR_SALE2 = getString("Not_For_Sale2");
				NOT_FOR_SALE3 = getString("Not_For_Sale3");
				SET_TYPE = getString("Set_Type");
				SET_TYPE2 = getString("Set_Type2");
				SET_TYPE3 = getString("Set_Type3");
				EVICT = getString("Evict");
				EVICT2 = getString("Evict2");
				EVICT3 = getString("Evict3");

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
