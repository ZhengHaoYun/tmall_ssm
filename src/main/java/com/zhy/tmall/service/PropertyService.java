package com.zhy.tmall.service;

import com.zhy.tmall.dao.PropertyMapper;
import com.zhy.tmall.pojo.CategoryExample;
import com.zhy.tmall.pojo.Property;
import com.zhy.tmall.pojo.PropertyExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zheng Haoyun
 * @date 2019-09-16 21:56
 */
@Service
public class PropertyService {

    @Autowired
    PropertyMapper propertyMapper;

    public void add(Property property) {
        propertyMapper.insert(property);
    }

    public void delete(Integer id){
        propertyMapper.deleteByPrimaryKey(id);
    }

    public void update(Property property){
        propertyMapper.updateByPrimaryKeySelective(property);
    }

    public Property get(Integer id){
        return propertyMapper.selectByPrimaryKey(id);
    }

    public List<Property> list(Integer cid){
        PropertyExample propertyExample = new PropertyExample();
        PropertyExample.Criteria criteria = propertyExample.createCriteria();
        criteria.andCidEqualTo(cid);
        return propertyMapper.selectByExample(propertyExample);
    }
}
