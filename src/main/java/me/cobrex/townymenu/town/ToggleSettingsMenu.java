package me.cobrex.townymenu.town;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.tasks.CooldownTimerTask;
import me.cobrex.townymenu.config.ConfigNodes;
import me.cobrex.townymenu.config.ConfigUtil;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.MenuHandler;
import me.cobrex.townymenu.utils.MenuItemBuilder;
import me.cobrex.townymenu.utils.MenuManager;
import me.cobrex.townymenu.utils.MessageFormatter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;



public class ToggleSettingsMenu extends MenuHandler {

	private final Player player;
	private final Town town;

	public ToggleSettingsMenu(Player player) {
		super(
				player,
				Localization.TownMenu.ToggleMenu.MENU_TITLE,
				ConfigUtil.getInt(ConfigNodes.TOWN_TOGGLE_MENU_SIZE)
		);

		this.player = player;
		this.town = TownyAPI.getInstance().getTown(player);

		if (town == null) {
			return;
		}

		fillEmptySlots("filler_town_toggle_menu");

		MenuItemBuilder.of("town_toggle_fire_button")
				.name(Localization.TownMenu.ToggleMenu.FIRE)
				.lore(town.isFire()
						? Localization.TownMenu.ToggleMenu.TOGGLE_OFF
						: Localization.TownMenu.ToggleMenu.TOGGLE_ON)
				.onClick(click -> toggleTownSetting("fire", "towny.command.town.toggle.fire"))
				.buildAndSet(player, this);

		MenuItemBuilder.of("town_toggle_mobs_button")
				.name(Localization.TownMenu.ToggleMenu.MOBS)
				.lore(town.hasMobs()
						? Localization.TownMenu.ToggleMenu.TOGGLE_OFF
						: Localization.TownMenu.ToggleMenu.TOGGLE_ON)
				.onClick(click -> toggleTownSetting("mobs", "towny.command.town.toggle.mobs"))
				.buildAndSet(player, this);

		MenuItemBuilder.of("town_toggle_explosions_button")
				.name(Localization.TownMenu.ToggleMenu.EXPLODE)
				.lore(town.isExplosion()
						? Localization.TownMenu.ToggleMenu.TOGGLE_OFF
						: Localization.TownMenu.ToggleMenu.TOGGLE_ON)
				.onClick(click -> toggleTownSetting("explosion", "towny.command.town.toggle.explosion"))
				.buildAndSet(player, this);

		MenuItemBuilder.of("town_toggle_pvp_button")
				.name(Localization.TownMenu.ToggleMenu.PVP)
				.lore(town.isPVP()
						? Localization.TownMenu.ToggleMenu.TOGGLE_OFF
						: Localization.TownMenu.ToggleMenu.TOGGLE_ON)
				.onClick(click -> togglePVP())
				.buildAndSet(player, this);

		MenuItemBuilder.of("town_toggle_public_button")
				.name(Localization.TownMenu.ToggleMenu.PUBLIC)
				.lore(town.isPublic()
						? Localization.TownMenu.ToggleMenu.TOGGLE_OFF
						: Localization.TownMenu.ToggleMenu.TOGGLE_ON)
				.onClick(click -> toggleTownSetting("public", "towny.command.town.toggle.public"))
				.buildAndSet(player, this);

		MenuItemBuilder.of("town_toggle_open_button")
				.name(Localization.TownMenu.ToggleMenu.OPEN)
				.lore(town.isOpen()
						? Localization.TownMenu.ToggleMenu.TOGGLE_OFF
						: Localization.TownMenu.ToggleMenu.TOGGLE_ON)
				.onClick(click -> toggleTownSetting("open", "towny.command.town.toggle.open"))
				.buildAndSet(player, this);

		MenuItemBuilder.of("town_toggle_tax_percent_button")
				.name(Localization.TownMenu.ToggleMenu.TAX_PERCENT)
				.lore(town.isTaxPercentage()
						? Localization.TownMenu.ToggleMenu.TOGGLE_OFF
						: Localization.TownMenu.ToggleMenu.TOGGLE_ON)
				.onClick(click -> toggleTownSetting("tax", "towny.command.town.toggle.taxpercent"))
				.buildAndSet(player, this);

		MenuItemBuilder.of("town_toggle_menu_info_button")
				.name(Localization.TownMenu.ToggleMenu.INFO)
				.lore(Localization.TownMenu.ToggleMenu.INFO_LORE)
				.onClick(click -> {})
				.buildAndSet(player, this);

		MenuItemBuilder.of("town_toggle_menu_back_button")
				.name(Localization.TownMenu.ToggleMenu.BACK_BUTTON)
				.lore(Localization.TownMenu.ToggleMenu.BACK_BUTTON_LORE)
				.onClick(click -> {
					try {
						MenuManager.switchMenu(player, new TownMenu(player, town));
					} catch (NotRegisteredException e) {
						throw new RuntimeException(e);
					}
				})
				.buildAndSet(player, this);
	}

