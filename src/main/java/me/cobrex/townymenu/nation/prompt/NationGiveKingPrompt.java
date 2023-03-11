package me.cobrex.townymenu.nation.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import lombok.SneakyThrows;
import me.cobrex.townymenu.settings.Localization;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.conversation.SimplePrompt;

public class NationGiveKingPrompt extends SimplePrompt {


	Resident resident;

	public NationGiveKingPrompt(Resident resident) {
		super(false);
		this.resident = resident;
	}

	@Override
	public boolean isModal() {
		return false;
	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return Localization.NationConversables.Nation_King.PROMPT.replace("{player}", resident.getName());
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		return input.toLowerCase().equals(Localization.CONFIRM) || input.toLowerCase().equals(Localization.CANCEL);
	}

	@SneakyThrows
	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
		if (!getPlayer(context).hasPermission("towny.command.nation.set.king"))
			return null;

		if (input.toLowerCase().equals(Localization.CONFIRM)) {
			Nation nation = resident.getNation();
			nation.setKing(resident);
			Common.tell(getPlayer(context), Localization.NationConversables.Nation_King.RESPONSE.replace("{player}", resident.getName()));
			TownyAPI.getInstance().getDataSource().saveNation(nation);
			TownyAPI.getInstance().getDataSource().saveResident(resident);
		}

		return null;
	}
}

