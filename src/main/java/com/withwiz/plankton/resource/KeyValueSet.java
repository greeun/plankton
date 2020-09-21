package com.withwiz.plankton.resource;

public class KeyValueSet<V> {
    String key = null;

    V value = null;

    public KeyValueSet(String key, V value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
