package mercrutio.worktable.common.block.interact.processor;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import mercrutio.worktable.common.block.BlockManager;
import mercrutio.worktable.common.block.interact.CanDisplay;
import mercrutio.worktable.common.block.interact.CanPower;
import mercrutio.worktable.common.block.ore.CanProcess;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

public class ProcessorEntity extends TileEntity implements IInventory, CanPower, CanDisplay {

	private static final int invSize = 2;
	private ItemStack[] items = new ItemStack[invSize];
	private String name;
	public ContainerProcessor container;
	private float current;
	private boolean curWorking;
	double processedTime;
	
	public ProcessorEntity() {
		super();
	}
	
	public void setContainer(ContainerProcessor container) {
		this.container = container;
	}
	
	@Override
	public int getSizeInventory() {
		return invSize;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (this.items[i] != null) {
			return this.items[i];
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (this.items[i] != null)
        {
            ItemStack itemstack;
            if (this.items[i].stackSize <= j) {
                itemstack = this.items[i];
                this.items[i] = null;
                this.onInputChanged();
                return itemstack;
            } else {
                itemstack = this.items[i].splitStack(j);
                if (this.items[i].stackSize == 0)
                {
                        this.items[i] = null;
                }
                this.onInputChanged();
                return itemstack;
            }
        } else {
            return null;
        }

	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (this.items[i] != null) {
			ItemStack temp = this.items[i];
			this.items[i] = null;
			return temp;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack){
		if (par1 < 2) {
			this.items[par1] = par2ItemStack;
			if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
				par2ItemStack.stackSize = this.getInventoryStackLimit();
			}
			if (par1 == 1) {
				this.onInputChanged();
			}
		}
	}
	
	public void onInputChanged() {
		if (this.getStackInSlot(1) != null) {
			Item item = this.getStackInSlot(1).getItem();
			if ((item.itemID == 1313) && (this.getCurrent() > 0)) {
				this.setWorking(true);
			} else {
				this.setWorking(false);
				if (!(item.itemID == 1313)) {
					this.processedTime = 0;
				}
			}
		} else {
			this.setWorking(false);
			this.processedTime = 0;
		}
	}
	
	public boolean isWorking() {
		return this.curWorking;
	}
	
	public void setWorking(boolean set) {
		this.curWorking = set;
	}
	
	public float getCurrent() {
		return current;
	}

	public void setCurrent(float f) {
		this.current = f;
	}
	
	@Override
	public String getInvName() {
        return this.isInvNameLocalized() ? this.name : "container.processor";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
            super.readFromNBT(par1NBTTagCompound);

            NBTTagList nbtTagList = par1NBTTagCompound.getTagList("ProcessorInventory");
         
            /*this.setCurrent(((NBTTagCompound)nbtTagList.tagAt(0)).getFloat("current"));
            this.setWorking(((NBTTagCompound)nbtTagList.tagAt(1)).getBoolean("isWorking"));
            this.processedTime = ((NBTTagCompound)nbtTagList.tagAt(2)).getDouble("processedTime");
            int itemCount = ((NBTTagCompound)nbtTagList.tagAt(3)).getInteger("items");*/
            
            NBTTagCompound dataTag = (NBTTagCompound)nbtTagList.tagAt(0);
            this.setCurrent(dataTag.getFloat("current"));
            this.setWorking(dataTag.getBoolean("isWorking"));
            this.processedTime = dataTag.getDouble("processedTime");
            int itemCount = dataTag.getInteger("items");
               
            for (int i = 4; i < itemCount + 4; ++i) {
                    NBTTagCompound nbtTagList1 = (NBTTagCompound)nbtTagList.tagAt(i);
                    int var5 = nbtTagList1.getInteger("Slot");
                    if (var5 != 0) {
                    	this.setInventorySlotContents(var5 - 1, ItemStack.loadItemStackFromNBT(nbtTagList1));
                    }
            }
	}

	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
            super.writeToNBT(par1NBTTagCompound);
            NBTTagList nbtTagList = new NBTTagList();
        	int itemCount = 0;
            
            if (this.items != null) {            	
            	for (int i = 0; i < this.getSizeInventory(); i++){
            		if (this.items[i] != null) {
                		NBTTagCompound nbtTagList1 = new NBTTagCompound();
                        nbtTagList1.setInteger("Slot", i + 1);
                        this.items[i].writeToNBT(nbtTagList1);
                        nbtTagList.appendTag(nbtTagList1);
                        itemCount++;
            		}
            	}
            }

            /*NBTTagCompound currentTag = new NBTTagCompound();
            currentTag.setFloat("current", getCurrent());
            nbtTagList.appendTag(currentTag);
            
            NBTTagCompound workingTag = new NBTTagCompound();
            workingTag.setBoolean("isWorking", isWorking());
            nbtTagList.appendTag(workingTag);
            
            NBTTagCompound timeTag = new NBTTagCompound();
            timeTag.setDouble("processedTime", processedTime);
            nbtTagList.appendTag(timeTag); */
            
            NBTTagCompound dataTag = new NBTTagCompound();
            dataTag.setFloat("current", getCurrent());
            dataTag.setBoolean("isWorking", isWorking());
            dataTag.setDouble("processedTime", processedTime);
            dataTag.setInteger("items", itemCount);
            nbtTagList.appendTag(dataTag);

            par1NBTTagCompound.setTag("ProcessorInventory", nbtTagList);
    }
	
