package openccsensors.api;

import net.minecraft.init.Blocks;

import net.minecraft.init.Items;

import java.util.HashMap;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public interface IGaugeSensor {
	public String[] getGaugeProperties();
	public boolean isValidTarget(Object obj);
	public HashMap getDetails(World world, Object obj, ChunkCoordinates location, boolean additional);
}
