package me.cobrex.townymenu.nation;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import lombok.SneakyThrows;
import me.cobrex.townymenu.settings.Localization;
import org.bukkit.entity.Player;
import org.mineacademy.fo.command.SimpleCommand;
import org.mineacademy.fo.plugin.SimplePlugin;

import java.util.List;

public class NationMenuCommand extends SimpleCommand {
	public NationMenuCommand() {
		super("nationmenu|nm");
		setPermission(null);
	}

	@SneakyThrows
	@Override
	protected void onCommand() {
		checkConsole();

		Player player = this.getPlayer();

		if (this.args.length == 0) {
			Nation nation;
			Resident resident = TownyAPI.getInstance().getResident(getPlayer().getName());
			Town town = TownyAPI.getInstance().getTown(getPlayer().getName());
			if (resident.hasTown() && resident.hasNation()) {
				town = resident.getTown();
				nation = town.getNation();

				if (nation.getKing().equals(resident) && town.getMayor().hasPermissionNode("townymenu.nation.use"))
					new NationMenu(nation, getPlayer()).displayTo(getPlayer());
				else if (getPlayer().hasPermission("townymenu.nation.use")) {
					new NationMenu(nation, getPlayer()).displayTo(getPlayer());
				} else
					tell(Localization.Error.NO_PERMISSION);
			} else if (!resident.hasNation()) {
				new JoinNationMenu(resident, getPlayer()).displayTo(getPlayer());
			}

			return;
		}

		String param = this.args[0].toLowerCase();

		if ("reload".equals(param) && player.hasPermission("townmenu reload") || player.isOp()) {
			SimplePlugin.getInstance().reload();

			this.tell("Settings reloaded!");
		}
	}

	@Override
	public List<String> tabComplete() {
		if (this.args.length == 1)
			return this.completeLastWord("reload");

		return NO_COMPLETE;
	}
}
