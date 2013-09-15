package mercrutio.worktable.common.block.wiring.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelStdWire extends ModelWire {
	private ModelRenderer wire;
	
	public ModelStdWire () {
		wire = new ModelRenderer(this, "wire");
		wire.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 1.0F);
		}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	  {
	    super.render(entity, f, f1, f2, f3, f4, f5);
	    setRotationAngles(f, f1, f2, f3, f4, f5, entity); //add entity here
	    wire.render(f5);
	  }
	 
	 
	  private void setRotation(ModelRenderer model, float x, float y, float z)
	  {
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
	  }
	 
	  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) //Add Entity entity here
	  {
	    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity); //Add entity here
	  }
	  
	  public void render(float f5) {
		  wire.render(f5);
	  }
}
