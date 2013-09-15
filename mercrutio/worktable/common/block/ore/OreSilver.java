package mercrutio.worktable.common.block.ore;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class OreSilver extends Block implements CanProcess {

	public OreSilver(int par1) {
		super(par1, Material.rock);
	}

	@Override
	public void registerIcons(IconRegister par1){
		this.blockIcon = par1.registerIcon("ores:silver");
	}
}
