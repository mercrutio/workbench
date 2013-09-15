package mercrutio.worktable.common.block.interact.processor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerProcessor extends Container {
	
	public ProcessorEntity entity;
	private ItemStack[] inv;
	private double lastProcessedTime;
	private float current;
	public EntityPlayer player;
	
	public ContainerProcessor(EntityPlayer player, InventoryPlayer inventory, ProcessorEntity tile_entity, World world, int x, int y, int z) {
		this.player = player;
		this.entity = (ProcessorEntity) world.getBlockTileEntity(x, y, z);
		entity.setContainer(this);
		this.inv = new ItemStack[entity.getSizeInventory()];
		
		this.addSlotToContainer(new SlotProcessor(inventory.player, tile_entity, 0, 139, 35));
		this.addSlotToContainer(new Slot (tile_entity, 1, 81, 35));
		for (int l = 0; l < 3; ++l)
        {
                for (int i1 = 0; i1 < 9; ++i1)
                {
                        this.addSlotToContainer(new Slot(inventory, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
                }
        }
		
        for (int l = 0; l < 9; ++l)
        {
                this.addSlotToContainer(new Slot(inventory, l, 8 + l * 18, 142));
        }
	}
	
	@Override
	public void detectAndSendChanges()
	{
	         super.detectAndSendChanges();
	         boolean invFlag = false;
	         int len = this.inv.length;
	         for (int i = 0; i < len; i++) {
	        	 if (this.inv[i] != entity.getStackInSlot(i)) {
	        		 invFlag = true;
	        	 }
	         }
	         if ((this.lastProcessedTime != entity.getProcessedTime()) || (invFlag) || (this.current != entity.getCurrent())) {
	        	 Side side = FMLCommonHandler.instance().getEffectiveSide();
	        	 if (side == Side.SERVER) {
	        		 update();     	 
	        	 }
	         }
	         
	         for (int i = 0; i < inv.length; i++) {
            	 this.inv[i] = this.entity.getStackInSlot(i);
             }
	         this.lastProcessedTime = entity.getProcessedTime();
	         this.current = entity.getCurrent();
	}
	
	public void update() {
        int len = this.inv.length;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(bos);
        
        try {
            outputStream.writeBoolean(false);
            outputStream.writeDouble(lastProcessedTime);
            outputStream.writeFloat(current);
        } catch (Exception ex) {
                ex.printStackTrace();
        }
       
        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "MercrutioPackets";
        packet.data = bos.toByteArray();
        packet.length = bos.size();
       
        Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.SERVER) {
        	PacketDispatcher.sendPacketToPlayer(packet, (Player)player);
        }	  
	}

	@SideOnly(Side.CLIENT)
	public void recieveUpdates(Packet250CustomPayload packet) {
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		
		int len;
		int itemID;
		int stackSize;
		int itemDamage;
		boolean ex;
		ItemStack temp;
		
		try {
			ex = inputStream.readBoolean();
            entity.processedTime = inputStream.readDouble();
            entity.setCurrent(inputStream.readFloat());
		} catch (IOException e) {
            e.printStackTrace();
            return;
    	}
	}
	
	@SideOnly(Side.CLIENT)
	public void putStackInSlot(int slot, ItemStack stack) {
		this.entity.setInventorySlotContents(slot, stack);
	}
	
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int par2)
	{
		ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(par2);
        int craftSize = 2;
        int playerSize = 27 + craftSize;
        int playerBSize = 9 + playerSize;

        if (slot != null && slot.getHasStack())
        {
                ItemStack itemstack1 = slot.getStack();
                itemstack = itemstack1.copy();
                if (par2 == 1) {
                	if (!this.mergeItemStack(itemstack1, craftSize, playerBSize, true)) {
                		return null;
                	}
                } else if (par2 == 0) {
                    if (!this.mergeItemStack(itemstack1, craftSize, playerBSize, true)) {
                        return null;
                    }
                        slot.onSlotChange(itemstack1, itemstack);
                }
                else if (par2 < playerSize)
                {
                        if (!this.mergeItemStack(itemstack1, 1, 2, false))
                        {
                                return null;
                        }
                }
                else if (par2 >= playerSize && par2 < playerBSize) {
                	if (!this.mergeItemStack(itemstack1, craftSize, playerSize, false))
                	{
                		return null;                
                	}
                }

                if (itemstack1.stackSize == 0)
                {
                        slot.putStack((ItemStack)null);
                }
                else
                {
                        slot.onSlotChanged();
                }

                if (itemstack1.stackSize == itemstack.stackSize)
                {
                        return null;
                }

                slot.onPickupFromSlot(entityplayer, itemstack1);
                entity.onInputChanged();
        }

        return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
        return true;
	}

}
