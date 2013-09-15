package mercrutio.worktable.common.event.machines;

import mercrutio.worktable.common.event.ModEvent;
import net.minecraft.world.World;

public class TPowerEvent extends ModEvent {
	
	public int x, y, z;
	public int x2, y2, z2;
	public World world;
	public int current;
	
	public TPowerEvent(World world, int x, int y, int z, int x2, int y2, int z2, int i) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.x2 = x2;
		this.y2 = y2;
		this.z2 = z2;
		this.world = world;
		this.current = i;
	}
	
}
