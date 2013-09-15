package mercrutio.worktable.common.block.interact.processor;

import mercrutio.worktable.Worktable;
import mercrutio.worktable.common.block.BlockMod;
import mercrutio.worktable.common.block.interact.CanPower;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockProcessor extends BlockMod {
	
	@SideOnly(Side.CLIENT)
	private Icon furnaceIconTop;
	@SideOnly(Side.CLIENT)
	private Icon furnaceIconFront;
	private boolean isActive = false;
	private double resistance;
	
	public BlockProcessor(int par1) {
		super(par1, Material.rock);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.resistance = 0.75;
	}
	
	public double getResistance() {
		return this.resistance;
	}
	
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return side == 1 ? this.furnaceIconTop : (side == 0 ? this.furnaceIconTop : (side != meta ? this.blockIcon : this.furnaceIconFront));
	}
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1){
		this.blockIcon = par1.registerIcon("processor:furnaceSide");
        this.furnaceIconFront = par1.registerIcon(this.isActive ? "processor:furnaceActive" : "processor:furnaceIdle");
        this.furnaceIconTop = par1.registerIcon("processor:furnaceTop");
	}
	
	private void setDefaultDirection(World par1World, int par2, int par3, int par4)
	{
	         if (!par1World.isRemote)
	         {
	                 int l = par1World.getBlockId(par2, par3, par4 - 1);
	                 int i1 = par1World.getBlockId(par2, par3, par4 + 1);
	                 int j1 = par1World.getBlockId(par2 - 1, par3, par4);
	                 int k1 = par1World.getBlockId(par2 + 1, par3, par4);
	                 byte b0 = 3;
	                 if (Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[i1])
	                 {
	                         b0 = 3;
	                 }
	                 if (Block.opaqueCubeLookup[i1] && !Block.opaqueCubeLookup[l])
	                 {
	                         b0 = 2;
	                 }
	                 if (Block.opaqueCubeLookup[j1] && !Block.opaqueCubeLookup[k1])
	                 {
	                         b0 = 5;
	                 }
	                 if (Block.opaqueCubeLookup[k1] && !Block.opaqueCubeLookup[j1])
	                 {
	                         b0 = 4;
	                 }
	                 par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 2);
	         }
	}
	
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		this.setDefaultDirection(world, x, y, z);
	}
	
	public boolean onBlockActivated(World par1World,
			int x, int y, int z, 
			EntityPlayer par5EntityPlayer, 
			int par6, float par7, float par8, float par9) {
		if (par1World.isRemote) {
			return true;
		} else if (!par5EntityPlayer.isSneaking()) {
			ProcessorEntity var10 = (ProcessorEntity) par1World.getBlockTileEntity(x, y, z);
			if (var10 != null) {
				par5EntityPlayer.openGui(Worktable.instance, 2, par1World, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void breakBlock(World world, int par1, int par2, int par3, int par4, int par5) {
		super.breakBlock(world, par1, par2, par3, par4, par5);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new ProcessorEntity();
	}
}