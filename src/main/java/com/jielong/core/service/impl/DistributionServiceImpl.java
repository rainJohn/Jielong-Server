package com.jielong.core.service.impl;

import com.jielong.base.util.Constants;
import com.jielong.core.dao.DistributionMapper;
import com.jielong.core.dao.UserMapper;
import com.jielong.core.domain.ContactUs;
import com.jielong.core.domain.Distribution;
import com.jielong.core.domain.Order;
import com.jielong.core.service.DistributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class DistributionServiceImpl implements DistributionService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    DistributionMapper distributionMapper;

    @Transactional
    @Override
    public int insert(Order order) {
        Integer buyUserId=order.getUserId();
        //找一级父节点
        Integer parentId=userMapper.selectByPrimaryKey(buyUserId).getParentId();
        if (parentId!=null){
            Distribution distribution=new Distribution();
            distribution.setToUser(parentId);
            distribution.setFromUser(buyUserId);
            distribution.setOrderNum(order.getOrderNum());
            distribution.setDistLevel(1);  //一级分销
            distribution.setCreateTime(new Date());
            //设置返还金额 单位：分
            //一级分销 10% :0.10
            Integer firstDist=order.getSumMoney().multiply(new BigDecimal(100)).multiply(new BigDecimal(Constants.FIRST_DISTRIBUTION_PERCENT)).intValue();
            distribution.setDistMoney(firstDist);
            distributionMapper.insertSelective(distribution);

            //找二级父节点
            Integer grandparentId=userMapper.selectByPrimaryKey(parentId).getParentId();
            if (grandparentId!=null){
                Distribution distribution2=new Distribution();
                distribution2.setToUser(grandparentId);
                distribution2.setFromUser(buyUserId);
                distribution2.setOrderNum(order.getOrderNum());
                distribution2.setDistLevel(2);  //二级分销
                distribution2.setCreateTime(new Date());
                //设置返还金额 单位：分
                //二级分销 10% :0.06
                Integer secondDist=order.getSumMoney().multiply(new BigDecimal(100)).multiply(new BigDecimal(Constants.SECOND_DISTRIBUTION_PERCENT)).intValue();
                distribution.setDistMoney(secondDist);
                distributionMapper.insertSelective(distribution);


            }

        }
        return 1;
    }
}
