package Project;

import java.io.Serializable;

public class Pair<K, V> implements Serializable{
private K key;
private V value;
public Pair(K key, V value) {
	super();
	this.key = key;
	this.value = value;
}
public K getKey() {
	return key;
}
public void setKey(K key) {
	this.key = key;
}
public V getValue() {
	return value;
}
public void setValue(V value) {
	this.value = value;
}

}
