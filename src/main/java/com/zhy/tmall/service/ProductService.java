package com.zhy.tmall.service;

import com.zhy.tmall.dao.ProductMapper;
import com.zhy.tmall.pojo.Category;
import com.zhy.tmall.pojo.Product;
import com.zhy.tmall.pojo.ProductExample;
import com.zhy.tmall.pojo.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zheng Haoyun
 * @date 2019-09-17 13:56
 */
@Service
public class ProductService {

    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductImageService productImageService;

    public void add(Product p) {
        productMapper.insert(p);
    }

    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }

    public void update(Product p) {
        productMapper.updateByPrimaryKeySelective(p);
    }

    public Product get(int id) {
        Product p = productMapper.selectByPrimaryKey(id);
        setFirstProductImage(p);
        setCategory(p);
        return p;
    }

    public void setCategory(List<Product> ps) {
        for (Product p : ps)
            setCategory(p);
    }

    public void setCategory(Product p) {
        int cid = p.getCid();
        Category c = categoryService.get(cid);
        p.setCategory(c);
    }

    public List list(int cid) {
        ProductExample example = new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        List result = productMapper.selectByExample(example);
        setCategory(result);
        setFirstProductImage(result);
        return result;
    }

    public void setFirstProductImage(Product p) {
        List<ProductImage> pis = productImageService.list(p.getId(), ProductImageService.type_single);
        if (!pis.isEmpty()) {
            ProductImage pi = pis.get(0);
            p.setFirstProductImage(pi);
        }
    }

    public void setFirstProductImage(List<Product> ps) {
        for (Product p : ps) {
            setFirstProductImage(p);
        }
    }


}
