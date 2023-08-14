package com.locydragon.rli.util;

public class Pack<K,V> {
	K key;
	V value;

	public void setKey(K k) {
		this.key = k;
	}

	public void setValue(V v) {
		this.value = v;
	}

	public K getKey() {
		return this.key;
	}

	public V getValue() {
		return this.value;
	}
}
