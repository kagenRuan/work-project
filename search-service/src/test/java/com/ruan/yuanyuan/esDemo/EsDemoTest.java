package com.ruan.yuanyuan.esDemo;

import org.elasticsearch.common.settings.Settings;

public class EsDemoTest {


    public static void main(String[] args) {

        Settings.Builder builder = Settings.builder()
                .put("cluster.name","elasticsearch");




    }
}
