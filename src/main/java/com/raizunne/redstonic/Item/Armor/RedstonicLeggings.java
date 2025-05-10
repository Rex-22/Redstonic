package com.raizunne.redstonic.Item.Armor;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

/**
 * Created by Raizunne as a part of Redstonic
 * on 27/06/2015, 11:34 PM.
 */
public class RedstonicLeggings extends RedArmorBase {

    public RedstonicLeggings() {
        super(2, "RedstonicLeggings");
        this.armorPoints = 6;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
        super.addInformation(stack, player, list, p_77624_4_);
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage,
        int slot) {
        return super.getProperties(player, armor, source, damage, slot);
    }
}
