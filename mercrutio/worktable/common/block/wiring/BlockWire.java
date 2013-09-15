package mercrutio.worktable.common.block.wiring;

import mercrutio.worktable.common.block.BlockMod;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockWire extends BlockMod {
	
	private float current;
	
	public BlockWire(int par1) {
		super(par1, Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

}
