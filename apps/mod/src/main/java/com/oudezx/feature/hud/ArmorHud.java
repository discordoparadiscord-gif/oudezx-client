package com.oudezx.feature.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class ArmorHud extends Hud {
    public ArmorHud() {
        super("Armor");
        this.x = 10;
        this.y = 130;
        this.width = 180;
        this.height = 20;
    }

    @Override
    public void update() {
        // Armor updates automatically
    }

    @Override
    public void render(MinecraftClient client, float tickDelta) {
        if (!enabled || client.player == null) return;
        
        // Get armor pieces
        ItemStack helmet = client.player.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chestplate = client.player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack leggings = client.player.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack boots = client.player.getEquippedStack(EquipmentSlot.FEET);
        
        // Render armor
        // This would render the armor items and their durability
    }
}