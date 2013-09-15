package mercrutio.worktable.common.block.interact.workbench;

import java.util.Random;

import mercrutio.worktable.Worktable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWorkbench extends BlockContainer {
	
	@SideOnly(Side.CLIENT)
	private Icon worktableSingleIconTop;
	@SideOnly(Side.CLIENT)
	private Icon worktableSingleIconFront;
	@SideOnly(Side.CLIENT)
	private Icon worktableDoubleIconTopVert1;
	@SideOnly(Side.CLIENT)
	private Icon worktableDoubleIconTopHorz1;
	@SideOnly(Side.CLIENT)
	private Icon worktableDoubleIconFront1;
	@SideOnly(Side.CLIENT)
	private Icon worktableDoubleIconSide1;
	@SideOnly(Side.CLIENT)
	private Icon worktableDoubleIconTopVert2;
	@SideOnly(Side.CLIENT)
	private Icon worktableDoubleIconTopHorz2;
	@SideOnly(Side.CLIENT)
	private Icon worktableDoubleIconFront2;
	@SideOnly(Side.CLIENT)
	private Icon worktableDoubleIconSide2;
	
	
	public BlockWorkbench(int par1) {
		super(par1, Material.wood);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setHardness(3.0f);
		this.setResistance(3.0f);
	}
	
	@SideOnly(Side.CLIENT)
	public Icon getIcon (int side, int meta) {
		if (meta < 6) {
			if (side == 1 || side == 0) {
				return worktableSingleIconTop;
			} else if (side != meta) {
				return blockIcon;
			} else {
				return worktableSingleIconFront;
			}

		} else if (meta < 12) {
			int sideOut = meta - 6;
			boolean horz = false;
			if (sideOut == 5) {
				horz = true;
			}
			if (side == 1 || side == 0) {	
				if (!horz) {
					return worktableDoubleIconTopVert1;
				} else {
					return worktableDoubleIconTopHorz1;
				}
				} else if (side != sideOut) {
				return worktableDoubleIconFront1;
			} else {
				return worktableDoubleIconSide1;
			}
		} else {
			int sideOut = meta - 10;
			boolean horz = false;
			if (sideOut == 4) {
				horz = true;
			}
			if (side == 1 || side == 0) {
				if (!horz) {
					return worktableDoubleIconTopVert2;
				} else {
					return worktableDoubleIconTopHorz2;
				}
			} else if (side != sideOut) {
				return worktableDoubleIconFront2;
			} else {
				return worktableDoubleIconSide2;
			}
		}
	}
		
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		world.setBlockMetadataWithNotify(x, y, z, 0, 2);
		int l = world.getBlockId(x, y, z - 1);
		int r = world.getBlockId(x, y, z + 1);
		int f = world.getBlockId(x + 1, y, z);
		int b = world.getBlockId(x - 1, y, z);
		boolean flag = false;
		
		if (l == this.blockID) {
            flag = this.unifyAdjacentTables(world, x, y, z, 1);
        }
		if ((r == this.blockID) && (flag == false)) {
        	flag = this.unifyAdjacentTables(world, x, y, z, 2);
		}
		if ((f == this.blockID) && (flag == false)) {
        	flag = this.unifyAdjacentTables(world, x, y, z, 3);
        }
		if ((b == this.blockID) && (flag == false)) {
        	flag = this.unifyAdjacentTables(world, x, y, z, 4);
        }
		if (flag == false) {
        	this.setDefaultDirection(world, x, y, z);
        }
	}

	private boolean unifyAdjacentTables(World world, int x, int y, int z, int dir) {	
		if (!world.isRemote){
			int meta = 0;
			
			switch (dir) {
			case 1:
				meta = world.getBlockMetadata(x, y, z - 1);
				break;
			case 2:
				meta = world.getBlockMetadata(x, y, z + 1);
				break;
			case 3:
				meta = world.getBlockMetadata(x + 1, y, z);
				break;
			case 4:
				meta = world.getBlockMetadata(x - 1, y, z);
				break;
			}
			
			if (!(meta < 6)) {
				return false;
			} else {
				switch (dir) {
				case 1:
					this.JoinInventories(world, x, y, z, x, z - 1);
					break;
				case 2:
					this.JoinInventories(world, x, y, z, x, z + 1);
					break;
				case 3:
					this.JoinInventories(world, x, y, z, x + 1, z);
					break;
				case 4:
					this.JoinInventories(world, x, y, z, x - 1, z);
					break;
				}
				return true;
			}
		}
		return false;
	}

	private void JoinInventories(World world, int x, int y, int z, int x2, int z2) {
		
		if (z != z2) {
			if (z > z2) {
				world.setBlockMetadataWithNotify(x, y, z, 9, 2);
				world.setBlockMetadataWithNotify(x2, y, z2, 12, 2);
			} else {
				world.setBlockMetadataWithNotify(x, y, z, 12, 2);
				world.setBlockMetadataWithNotify(x2, y, z2, 9, 2);
			}
		} else {
			if (x > x2) {
				world.setBlockMetadataWithNotify(x, y, z, 11, 2);
				world.setBlockMetadataWithNotify(x2, y, z2, 14, 2);
			} else {
				world.setBlockMetadataWithNotify(x, y, z, 14, 2);
				world.setBlockMetadataWithNotify(x2, y, z2, 11, 2);
			}
		}
		
	}

	private void setDefaultDirection(World world, int x, int y, int z) {
		if (!world.isRemote) {
			int zNeg = world.getBlockId(x, y, z - 1);
			int zPos = world.getBlockId(x, y, z + 1);
			int xNeg = world.getBlockId(x - 1, y, z);
			int xPos = world.getBlockId(x + 1, y, z);
			byte meta = 3;

			if(Block.opaqueCubeLookup[xNeg] && !Block.opaqueCubeLookup[xPos]) meta = 5;
			if(Block.opaqueCubeLookup[xPos] && !Block.opaqueCubeLookup[xNeg]) meta = 4;
			if(Block.opaqueCubeLookup[zNeg] && !Block.opaqueCubeLookup[zPos]) meta = 3;
			if(Block.opaqueCubeLookup[zPos] && !Block.opaqueCubeLookup[zNeg]) meta = 2;

			world.setBlockMetadataWithNotify(x, y, z, meta, 2);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon("worktable:crafting_table_side");
		this.worktableSingleIconTop = par1IconRegister.registerIcon("worktable:crafting_table_top");
		this.worktableSingleIconFront = par1IconRegister.registerIcon("worktable:crafting_table_front");
		
		this.worktableDoubleIconSide1 = par1IconRegister.registerIcon("worktable:crafting_table_double_side1");
		this.worktableDoubleIconTopVert1 = par1IconRegister.registerIcon("worktable:crafting_table_double_top_vert1");
		this.worktableDoubleIconTopHorz1 = par1IconRegister.registerIcon("worktable:crafting_table_double_top_horz1");
		this.worktableDoubleIconFront1 = par1IconRegister.registerIcon("worktable:crafting_table_double_front1");
		this.worktableDoubleIconSide2 = par1IconRegister.registerIcon("worktable:crafting_table_double_side2");
		this.worktableDoubleIconTopVert2 = par1IconRegister.registerIcon("worktable:crafting_table_double_top_vert2");
		this.worktableDoubleIconTopHorz2 = par1IconRegister.registerIcon("worktable:crafting_table_double_top_horz2");
		this.worktableDoubleIconFront2 = par1IconRegister.registerIcon("worktable:crafting_table_double_front2");
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int var6, float var7, float var8, float var9) {
    	TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (!player.isSneaking() && tileEntity != null) {
			if (world.getBlockMetadata(x, y, z) < 6) {
				player.openGui(Worktable.instance, 0, world, x, y, z);
				return true;
			} else {
				player.openGui(Worktable.instance, 1, world, x, y, z);
				return true;
			}
	    }
    	return false;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		dropItems(world, x, y, z);
		//world.setBlockMetadataWithNotify(x, y, z, 0, 2);
		int l = world.getBlockId(x, y, z - 1);
		int r = world.getBlockId(x, y, z + 1);
		int f = world.getBlockId(x + 1, y , z);
		int b = world.getBlockId(x - 1, y, z);
		
		if ((l == this.blockID) && ((world.getBlockMetadata(x, y, z - 1) == 8) || (world.getBlockMetadata(x, y, z - 1) == 12))) {
			this.setDefaultDirection(world, x, y, z - 1);
		}
		
		if ((r == this.blockID) && ((world.getBlockMetadata(x, y, z + 1) == 9) || (world.getBlockMetadata(x, y, z + 1) == 13))) {
			this.setDefaultDirection(world, x, y, z + 1);
		}
		
		if ((f == this.blockID) && ((world.getBlockMetadata(x + 1, y, z) == 11) || (world.getBlockMetadata(x + 1, y, z) == 15))) {
			this.setDefaultDirection(world, x + 1, y, z);
		}
		
		if ((b == this.blockID) && ((world.getBlockMetadata(x - 1, y, z) == 10) || (world.getBlockMetadata(x - 1, y, z) == 14))) {
			this.setDefaultDirection(world, x - 1, y, z);
		}
		
		super.breakBlock(world, x, y, z, par5, par6);
	}
	
	private void dropItems(World world, int x, int y, int z){
        Random rand = new Random();

        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (!(tileEntity instanceof IInventory)) {
                return;
        }
        IInventory inventory = (IInventory) tileEntity;

        for (int i = 0; i < inventory.getSizeInventory(); i++) {
                ItemStack item = inventory.getStackInSlot(i);

                if (item != null && item.stackSize > 0) {
                        float rx = rand.nextFloat() * 0.8F + 0.1F;
                        float ry = rand.nextFloat() * 0.8F + 0.1F;
                        float rz = rand.nextFloat() * 0.8F + 0.1F;

                        EntityItem entityItem = new EntityItem(world,
                                        x + rx, y + ry, z + rz,
                                        new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));

                        if (item.hasTagCompound()) {
                                entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
                        }

                        float factor = 0.05F;
                        entityItem.motionX = rand.nextGaussian() * factor;
                        entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                        entityItem.motionZ = rand.nextGaussian() * factor;
                        world.spawnEntityInWorld(entityItem);
                        item.stackSize = 0;
                }
        }
	}
	
    public TileEntity createNewTileEntity(World world) {
    	return new WorkbenchInventory();
    }
}
