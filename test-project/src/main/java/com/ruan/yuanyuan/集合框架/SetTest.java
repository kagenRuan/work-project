package com.ruan.yuanyuan.集合框架;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

public class SetTest {

    public static void main(String[] args) {

        Set set = Collections.synchronizedSet(new HashSet<>());
        Set set1 = new CopyOnWriteArraySet();
        Set set2 = new LinkedHashSet();
        set.add(1);
    }
}
