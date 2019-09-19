package com.zhy.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhy.tmall.pojo.Category;
import com.zhy.tmall.service.CategoryService;
import com.zhy.tmall.util.ImageUtil;
import com.zhy.tmall.util.Page;
import com.zhy.tmall.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Zheng Haoyun
 * @date 2019-09-14 15:06
 */
@Controller
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_category_list")
    public String list(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Category> categories = categoryService.list();
        PageInfo pageInfo = new PageInfo(categories);

        int total = (int) pageInfo.getTotal();
        page.setTotal(total);
        model.addAttribute("cs", categories);
        model.addAttribute("page", page);
        return "admin/listCategory";
    }

    @RequestMapping("admin_category_add")
    public String add(Category c, HttpSession session, UploadedImageFile uploadedImageFile) {
        categoryService.add(c);
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, c.getId() + ".jpg");
        if (!imageFolder.exists()) {
            imageFolder.mkdirs();
        }
        try {
            uploadedImageFile.getImage().transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin_category_list";
    }

    @RequestMapping("admin_category_delete")
    public String delete(Integer id, HttpSession session) {
        categoryService.delete(id);
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, id + ".jpg");
        file.delete();

        return "redirect:/admin_category_list";

    }

    /**
     * 根据id查询到具体的Category，去到Category编辑页面。
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("admin_category_edit")
    public String edit(Integer id, Model model) {
        Category category = categoryService.get(id);
        model.addAttribute("c", category);
        return "admin/editCategory";
    }

    @RequestMapping("admin_category_update")
    public String update(Category category, HttpSession session, UploadedImageFile uploadedImageFile) {
        categoryService.update(category);
        MultipartFile image = uploadedImageFile.getImage();
        if (null != image && !image.isEmpty()) {
            File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
            File file = new File(imageFolder, category.getId()+".jpg");
            try {
                image.transferTo(file);
                BufferedImage img = ImageUtil.change2jpg(file);
                ImageIO.write(img,".jpg",file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/admin_category_list";
    }


}
