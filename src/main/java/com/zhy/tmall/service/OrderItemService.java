package com.zhy.tmall.service;

import com.zhy.tmall.dao.OrderItemMapper;
import com.zhy.tmall.pojo.Order;
import com.zhy.tmall.pojo.OrderItem;
import com.zhy.tmall.pojo.OrderItemExample;
import com.zhy.tmall.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zheng Haoyun
 * @date 2019-09-21 9:10
 */
@Service
public class OrderItemService {

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    ProductService productService;

    public void delete(Integer id) {
        orderItemMapper.deleteByPrimaryKey(id);
    }

    public void update(OrderItem c) {
        orderItemMapper.updateByPrimaryKeySelective(c);
    }

    public OrderItem get(Integer id) {
        OrderItem orderItem = orderItemMapper.selectByPrimaryKey(id);
        setProduct(orderItem);
        return orderItem;
    }

    public List<OrderItem> list(){
        OrderItemExample example =new OrderItemExample();
        example.setOrderByClause("id desc");
        return orderItemMapper.selectByExample(example);

    }

    /**
     * OrderItemServiceImpl实现OrderItemService，提供CRUD一套方法的实现。
     * 同时还提供fill(Order order)和fill(List<Order> orders), 先说fill(Order order) :
     * 为什么要提供这个方法呢？ 因为在订单管理界面，首先是遍历多个订单，然后遍历这个订单下的多个订单项。 而由MybatisGenerator逆向工程所创建的一套自动生成代码，是不具备一对多关系的，需要自己去二次开发。 这里就是做订单与订单项的一对多关系。
     * 在fill(Order order)中：
     * 1. 根据订单id查询出其对应的所有订单项
     * 2. 通过setProduct为所有的订单项设置Product属性
     * 3. 遍历所有的订单项，然后计算出该订单的总金额和总数量
     * 4. 最后再把订单项设置在订单的orderItems属性上。
     *
     * 在fill(List<Order> orders) 中，就是遍历每个订单，然后挨个调用fill(Order order)。
     * @param os
     */
    public void fill(List<Order> os) {
        for (Order o : os) {
            fill(o);
        }
    }

    public void fill(Order o) {
        OrderItemExample example =new OrderItemExample();
        example.createCriteria().andOidEqualTo(o.getId());
        example.setOrderByClause("id desc");
        List<OrderItem> ois =orderItemMapper.selectByExample(example);
        setProduct(ois);

        float total = 0;
        int totalNumber = 0;
        for (OrderItem oi : ois) {
            total+=oi.getNumber()*oi.getProduct().getPromotePrice();
            totalNumber+=oi.getNumber();
        }
        o.setTotal(total);
        o.setTotalNumber(totalNumber);
        o.setOrderItems(ois);

    }

    public void setProduct(List<OrderItem> ois){
        for (OrderItem oi: ois) {
            setProduct(oi);
        }
    }

    private void setProduct(OrderItem oi) {
        Product p = productService.get(oi.getPid());
        oi.setProduct(p);
    }
}
