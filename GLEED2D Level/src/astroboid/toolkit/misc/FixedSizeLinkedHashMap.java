package astroboid.toolkit.misc;

import java.util.LinkedHashMap;

public class FixedSizeLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
	private final int maxSize;

	public FixedSizeLinkedHashMap(int maxSize) {
		super(maxSize / 4);
		this.maxSize = maxSize;
	}

	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		return size() > maxSize;
	}
}

