package com.rt.springboot.app;

import lombok.Value;

public record Pair<K, V>(K first, V second) { }
