package openccsensors.common.item.meta;

import net.minecraft.init.Blocks;

import net.minecraft.init.Items;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.ShapedOreRecipe;
import openccsensors.OpenCCSensors;
import openccsensors.api.IItemMeta;
import openccsensors.api.IRequiresIconLoading;

public class ItemMetaSignalAmplifier implements IItemMeta, IRequiresIconLoading {

	private int id;
	private IIcon icon;
	
	public ItemMetaSignalAmplifier(int id) {
		this.id = id;

		OpenCCSensors.Items.genericItem.addMeta(this);
		
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(
			newItemStack(1),
			new Object[] {
				"sgs",
				"rrr",
				"sgs",
				Character.valueOf('s'), new ItemStack(Blocks.stone),
				Character.valueOf('r'), new ItemStack(Items.redstone),
				Character.valueOf('g'), new ItemStack(Items.gold_ingot),			
			}
		));
	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public boolean displayInCreative() {
		return true;
	}

	@Override
	public void loadIcon(IIconRegister iconRegistry) {
		icon = iconRegistry.registerIcon("openccsensors:signalAmplifier");
	}

	@Override
	public IIcon getIcon() {
		return icon;
	}

	@Override
	public ItemStack newItemStack(int size) {
		return new ItemStack(OpenCCSensors.Items.genericItem, size, getId());
	}
	
	@Override
	public String getName() {
		return "signalAmplifier";
	}
}
