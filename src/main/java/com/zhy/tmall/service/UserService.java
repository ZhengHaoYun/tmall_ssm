package com.zhy.tmall.service;

import com.zhy.tmall.dao.UserMapper;
import com.zhy.tmall.pojo.User;
import com.zhy.tmall.pojo.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zheng Haoyun
 * @date 2019-09-21 8:10
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void add(User user) {
        userMapper.insert(user);
    }

    public void delete(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    public User get(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    public List<User> list() {
        UserExample userExample = new UserExample();
        userExample.setOrderByClause("id desc");
        List<User> users = userMapper.selectByExample(userExample);
        return users;
    }
}
