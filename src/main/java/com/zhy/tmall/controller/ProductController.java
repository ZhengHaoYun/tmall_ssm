package com.zhy.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhy.tmall.pojo.Category;
import com.zhy.tmall.pojo.Product;
import com.zhy.tmall.service.CategoryService;
import com.zhy.tmall.service.ProductService;
import com.zhy.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * @author Zheng Haoyun
 * @date 2019-09-17 15:59
 */
@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_product_add")
    public String add(Product p) {
        p.setCreateDate(new Date());
        productService.add(p);
        return "redirect:admin_product_list?cid="+p.getCid();
    }

    @RequestMapping("admin_product_delete")
    public String delete(int id) {
        Product p = productService.get(id);
        productService.delete(id);
        return "redirect:admin_product_list?cid="+p.getCid();
    }

    @RequestMapping("admin_product_edit")
    public String edit(Model model, int id) {
        Product product = productService.get(id);
        product.setCategory(categoryService.get(product.getCid()));

        model.addAttribute("p",product);
        return "admin/editProduct";
    }

    @RequestMapping("admin_product_update")
    public String update(Product p) {
        productService.update(p);
        return "redirect:admin_product_list?cid="+p.getCid();
    }

    @RequestMapping("admin_product_list")
    public String list(int cid, Model model, Page page) {
        Category category = categoryService.get(cid);
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Product> products = productService.list(cid);
        page.setTotal((int) new PageInfo<>(products).getTotal());
        page.setParam("&cid="+category.getId());
        model.addAttribute("c",category);
        model.addAttribute("ps",products);
        model.addAttribute("page",page);
        return "admin/listProduct";
    }
}
