package com.ruan.yuanyuan;

import java.util.Arrays;

public enum CityEnum {

    ONE(1, "成都"),
    TWO(2, "遂宁"),
    THREE(3, "巴中"),
    FORE(4, "简阳"),
    FIVE(5, "德阳"),
    SIX(6, "重庆"),
    ;

    CityEnum(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    private Integer num;
    private String name;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static CityEnum getNameByNum(int num) {
        CityEnum[] cityEnums = CityEnum.values();
        return Arrays.stream(cityEnums).filter(obj -> obj.getNum() == num).findFirst().get();
    }
}
