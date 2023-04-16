package me.cobrex.townymenu.plot.prompt;

import com.palmergames.bukkit.towny.object.TownBlock;
import lombok.SneakyThrows;
import me.cobrex.townymenu.settings.Localization;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.conversation.SimplePrompt;

public class PlotNotForSalePrompt extends SimplePrompt {

	TownBlock townBlock;

	public PlotNotForSalePrompt(TownBlock townBlock) {
		super(false);

		this.townBlock = townBlock;
	}

	@Override
	public boolean isModal() {
		return false;
	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return Localization.PlotConversables.NotForSale.PROMPT;
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		return (input.equalsIgnoreCase(Localization.CANCEL) || input.equalsIgnoreCase(Localization.CONFIRM));
	}


	@SneakyThrows
	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
		if (!getPlayer(context).hasPermission("towny.command.plot.notforsale") || input.equalsIgnoreCase(Localization.CANCEL)) {
			return null;
		}

		getPlayer(context).performCommand("plot nfs");
		tell(Localization.PlotConversables.NotForSale.RESPONSE);
		return null;
	}
}
