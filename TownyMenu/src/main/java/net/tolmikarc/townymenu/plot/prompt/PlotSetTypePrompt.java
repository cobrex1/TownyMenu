package net.tolmikarc.townymenu.plot.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.event.TownBlockSettingsChangedEvent;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownBlockType;
import lombok.SneakyThrows;
import net.tolmikarc.townymenu.settings.Localization;
import org.bukkit.Bukkit;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.conversation.SimplePrompt;

import java.util.Arrays;
import java.util.List;

public class PlotSetTypePrompt extends SimplePrompt {

	private final List<String> plotTypes = Arrays.asList("residential", "commercial", "bank", "farm", "jail", "embassy", "wilds", "inn", "spleef", "arena");

	TownBlock townBlock;

	public PlotSetTypePrompt(TownBlock townBlock) {
		super(false);

		this.townBlock = townBlock;

	}


	@Override
	protected String getPrompt(ConversationContext ctx) {
		return Localization.PlotConversables.SetType.PROMPT.replace("{options}", Common.join(plotTypes, ", "));
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		return (plotTypes.contains(input.toLowerCase())) || (input.equalsIgnoreCase(Localization.CANCEL));
	}

	@Override
	protected String getFailedValidationText(ConversationContext context, String invalidInput) {
		return Localization.PlotConversables.SetType.INVALID.replace("{options}", Common.join(plotTypes, ", "));
	}

	@SneakyThrows
	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
		if (!getPlayer(context).hasPermission("towny.command.plot.set." + input.toLowerCase()) || input.equalsIgnoreCase(Localization.CANCEL)) {
			return null;
		}
		townBlock.setType(TownBlockType.valueOf(input.toUpperCase()));
		townBlock.setChanged(true);
		TownBlockSettingsChangedEvent event = new TownBlockSettingsChangedEvent(townBlock);
		Bukkit.getServer().getPluginManager().callEvent(event);
		TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
		TownyAPI.getInstance().getDataSource().saveTown(townBlock.getTown());
		tell(Localization.PlotConversables.SetType.RESPONSE.replace("{input}", input));
		return null;
	}
}
