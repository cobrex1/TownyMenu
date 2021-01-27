package net.tolmikarc.townymenu.plot.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.event.TownBlockSettingsChangedEvent;
import com.palmergames.bukkit.towny.object.TownBlock;
import lombok.SneakyThrows;
import net.tolmikarc.townymenu.settings.Localization;
import org.bukkit.Bukkit;
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
		townBlock.setPlotPrice(-1);
		townBlock.setChanged(true);
		TownBlockSettingsChangedEvent event = new TownBlockSettingsChangedEvent(townBlock);
		Bukkit.getServer().getPluginManager().callEvent(event);
		TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
		TownyAPI.getInstance().getDataSource().saveTown(townBlock.getTown());
		tell(Localization.PlotConversables.NotForSale.RESPONSE);
		return null;
	}
}
