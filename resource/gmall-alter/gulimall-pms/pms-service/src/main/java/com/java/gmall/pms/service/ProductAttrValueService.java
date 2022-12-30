package com.java.gmall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.core.bean.Resp;
import com.java.gmall.pms.entity.ProductAttrValue;
import com.java.gmall.pms.vo.BaseGroupVO;

import java.util.List;


/**
 * spu属性值
 *
 * @author jiangli
 * @since  2020-01-10 04:05:29
 */
public interface ProductAttrValueService extends IService<ProductAttrValue> {

    PageVo queryPage(QueryCondition params);

	List<ProductAttrValue> querySearchAttrValueBySpuId(Long spuId);

	List<BaseGroupVO> queryAttrGroups(Long spuId, Long cateId);
}

