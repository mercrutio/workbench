package mercrutio.worktable.common.block.interact.processor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotProcessor extends Slot {

	private Object thePlayer;

	public SlotProcessor(EntityPlayer player, IInventory output, int par2, int par3, int par4) {
		super(output, par2, par3, par4);
        this.thePlayer = player;
	}
	
	public boolean isItemValid(ItemStack par1ItemStack) {
        return false;
    }	
}