	@Override
	public void updateEntity() {
		boolean flag = false;
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		
		if (this.isWorking()) {
			if (this.getStackInSlot(1) != null) {
				if (this.getStackInSlot(1).getItem().itemID == 1313) {
					CanProcess processing = (CanProcess) Block.blocksList[this.getStackInSlot(1).getItem().itemID];
					this.processedTime += (this.getPower()/100);
					if (processing.processTime < this.processedTime) {
						ItemStack output = ProcessingManager.getInstance().findOutput(this.getStackInSlot(1), getWorldObj());
						if (this.getStackInSlot(0) != null) {
							if (this.getStackInSlot(0).isItemEqual(output)) {
								output.stackSize += this.getStackInSlot(0).stackSize;
							} else {
								this.processedTime = processing.processTime;
								this.setWorking(false);
								flag = true;
							}
						}
						
						if (!flag) {
					        if (side == Side.SERVER) {
								this.setInventorySlotContents(0, output);
								this.processedTime = 0;
								this.decrStackSize(1, 1);
								flag = true;
					        }
						}
					}
				}
			}
		}
	}

	public double getPower() {
		double toReturn = Math.pow(this.current,2)*(((BlockProcessor)BlockManager.processor).getResistance());
		return toReturn;
	}

	public int fractionComplete(int tot) {
		if (this.getStackInSlot(1) != null) {
			if (this.getStackInSlot(1).getItem().itemID == 1313) {
				CanProcess processing = (CanProcess) Block.blocksList[this.getStackInSlot(1).getItem().itemID];
				return (int) (this.getProcessedTime()*tot)/processing.processTime;
			}
		}
		return 0;
	}
	
	public double getProcessedTime() {
		return this.processedTime;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	/*This might have something to do with automation, should look at...*/
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}

	public double getMaxPower() {
		return 200;
	}

	public double getMaxVolt() {
		return 20;
	}
	
	public void requestClientUpdate() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(1);
        DataOutputStream outputStream = new DataOutputStream(bos);
        try {
            outputStream.writeBoolean(true);
        } catch (Exception ex) {
                ex.printStackTrace();
        }
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "MercrutioPackets";
        packet.data = bos.toByteArray();
        packet.length = bos.size();
		PacketDispatcher.sendPacketToServer(packet);
	}
}
