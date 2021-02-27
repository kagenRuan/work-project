package com.ruan.yuanyuan.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Data
@Document(indexName = "goods",shards = 1,replicas = 0)
public class Goods implements Serializable {

    @Id
    Long id;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    String title;

    @Field(type = FieldType.Keyword)
    String category;

    @Field(type = FieldType.Keyword)
    String brand;

    @Field(type = FieldType.Float)
    float price;

    @Field(type = FieldType.Keyword,index = false)//不会对图片地址查询,指定为false
    String images;

    public Goods(Long id, String title, String category, String brand, float price) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.brand = brand;
        this.price = price;
    }
}
