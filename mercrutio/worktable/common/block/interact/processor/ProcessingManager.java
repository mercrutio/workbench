package mercrutio.worktable.common.block.interact.processor;

import mercrutio.worktable.common.Ids;
import mercrutio.worktable.common.block.BlockManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ProcessingManager {
	
	private static final ProcessingManager instance = new ProcessingManager();
	
	public static final ProcessingManager getInstance()	{
	         return instance;
	}
	
	private ProcessingManager()	{
		
	}
	
	public ItemStack findOutput(ItemStack item, World world) {
		if (item != null) {
			if (item.isItemEqual(new ItemStack(BlockManager.copper))) {
				return new ItemStack(BlockManager.lead);
			}
		}
		return null;
	}
}
