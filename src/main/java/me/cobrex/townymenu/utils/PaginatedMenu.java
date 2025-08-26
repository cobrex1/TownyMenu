package me.cobrex.townymenu.utils;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


public abstract class PaginatedMenu extends MenuHandler {

	protected final List<MenuItem> contentItems = new ArrayList<>();
	protected int currentPage = 0;
	int rows;

	public PaginatedMenu(Player player, String rawTitle, List<MenuItem> items) {
		super(player, rawTitle, calculateMenuRows(items.size()) * 9);
		this.contentItems.addAll(items);
		this.rows = calculateMenuRows(items.size());
		drawPage();
	}

	private static int calculateMenuRows(int itemCount) {
		int itemsPerRow = 9;
		int maxContentRows = 5;
		int requiredRows = (int) Math.ceil(itemCount / (double) itemsPerRow);
		return Math.min(1 + requiredRows, maxContentRows + 1);
	}

	protected void drawPage() {
		clearMenuItems();

		int contentSlots = (rows - 1) * 9;
		int startIndex = currentPage * contentSlots;
		int endIndex = Math.min(startIndex + contentSlots, contentItems.size());

		for (int i = startIndex, slot = 0; i < endIndex; i++, slot++) {
			setMenuItem(slot, contentItems.get(i));
//			Bukkit.getLogger().info("[DEBUG] Placing content item at slot " + slot);
		}
		drawNavigationButtons(rows);
	}

	private void drawNavigationButtons(int totalRows) {
		int navRow = totalRows - 1;

		int backSlot = navRow * 9 + 4;
//		Bukkit.getLogger().info("[DEBUG] Back button will be placed at slot: " + backSlot + " | Inv size: " + getInventory().getSize());
		addNavMenuItem("back_button", backSlot, click -> {
			try {
				onBack();
			} catch (NotRegisteredException e) {
				throw new RuntimeException(e);
			}
		});

		if (currentPage > 0) {
			int prevSlot = navRow * 9 + 2;
//			Bukkit.getLogger().info("[DEBUG] Prev button will be placed at slot: " + prevSlot);
			addNavMenuItem("menu_previous_page_button", prevSlot, click -> {
				currentPage--;
				drawPage();
			});
		}

		int contentSlots = (totalRows - 1) * 9;
		if ((currentPage + 1) * contentSlots < contentItems.size()) {
			int nextSlot = navRow * 9 + 6;
//			Bukkit.getLogger().info("[DEBUG] Next button will be placed at slot: " + nextSlot);
			addNavMenuItem("menu_next_page_button", nextSlot, click -> {
				currentPage++;
				drawPage();
			});
		}

		MenuItem info = getInfoButton();
		if (info != null) {
			int infoSlot = getInventory().getSize() - 9;
			setMenuItem(infoSlot, info);
		}
	}

	protected @Nullable MenuItem getInfoButton() {
		return null;
	}

	protected abstract void onBack() throws NotRegisteredException;
}
