package openccsensors.common.util;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.Loader;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class InventoryUtils {

	public static final String FACTORIZATION_BARREL_CLASS = "factorization.common.TileEntityBarrel";
	
	public static HashMap itemstackToMap(ItemStack itemstack) {
		
		HashMap map = new HashMap();

		if (itemstack == null) {
			
			map.put("Name", "empty");
			map.put("Size", 0);
			map.put("Damagevalue", 0);
			map.put("MaxStack", 64);
			return map;
		
		} else {
		
			Item item = itemstack.getItem();

			map.put("Name", getNameForItemStack(itemstack));
			map.put("RawName", getRawNameForStack(itemstack));
			map.put("Size", itemstack.stackSize);
			map.put("DamageValue", itemstack.getItemDamage());
			map.put("MaxStack", itemstack.getMaxStackSize());
			
		}

		return map;
	}


	public static HashMap invToMap(IInventory inventory) {
		HashMap map = new HashMap();
		if (inventory.getClass().getName() == FACTORIZATION_BARREL_CLASS) {
			Map details = itemstackToMap(inventory.getStackInSlot(0));
			try {
				TileEntity barrel = (TileEntity) inventory;
				NBTTagCompound compound = new NBTTagCompound();
				barrel.writeToNBT(compound);
				details.put("Size", compound.getInteger("item_count"));
				details.put("MaxStack", compound.getInteger("upgrade") == 1 ?  65536 : 4096);

			}catch(Exception e) {
			}
			map.put(1, details);
		}else {
			for (int i = 0; i < inventory.getSizeInventory(); i++) {
				map.put(i + 1, itemstackToMap(inventory.getStackInSlot(i)));
			}
		}
		return map;
	}
	
	public static String getNameForItemStack(ItemStack is) {
		String name = "Unknown";
		try {
			name = is.getDisplayName();
		}catch(Exception e) {
			try {
				name = is.getItemName();
			}catch(Exception e2) {
			}
		}
		return name;
	}
	
	public static String getRawNameForStack(ItemStack is) {

		String rawName = "unknown";

		try {
			rawName = is.getItemName().toLowerCase();
		}catch(Exception e) {			
		}
		try {
			if (rawName.length() - rawName.replaceAll("\\.", "").length() == 0) {
				String packageName = is.getItem().getClass().getName().toLowerCase();
				String[] packageLevels = packageName.split("\\.");
				if (!rawName.startsWith(packageLevels[0]) && packageLevels.length > 1) {
					rawName = packageLevels[0] + "." + rawName;
				}
			}
		}catch(Exception e) {

		}

		return rawName.trim();
	}
}
