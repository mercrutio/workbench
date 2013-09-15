package mercrutio.worktable.common.block.ore;

import mercrutio.worktable.common.event.machines.PowerEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class OreLead extends Block implements CanProcess {

	public OreLead(int par1) {
		super(par1, Material.rock);
	}

	
	@Override
	public int onBlockPlaced(World world,
			int x, int y, int z,
			int side,
			float hitX, float hitY, float hitZ,
			int meta) {
		return meta;
	}
	
	@Override
	public void registerIcons(IconRegister par1){
		this.blockIcon = par1.registerIcon("ores:lead");
	}
}
