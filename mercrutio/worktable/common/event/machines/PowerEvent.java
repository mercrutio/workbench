package mercrutio.worktable.common.event.machines;

import mercrutio.worktable.common.event.ModEvent;
import net.minecraft.world.World;

public class PowerEvent extends ModEvent {
	
	public int x, y, z;
	public World world;
	public int current;
	
	public PowerEvent(World world, int x, int y, int z, int i) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
		this.current = i;
	}

}
