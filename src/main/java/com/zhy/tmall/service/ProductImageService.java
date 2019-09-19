package com.zhy.tmall.service;

import com.zhy.tmall.dao.ProductImageMapper;
import com.zhy.tmall.pojo.ProductImage;
import com.zhy.tmall.pojo.ProductImageExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zheng Haoyun
 * @date 2019-09-17 16:36
 */
@Service
public class ProductImageService {

    public static final String type_single = "type_single";
    public static final String type_detail = "type_detail";

    @Autowired
    ProductImageMapper productImageMapper;

    public void add(ProductImage pi) {
        productImageMapper.insert(pi);
    }

    public void delete(int id) {
        productImageMapper.deleteByPrimaryKey(id);
    }

    public void update(ProductImage pi) {
        productImageMapper.updateByPrimaryKeySelective(pi);
    }

    public ProductImage get(int id) {
        return productImageMapper.selectByPrimaryKey(id);
    }

    public List<ProductImage> list(int pid, String type) {
        ProductImageExample productImageExample = new ProductImageExample();
        productImageExample.createCriteria().andPidEqualTo(pid).andTypeEqualTo(type);
        productImageExample.setOrderByClause("id desc");
        List<ProductImage> productImages = productImageMapper.selectByExample(productImageExample);
        return productImages;
    }

}
