package com.java.gmall.sms.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.sms.dao.CouponSpuCategoryRelationDao;
import com.java.gmall.sms.entity.CouponSpuCategoryRelation;
import com.java.gmall.sms.service.CouponSpuCategoryRelationService;

@Service("couponSpuCategoryRelationService")
public class CouponSpuCategoryRelationServiceImpl extends ServiceImpl<CouponSpuCategoryRelationDao, CouponSpuCategoryRelation> implements CouponSpuCategoryRelationService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<CouponSpuCategoryRelation> page = this.page(
                new Query<CouponSpuCategoryRelation>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}