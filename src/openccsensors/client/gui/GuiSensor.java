package openccsensors.client.gui;

import org.lwjgl.opengl.GL11;

import openccsensors.common.container.ContainerSensor;
import openccsensors.common.tileentity.TileEntitySensor;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

public class GuiSensor extends GuiContainer {

	public GuiSensor(InventoryPlayer inventory, TileEntity sensor) {
		super(new ContainerSensor(inventory, sensor));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
			int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.func_98187_b("/mods/OpenCCSensors/textures/gui/sensor.png");
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {

		String sensorGuiName = StatCollector.translateToLocal("openccsensors.gui.sensor");

		fontRenderer.drawString(
				sensorGuiName,
				this.xSize / 2
				- (fontRenderer.getStringWidth(sensorGuiName) / 2),
				6,
				4210752
		);
		fontRenderer.drawString(
				StatCollector.translateToLocal("container.inventory"),
				8,
				this.ySize - 96 + 2,
				4210752
		);
	}

}