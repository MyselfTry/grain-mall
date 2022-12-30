package com.java.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.admin.common.util.MapUtils;
import com.java.admin.dao.SysUserRoleDao;
import com.java.admin.entity.SysUserRoleEntity;
import com.java.admin.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 用户与角色对应关系
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRoleEntity> implements SysUserRoleService {

	@Override
	public void saveOrUpdate(Long userId, List<Long> roleIdList) {
		//先删除用户与角色关系
		this.removeByMap(new MapUtils().put("user_id", userId));

		if(roleIdList == null || roleIdList.size() == 0){
			return ;
		}

		//保存用户与角色关系
		for(Long roleId : roleIdList){
			SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
			sysUserRoleEntity.setUserId(userId);
			sysUserRoleEntity.setRoleId(roleId);

			this.save(sysUserRoleEntity);
		}
	}

	@Override
	public List<Long> queryRoleIdList(Long userId) {
		return baseMapper.queryRoleIdList(userId);
	}

	@Override
	public int deleteBatch(Long[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}
}
