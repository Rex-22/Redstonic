package com.raizunne.redstonic.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParser;
import com.raizunne.redstonic.Handler.RedstonicWorldData;
import com.raizunne.redstonic.Redstonic;
import com.raizunne.redstonic.Util.Util;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

/**
 * Created by Raizunne as a part of Redstonic
 * on 09/07/2015, 12:00 AM.
 */
public class RedstonicMessanger extends Item {

    public RedstonicMessanger() {
        setUnlocalizedName("RedstonicMessanger");
        setMaxStackSize(1);
        setCreativeTab(Redstonic.redTab);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {

    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (player.isSneaking()) {
            sendMessage(
                player,
                player.getDisplayName(),
                new ItemStack[] { new ItemStack(Items.apple, 69), new ItemStack(Items.potato, 69) });
            getCratesFromPlayer(player, world);
            System.out.println(stack.stackTagCompound);
            return stack;
        }

        FMLNetworkHandler
            .openGui(player, Redstonic.instance, 7, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        return stack;
    }

    public static void sendMessage(EntityPlayer sender, String playerName, ItemStack[] stack) {
        RedstonicWorldData worldData = RedstonicWorldData.get(sender.getEntityWorld());
        NBTTagCompound worldTag = worldData.getData();
        NBTTagList playerCrates = worldTag.getTagList(playerName.toLowerCase(), 10);

        ItemStack tempCrate = Util.newCrate(stack, sender.getDisplayName());

        NBTTagCompound newCrate = new NBTTagCompound();
        tempCrate.writeToNBT(newCrate);
        playerCrates.appendTag(newCrate);

        worldTag.setTag(playerName.toLowerCase(), playerCrates);
        worldData.markDirty();
    }

    public static void resetMessages(String sender, World world) {
        RedstonicWorldData worldData = RedstonicWorldData.get(world);
        NBTTagCompound worldTag = worldData.getData();
        NBTTagList playerCrates = worldTag.getTagList(sender.toLowerCase(), 10);
        // for(int i=0; i<playerCrates.tagCount(); i++){
        // playerCrates.removeTag(i);
        // }
        worldTag.removeTag(sender);
        worldData.markDirty();
    }

    public static List<ItemStack> getCratesFromPlayer(EntityPlayer player, World world) {
        List<ItemStack> crates = new ArrayList<ItemStack>();
        RedstonicWorldData worldData = RedstonicWorldData.get(player.getEntityWorld());
        NBTTagCompound worldTag = worldData.getData();
        NBTTagList playerCrates = worldTag.getTagList(
            player.getDisplayName()
                .toLowerCase(),
            10);

        System.out.println(playerCrates);

        for (int i = 0; i < playerCrates.tagCount(); i++) {
            crates.add(ItemStack.loadItemStackFromNBT(playerCrates.getCompoundTagAt(i)));
        }
        return crates;
    }

    public String getNameFromUUID(String uuid) {
        try {
            URL url = new URL("https://raw.githubusercontent.com/Raizunne/Redstonic/master/src/versions/changelog.txt");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return "fuck";
    }

    public String getUUIDFromName(String name) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            BufferedReader read = new BufferedReader(new InputStreamReader(url.openStream(), Charset.forName("UTF-8")));
            int cp;
            StringBuilder builder = new StringBuilder();
            while ((cp = read.read()) != -1) {
                builder.append((char) cp);
            }
            String text = builder.toString();
            JsonElement json = new JsonParser().parse(text);
            if (json instanceof JsonNull) {
                return null;
            } else {
                return json.getAsJsonObject()
                    .get("id")
                    .getAsString()
                    .replaceFirst(
                        "([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)",
                        "$1-$2-$3-$4-$5");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
