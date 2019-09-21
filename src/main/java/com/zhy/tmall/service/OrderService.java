package com.zhy.tmall.service;

import com.zhy.tmall.dao.OrderMapper;
import com.zhy.tmall.pojo.Order;
import com.zhy.tmall.pojo.OrderExample;
import com.zhy.tmall.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zheng Haoyun
 * @date 2019-09-21 9:05
 */
@Service
public class OrderService {

    public static final String waitPay = "waitPay";
    public static final String waitDelivery = "waitDelivery";
    public static final String waitConfirm = "waitConfirm";
    public static final String waitReview = "waitReview";
    public static final String finish = "finish";
    public static final String delete = "delete";

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    UserService userService;

    public void add(Order c) {
        orderMapper.insert(c);
    }

    public void delete(int id) {
        orderMapper.deleteByPrimaryKey(id);
    }

    public void update(Order c) {
        orderMapper.updateByPrimaryKeySelective(c);
    }

    public Order get(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    public List<Order> list() {
        OrderExample example = new OrderExample();
        example.setOrderByClause("id desc");
        List<Order> result = orderMapper.selectByExample(example);
        setUser(result);
        return result;
    }

    public void setUser(List<Order> os) {
        for (Order o : os)
            setUser(o);
    }

    public void setUser(Order o) {
        int uid = o.getUid();
        User u = userService.get(uid);
        o.setUser(u);
    }
}
