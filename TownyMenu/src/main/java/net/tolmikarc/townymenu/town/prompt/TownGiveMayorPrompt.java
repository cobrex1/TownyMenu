package net.tolmikarc.townymenu.town.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import lombok.SneakyThrows;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.conversation.SimplePrompt;

public class TownGiveMayorPrompt extends SimplePrompt {

	Resident resident;

	public TownGiveMayorPrompt(Resident resident) {
		super(false);
		this.resident = resident;
	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return "&cAre you sure you would like to give &a" + resident.getName() + " &cthe Mayor position? &6(Type &aconfirm &6 or &4deny&6)";
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		return input.toLowerCase().equals("confirm") || input.toLowerCase().equals("deny");
	}

	@SneakyThrows
	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
		if (!getPlayer(context).hasPermission("towny.command.town.set.mayor"))
			return null;


		if (input.toLowerCase().equals("confirm")) {
			Town town = resident.getTown();
			town.setMayor(resident);
			TownyAPI.getInstance().getDataSource().saveTown(town);
		}

		return null;
	}
}
