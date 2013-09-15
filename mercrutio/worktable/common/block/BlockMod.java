package mercrutio.worktable.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMod extends Block implements ITileEntityProvider {

	public BlockMod(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return null;
	}

}
