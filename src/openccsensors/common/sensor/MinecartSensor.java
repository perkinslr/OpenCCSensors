package openccsensors.common.sensor;

import net.minecraft.init.Blocks;

import net.minecraft.init.Items;

import java.util.HashMap;

import mods.railcraft.api.carts.IEnergyTransfer;
import mods.railcraft.api.carts.IExplosiveCart;
import mods.railcraft.api.carts.IRoutableCart;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidHandler;
import openccsensors.api.IRequiresIconLoading;
import openccsensors.api.ISensor;
import openccsensors.api.ISensorTier;
import openccsensors.common.util.EntityUtils;
import openccsensors.common.util.InventoryUtils;
import openccsensors.common.util.Mods;
import openccsensors.common.util.RailcraftUtils;
import openccsensors.common.util.TankUtils;

public class MinecartSensor implements ISensor, IRequiresIconLoading {

	private IIcon icon;


	@Override
	public HashMap getDetails(World world, Object obj, ChunkCoordinates sensorPos, boolean additional) {

		EntityMinecart minecart = (EntityMinecart) obj;

		HashMap response = new HashMap();
		HashMap position = new HashMap();
		
		position.put("X", minecart.posX - sensorPos.posX);
		position.put("Y", minecart.posY - sensorPos.posY);
		position.put("Z", minecart.posZ - sensorPos.posZ);
		response.put("Position", position);
		
		response.put("Name", minecart.func_95999_t());
		response.put("RawName", EntityList.getEntityString(minecart));
		
		if (minecart instanceof IInventory) {
			response.put("Slots", InventoryUtils.invToMap((IInventory)minecart));
		}

		if (minecart instanceof IFluidHandler) {
			response.put("Tanks", TankUtils.fluidHandlerToMap((IFluidHandler)minecart));
		}
		
		if (minecart.riddenByEntity != null && minecart.riddenByEntity instanceof EntityLivingBase) {
			response.put("Riding", EntityUtils.livingToMap((EntityLivingBase)minecart.riddenByEntity, sensorPos, true));
		}
		
		if (Mods.RAIL) {
			if (minecart instanceof IEnergyTransfer) {
				response.putAll(RailcraftUtils.getEnergyDetails(minecart));
			}
			
			if (minecart instanceof IExplosiveCart) {
				response.putAll(RailcraftUtils.getExplosiveDetails(minecart));
			}
			
			if (minecart instanceof IRoutableCart) {
				response.put("RouteDestination", ((IRoutableCart)minecart).getDestination());
			}
		}
		return response;
	}

	@Override
	public HashMap getTargets(World world, ChunkCoordinates location, ISensorTier tier) {
		double radius = tier.getMultiplier() * 4;
		return (HashMap) EntityUtils.getEntities(world, location, radius, EntityMinecart.class);
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
		return "minecartCard";
	}

	@Override
	public IIcon getIcon() {
		return icon;
	}

	@Override
	public void loadIcon(IIconRegister iconRegistry) {
		icon = iconRegistry.registerIcon("openccsensors:minecart");
		
	}

	@Override
	public ItemStack getUniqueRecipeItem() {
		return new ItemStack(Items.minecart);
	}

}
