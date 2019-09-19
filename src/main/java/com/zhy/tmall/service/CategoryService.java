package com.zhy.tmall.service;

import com.zhy.tmall.dao.CategoryMapper;
import com.zhy.tmall.pojo.Category;
import com.zhy.tmall.pojo.CategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zheng Haoyun
 * @date 2019-09-13 18:50
 */

@Service
public class CategoryService {

    @Autowired
    CategoryMapper categoryMapper;
    public List<Category> list() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("id desc");
        return categoryMapper.selectByExample(categoryExample);
    }


    public void add(Category category){
        categoryMapper.insert(category);
    }

    public void delete(Integer id){
        categoryMapper.deleteByPrimaryKey(id);
    }

    public Category get(Integer id){
        return categoryMapper.selectByPrimaryKey(id);
    }

    public void update(Category category){
        categoryMapper.updateByPrimaryKeySelective(category);
    }
}
