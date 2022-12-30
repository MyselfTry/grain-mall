package com.java.gmall.pms.service.impl;

import com.java.gmall.pms.dao.SpuCommentDao;
import com.java.gmall.pms.entity.SpuComment;
import com.java.gmall.pms.service.SpuCommentService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;



@Service("spuCommentService")
public class SpuCommentServiceImpl extends ServiceImpl<SpuCommentDao, SpuComment> implements SpuCommentService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SpuComment> page = this.page(
                new Query<SpuComment>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}