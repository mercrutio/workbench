package mercrutio.worktable.common.block.wiring;

import mercrutio.worktable.common.block.interact.CanPower;
import mercrutio.worktable.common.event.machines.PowerEvent;
import mercrutio.worktable.common.event.machines.TPowerEvent;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;

public class WireEntity extends TileEntity implements CanPower {
	
	public float current;
	
	public enum Dir {
		North, NorthEast, East, SouthEast, South, SouthWest, West, NorthWest, Center;
	}
	
	@Override
	public void setCurrent(float current) {
		this.current = current;
	}

	@Override
	public float getCurrent() {
		return this.current;
	}
	
	public void recieveAndSendPower(PowerEvent event) {
		Dir dir = getDir(event);
		int x2 = this.xCoord, y2 = this.yCoord, z2 = this.zCoord;
		
		switch(dir) {
		case Center:
			break;
		case East:
			x2 = this.xCoord + 1;
			break;
		case North:
			z2 = this.zCoord - 1;
			break;
		case NorthEast:
			x2 = this.xCoord + 1;
			z2 = this.zCoord - 1;
			break;
		case NorthWest:
			x2 = this.xCoord - 1;
			z2 = this.zCoord - 1;
			break;
		case South:
			z2 = this.zCoord + 1;
			break;
		case SouthEast:
			z2 = this.zCoord + 1;
			x2 = this.xCoord + 1;
			break;
		case SouthWest:
			z2 = this.zCoord + 1;
			x2 = this.xCoord - 1;
			break;
		case West:
			x2 = this.xCoord - 1;
			break;
		default:
			break;
		}
		
		MinecraftForge.EVENT_BUS.post(new TPowerEvent(event.world, this.xCoord, this.yCoord, this.zCoord, x2, y2, z2, event.current));
	}
	
	public void recieveAndSendPower(TPowerEvent event) {
		Dir dir = getDir(event);
		int x2 = this.xCoord, y2 = this.yCoord, z2 = this.zCoord;
		
		switch(dir) {
		case Center:
			break;
		case East:
			x2 = this.xCoord + 1;
			break;
		case North:
			z2 = this.zCoord - 1;
			break;
		case NorthEast:
			x2 = this.xCoord + 1;
			z2 = this.zCoord - 1;
			break;
		case NorthWest:
			x2 = this.xCoord - 1;
			z2 = this.zCoord - 1;
			break;
		case South:
			z2 = this.zCoord + 1;
			break;
		case SouthEast:
			z2 = this.zCoord + 1;
			x2 = this.xCoord + 1;
			break;
		case SouthWest:
			z2 = this.zCoord + 1;
			x2 = this.xCoord - 1;
			break;
		case West:
			x2 = this.xCoord - 1;
			break;
		default:
			break;
		}
		
		MinecraftForge.EVENT_BUS.post(new TPowerEvent(event.world, this.xCoord, this.yCoord, this.zCoord, x2, y2, z2, event.current));
	}
	
	private Dir getDir(TPowerEvent event) {
		return getDir(event.x, event.x2, event.z, event.z2);
	}
	
	private Dir getDir(PowerEvent event) {
		return getDir(event.x, this.xCoord, event.z, this.zCoord);
	}
	
	private Dir getDir(int x1, int x2, int z1, int z2) {
		int xval = x2 - x1;
		int zval = z2 - z1;
		
		if (xval > 0) {
			if (zval < 0) {
				return Dir.NorthEast;
			} else if (zval > 0) {
				return Dir.SouthEast;
			} else {
				return Dir.East;
			}
		} else if (xval < 0) {
			if (zval < 0) {
				return Dir.NorthWest;
			} else if (zval > 0) {
				return Dir.SouthWest;
			} else {
				return Dir.West;
			}
		} else {
			if (zval < 0) {
				return Dir.North;
			} else if (zval > 0) {
				return Dir.South;
			} else {
				return Dir.Center;
			}
		}
	}
}
