package me.cobrex.townymenu.utils;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class MenuItem {

	private final ItemStack itemStack;
	private final Consumer<InventoryClickEvent> handler;

	public MenuItem(ItemStack itemStack, Consumer<InventoryClickEvent> handler) {
		this.itemStack = itemStack;
		this.handler = handler;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public void onClick(InventoryClickEvent event) {
		if (handler != null) {
			handler.accept(event);
		}
	}

	public Material getMaterial() {
		return this.itemStack.getType();
	}

}