package com.ruan.yuanyuan.controller;

import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.entity.User;
import com.ruan.yuanyuan.enums.MenuEnum;
import com.ruan.yuanyuan.exception.ExceptionUtil;
import com.ruan.yuanyuan.service.IPermissionsService;
import com.ruan.yuanyuan.vo.PermissionsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: MenuController
 * @author: ruanyuanyuan
 * @date: 2020/7/7 22:08
 * @version: 1.0
 * @description:
 **/
@RestController
@RequestMapping("/api/menu")
public class MenuController extends BaseController {

    @Autowired
    private IPermissionsService permissionsService;

    /**
     * 查询左侧菜单栏数据
     * @return
     */
    @RequestMapping("/list")
    public ResultObject menuList(){
        User user = getLoginUser();
        ResultObject resultObject = new ResultObject();
        //查询资源信息
        List<PermissionsVo> permissionsVos = permissionsService.findPermissionsByUserId(user.getId(), MenuEnum.MENU.getCode());
        //获取所有的顶级父级菜单
        List<PermissionsVo>  parentPermissionsVos = permissionsVos.stream().filter(obj -> obj.getParentId().equals("0")).collect(Collectors.toList());
        //将list转为map,并且如果key有相同的话选择最前面的key
        //Map<String,PermissionsVo> map = permissionsVos.stream().collect(Collectors.toMap(PermissionsVo::getParentId, Function.identity(),(key1,key2) -> key1));
        //通过根据ParentId进行分组的方式，将ParentId相同的放在一个list中
        Map<String,List<PermissionsVo>> map = permissionsVos.stream().collect(Collectors.groupingBy(PermissionsVo::getParentId));
        //递归组装数据
        doEachPermissionsVos(parentPermissionsVos,map);
        //组装返回数据并返回前端
        Map<String,Object> result = new HashMap<>();
        result.put("menuInfo",parentPermissionsVos);
        resultObject.setData(result);
        resultObject.setCode(ExceptionUtil.SystemExceptionEnum.SUCCESS.getCode());
        resultObject.setMsg(ExceptionUtil.SystemExceptionEnum.SUCCESS.getMessage());
        return resultObject;
    }

    /**
     * 递归根据id获取子菜单
     * @param parentPermissionsVos 所有的父级菜单
     * @param map 根据parentId分组后的数据
     */
    private void doEachPermissionsVos(List<PermissionsVo>  parentPermissionsVos,Map<String,List<PermissionsVo>> map ){
        List<PermissionsVo> list = new ArrayList<>();
        parentPermissionsVos.stream().forEach(obj ->{
            String permissionsId = obj.getId();
            if(!StringUtils.isEmpty(permissionsId)){
                List<PermissionsVo> permissionsVoList = map.get(permissionsId);
                obj.setChild(permissionsVoList);
                if(null != permissionsVoList){
                    //只要获取到子节点就把其加入到最外层list,让他继续循环查找是否还有字节点
                    list.addAll(permissionsVoList);
                }
            }
        });
        if(!ObjectUtils.isEmpty(list)){
            doEachPermissionsVos(list,map);
        }
    }
}
