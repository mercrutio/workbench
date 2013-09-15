package mercrutio.worktable.common.block.ore;

import mercrutio.worktable.common.event.machines.PowerEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class OreCopper extends Block implements CanProcess {

	int current = 3;
	
	public OreCopper(int par1) {
		super(par1, Material.rock);
	}
	
	@Override
	public Icon getIcon(int side, int meta){
		return blockIcon;
	}
	
	@Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		MinecraftForge.EVENT_BUS.post(new PowerEvent(world, x, y, z, current));
		return meta;
	}
	
	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
		MinecraftForge.EVENT_BUS.post(new PowerEvent(par1World, par2, par3, par4, -current));
	}
	
	@Override
	public void registerIcons(IconRegister par1){
		this.blockIcon = par1.registerIcon("ores:copper");
	}
}
