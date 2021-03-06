package openccsensors.common.sensor;

import net.minecraft.init.Blocks;

import net.minecraft.init.Items;

import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import openccsensors.api.IRequiresIconLoading;
import openccsensors.api.ISensor;
import openccsensors.api.ISensorTier;
import openccsensors.common.util.EntityUtils;

public class ProximitySensor implements ISensor, IRequiresIconLoading {
	
	public static final int MODE_ALL = 0;
	public static final int MODE_PLAYERS = 1;
	public static final int MODE_OWNER = 2;
	
	private IIcon icon;

	@Override
	public HashMap getDetails(World world, Object obj, ChunkCoordinates sensorPos, boolean additional) {
		return EntityUtils.livingToMap((EntityLivingBase)obj, sensorPos, additional);
	}

	@Override
	public HashMap getTargets(World world, ChunkCoordinates location,
			ISensorTier tier) {
		double radius = tier.getMultiplier() * 4;
		return (HashMap) EntityUtils.getEntities(world, location, radius, EntityLivingBase.class);
	}

	@Override
	public String[] getCustomMethods(ISensorTier tier) {
		return null;
	}

	@Override
	public Object callCustomMethod(World world, ChunkCoordinates location, int methodID,
			Object[] args, ISensorTier tier) {
		return null;
	}

	@Override
	public String getName() {
		return "proximityCard";
	}
	
	@Override
	public IIcon getIcon() {
		return icon;
	}

	@Override
	public void loadIcon(IIconRegister iconRegistry) {
		icon = iconRegistry.registerIcon("openccsensors:proximity");
	}

	@Override
	public ItemStack getUniqueRecipeItem() {
		return new ItemStack(Blocks.stone_pressure_plate);
	}

	public double getDistanceToNearestEntity(World world, Vec3 location, int mode, String owner) {
		Class klazz = EntityLivingBase.class;
		
		if (mode == MODE_PLAYERS || mode == MODE_OWNER) {
			klazz = EntityPlayer.class;
		}
		
		List list = world.getEntitiesWithinAABB(
				klazz,
				AxisAlignedBB.getBoundingBox(location.xCoord - 16.0F,
													location.yCoord - 16.0F,
													location.zCoord - 16.0F,
													location.xCoord + 16.0F,
													location.yCoord + 16.0F,
													location.zCoord + 16.0F));
		
		double closestDistance = Double.MAX_VALUE;
		Vec3 livingPos = Vec3.createVectorHelper(0, 0, 0);
		for (EntityLivingBase current : (List<EntityLivingBase>) list) {
			if (mode == MODE_OWNER && !current.getCommandSenderName().equals(owner)) {
				continue;
			}
			livingPos.xCoord = current.posX + 0.5;
			livingPos.yCoord = current.posY + 0.5;
			livingPos.zCoord = current.posZ + 0.5;
			double distanceTo = location.distanceTo(livingPos);
			if (distanceTo <= 16.0) {
				closestDistance = Math.min(distanceTo, closestDistance);
			}
		}
		return closestDistance;
	}
	
}
