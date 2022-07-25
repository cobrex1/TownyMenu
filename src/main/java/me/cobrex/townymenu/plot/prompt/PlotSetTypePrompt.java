package me.cobrex.townymenu.plot.prompt;

import com.palmergames.bukkit.towny.object.TownBlock;
import lombok.SneakyThrows;
import me.cobrex.townymenu.settings.Localization;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.conversation.SimplePrompt;

import java.util.Arrays;
import java.util.List;

public class PlotSetTypePrompt extends SimplePrompt {

//	private final List<String> plotTypes = Arrays.asList("arena", "bank", "embassy", "farm", "inn", "jail", "shop", "default", "wilds", "residential", "commercial", "wilds", "spleef", "arena");
	private final List<String> plotTypes = Arrays.asList("arena", "bank", "default", "embassy", "farm", "inn", "jail", "shop", "wilds");

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

		getPlayer(context).performCommand("plot set " + (input));
		tell(Localization.PlotConversables.SetType.RESPONSE.replace("{input}", input));
		return null;
	}
}
