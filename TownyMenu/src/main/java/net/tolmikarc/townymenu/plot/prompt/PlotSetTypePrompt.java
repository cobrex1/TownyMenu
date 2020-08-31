package net.tolmikarc.townymenu.plot.prompt;

import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownBlockType;
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
		return "&6What type would you like to set this plot? &2(Options: " + Common.join(plotTypes, ", ") + ")";
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		return (plotTypes.contains(input.toLowerCase()));
	}

	@Override
	protected String getFailedValidationText(ConversationContext context, String invalidInput) {
		return "&cPlease enter a valid plot type. Valid types: " + Common.join(plotTypes, ", ");
	}

	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
		if (!getPlayer(context).hasPermission("towny.command.plot.set" + input.toLowerCase())) {
			tell("&cYou do not have permission to set this plot type here.");
			return null;
		}
		townBlock.setType(TownBlockType.valueOf(input.toUpperCase()));
		tell("&aPlot type set to: " + input);
		return null;
	}
}
