package com.java.gmall.pms.service.impl;

import com.java.gmall.pms.dao.CommentReplayDao;
import com.java.gmall.pms.entity.CommentReplay;
import com.java.gmall.pms.service.CommentReplayService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;


@Service("commentReplayService")
public class CommentReplayServiceImpl extends ServiceImpl<CommentReplayDao, CommentReplay> implements CommentReplayService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<CommentReplay> page = this.page(
                new Query<CommentReplay>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}