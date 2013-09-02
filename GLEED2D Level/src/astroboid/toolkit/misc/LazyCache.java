package astroboid.toolkit.misc;


public class LazyCache<K, V> extends FixedSizeLinkedHashMap<K, V> {
	private final Functor1<V, K> lazyHandler;

	public LazyCache(int maxSize, Functor1<V, K> lazyHandler) {
		super(maxSize);
		this.lazyHandler = lazyHandler;
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(Object key) {
		if (!containsKey(key)) {
			K k = (K) key;
			put(k, lazyHandler.invoke(k));
		}
		return super.get(key);
	}
}