	private void toggleTownSetting(String type, String permission) {
		if (!player.hasPermission(permission)) {
			MessageFormatter.format(Localization.Error.NO_PERMISSION, player);
			return;
		}

		switch (type.toLowerCase()) {
			case "fire" -> town.setFire(!town.isFire());
			case "mobs" -> town.setHasMobs(!town.hasMobs());
			case "explosion" -> town.setExplosion(!town.isExplosion());
			case "public" -> town.setPublic(!town.isPublic());
			case "open" -> town.setOpen(!town.isOpen());
			case "tax" -> town.setTaxPercentage(!town.isTaxPercentage());
		}

		saveTown();
		MenuManager.refreshInPlace(player, new ToggleSettingsMenu(player));
	}

	private void togglePVP() {
		if (!player.hasPermission("towny.command.town.toggle.pvp")) {
			MessageFormatter.format(Localization.Error.NO_PERMISSION, player);
			return;
		}

		if (TownySettings.getOutsidersPreventPVPToggle()) {
			for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
				Resident res = TownyAPI.getInstance().getResident(onlinePlayer.getName());
				if (res.hasTown()) {
					try {
						if (!res.getTown().equals(town) &&
								TownyAPI.getInstance().getTownBlock(onlinePlayer.getLocation()) != null &&
								Objects.requireNonNull(TownyAPI.getInstance().getTownBlock(onlinePlayer.getLocation())).getTown().equals(town)) {
							player.sendMessage(MessageFormatter.format(Localization.Error.TOGGLE_PVP_OUTSIDERS, player));
							player.closeInventory();
							return;
						}
					} catch (NotRegisteredException e) {
						throw new RuntimeException(e);
					}
				} else {
					if (TownyAPI.getInstance().getTownBlock(onlinePlayer.getLocation()) != null) {
						try {
							if (Objects.requireNonNull(TownyAPI.getInstance().getTownBlock(onlinePlayer.getLocation())).getTown().equals(town)) {
								player.sendMessage(MessageFormatter.format(Localization.Error.TOGGLE_PVP_OUTSIDERS, player));
								player.closeInventory();
								return;
							}
						} catch (NotRegisteredException e) {
							throw new RuntimeException(e);
						}
					}
				}
			}
		}

		if (TownySettings.getPVPCoolDownTime() > 0 &&
				CooldownTimerTask.hasCooldown(town.getName(), CooldownTimerTask.CooldownType.PVP)) {
			player.sendMessage(MessageFormatter.format(Localization.Error.TOGGLE_PVP_COOLDOWN, player));
			player.closeInventory();
			return;
		}

		town.setPVP(!town.isPVP());
		CooldownTimerTask.addCooldownTimer(town.getName(), CooldownTimerTask.CooldownType.PVP);

		saveTown();
		MenuManager.refreshInPlace(player, new ToggleSettingsMenu(player));
	}

	private void saveTown() {
		TownyAPI.getInstance().getDataSource().saveTown(town);
	}
}
