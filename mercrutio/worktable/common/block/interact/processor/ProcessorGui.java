package mercrutio.worktable.common.block.interact.processor;

import mercrutio.worktable.common.block.BlockManager;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class ProcessorGui extends GuiContainer {
	
	private ResourceLocation loc = new ResourceLocation("processor:/textures/gui/processor.png");
	private int x, y, z;
	private World world;
	
	public ProcessorGui(EntityPlayer player, InventoryPlayer inventory, ProcessorEntity tile_entity,
			World world, int x, int y, int z) {
		super(new ContainerProcessor((EntityClientPlayerMP) player, inventory, tile_entity, world, x, y, z));
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
		((ProcessorEntity)world.getBlockTileEntity(x, y, z)).requestClientUpdate();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.func_110577_a(loc);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);

		ProcessorEntity entity = (ProcessorEntity) world.getBlockTileEntity(x, y, z);
		
		int xamt = entity.fractionComplete(24);
    	this.drawTexturedModalRect(var5 + 102, var6 + 35, 176, 14, xamt, 16);
    	
    	double power = entity.getPower();
    	double res = ((BlockProcessor) BlockManager.processor).getResistance();
    	double volt = Math.sqrt(power*Math.pow(res, 2));
    	
    	int y = 49;
    	int yamtP = (int) ((power/entity.getMaxPower()) * y);
    	int yamtV = (int) ((volt/entity.getMaxVolt()) * y);
    	
    	this.drawTexturedModalRect(var5 + 14, var6 + 22 + (y - yamtP), 176, 31 + (y - yamtP), 22, yamtP);
    	this.drawTexturedModalRect(var5 + 48, var6 + 22 + (y - yamtV), 176, 31 + (y - yamtV), 22, yamtV);
    	
        this.fontRenderer.drawString(String.valueOf(roundToDigits(power,1)) + "W", var5 + 9, var6 + 72, 4210752);
        this.fontRenderer.drawString(String.valueOf(roundToDigits(volt,1)) + "V", var5 + 49, var6 + 72, 4210752);
        
	}
	
	public double roundToDigits(double num, int digits) {
		double temp = (num - (Math.pow(10, -digits)*(((double)(Math.pow(10, digits)*num)) - (double)(Math.round(Math.pow(10, digits)*num)))));
		return temp;
	}
}
