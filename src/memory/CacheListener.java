package memory;

public interface CacheListener {

	
	public default void upData(Cache cache) {
		cache.update();
	}
	
}
