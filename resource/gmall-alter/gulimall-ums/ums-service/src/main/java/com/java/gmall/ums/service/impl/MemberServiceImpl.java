package com.java.gmall.ums.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.ums.dao.MemberDao;
import com.java.gmall.ums.entity.Member;
import com.java.gmall.ums.service.MemberService;

import java.util.Date;
import java.util.UUID;

@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, Member> implements MemberService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<Member> page = this.page(
                new Query<Member>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

	@Override
	public Boolean checkData(String data, Integer type) {
		QueryWrapper<Member> wrapper = new QueryWrapper<>();
		switch (type) {
			case 1:
				wrapper.eq("username", data);
				break;
			case 2:
				wrapper.eq("mobile", data);
				break;
			case 3:
				wrapper.eq("email", data);
				break;
			default:
				return false;
		}
		return this.baseMapper.selectCount(wrapper) == 0;
	}

	public void register(Member member, String code) {
		// 校验短信验证码
		// String cacheCode = this.redisTemplate.opsForValue().get(KEY_PREFIX + memberEntity.getMobile());
		// if (!StringUtils.equals(code, cacheCode)) {
		//     return false;
		// }

		// 生成盐
		String salt = StringUtils.replace(UUID.randomUUID().toString(), "-", "");
		member.setSalt(salt);

		// 对密码加密
		member.setPassword(DigestUtils.md5Hex(salt + DigestUtils.md5Hex(member.getPassword())));

		// 初始化
		member.setGrowth(0);
		member.setIntegration(0);
		member.setLevelId(0L);
		member.setStatus(1);
		member.setCreateTime(new Date());

		// 添加到数据库
		boolean b = this.save(member);

		// if(b){
		// 注册成功，删除redis中的记录
		// this.redisTemplate.delete(KEY_PREFIX + memberEntity.getMobile());
		// }
	}

	@Override
	public Member queryUser(String username, String password) {
		// 查询
		Member member = this.getOne(new QueryWrapper<Member>().eq("username", username));
		// 校验用户名
		if (member == null) {
			return null;
		}
		// 校验密码
		if (!member.getPassword().equals(DigestUtils.md5Hex(member.getSalt() + DigestUtils.md5Hex(password)))) {
			return null;
		}
		// 用户名密码都正确
		return member;
	}

}