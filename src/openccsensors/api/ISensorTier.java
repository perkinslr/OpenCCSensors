package openccsensors.api;

import net.minecraft.init.Blocks;

import net.minecraft.init.Items;

import net.minecraft.util.IIcon;

public interface ISensorTier {
	
	public EnumItemRarity getRarity();
	public double getMultiplier();
	public String getName();
	public IIcon getIcon();
	
}
