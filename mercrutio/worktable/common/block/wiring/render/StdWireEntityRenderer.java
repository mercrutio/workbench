package mercrutio.worktable.common.block.wiring.render;

import mercrutio.worktable.common.block.wiring.StdWireEntity;
import mercrutio.worktable.common.block.wiring.model.ModelOneWire;
import mercrutio.worktable.common.block.wiring.model.ModelStdWire;
import mercrutio.worktable.common.block.wiring.model.ModelWire;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class StdWireEntityRenderer extends TileEntitySpecialRenderer {
	public static final ResourceLocation stdLocation = new ResourceLocation("wire:textures/ModelStandard.png");
	public static final ResourceLocation oneLocation = new ResourceLocation("wire:textures/Model1Out.png");
	private static TextureManager textureManager;
	private final ModelStdWire stdModel;
	private final ModelOneWire oneModel;
    
    public StdWireEntityRenderer() {
            this.stdModel = new ModelStdWire();
            this.oneModel = new ModelOneWire();
    }
   
    private void adjustRotatePivotViaMeta(World world, int x, int y, int z) {
            int meta = world.getBlockMetadata(x, y, z);
            GL11.glPushMatrix();
            GL11.glRotatef(meta * (-90), 0.0F, 0.0F, 1.0F);
            GL11.glPopMatrix();
    }
   
    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
            GL11.glPushMatrix();
    //This is setting the initial location.
            GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
    //This is the texture of your block. It's pathed to be the same place as your other blocks here.
            //Outdated bindTextureByName("/mods/roads/textures/blocks/TrafficLightPoleRed.png");
   //Use in 1.6.2  this
            ModelWire model = stdModel;
            ResourceLocation location = stdLocation;
            switch(((StdWireEntity)te).sides) {
			case Std:
				model = stdModel;
				location = stdLocation;
				break;
			case oneOut:
				model = oneModel;
				location = oneLocation;
				break;
			case twoAdj:
				break;
			case twoOpp:
				break;	
            }
            
            func_110628_a(location);

    //This rotation part is very important! Without it, your model will render upside-down! And for some reason you DO need PushMatrix again!                      
            GL11.glPushMatrix();
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
    //A reference to your Model file. Again, very important.
    		model.render((Entity)null, 0.0F, 0.0F, 0F, 0F, 0F, 0.0625F);
    //Tell it to stop rendering for both the PushMatrix's
            GL11.glPopMatrix();
            GL11.glPopMatrix();
    }

    //Set the lighting stuff, so it changes it's brightness properly.      
    private void adjustLightFixture(World world, int i, int j, int k, Block block) {
            Tessellator tess = Tessellator.instance;
            float brightness = block.getBlockBrightness(world, i, j, k);
            int skyLight = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
            int modulousModifier = skyLight % 65536;
            int divModifier = skyLight / 65536;
            tess.setColorOpaque_F(brightness, brightness, brightness);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,  (float) modulousModifier,  divModifier);
    }

}
