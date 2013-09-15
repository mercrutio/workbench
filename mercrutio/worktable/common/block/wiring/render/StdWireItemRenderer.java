package mercrutio.worktable.common.block.wiring.render;

import mercrutio.worktable.common.block.wiring.model.ModelStdWire;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class StdWireItemRenderer implements IItemRenderer {
	public static final ResourceLocation location = new ResourceLocation("wire:textures/ModelStandard.png");
	private static TextureManager textureManager;
	private ModelStdWire model;
    
    public StdWireItemRenderer()
    {
        model = new ModelStdWire();
    }
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch(type)
	    {
	        case ENTITY:{
	            renderWire(0f, 0f, 0f, 0.3f);
	            return;
	        }
	         
	        case EQUIPPED:{
	            renderWire(0f, 1f, 1f, 0.3f);
	            return;
	        }
	             
	        case INVENTORY:{
	            renderWire(0f, 0f, 0f, 0.3f);
	            return;
	        }
	         
	        default:return;
	    }
	}

	private void renderWire(float x, float y, float z, float scale) {
		GL11.glPushMatrix();
		 
	    // Disable Lighting Calculations
	    GL11.glDisable(GL11.GL_LIGHTING);
	     
	    GL11.glTranslatef(x,  y,  z);
	    GL11.glScalef(scale, scale, scale);
	    GL11.glRotatef(180f, 0f, 1f, 0f);
	     
	    textureManager = Minecraft.getMinecraft().func_110434_K();
		textureManager.func_110577_a(location);
		model.render((Entity)null, 0.0F, 0.0F, x, y, z, scale);
	     
	    // Re-enable Lighting Calculations
	    GL11.glEnable(GL11.GL_LIGHTING);
	    GL11.glPopMatrix();
	}

}
