package com.java.gmall.ums.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.ums.dao.MemberReceiveAddressDao;
import com.java.gmall.ums.entity.MemberReceiveAddress;
import com.java.gmall.ums.service.MemberReceiveAddressService;

@Service("memberReceiveAddressService")
public class MemberReceiveAddressServiceImpl extends ServiceImpl<MemberReceiveAddressDao, MemberReceiveAddress> implements MemberReceiveAddressService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<MemberReceiveAddress> page = this.page(
                new Query<MemberReceiveAddress>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}