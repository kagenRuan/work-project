package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.dao.PermissionsMapper;
import com.ruan.yuanyuan.entity.Permissions;
import com.ruan.yuanyuan.exception.BusinessAssert;
import com.ruan.yuanyuan.exception.ExceptionUtil;
import com.ruan.yuanyuan.service.IPermissionsRoleService;
import com.ruan.yuanyuan.service.IPermissionsService;
import com.ruan.yuanyuan.vo.PermissionsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import java.util.*;
import java.util.stream.Collectors;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 16:52
 * version:
 * Description: 资源 添加，删除和修改了权限后都需要清空缓存
 */
@Service
@SuppressWarnings("all")
public class PermissionsServiceImpl extends ServiceImpl<PermissionsMapper, Permissions> implements IPermissionsService {


    @Autowired
    private PermissionsMapper permissionsMapper;
    @Autowired
    private IPermissionsRoleService permissionsRoleService;

    /**
     * 根据角色查询权限
     * @param roleIds 角色ID
     * @return Set<PermissionsVo>
     */
    @Override
    public Set<PermissionsVo> findPermissionsByRoleId(Set<String> roleIds,String isButton) {
        return permissionsMapper.findPermissionsByRoleId(roleIds,isButton);
    }

    /**
     * 根据用户ID查询资源信息
     * @param userId 用户ID
     * @param isButton 是否是菜单 0为按钮1为菜单
     * @return List<PermissionsVo>
     */
    @Override
    public List<PermissionsVo> findPermissionsByUserId(String userId, String isButton) {
        //验证
        List<PermissionsVo> permissionsVos = permissionsMapper.findPermissionsByUserId(userId, isButton);
        return permissionsVos;
    }

    /**
     * 查询所有的资源信息
     * @return
     */
    @Override
    public List<PermissionsVo> findAll() {
        return permissionsMapper.findAll();
    }

    /**
     * 根据资源ID删除资源新
     * @param id 资源ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        BusinessAssert.notBlank(id,ExceptionUtil.PermissionExceptionEnum.PERMISSION_ID_NOT_NULL);
        Permissions permissions = baseMapper.selectOne(new QueryWrapper<Permissions>().eq("id",id));
        BusinessAssert.notNull(permissions,ExceptionUtil.PermissionExceptionEnum.PERMISSION_NOT_NULL);
        //查询到索引的资源信息
        List<PermissionsVo> permissionsVos = permissionsMapper.findPermissionsByUserId(null, null);

        Map<String,List<PermissionsVo>> map = permissionsVos.stream().collect(Collectors.groupingBy(PermissionsVo::getParentId));
        //得到第一个子节点
        List<PermissionsVo> clild = Optional.ofNullable(map.get(id)).orElse(new ArrayList<>());
        //得到所有的子节点
        List<PermissionsVo> resultAllchild = new ArrayList<>();
        getPermissionsIds(clild,map,resultAllchild);
        resultAllchild.addAll(clild);
        //得到所有要删除的资源ID
        List<String> permissionsIds = Optional.of(resultAllchild.stream().map(obj -> obj.getId()).collect(Collectors.toList())).orElse(new ArrayList<>());
        permissionsIds.add(id);
        //删除资源
        if(!ObjectUtils.isEmpty(permissionsIds)){
            baseMapper.deleteBatchIds(permissionsIds);
        }
        //得到资源与角色中间表数据
        List<String> permissionsRoleId = Optional.of(resultAllchild.stream().map(obj -> obj.getPermissionsRoleId()).collect(Collectors.toList())).orElse(new ArrayList<>());
        if(!ObjectUtils.isEmpty(permissionsRoleId)){
            //删除资源与角色中间表信息
            permissionsRoleService.removeByIds(permissionsRoleId);
        }
    }

    /**
     * 根据资源ID修改资源信息
     * @param permissionsVo 资源信息参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(PermissionsVo permissionsVo) {
        Permissions permissions = new Permissions();
        permissions.updateBean();
        permissions.setId(permissionsVo.getId());
        permissions.setName(permissionsVo.getTitle());
        permissions.setCode(permissionsVo.getPermission());
        permissions.setUrl(permissionsVo.getHref());
        baseMapper.updateById(permissions);
    }

    /**
     * 添加资源信息
     * @param permissionsVo 资源信息参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(PermissionsVo permissionsVo) {
        Permissions permissions = new Permissions();
        permissions.initBean();
        permissions.setName(permissionsVo.getTitle());
        permissions.setCode(permissionsVo.getPermission());
        permissions.setParentId(permissionsVo.getParentId());
        permissions.setUrl(permissionsVo.getHref());
        permissions.setIsButton(permissionsVo.getIsButton());
        baseMapper.insert(permissions);
    }


    /**
     * 递归根据id获取子菜单
     * @param parentPermissionsVos 所有的父级菜单
     * @param map 根据parentId分组后的数据
     */
    private void getPermissionsIds(List<PermissionsVo>  parentPermissionsVos,Map<String,List<PermissionsVo>> map,List<PermissionsVo> resultAllchild){
        if(!ObjectUtils.isEmpty(parentPermissionsVos)){
            List<PermissionsVo> list = new ArrayList<>();
            parentPermissionsVos.stream().forEach(obj ->{
                String permissionsId = obj.getId();
                if(!StringUtils.isEmpty(permissionsId)){
                    List<PermissionsVo> permissionsVoList = map.get(permissionsId);
                    if(null != permissionsVoList){
                        resultAllchild.addAll(permissionsVoList);
                    }
                    if(null != permissionsVoList){
                        //只要获取到子节点就把其加入到最外层list,让他继续循环查找是否还有字节点
                        list.addAll(permissionsVoList);
                    }
                }
            });
            if(!ObjectUtils.isEmpty(list)){
                getPermissionsIds(list,map,resultAllchild);
            }
        }
    }

}
