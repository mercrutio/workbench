package mercrutio.worktable.common.block.wiring;

import mercrutio.worktable.common.Ids;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;

public class BlockStdWire extends BlockWire {

	public BlockStdWire(int par1) {
		super(par1);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override
	public int getRenderType(){
		return Ids.wire_actual;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	public boolean hasTileEntity() {
		return true;
	}
	
	public TileEntity createNewTileEntity(World var1)
	{
		return new StdWireEntity();
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z){	
		super.onBlockAdded(world, x, y, z);
	}

}
