package openccsensors.api;

import net.minecraft.init.Blocks;

import net.minecraft.init.Items;

public interface ISensorAccess {
	public ISensorEnvironment getSensorEnvironment();
	public boolean isTurtle();
}