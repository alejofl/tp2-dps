package com.rt.springboot.app;

import lombok.Value;

@Value
public class Pair<K, V> {
    K first;
    V second;
}
