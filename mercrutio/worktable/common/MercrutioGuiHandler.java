package mercrutio.worktable.common;

import mercrutio.worktable.common.block.interact.processor.ContainerProcessor;
import mercrutio.worktable.common.block.interact.processor.ProcessorGui;
import mercrutio.worktable.common.block.interact.processor.ProcessorEntity;
import mercrutio.worktable.common.block.interact.workbench.ContainerWorkbench;
import mercrutio.worktable.common.block.interact.workbench.WorkbenchGui;
import mercrutio.worktable.common.block.interact.workbench.WorkbenchInventory;
import mercrutio.worktable.common.block.interact.workbench.worktable.ContainerWorktable;
import mercrutio.worktable.common.block.interact.workbench.worktable.WorktableGui;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class MercrutioGuiHandler implements IGuiHandler {

	public MercrutioGuiHandler() {
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
		if (tile_entity instanceof WorkbenchInventory || tile_entity instanceof ProcessorEntity){
			switch (id) {
			case 0:
				return new ContainerWorkbench(player.inventory, (IInventory) tile_entity, world, x, y, z);
			case 1:
				TileEntity tile_entity2 = null;
				int x1 = 0, z1 = 0;
				switch (world.getBlockMetadata(x, y, z)) {
				case 9:
					x1 = 0;
					z1 = -1;
					break;
				case 11:
					x1 = -1;
					z1 = 0;
					break;
				case 12:
					x1 = 0;
					z1 = 1;
					break;
				case 14:
					x1 = 1;
					z1 = 0;
					break;
				}
				tile_entity2 = world.getBlockTileEntity(x + x1, y, z + z1);
				return new ContainerWorktable(player.inventory, (IInventory) tile_entity, (IInventory) tile_entity2, world, x, y, z);
			case 2:
				return new ContainerProcessor(player, player.inventory, (ProcessorEntity) tile_entity, world, x, y, z);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
		if (tile_entity instanceof WorkbenchInventory|| tile_entity instanceof ProcessorEntity){
			switch (id) {
			case 0:
				return new WorkbenchGui(player.inventory, tile_entity, world, x, y, z);
			case 1:
				TileEntity tile_entity2 = null;
				int x1 = 0, z1 = 0;
				switch (world.getBlockMetadata(x, y, z)) {
				case 9:
					x1 = 0;
					z1 = -1;
					break;
				case 11:
					x1 = -1;
					z1 = 0;
					break;
				case 12:
					x1 = 0;
					z1 = 1;
					break;
				case 14:
					x1 = 1;
					z1 = 0;
					break;
				}
				tile_entity2 = world.getBlockTileEntity(x + x1, y, z + z1);
				if (x1 > 0 || z1 > 0) {
					TileEntity temp = tile_entity;
					tile_entity = tile_entity2;
					tile_entity2 = temp;
				}
				return new WorktableGui(player.inventory, tile_entity, tile_entity2, world, x, y, z);
			case 2:
				return new ProcessorGui(player, player.inventory, (ProcessorEntity) tile_entity, world, x, y, z);
			}
		}
		return null;
	}

}
