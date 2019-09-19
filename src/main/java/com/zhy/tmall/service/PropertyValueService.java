package com.zhy.tmall.service;

import com.zhy.tmall.dao.PropertyValueMapper;
import com.zhy.tmall.pojo.Product;
import com.zhy.tmall.pojo.Property;
import com.zhy.tmall.pojo.PropertyValue;
import com.zhy.tmall.pojo.PropertyValueExample;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Provider;
import java.util.List;

/**
 * @author Zheng Haoyun
 * @date 2019-09-19 22:04
 */

@Service
public class PropertyValueService {

    @Autowired
    PropertyValueMapper propertyValueMapper;

    @Autowired
    PropertyService propertyService;

    public PropertyValue get(int ptid, int pid) {
        PropertyValueExample propertyValueExample = new PropertyValueExample();
        propertyValueExample.createCriteria().andPidEqualTo(pid).andPtidEqualTo(ptid);
        List<PropertyValue> propertyValues =
                propertyValueMapper.selectByExample(propertyValueExample);
        if (propertyValues.isEmpty()) {
            return null;
        }
        return propertyValues.get(0);
    }

    public void init(Product p) {
        List<Property> pts = propertyService.list(p.getCid());
        for (Property pt : pts) {
            PropertyValue propertyValue = get(pt.getId(), p.getId());
            if (null == propertyValue) {
                propertyValue = new PropertyValue();
                propertyValue.setPid(p.getId());
                propertyValue.setPtid(pt.getId());
                propertyValueMapper.insert(propertyValue);
            }

        }
    }

    public void update(PropertyValue propertyValue) {
        propertyValueMapper.updateByPrimaryKeySelective(propertyValue);
    }

    public List<PropertyValue> list(int pid) {
        PropertyValueExample propertyValueExample = new PropertyValueExample();
        propertyValueExample.createCriteria().andPtidEqualTo(pid);
        List<PropertyValue> propertyValues = propertyValueMapper.selectByExample(propertyValueExample);
        for (PropertyValue propertyValue : propertyValues) {
            Property property = propertyService.get(propertyValue.getPid());
            propertyValue.setProperty(property);
        }
        return propertyValues;
    }
}
