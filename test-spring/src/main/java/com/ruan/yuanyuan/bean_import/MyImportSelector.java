package com.ruan.yuanyuan.bean_import;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @ClassName: B
 * @author: ruanyuanyuan
 * @date: 2020/6/16 13:27
 * @version: 1.0
 * @description: 用于实验实现ImportSelector接口注入实例Bean【C】
 **/
public class MyImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.ruan.yuanyuan.entry.C"};
    }
}
