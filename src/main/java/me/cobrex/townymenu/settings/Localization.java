package me.cobrex.townymenu.settings;

import me.cobrex.townymenu.TownyMenuPlugin;
import me.cobrex.townymenu.utils.MessageFormatter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class Localization {

	private static YamlConfiguration config;
	private static File langFile;
	private static final Deque<String> pathPrefixStack = new ArrayDeque<>();

	public static void load(TownyMenuPlugin plugin) {
		String lang = (Settings.LOCALE != null) ? Settings.LOCALE : "en";
		String fileName = lang + ".yml";
		File langFile = new File(plugin.getDataFolder(), "localization/messages_" + fileName);

		File langFolder = new File(plugin.getDataFolder(), "localization");
		if (!langFolder.exists()) langFolder.mkdirs();

		if (!langFile.exists()) {
			try (InputStream in = plugin.getResource("localization/messages_" + fileName)) {
				if (in != null) {
					Files.copy(in, langFile.toPath());
				} else {
					System.err.println("[TownyMenu] Missing language file inside jar: localization/messages_" + fileName);
				}
			} catch (IOException e) {
				System.err.println("[TownyMenu] Failed to copy default lang file for: " + fileName);
				e.printStackTrace();
			}
		}

		config = YamlConfiguration.loadConfiguration(langFile);

		try (InputStream defaultLangStream = plugin.getResource("localization/messages_en.yml")) {
			if (defaultLangStream != null) {
				YamlConfiguration defaults = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultLangStream, StandardCharsets.UTF_8));
				config.setDefaults(defaults);
				config.options().copyDefaults(true);
			} else {
				System.err.println("[TownyMenu] Could not find fallback localization/messages_en.yml inside jar.");
			}
		} catch (Exception e) {
			System.err.println("[TownyMenu] Error loading default lang file (messages_en.yml):");
			e.printStackTrace();
		}

		try {
			config.save(langFile);
		} catch (IOException e) {
			System.err.println("[TownyMenu] Failed to save completed lang file: " + fileName);
			e.printStackTrace();
		}

		initAll(null);
	}

	public static void save() {
		if (config != null && langFile != null) {
			try {
				config.save(langFile);
			} catch (IOException e) {
				System.err.println("[TownyMenu] Failed to save localization file: " + langFile.getName());
				e.printStackTrace();
			}
		}
	}

	public static YamlConfiguration getConfig() {
		return config;
	}

	private static void loadFallback() {
		try {
			InputStreamReader fallbackStream = new InputStreamReader(
					Objects.requireNonNull(Localization.class.getClassLoader().getResourceAsStream("localization/messages_en.yml")),
					StandardCharsets.UTF_8
			);
			config = YamlConfiguration.loadConfiguration(fallbackStream);
			initAll(null);
		} catch (Exception ex) {
			System.err.println("[TownyMenu] Failed to load fallback locale. Plugin may behave unpredictably.");
			ex.printStackTrace();
		}
	}

	private static void initAll(Player player) {

		ChunkView.init(player);
		Next_Page_Button.init(player);
		Previous_Page_Button.init(player);
		Back_Button.init(player);
		JoinCreateNationMenu.init(player);
		NationMenu.init(player);
		NationMenu.NationSettingsMenu.init(player);
		Back_Button.init(player);
		JoinCreateNationMenu.init(player);
		NationMenu.init(player);
		NationMenu.NationSettingsMenu.init(player);
		NationMenu.NationTownMenu.init(player);
		NationMenu.NationResidentMenu.init(player);
		NationMenu.NationEconomyMenu.init(player);
		NationMenu.NationToggleMenu.init(player);
		NationMenu.NationInviteTownMenu.init(player);
		NationMenu.NationExtraInfoMenu.init(player);
		JoinCreateMenu.init(player);
		TownMenu.init(player);
		TownMenu.ToggleMenu.init(player);
		TownMenu.ResidentMenu.init(player);
		TownMenu.PlayerPermissionsMenu.init(player);
		TownMenu.EconomyMenu.init(player);
		TownMenu.GeneralSettingsMenu.init(player);
		TownMenu.ExtraInfoMenu.init(player);
		PlotMenu.init(player);
		PlotMenu.FriendMenu.init(player);
		PlotMenu.ToggleMenu.init(player);
		PlotMenu.PlayerPermissionsMenu.init(player);
		PlotMenu.PlotAdminMenu.init(player);
		NationConversables.Nation_Board.init(player);
		NationConversables.Nation_Deposit.init(player);
		NationConversables.Nation_King.init(player);
		NationConversables.Nation_Kick.init(player);
		NationConversables.Nation_Name.init(player);
		NationConversables.Nation_Rank.init(player);
		NationConversables.Nation_Title.init(player);
		NationConversables.Nation_Tax.init(player);
		NationConversables.Nation_Withdraw.init(player);
		TownConversables.Board.init(player);
		TownConversables.Deposit.init(player);
		TownConversables.Mayor.init(player);
		TownConversables.Kick.init(player);
		TownConversables.Name.init(player);
		TownConversables.Rank.init(player);
		TownConversables.Tax.init(player);
		TownConversables.Title.init(player);
		TownConversables.Withdraw.init(player);
		PlotConversables.Evict.init(player);
		PlotConversables.ForSale.init(player);
		PlotConversables.NotForSale.init(player);
		PlotConversables.SetType.init(player);
		Error.init(player);

	}

	public static class ChunkView {

		public static String TOGGLE_REMOVE;
		public static String REMOVED;
		public static String PARTICLES;

		private static void init(Player player) {
			String locale = (player != null && player.getLocale() != null)
					? player.getLocale()
					: "messages_en";
			pushPathPrefix("Chunk_View");

			TOGGLE_REMOVE = get(player, "Toggle_Remove");
			REMOVED = get(player, "Removed");
			PARTICLES = get(player, "Particles");

			popPathPrefix();
		}
	}

	public static String confirm(Player player) {
		String raw = config.getString("Confirm", "confirm");
		return MessageFormatter.format(raw, player);
	}

	public static String cancel(Player player) {
		String raw = config.getString("Cancel", "cancel");
		return MessageFormatter.format(raw, player);
	}

	public static String menuInformation(Player player) {
		return get(player, "Menu_Information");
	}

	public static String reloaded(Player player) {
		return get(player, "Reloaded");
	}

	public static void init() {

	}

	public static class Next_Page_Button {
		public static String NEXT_PAGE_BUTTON_TITLE;
		public static List<String> NEXT_PAGE_BUTTON_LORE;

		private static void init(Player player) {
			String locale = (player != null && player.getLocale() != null)
					? player.getLocale()
					: "messages_en";
			pushPathPrefix("Next_Page_Button");
			NEXT_PAGE_BUTTON_TITLE = get(player, "Next_Page_Button_Title");
			NEXT_PAGE_BUTTON_LORE = getList(player, "Next_Page_Button_Lore");
			popPathPrefix();
		}
	}

	public static class Previous_Page_Button {
		public static String PREVIOUS_PAGE_BUTTON_TITLE;
		public static List<String> PREVIOUS_PAGE_BUTTON_LORE;

		private static void init(Player player) {
			String locale = (player != null && player.getLocale() != null)
					? player.getLocale()
					: "messages_en";
			pushPathPrefix("Previous_Page_Button");
			PREVIOUS_PAGE_BUTTON_TITLE = get(player, "Previous_Page_Button_Title");
			PREVIOUS_PAGE_BUTTON_LORE = getList(player, "Previous_Page_Button_Lore");
			popPathPrefix();
		}
	}

	public static class Back_Button {
		public static String BACK_BUTTON_TITLE;
		public static List<String> BACK_BUTTON_LORE;

		private static void init(Player player) {
			String locale = (player != null && player.getLocale() != null)
					? player.getLocale()
					: "messages_en";
			pushPathPrefix("Back_Button");
			BACK_BUTTON_TITLE = get(player, "Back_Button_Title");
			BACK_BUTTON_LORE = getList(player, "Back_Button_Lore");
			popPathPrefix();
		}
	}

	public static class JoinCreateNationMenu {
		public static String MAIN_MENU_TITLE;
		public static String FIND_NATION_BUTTON;
		public static String CLICK_CREATE_NATION_BUTTON;
		public static String JOIN_OPEN_NATION;
		public static String NATION_NAME;
		public static String KING;
		public static String NUMBER_OF_TOWNS;
		public static String CREATE_OWN_NATION;

		private static void init(Player player) {
			String locale = (player != null && player.getLocale() != null)
					? player.getLocale()
					: "messages_en";
			pushPathPrefix("Join_Create_Nation_Menu");
			MAIN_MENU_TITLE = get(player, "Main_Menu_Title");
			FIND_NATION_BUTTON = get(player, "Find_Nation_Button");
			CLICK_CREATE_NATION_BUTTON = get(player, "Click_Create_Nation_Button");
			JOIN_OPEN_NATION = get(player, "Join_Open_Nation");
			NATION_NAME = get(player, "Nation_Name");
			KING = get(player, "King");
			NUMBER_OF_TOWNS = get(player, "Number_of_Towns");
			CREATE_OWN_NATION = get(player, "Create_Own_Nation");
			popPathPrefix();
		}
	}

	public static class NationMenu {
		public static String MAIN_MENU_TITLE;
		public static String NATION_TOGGLE_MENU_BUTTON;
		public static List<String> NATION_TOGGLE_MENU_BUTTON_LORE;
		public static String NATION_TOWN_LIST_BUTTON;
		public static List<String> NATION_TOWN_LIST_BUTTON_LORE;
		public static String NATION_RESIDENT_MENU_BUTTON;
		public static List<String> NATION_RESIDENT_MENU_BUTTON_LORE;
		public static String NATION_ECONOMY_MENU_BUTTON;
		public static List<String> NATION_ECONOMY_MENU_BUTTON_LORE;
		public static String NATION_ECONOMY_DISABLED_MENU_BUTTON;
		public static List<String> NATION_ECONOMY_DISABLED_MENU_BUTTON_LORE;
		public static String NATION_SETTINGS_MENU_BUTTON;
		public static List<String> NATION_SETTINGS_MENU_BUTTON_LORE;
		public static String NATION_INVITE_TOWN_MENU_BUTTON;
		public static List<String> NATION_INVITE_TOWN_MENU_BUTTON_LORE;
		public static String NATION_EXTRA_INFO_MENU_BUTTON;
		public static List<String> NATION_EXTRA_INFO_MENU_BUTTON_LORE;
		public static String NATION_NAME;
		public static String TOWNS;
		public static String NUMBER_TOWNS;
		public static String RESIDENTS;
		public static String NUMBER_RESIDENTS;
		public static String BALANCE;
		public static String BALANCE_AMOUNT;
		public static String KING;
		public static String KING_NAME;

		private static void init(Player player) {
			String locale = (player != null && player.getLocale() != null)
					? player.getLocale()
					: "messages_en";
			Localization.pushPathPrefix("Nation_Menu");

			MAIN_MENU_TITLE = get(player, "Main_Menu_Title");
			NATION_TOGGLE_MENU_BUTTON = get(player, "Nation_Toggle_Menu_Button");
			NATION_TOGGLE_MENU_BUTTON_LORE = getList(player, "Nation_Toggle_Menu_Button_Lore");
			NATION_TOWN_LIST_BUTTON = get(player, "Nation_Town_List_Button");
			NATION_TOWN_LIST_BUTTON_LORE = getList(player, "Nation_Town_List_Button_Lore");
			NATION_RESIDENT_MENU_BUTTON = get(player, "Nation_Resident_Menu_Button");
			NATION_RESIDENT_MENU_BUTTON_LORE = getList(player, "Nation_Resident_Menu_Button_Lore");
			NATION_ECONOMY_MENU_BUTTON = get(player, "Nation_Economy_Menu_Button");
			NATION_ECONOMY_DISABLED_MENU_BUTTON = get(player, "Nation_Economy_Disabled_Button");
			NATION_ECONOMY_DISABLED_MENU_BUTTON_LORE = getList(player, "Nation_Economy_Disabled_Button_Lore");
			NATION_ECONOMY_MENU_BUTTON_LORE = getList(player, "Nation_Economy_Menu_Button_Lore");
			NATION_SETTINGS_MENU_BUTTON = get(player, "Nation_Settings_Button");
			NATION_SETTINGS_MENU_BUTTON_LORE = getList(player, "Nation_Settings_Button_Lore");
			NATION_INVITE_TOWN_MENU_BUTTON = get(player, "Nation_Invite_Town_Menu_Button");
			NATION_INVITE_TOWN_MENU_BUTTON_LORE = getList(player, "Nation_Invite_Town_Menu_Button_Lore");
			NATION_EXTRA_INFO_MENU_BUTTON = get(player, "Nation_Extra_Info_Menu_Button");
			NATION_EXTRA_INFO_MENU_BUTTON_LORE = getList(player, "Nation_Extra_Info_Menu_Button_Lore");
			NATION_NAME = get(player, "Nation_Name");
			TOWNS = get(player, "Towns");
			NUMBER_TOWNS = get(player, "Number_Towns");
			RESIDENTS = get(player, "Residents");
			NUMBER_RESIDENTS = get(player, "Number_Residents");
			BALANCE = get(player, "Balance");
			BALANCE_AMOUNT = get(player, "Balance_Amount");
			KING = get(player, "King");
			KING_NAME = get(player, "King_Name");

			Localization.popPathPrefix();
		}

		public static class NationSettingsMenu {
			public static String MENU_TITLE;
			public static String NATION_SETTINGS_MENU_INFO;
			public static List<String> NATION_SETTINGS_MENU_INFO_LORE;
			public static String SET_SPAWN_MSG;
			public static String SET_SPAWN;
			public static List<String> SET_SPAWN_LORE;
			public static String SET_NAME;
			public static List<String> SET_NAME_LORE;
			public static String SET_BOARD;
			public static List<String> SET_BOARD_LORE;
			public static String BACK_BUTTON;
			public static List<String> BACK_BUTTON_LORE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Nation_Settings_Menu");

				MENU_TITLE = get(player, "Menu_Title");
				NATION_SETTINGS_MENU_INFO = get(player, "Nation_Settings_Info");
				NATION_SETTINGS_MENU_INFO_LORE = getList(player, "Nation_Settings_Info_Lore");
				SET_SPAWN_MSG = get(player, "Set_Spawn_Msg");
				SET_SPAWN = get(player, "Set_Spawn");
				SET_SPAWN_LORE = getList(player, "Set_Spawn_Lore");
				SET_NAME = get(player, "Set_Name");
				SET_NAME_LORE = getList(player, "Set_Name_Lore");
				SET_BOARD = get(player, "Set_Board");
				SET_BOARD_LORE = getList(player, "Set_Board_Lore");
				BACK_BUTTON = get(player, "Back_Button");
				BACK_BUTTON_LORE = getList(player, "Back_Button_Lore");

				popPathPrefix();
			}
		}

		public static class NationTownMenu {

			public static String MENU_TITLE;
			public static String INFO;
			public static List<String> INFO_LORE;
			public static String ONLINE;
			public static String TOWN_NAME;
			public static String MAYOR;
			public static String NUMBER_RESIDENTS;
			public static String KICK;
			public static List<String> KICK_LORE;
			public static String BACK_BUTTON;
			public static List<String> BACK_BUTTON_LORE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Nation_Town_Menu");
				MENU_TITLE = get(player, "Menu_Title");
				INFO = get(player, "Info");
				INFO_LORE = getList(player, "Info_Lore");
				ONLINE = get(player, "Online");
				TOWN_NAME = get(player, "Town_Name");
				MAYOR = get(player, "Mayor");
				NUMBER_RESIDENTS = get(player, "Number_of_Residents");
				KICK = get(player, "Kick");
				KICK_LORE = getList(player, "Kick_Lore");
				BACK_BUTTON = get(player, "Back_Button");
				BACK_BUTTON_LORE = getList(player, "Back_Button_Lore");
				popPathPrefix();
			}
		}

		public static class NationResidentMenu {

			public static String MENU_TITLE;
			public static String RESIDENT_INFO;
			public static List<String> RESIDENT_INFO_LORE;
			public static String ONLINE;
			public static String RES_NAME;
			public static String TOWN;
			public static String NATION_RANK;
			public static List<String> NATION_RANK_LORE;
			public static String NATION_TITLE;
			public static List<String> NATION_TITLE_LORE;
			public static String NATION_KING;
			public static List<String> NATION_KING_LORE;
			public static String BACK_BUTTON;
			public static List<String> BACK_BUTTON_LORE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Nation_Resident_Menu");
				MENU_TITLE = get(player, "Menu_Title");
				RESIDENT_INFO = get(player, "Nation_Resident_Info_Button");
				RESIDENT_INFO_LORE = getList(player, "Nation_Resident_Info_Button_Lore");
				ONLINE = get(player, "Online");
				RES_NAME = get(player, "Res_Name");
				TOWN = get(player, "Town_Name");
				NATION_RANK = get(player, "Nation_Rank");
				NATION_RANK_LORE = getList(player, "Nation_Rank_Lore");
				NATION_TITLE = get(player, "Nation_Title");
				NATION_TITLE_LORE = getList(player, "Nation_Title_Lore");
				NATION_KING = get(player, "Nation_King");
				NATION_KING_LORE = getList(player, "Nation_King_Lore");
				BACK_BUTTON = get(player, "Back_Button");
				BACK_BUTTON_LORE = getList(player, "Back_Button_Lore");
				popPathPrefix();
			}
		}

		public static class NationEconomyMenu {

			public static String MENU_TITLE;
			public static String NATION_ECONOMY_MENU_INFO;
			public static List<String> NATION_ECONOMY_MENU_INFO_LORE;
			public static String BALANCE;
			public static String NATION_BALANCE;
			public static String UPKEEP;
			public static String WITHDRAW;
			public static List<String> WITHDRAW_LORE;
			public static String DEPOSIT;
			public static List<String> DEPOSIT_LORE;
			public static String TAX;
			public static String TAX_PERCENTAGE;
			public static String TAX_AMOUNT;
			public static String BACK_BUTTON;
			public static List<String> BACK_BUTTON_LORE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Nation_Economy_Menu");

				MENU_TITLE = get(player, "Menu_Title");
				NATION_ECONOMY_MENU_INFO = get(player, "Nation_Economy_Menu_Info");
				NATION_ECONOMY_MENU_INFO_LORE = getList(player, "Nation_Economy_Menu_Info_Lore");
				BALANCE = get(player, "Balance");
				NATION_BALANCE = get(player, "Nation_Balance");
				UPKEEP = get(player, "Upkeep");
				WITHDRAW = get(player, "Withdraw");
				WITHDRAW_LORE = getList(player, "Withdraw_Lore");
				DEPOSIT = get(player, "Deposit");
				DEPOSIT_LORE = getList(player, "Deposit_Lore");
				TAX = get(player, "Tax");
				TAX_PERCENTAGE = get(player, "Tax_Percentage");
				TAX_AMOUNT = get(player, "Tax_Amount");
				BACK_BUTTON = get(player, "Back_Button");
				BACK_BUTTON_LORE = getList(player, "Back_Button_Lore");

				popPathPrefix();
			}
		}

		public static class NationToggleMenu {
			public static String MENU_TITLE;
			public static String NATION_TOGGLE_MENU_INFO;
			public static List<String> NATION_TOGGLE_MENU_INFO_LORE;
			public static String TOGGLE_OFF;
			public static String TOGGLE_ON;
			public static String PUBLIC;
			public static String OPEN;
			public static String NEUTRAL;
			public static String PEACEFUL;
			public static String TAX_PERCENT;
			public static String BACK_BUTTON;
			public static List<String> BACK_BUTTON_LORE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Nation_Toggle_Menu");

				MENU_TITLE = get(player, "Menu_Title");
				NATION_TOGGLE_MENU_INFO = get(player, "Nation_Toggle_Menu_Info");
				NATION_TOGGLE_MENU_INFO_LORE = getList(player, "Nation_Toggle_Menu_Info_Lore");
				TOGGLE_OFF = get(player, "Toggle_Off");
				TOGGLE_ON = get(player, "Toggle_On");
				PUBLIC = get(player, "Public");
				OPEN = get(player, "Open");
				NEUTRAL = get(player, "Neutral");
				PEACEFUL = get(player, "Peaceful");
				TAX_PERCENT = get(player, "Tax_Percent");
				BACK_BUTTON = get(player, "Back_Button");
				BACK_BUTTON_LORE = getList(player, "Back_Button_Lore");

				popPathPrefix();
			}
		}

		public static class NationInviteTownMenu {
			public static String MENU_TITLE;
			public static String INVITE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Nation_Invite_Town_Menu");

				MENU_TITLE = get(player, "Menu_Title");
				INVITE = get(player, "Invite");

				popPathPrefix();
			}
		}

		public static class NationExtraInfoMenu {

			public static String MENU_TITLE;
			public static String COMMANDS_1;
			public static List<String> COMMANDS_1_LORE;
			public static String COMMANDS_2;
			public static List<String> COMMANDS_2_LORE;
			public static String BACK_BUTTON;
			public static List<String> BACK_BUTTON_LORE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Nation_Extra_Info_Menu");

				MENU_TITLE = get(player, "Menu_Title");
				COMMANDS_1 = get(player, "Commands_1");
				COMMANDS_1_LORE = getList(player, "Commands_1_Lore");
				COMMANDS_2 = get(player, "Commands_2");
				COMMANDS_2_LORE = getList(player, "Commands_2_Lore");
				BACK_BUTTON = get(player, "Back_Button");
				BACK_BUTTON_LORE = getList(player, "Back_Button_Lore");

				popPathPrefix();
			}
		}
	}

	public static class JoinCreateMenu {

		public static String MAIN_MENU_TITLE;
		public static String FIND_OPEN_TOWN;
		public static String CLICK_CREATE_TOWN;
		public static String JOIN_OPEN_TOWN;
		public static String TOWN_NAME;
		public static String MAYOR;
		public static String NUMBER_RESIDENTS;
		public static String CREATE_OWN_TOWN;

		private static void init(Player player) {
			String locale = (player != null && player.getLocale() != null)
					? player.getLocale()
					: "messages_en";
			pushPathPrefix("Join_Create_Menu");

			MAIN_MENU_TITLE = get(player, "Main_Menu_Title");
			FIND_OPEN_TOWN = get(player, "Find_Open_Town");
			CLICK_CREATE_TOWN = get(player, "Click_Create_Town");
			JOIN_OPEN_TOWN = get(player, "Join_Open_Town");
			TOWN_NAME = get(player, "Town_Name");
			MAYOR = get(player, "Mayor");
			NUMBER_RESIDENTS = get(player, "Number_Residents");
			CREATE_OWN_TOWN = get(player, "Create_Own_Town");

			popPathPrefix();
		}
	}

	public static class TownMenu {

		public static String MAIN_MENU_TITLE;
		public static String TOGGLE_MENU_BUTTON;
		public static List<String> TOGGLE_MENU_BUTTON_LORE;
		public static String RESIDENT_MENU_BUTTON;
		public static List<String> RESIDENT_MENU_BUTTON_LORE;
		public static String PERMISSIONS_MENU_BUTTON;
		public static List<String> PERMISSIONS_MENU_BUTTON_LORE;
		public static String ECONOMY_MENU_BUTTON;
		public static List<String> ECONOMY_MENU_BUTTON_LORE;
		public static String ECONOMY_DISABLED_MENU_BUTTON;
		public static List<String> ECONOMY_DISABLED_MENU_BUTTON_LORE;
		public static String GENERAL_SETTINGS_MENU_BUTTON;
		public static List<String> GENERAL_SETTINGS_MENU_BUTTON_LORE;
		public static String INVITE_PLAYER_MENU_BUTTON;
		public static List<String> INVITE_PLAYER_MENU_BUTTON_LORE;
		public static String EXTRA_INFO_MENU_BUTTON;
		public static List<String> EXTRA_INFO_MENU_BUTTON_LORE;
		public static String PLOT_MENU_BUTTON;
		public static List<String> PLOT_MENU_BUTTON_LORE;
		public static String TOWN_NAME;
		public static String TOWN_POSTFIX;
		public static String RESIDENTS;
		public static String NUMBER_RESIDENTS;
		public static String CLAIM_BLOCKS;
		public static String TOTAL_CLAIMED_BLOCKS;
		public static String MAX_CLAIM_BLOCKS;
		public static String BALANCE;
		public static String BALANCE_AMOUNT;
		public static String MAYOR;
		public static String MAYOR_NAME;
		public static String NATION;
		public static String NATION_NAME;

		private static void init(Player player) {
			String locale = (player != null && player.getLocale() != null)
					? player.getLocale()
					: "messages_en";
			pushPathPrefix("Town_Menu");
//			System.out.println("Available keys under 'Town_Menu': " + config.getConfigurationSection("Town_Menu").getKeys(false));

			MAIN_MENU_TITLE = get(player, "Main_Menu_Title");
			TOGGLE_MENU_BUTTON = get(player, "Toggle_Menu_Button");
			TOGGLE_MENU_BUTTON_LORE = getList(player, "Toggle_Menu_Button_Lore");
			RESIDENT_MENU_BUTTON = get(player, "Resident_Menu_Button");
			RESIDENT_MENU_BUTTON_LORE = getList(player, "Resident_Menu_Button_Lore");
			PERMISSIONS_MENU_BUTTON = get(player, "Permissions_Menu_Button");
			PERMISSIONS_MENU_BUTTON_LORE = getList(player, "Permissions_Menu_Button_Lore");
			ECONOMY_MENU_BUTTON = get(player, "Economy_Menu_Button");
			ECONOMY_MENU_BUTTON_LORE = getList(player, "Economy_Menu_Button_Lore");
			ECONOMY_DISABLED_MENU_BUTTON = get(player, "Economy_Disabled_Menu_Button");
			ECONOMY_DISABLED_MENU_BUTTON_LORE = getList(player, "Economy_Disabled_Menu_Button_Lore");
			GENERAL_SETTINGS_MENU_BUTTON = get(player, "General_Settings_Menu_Button");
			GENERAL_SETTINGS_MENU_BUTTON_LORE = getList(player, "General_Settings_Menu_Button_Lore");
			INVITE_PLAYER_MENU_BUTTON = get(player, "Invite_Player_Menu_Button");
			INVITE_PLAYER_MENU_BUTTON_LORE = getList(player, "Invite_Player_Menu_Button_Lore");
			EXTRA_INFO_MENU_BUTTON = get(player, "Extra_Info_Menu_Button");
			EXTRA_INFO_MENU_BUTTON_LORE = getList(player, "Extra_Info_Menu_Button_Lore");
			PLOT_MENU_BUTTON = get(player, "Plot_Menu_Button");
			PLOT_MENU_BUTTON_LORE = getList(player, "Plot_Menu_Button_Lore");
			TOWN_NAME = get(player, "Town_Name");
			TOWN_POSTFIX = get(player, "Town_Postfix");
			RESIDENTS = get(player, "Residents");
			NUMBER_RESIDENTS = get(player, "Number_Residents");
			CLAIM_BLOCKS = get(player, "Claim_Blocks");
			TOTAL_CLAIMED_BLOCKS = get(player, "Total_Claimed_Blocks");
			MAX_CLAIM_BLOCKS = get(player, "Max_Claim_Blocks");
			BALANCE = get(player, "Balance");
			BALANCE_AMOUNT = get(player, "Balance_Amount");
			MAYOR = get(player, "Mayor");
			MAYOR_NAME = get(player, "Mayor_Name");
			NATION = get(player, "Nation");
			NATION_NAME = get(player, "Nation_Name");

			popPathPrefix();
		}

		public static class ToggleMenu {
			public static String MENU_TITLE;
			public static String INFO;
			public static List<String> INFO_LORE;
			public static String TOGGLE_OFF;
			public static String TOGGLE_ON;
			public static String FIRE;
			public static String MOBS;
			public static String EXPLODE;
			public static String PVP;
			public static String PUBLIC;
			public static String OPEN;
			public static String TAX_PERCENT;
			public static String BACK_BUTTON;
			public static List<String> BACK_BUTTON_LORE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Toggle_Menu");

				MENU_TITLE = get(player, "Menu_Title");
				INFO = get(player, "Info");
				INFO_LORE = getList(player, "Info_Lore");
				TOGGLE_OFF = get(player, "Toggle_Off");
				TOGGLE_ON = get(player, "Toggle_On");
				FIRE = get(player, "Fire");
				MOBS = get(player, "Mobs");
				EXPLODE = get(player, "Explode");
				PVP = get(player, "PVP");
				PUBLIC = get(player, "Public");
				OPEN = get(player, "Open");
				TAX_PERCENT = get(player, "Tax_Percent");
				BACK_BUTTON = get(player, "Back_Button");
				BACK_BUTTON_LORE = getList(player, "Back_Button_Lore");

				popPathPrefix();
			}
		}

		public static class ResidentMenu {

			public static String MENU_TITLE;
			public static String INFO;
			public static List<String> INFO_LORE;
			public static String RESIDENT_NAME;
			public static String ONLINE;
			public static String TOWN_RANK;
			public static String INVITE;
			public static String KICK;
			public static List<String> KICK_LORE;
			public static String TITLE;
			public static List<String> TITLE_LORE;
			public static String RANK;
			public static List<String> RANK_LORE;
			public static String MAYOR;
			public static List<String> MAYOR_LORE;
			public static String BACK_BUTTON;
			public static List<String> BACK_BUTTON_LORE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Resident_Menu");

				MENU_TITLE = get(player, "Menu_Title");
				INFO = get(player, "Info");
				INFO_LORE = getList(player, "Info_Lore");
				RESIDENT_NAME = get(player, "Resident_Name");
				ONLINE = get(player, "Online");
				TOWN_RANK = get(player, "Town_Rank");
				INVITE = get(player, "Invite");
				KICK = get(player, "Kick");
				KICK_LORE = getList(player, "Kick_Lore");
				TITLE = get(player, "Title");
				TITLE_LORE = getList(player, "Title_Lore");
				RANK = get(player, "Rank");
				RANK_LORE = getList(player, "Rank_Lore");
				MAYOR = get(player, "Mayor");
				MAYOR_LORE = getList(player, "Mayor_Lore");
				BACK_BUTTON = get(player, "Back_Button");
				BACK_BUTTON_LORE = getList(player, "Back_Button_Lore");

				popPathPrefix();
			}
		}

		public static class PlayerPermissionsMenu {

			public static String MENU_TITLE;
			public static String INFO;
			public static List<String> INFO_LORE;
			public static String TRUE_MSG;
			public static String FALSE_MSG;
			public static String CHANGE;
			public static String RESET;
			public static List<String> RESET_LORE;
			public static String ON;
			public static List<String> ON_LORE;
			public static String RESET_TO_TOWN_PERM;
			public static List<String> RESET_TO_TOWN_PERM_LORE;
			public static String BUILD;
			public static List<String> BUILD_LORE;
			public static String BREAK;
			public static List<String> BREAK_LORE;
			public static String USE;
			public static List<String> USE_LORE;
			public static String SWITCH;
			public static List<String> SWITCH_LORE;
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
			public static String BACK_BUTTON;
			public static List<String> BACK_BUTTON_LORE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Player_Permission_Menu");

				MENU_TITLE = get(player, "Menu_Title");
				INFO = get(player, "Info");
				INFO_LORE = getList(player, "Info_Lore");
				TRUE_MSG = get(player, "True_Msg");
				FALSE_MSG = get(player, "False_Msg");
				CHANGE = get(player, "Change");
				RESET = get(player, "Reset");
				RESET_LORE = getList(player, "Reset_Lore");
				ON = get(player, "On_Msg");
				ON_LORE = getList(player, "On_Msg_Lore");
				RESET_TO_TOWN_PERM = get(player, "Reset_to_Town_Perm");
				RESET_TO_TOWN_PERM_LORE = getList(player, "Reset_to_Town_Perm_Lore");
				BUILD = get(player, "Build");
				BUILD_LORE = getList(player, "Build_Lore");
				BREAK = get(player, "Break");
				BREAK_LORE = getList(player, "Break_Lore");
				USE = get(player, "Use");
				USE_LORE = getList(player, "Use_Lore");
				SWITCH = get(player, "Switch");
				SWITCH_LORE = getList(player, "Switch_Lore");
				BUILD_RES = get(player, "Build_Res");
				BUILD_RES2 = get(player, "Build_Res2");
				BUILD_NATION = get(player, "Build_Nation");
				BUILD_NATION2 = get(player, "Build_Nation2");
				BUILD_ALLY = get(player, "Build_Ally");
				BUILD_ALLY2 = get(player, "Build_Ally2");
				BUILD_OUTSIDER = get(player, "Build_Outsider");
				BUILD_OUTSIDER2 = get(player, "Build_Outsider2");
				BREAK_RES = get(player, "Break_Res");
				BREAK_RES2 = get(player, "Break_Res2");
				BREAK_NATION = get(player, "Break_Nation");
				BREAK_NATION2 = get(player, "Break_Nation2");
				BREAK_ALLY = get(player, "Break_Ally");
				BREAK_ALLY2 = get(player, "Break_Ally2");
				BREAK_OUTSIDER = get(player, "Break_Outsider");
				BREAK_OUTSIDER2 = get(player, "Break_Outsider2");
				USE_RES = get(player, "Use_Res");
				USE_RES2 = get(player, "Use_Res2");
				USE_NATION = get(player, "Use_Nation");
				USE_NATION2 = get(player, "Use_Nation2");
				USE_ALLY = get(player, "Use_Ally");
				USE_ALLY2 = get(player, "Use_Ally2");
				USE_OUTSIDER = get(player, "Use_Outsider");
				USE_OUTSIDER2 = get(player, "Use_Outsider2");
				SWITCH_RES = get(player, "Switch_Res");
				SWITCH_RES2 = get(player, "Switch_Res2");
				SWITCH_NATION = get(player, "Switch_Nation");
				SWITCH_NATION2 = get(player, "Switch_Nation2");
				SWITCH_ALLY = get(player, "Switch_Ally");
				SWITCH_ALLY2 = get(player, "Switch_Ally2");
				SWITCH_OUTSIDER = get(player, "Switch_Outsider");
				SWITCH_OUTSIDER2 = get(player, "Switch_Outsider2");
				BACK_BUTTON = get(player, "Back_Button");
				BACK_BUTTON_LORE = getList(player, "Back_Button_Lore");

				popPathPrefix();
			}
		}

		public static class EconomyMenu {

			public static String MENU_TITLE;
			public static String INFO;
			public static List<String> INFO_LORE;
			public static String BALANCE;
			public static String TOWN_BALANCE;
			public static String UPKEEP;
			public static String WITHDRAW;
			public static List<String> WITHDRAW_LORE;
			public static String DEPOSIT;
			public static List<String> DEPOSIT_LORE;
			public static String TAX;
			public static String TAX_PERCENTAGE;
			public static String TAX_AMOUNT;
			public static String BACK_BUTTON;
			public static List<String> BACK_BUTTON_LORE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Economy_Menu");

				MENU_TITLE = get(player, "Menu_Title");
				INFO = get(player, "Info");
				INFO_LORE = getList(player, "Info_Lore");
				BALANCE = get(player, "Balance");
				TOWN_BALANCE = get(player, "Town_Balance");
				UPKEEP = get(player, "Upkeep");
				WITHDRAW = get(player, "Withdraw");
				WITHDRAW_LORE = getList(player, "Withdraw_Lore");
				DEPOSIT = get(player, "Deposit");
				DEPOSIT_LORE = getList(player, "Deposit_Lore");
				TAX = get(player, "Tax");
				TAX_PERCENTAGE = get(player, "Tax_Percentage");
				TAX_AMOUNT = get(player, "Tax_Amount");
				BACK_BUTTON = get(player, "Back_Button");
				BACK_BUTTON_LORE = getList(player, "Back_Button_Lore");

				popPathPrefix();
			}
		}

		public static class GeneralSettingsMenu {
			public static String MENU_TITLE;
			public static String INFO;
			public static List<String> INFO_LORE;
			public static String SET_HOME_BLOCK_MSG;
			public static String SPAWN_REMINDER;
			public static String SET_HOME_BLOCK;
			public static List<String> SET_HOME_BLOCK_LORE;
			public static String SET_SPAWN_MSG;
			public static String SET_SPAWN;
			public static List<String> SET_SPAWN_LORE;
			public static String SET_NAME;
			public static List<String> SET_NAME_LORE;
			public static String SET_BOARD;
			public static List<String> SET_BOARD_LORE;
			public static String BACK_BUTTON;
			public static List<String> BACK_BUTTON_LORE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("General_Settings_Menu");

				MENU_TITLE = get(player, "Menu_Title");
				INFO = get(player, "Info");
				INFO_LORE = getList(player, "Info_Lore");
				SET_HOME_BLOCK_MSG = get(player, "Set_Home_Block_Msg");
				SPAWN_REMINDER = get(player, "Spawn_Reminder");
				SET_HOME_BLOCK = get(player, "Set_Home_Block");
				SET_HOME_BLOCK_LORE = getList(player, "Set_Home_Block_Lore");
				SET_SPAWN_MSG = get(player, "Set_Spawn_Msg");
				SET_SPAWN = get(player, "Set_Spawn");
				SET_SPAWN_LORE = getList(player, "Set_Spawn_Lore");
				SET_NAME = get(player, "Set_Name");
				SET_NAME_LORE = getList(player, "Set_Name_Lore");
				SET_BOARD = get(player, "Set_Board");
				SET_BOARD_LORE = getList(player, "Set_Board_Lore");
				BACK_BUTTON = get(player, "Back_Button");
				BACK_BUTTON_LORE = getList(player, "Back_Button_Lore");

				popPathPrefix();
			}
		}

		public static class ExtraInfoMenu {

			public static String MENU_TITLE;
			public static String INFO;
			public static List<String> INFO_LORE;
			public static String CLAIMING;
			public static List<String> CLAIMING_LORE;
			public static String COMMANDS;
			public static List<String> COMMANDS_LORE;
			public static String BACK_BUTTON;
			public static List<String> BACK_BUTTON_LORE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Extra_Info_Menu");

				MENU_TITLE = get(player, "Menu_Title");
				INFO = get(player, "Info");
				INFO_LORE = getList(player, "Info_Lore");
				CLAIMING = get(player, "Claiming");
				CLAIMING_LORE = getList(player, "Claiming_Lore");
				COMMANDS = get(player, "Commands");
				COMMANDS_LORE = getList(player, "Commands_Lore");
				BACK_BUTTON = get(player, "Back_Button");
				BACK_BUTTON_LORE = getList(player, "Back_Button_Lore");

				popPathPrefix();
			}
		}
	}

	public static class PlotMenu {

		public static String MAIN_MENU_TITLE;
		public static String INFO;
		public static List<String> INFO_LORE;
		public static String TOGGLE_SETTINGS_MENU_BUTTON;
		public static List<String> TOGGLE_SETTINGS_MENU_BUTTON_LORE;
		public static String PERMISSIONS_MENU_BUTTON;
		public static List<String> PERMISSIONS_MENU_BUTTON_LORE;
		public static String PLOT_ADMIN_MENU_BUTTON;
		public static List<String> PLOT_ADMIN_MENU_BUTTON_LORE;
		public static String FRIEND_MENU_BUTTON;
		public static List<String> FRIEND_MENU_BUTTON_LORE;
		public static String BACK_BUTTON;
		public static List<String> BACK_BUTTON_LORE;

		private static void init(Player player) {
			String locale = (player != null && player.getLocale() != null)
					? player.getLocale()
					: "messages_en";
			pushPathPrefix("Plot_Menu");
			MAIN_MENU_TITLE = get(player, "Main_Menu_Title");
			INFO = get(player, "Info");
			INFO_LORE = getList(player, "Info_Lore");
			TOGGLE_SETTINGS_MENU_BUTTON = get(player, "Toggle_Settings_Menu_Button");
			TOGGLE_SETTINGS_MENU_BUTTON_LORE = getList(player, "Toggle_Settings_Menu_Button_Lore");
			PERMISSIONS_MENU_BUTTON = get(player, "Permissions_Menu_Button");
			PERMISSIONS_MENU_BUTTON_LORE = getList(player, "Permissions_Menu_Button_Lore");
			PLOT_ADMIN_MENU_BUTTON = get(player, "Plot_Admin_Menu_Button");
			PLOT_ADMIN_MENU_BUTTON_LORE = getList(player, "Plot_Admin_Menu_Button_Lore");
			FRIEND_MENU_BUTTON = get(player, "Friend_Menu_Button");
			FRIEND_MENU_BUTTON_LORE = getList(player, "Friend_Menu_Button_Lore");
			BACK_BUTTON = get(player, "Back_Button");
			BACK_BUTTON_LORE = getList(player, "Back_Button_Lore");
			popPathPrefix();
		}

		public static class FriendMenu {

			public static String MENU_TITLE;
			public static String INFO;
			public static List<String> INFO_LORE;
			public static String REMOVE;
			public static String ADD;
			public static String CLICK_REMOVE_LORE;
			public static String CLICK_ADD_LORE;
			public static String BACK_BUTTON;
			public static List<String> BACK_BUTTON_LORE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Friend_Menu");
				INFO = get(player, "Info");
				INFO_LORE = getList(player, "Info_Lore");
				MENU_TITLE = get(player, "Menu_Title");
				REMOVE = get(player, "Remove");
				ADD = get(player, "Add");
				CLICK_REMOVE_LORE = get(player, "Click_Remove_Lore");
				CLICK_ADD_LORE = get(player, "Click_Add_Lore");
				BACK_BUTTON = get(player, "Back_Button");
				BACK_BUTTON_LORE = getList(player, "Back_Button_Lore");
				popPathPrefix();
			}
		}

		public static class ToggleMenu {
			public static String MENU_TITLE;
			public static String INFO;
			public static List<String> INFO_LORE;
			public static String TOGGLE_OFF;
			public static String TOGGLE_ON;
			public static String FIRE;
			public static String MOBS;
			public static String EXPLODE;
			public static String PVP;
			public static String BACK_BUTTON;
			public static List<String> BACK_BUTTON_LORE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Plot_Toggle_Menu");

				MENU_TITLE = get(player, "Menu_Title");
				INFO = get(player, "Info");
				INFO_LORE = getList(player, "Info_Lore");
				TOGGLE_OFF = get(player, "Toggle_Off");
				TOGGLE_ON = get(player, "Toggle_On");
				FIRE = get(player, "Fire");
				MOBS = get(player, "Mobs");
				EXPLODE = get(player, "Explode");
				PVP = get(player, "PVP");
				BACK_BUTTON = get(player, "Back_Button");
				BACK_BUTTON_LORE = getList(player, "Back_Button_Lore");

				popPathPrefix();
			}
		}

		public static class PlayerPermissionsMenu {

			public static String MENU_TITLE;
			public static String INFO;
			public static List<String> INFO_LORE;
			public static String TRUE_MSG;
			public static String FALSE_MSG;
			public static String CHANGE;
			public static String RESET;
			public static List<String> RESET_LORE;
			public static String ON;
			public static List<String> ON_LORE;
			public static String BUILD;
			public static List<String> BUILD_LORE;
			public static String BREAK;
			public static List<String> BREAK_LORE;
			public static String USE;
			public static List<String> USE_LORE;
			public static String SWITCH;
			public static List<String> SWITCH_LORE;
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
			public static String BACK_BUTTON;
			public static List<String> BACK_BUTTON_LORE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Plot_Player_Permission_Menu");
				MENU_TITLE = get(player, "Menu_Title");
				INFO = get(player, "Info");
				INFO_LORE = getList(player, "Info_Lore");
				TRUE_MSG = get(player, "True_Msg");
				FALSE_MSG = get(player, "False_Msg");
				CHANGE = get(player, "Change");
				RESET = get(player, "Reset");
				RESET_LORE = getList(player, "Reset_Lore");
				ON = get(player, "On_Msg");
				ON_LORE = getList(player, "On_Msg_Lore");
				BUILD = get(player, "Build");
				BUILD_LORE = getList(player, "Build_Lore");
				BREAK = get(player, "Break");
				BREAK_LORE = getList(player, "Break_Lore");
				USE = get(player, "Use");
				USE_LORE = getList(player, "Use_Lore");
				SWITCH = get(player, "Switch");
				SWITCH_LORE = getList(player, "Switch_Lore");
				BUILD_RES = get(player, "Build_Res");
				BUILD_RES2 = get(player, "Build_Res2");
				BUILD_NATION = get(player, "Build_Nation");
				BUILD_NATION2 = get(player, "Build_Nation2");
				BUILD_ALLY = get(player, "Build_Ally");
				BUILD_ALLY2 = get(player, "Build_Ally2");
				BUILD_OUTSIDER = get(player, "Build_Outsider");
				BUILD_OUTSIDER2 = get(player, "Build_Outsider2");
				BREAK_RES = get(player, "Break_Res");
				BREAK_RES2 = get(player, "Break_Res2");
				BREAK_NATION = get(player, "Break_Nation");
				BREAK_NATION2 = get(player, "Break_Nation2");
				BREAK_ALLY = get(player, "Break_Ally");
				BREAK_ALLY2 = get(player, "Break_Ally2");
				BREAK_OUTSIDER = get(player, "Break_Outsider");
				BREAK_OUTSIDER2 = get(player, "Break_Outsider2");
				USE_RES = get(player, "Use_Res");
				USE_RES2 = get(player, "Use_Res2");
				USE_NATION = get(player, "Use_Nation");
				USE_NATION2 = get(player, "Use_Nation2");
				USE_ALLY = get(player, "Use_Ally");
				USE_ALLY2 = get(player, "Use_Ally2");
				USE_OUTSIDER = get(player, "Use_Outsider");
				USE_OUTSIDER2 = get(player, "Use_Outsider2");
				SWITCH_RES = get(player, "Switch_Res");
				SWITCH_RES2 = get(player, "Switch_Res2");
				SWITCH_NATION = get(player, "Switch_Nation");
				SWITCH_NATION2 = get(player, "Switch_Nation2");
				SWITCH_ALLY = get(player, "Switch_Ally");
				SWITCH_ALLY2 = get(player, "Switch_Ally2");
				SWITCH_OUTSIDER = get(player, "Switch_Outsider");
				SWITCH_OUTSIDER2 = get(player, "Switch_Outsider2");
				BACK_BUTTON = get(player, "Back_Button");
				BACK_BUTTON_LORE = getList(player, "Back_Button_Lore");
				popPathPrefix();
			}
		}

		public static class PlotAdminMenu {

			public static String MENU_TITLE;
			public static String INFO;
			public static List<String> INFO_LORE;
			public static String FOR_SALE;
			public static List<String> FOR_SALE_LORE;
			public static String NOT_FOR_SALE;
			public static List<String> NOT_FOR_SALE_LORE;
			public static String SET_TYPE;
			public static List<String> SET_TYPE_LORE;
			public static String EVICT;
			public static List<String> EVICT_LORE;
			public static String BACK_BUTTON;
			public static List<String> BACK_BUTTON_LORE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Plot_Admin_Menu");

				MENU_TITLE = get(player, "Menu_Title");
				INFO = get(player, "Info");
				INFO_LORE = getList(player, "Info_Lore");
				FOR_SALE = get(player, "For_Sale");
				FOR_SALE_LORE = getList(player, "For_Sale_Lore");
				NOT_FOR_SALE = get(player, "Not_For_Sale");
				NOT_FOR_SALE_LORE = getList(player, "Not_For_Sale_Lore");
				SET_TYPE = get(player, "Set_Type");
				SET_TYPE_LORE = getList(player, "Set_Type_Lore");
				EVICT = get(player, "Evict");
				EVICT_LORE = getList(player, "Evict_Lore");
				BACK_BUTTON = get(player, "Back_Button");
				BACK_BUTTON_LORE = getList(player, "Back_Button_Lore");

				popPathPrefix();
			}
		}
	}

	public static class NationConversables {

		public static class Nation_Board {
			public static String PROMPT;
			public static String RESPONSE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Nation_Conversables.Board");
				PROMPT = get(player, "Prompt");
				RESPONSE = get(player, "Response");
				popPathPrefix();
			}
		}

		public static class Nation_Deposit {
			public static String PROMPT;
			public static String INVALID;
			public static String RESPONSE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Nation_Conversables.Deposit");
				PROMPT = get(player, "Prompt");
				INVALID = get(player, "Invalid");
				RESPONSE = get(player, "Response");
				popPathPrefix();
			}
		}

		public static class Nation_King {
			public static String PROMPT;
			public static String RESPONSE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";

				pushPathPrefix("Nation_Conversables.Mayor");
				PROMPT = get(player, "Prompt");
				RESPONSE = get(player, "Response");
				popPathPrefix();
			}
		}

		public static class Nation_Kick {
			public static String PROMPT;
			public static String RESPONSE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Nation_Conversables.Kick");
				PROMPT = get(player, "Prompt");
				RESPONSE = get(player, "Response");
				popPathPrefix();
			}
		}

		public static class Nation_Name {
			public static String PROMPT;
			public static String INVALID;
			public static String RESPONSE;
			public static String RETURN;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Nation_Conversables.Name");
				PROMPT = get(player, "Prompt");
				INVALID = get(player, "Invalid");
				RESPONSE = get(player, "Response");
				RETURN = get(player, "Return");
				popPathPrefix();
			}
		}

		public static class Nation_Rank {
			public static String PROMPT;
			public static String INVALID;
			public static String RESPONSE;
			public static String REMOVE;
			public static String REMOVED_ALL;
			public static String ALREADY_HAS_RANK;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Nation_Conversables.Title");
				PROMPT = get(player, "Prompt");
				INVALID = get(player, "Invalid");
				RESPONSE = get(player, "Response");
				REMOVE = get(player, "Remove");
				REMOVED_ALL = get(player, "Removed_All");
				ALREADY_HAS_RANK = get(player, "Has_Rank");
				popPathPrefix();
			}
		}

		public static class Nation_Title {
			public static String PROMPT;
			public static String INVALID;
			public static String RESPONSE;
			public static String REMOVE;
			public static String REMOVED_ALL;
			public static String ALREADY_HAS_RANK;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Nation_Conversables.Title");
				PROMPT = get(player, "Prompt");
				INVALID = get(player, "Invalid");
				RESPONSE = get(player, "Response");
				REMOVE = get(player, "Remove");
				REMOVED_ALL = get(player, "Removed_All");
				ALREADY_HAS_RANK = get(player, "Has_Rank");
				popPathPrefix();
			}
		}

		public static class Nation_Tax {
			public static String PROMPT;
			public static String INVALID_PERCENT;
			public static String INVALID_AMOUNT;
			public static String RESPONSE_AMOUNT;
			public static String RESPONSE_PERCENT;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Nation_Conversables.Tax");
				PROMPT = get(player, "Prompt");
				INVALID_AMOUNT = get(player, "Invalid_Amount");
				INVALID_PERCENT = get(player, "Invalid_Percent");
				RESPONSE_PERCENT = get(player, "Response_Percent");
				RESPONSE_AMOUNT = get(player, "Response_Amount");
				popPathPrefix();
			}
		}

		public static class Nation_Withdraw {
			public static String PROMPT;
			public static String INVALID;
			public static String RESPONSE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Nation_Conversables.Withdraw");
				PROMPT = get(player, "Prompt");
				INVALID = get(player, "Invalid");
				RESPONSE = get(player, "Response");
				popPathPrefix();
			}
		}
	}

	public static class TownConversables {

		public static class Board {
			public static String PROMPT;
			public static String RESPONSE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Town_Conversables.Board");
				PROMPT = get(player, "Prompt");
				RESPONSE = get(player, "Response");
				popPathPrefix();
			}
		}

		public static class Deposit {
			public static String PROMPT;
			public static String INVALID;
			public static String RESPONSE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Town_Conversables.Deposit");
				PROMPT = get(player, "Prompt");
				INVALID = get(player, "Invalid");
				RESPONSE = get(player, "Response");
				popPathPrefix();
			}
		}

		public static class Mayor {
			public static String PROMPT;
			public static String RESPONSE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Town_Conversables.Mayor");
				PROMPT = get(player, "Prompt");
				RESPONSE = get(player, "Response");
				popPathPrefix();
			}
		}

		public static class Kick {
			public static String PROMPT;
			public static String RESPONSE;
			public static String MAYOR;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Town_Conversables.Kick");
				PROMPT = get(player, "Prompt");
				RESPONSE = get(player, "Response");
				MAYOR = get(player, "Mayor");
				popPathPrefix();
			}
		}

		public static class Name {
			public static String PROMPT;
			public static String INVALID;
			public static String RESPONSE;
			public static String RETURN;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Town_Conversables.Name");
				PROMPT = get(player, "Prompt");
				INVALID = get(player, "Invalid");
				RESPONSE = get(player, "Response");
				RETURN = get(player, "Return");
				popPathPrefix();
			}
		}

		public static class Rank {
			public static String PROMPT;
			public static String INVALID;
			public static String RESPONSE;
			public static String REMOVE;
			public static String REMOVED_ALL;
			public static String ALREADY_HAS_RANK;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Town_Conversables.Rank");
				PROMPT = get(player, "Prompt");
				INVALID = get(player, "Invalid");
				RESPONSE = get(player, "Response");
				REMOVE = get(player, "Remove");
				REMOVED_ALL = get(player, "Removed_All");
				ALREADY_HAS_RANK = get(player, "Has_Rank");
				popPathPrefix();
			}
		}

		public static class Tax {
			public static String PROMPT;
			public static String INVALID_PERCENT;
			public static String INVALID_AMOUNT;
			public static String RESPONSE_AMOUNT;
			public static String RESPONSE_PERCENT;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Town_Conversables.Tax");
				PROMPT = get(player, "Prompt");
				INVALID_AMOUNT = get(player, "Invalid_Amount");
				INVALID_PERCENT = get(player, "Invalid_Percent");
				RESPONSE_PERCENT = get(player, "Response_Percent");
				RESPONSE_AMOUNT = get(player, "Response_Amount");
				popPathPrefix();
			}
		}

		public static class Title {
			public static String PROMPT;
			public static String RESPONSE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Town_Conversables.Title");
				PROMPT = get(player, "Prompt");
				RESPONSE = get(player, "Response");
				popPathPrefix();
			}
		}

		public static class Withdraw {
			public static String PROMPT;
			public static String INVALID;
			public static String RESPONSE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Town_Conversables.Withdraw");
				PROMPT = get(player, "Prompt");
				INVALID = get(player, "Invalid");
				RESPONSE = get(player, "Response");
				popPathPrefix();
			}
		}
	}

	public static class PlotConversables {

		public static class Evict {
			public static String PROMPT;
			public static String INVALID;
			public static String RESPONSE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Plot_Conversables.Evict");
				PROMPT = get(player, "Prompt");
				INVALID = get(player, "Invalid");
				RESPONSE = get(player, "Response");
				popPathPrefix();
			}
		}

		public static class ForSale {
			public static String PROMPT;
			public static String INVALID;
			public static String RESPONSE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Plot_Conversables.For_Sale");
				PROMPT = get(player, "Prompt");
				INVALID = get(player, "Invalid");
				RESPONSE = get(player, "Response");
				popPathPrefix();
			}
		}

		public static class NotForSale {
			public static String PROMPT;
			public static String RESPONSE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Plot_Conversables.Not_For_Sale");
				PROMPT = get(player, "Prompt");
				RESPONSE = get(player, "Response");
				popPathPrefix();
			}
		}

		public static class SetType {
			public static String PROMPT;
			public static String INVALID;
			public static String RESPONSE;

			private static void init(Player player) {
				String locale = (player != null && player.getLocale() != null)
						? player.getLocale()
						: "messages_en";
				pushPathPrefix("Plot_Conversables.Set_Type");
				PROMPT = get(player, "Prompt");
				INVALID = get(player, "Invalid");
				RESPONSE = get(player, "Response");
				popPathPrefix();
			}
		}
	}

	public static class Error {

		public static String NO_PERMISSION;
		public static String INVALID;
		public static String NO_TOWN;
		public static String ECONOMY_DISABLED;
		public static String WAR_TIME;
		public static String MUST_BE_IN_OWN_PLOT;
		public static String MUST_BE_IN_NATION;
		public static String MUST_BE_IN_TOWN;
		public static String MUST_BE_IN_BANK;
		public static String TOGGLE_PVP_OUTSIDERS;
		public static String TOGGLE_PVP_COOLDOWN;
		public static String CANNOT_SELECT_SELF;
		public static String CANNOT_SET_HOMEBLOCK;
		public static String CANNOT_SET_SPAWN;
		public static String CANNOT_CHANGE_NAME;
		public static String CANNOT_CHANGE_BOARD;
		public static String CANCELLED;

		private static void init(Player player) {
			String locale = (player != null && player.getLocale() != null)
					? player.getLocale()
					: "messages_en";
			pushPathPrefix("Error");

			NO_PERMISSION = get(player, "No_Permission");
			INVALID = get(player, "Invalid_Input");
			NO_TOWN = get(player, "No_Town");
			ECONOMY_DISABLED = get(player, "Economy_Disabled");
			WAR_TIME = get(player, "War_Time");
			MUST_BE_IN_OWN_PLOT = get(player, "Must_Be_In_Own_Plot");
			MUST_BE_IN_NATION = get(player, "Must_Be_In_Nation");
			MUST_BE_IN_TOWN = get(player, "Must_Be_In_Town");
			MUST_BE_IN_BANK = get(player, "Must_Be_In_Bank");
			TOGGLE_PVP_OUTSIDERS = get(player, "Toggle_PVP_Outsiders");
			TOGGLE_PVP_COOLDOWN = get(player, "Toggle_PVP_Cooldown");
			CANNOT_SELECT_SELF = get(player, "Cannot_Select_Self");
			CANNOT_SET_HOMEBLOCK = get(player, "Cannot_Set_Homeblock");
			CANNOT_SET_SPAWN = get(player, "Cannot_Set_Spawn");
			CANNOT_CHANGE_NAME = get(player, "Cannot_Change_Name");
			CANNOT_CHANGE_BOARD = get(player, "Cannot_Change_Board");
			CANCELLED = get(player, "Cancelled");

			popPathPrefix();
		}
	}

	public static void pushPathPrefix(String prefix) {
		pathPrefixStack.push(prefix.endsWith(".") ? prefix : prefix + ".");
	}

	public static void popPathPrefix() {
		if (!pathPrefixStack.isEmpty()) pathPrefixStack.pop();
	}

	public static String get(Player player, String path) {
		String fullPath = applyPrefix(path);
		String raw = config.getString(fullPath);
		if (raw == null)
			return "Missing lang key: " + fullPath;
		return raw;
	}

	public static List<String> getList(Player player, String path) {
		String fullPath = applyPrefix(path);
		List<String> rawList = config.getStringList(fullPath);
		if (rawList == null || rawList.isEmpty())
			return List.of("Missing lang key: " + fullPath);
		return rawList;
	}

	private static String applyPrefix(String path) {
		StringBuilder fullPath = new StringBuilder();
		Iterator<String> it = pathPrefixStack.descendingIterator();
		while (it.hasNext()) {
			fullPath.append(it.next());
		}
		fullPath.append(path);
		return fullPath.toString();
	}
}
