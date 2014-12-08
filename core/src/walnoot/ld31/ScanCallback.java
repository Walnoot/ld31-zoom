package walnoot.ld31;

import walnoot.ld31.entities.CollidableEntity;

public interface ScanCallback {
	public void reportEntity(CollidableEntity e);
}
