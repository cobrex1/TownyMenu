package me.cobrex.townymenu.town.command;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import lombok.SneakyThrows;
import me.cobrex.townymenu.join.JoinTownMenu;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.town.TownMenu;
import org.bukkit.entity.Player;
import org.mineacademy.fo.command.SimpleCommand;
import org.mineacademy.fo.plugin.SimplePlugin;

import java.util.List;

public class TownMenuCommand extends SimpleCommand {
	public TownMenuCommand() {
		super("townmenu|tm");
		setPermission(null);
	}

	@SneakyThrows
	@Override
	protected void onCommand() {
		checkConsole();

		Player player = this.getPlayer();

		if (this.args.length == 0) {
			Town town;
			Resident resident = TownyAPI.getInstance().getResident(getPlayer().getName());
			assert resident != null;
			if (resident.hasTown()) {
				town = resident.getTown();
				if (town.getMayor().equals(resident) && town.getMayor().hasPermissionNode("townymenu.town.use"))
					new TownMenu(town, getPlayer()).displayTo(getPlayer());
				else if (getPlayer().hasPermission("townymenu.town.use")) {
					new TownMenu(town, getPlayer()).displayTo(getPlayer());
				} else
					tell(Localization.Error.NO_PERMISSION);
			} else if (!resident.hasTown()) {
				new JoinTownMenu(resident, getPlayer()).displayTo(getPlayer());
			}

			return;
		}

		String param = this.args[0].toLowerCase();

		if ("reload".equals(param) && player.hasPermission("townmenu reload") || player.isOp()) {
			SimplePlugin.getInstance().reload();

			this.tell(Localization.RELOADED);
		}
	}

	@Override
	public List<String> tabComplete() {
		if (this.args.length == 1)
			return this.completeLastWord("reload");

		return NO_COMPLETE;
	}
}
