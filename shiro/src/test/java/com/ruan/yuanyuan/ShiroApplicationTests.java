package com.ruan.yuanyuan;

import com.ruan.yuanyuan.vo.PermissionsVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ShiroApplicationTests {

    @Test
    public   void contextLoads() {

        List<PermissionsVo> permissionsVos = new ArrayList<>();
        PermissionsVo permissionsVo1 = new PermissionsVo();
        permissionsVo1.setId("1");
        permissionsVo1.setParentId("0");
        PermissionsVo permissionsVo4 = new PermissionsVo();
        permissionsVo4.setId("4");
        permissionsVo4.setParentId("1");
        PermissionsVo permissionsVo5 = new PermissionsVo();
        permissionsVo5.setId("5");
        permissionsVo5.setParentId("1");
        permissionsVos.add(permissionsVo1);
        permissionsVos.add(permissionsVo4);
        permissionsVos.add(permissionsVo5);

        PermissionsVo permissionsVo2 = new PermissionsVo();
        permissionsVo2.setId("2");
        permissionsVo2.setParentId("0");
        PermissionsVo permissionsVo6 = new PermissionsVo();
        permissionsVo6.setId("6");
        permissionsVo6.setParentId("2");
        PermissionsVo permissionsVo7 = new PermissionsVo();
        permissionsVo7.setId("7");
        permissionsVo7.setParentId("2");
        permissionsVos.add(permissionsVo2);
        permissionsVos.add(permissionsVo6);
        permissionsVos.add(permissionsVo7);

        PermissionsVo permissionsVo3 = new PermissionsVo();
        permissionsVo3.setId("3");
        permissionsVo3.setParentId("0");
        PermissionsVo permissionsVo8 = new PermissionsVo();
        permissionsVo8.setId("8");
        permissionsVo8.setParentId("3");
        PermissionsVo permissionsVo9 = new PermissionsVo();
        permissionsVo9.setId("9");
        permissionsVo9.setParentId("3");

        PermissionsVo permissionsVo10 = new PermissionsVo();
        permissionsVo10.setId("10");
        permissionsVo10.setParentId("9");
        PermissionsVo permissionsVo11 = new PermissionsVo();
        permissionsVo11.setId("11");
        permissionsVo11.setParentId("9");
        permissionsVos.add(permissionsVo10);
        permissionsVos.add(permissionsVo11);

        permissionsVos.add(permissionsVo3);
        permissionsVos.add(permissionsVo8);
        permissionsVos.add(permissionsVo9);
       //获取所有的顶级父级菜单
       List<PermissionsVo>  parentPermissionsVos = permissionsVos.stream().filter(obj -> obj.getParentId().equals("0")).collect(Collectors.toList());
       Map<String,List<PermissionsVo>> map = permissionsVos.stream().filter(obj ->!obj.getParentId().equals("0")).collect(Collectors.groupingBy(PermissionsVo::getParentId));
       doEachPermissionsVos(parentPermissionsVos,map);
       System.out.println(map);


    }

    private void doEachPermissionsVos(List<PermissionsVo>  parentPermissionsVos,Map<String,List<PermissionsVo>> map ){
        List<PermissionsVo> list = new ArrayList<>();
        parentPermissionsVos.stream().forEach(obj ->{
            String permissionsId = obj.getId();
            if(!StringUtils.isEmpty(permissionsId)){
                List<PermissionsVo> permissionsVoList = map.get(permissionsId);
                obj.setChild(permissionsVoList);
                if(null != permissionsVoList){
                    list.addAll(permissionsVoList);
                }
            }
        });
        if(!ObjectUtils.isEmpty(list)){
            doEachPermissionsVos(list,map);
        }
    }

}
