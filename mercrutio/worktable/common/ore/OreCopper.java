package mercrutio.worktable.common.ore;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public class OreCopper extends Block {

	public OreCopper(int par1) {
		super(par1, Material.rock);
	}
	
	@Override
	public Icon getIcon(int side, int meta){
		return blockIcon;
	}
	
	@Override
	public void registerIcons(IconRegister par1){
		this.blockIcon = par1.registerIcon("ores:copper");
	}
}
