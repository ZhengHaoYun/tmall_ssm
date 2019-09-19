package com.zhy.tmall.controller;

import com.zhy.tmall.pojo.Product;
import com.zhy.tmall.pojo.ProductImage;
import com.zhy.tmall.service.ProductImageService;
import com.zhy.tmall.service.ProductService;
import com.zhy.tmall.util.ImageUtil;
import com.zhy.tmall.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Zheng Haoyun
 * @date 2019-09-17 16:51
 */
@Controller
public class ProductImageController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductImageService productImageService;

    @RequestMapping("admin_productImage_add")
    public String add(ProductImage pi, HttpSession session, UploadedImageFile uploadedImageFile) {
        productImageService.add(pi);
        String fileName = pi.getId() + ".jpg";
        String imageFolder;
        String imageFolder_small = null;
        String imageFolder_middle = null;
        if (productImageService.type_single.equals(pi.getType())) {
            imageFolder = session.getServletContext().getRealPath("img/productSingle");
            imageFolder_small = session.getServletContext().getRealPath("img/product/productSingle_small");
            imageFolder_middle = session.getServletContext().getRealPath("img/product/productSingle_middle");

        } else {
            imageFolder = session.getServletContext().getRealPath("img/product/productDetail");
        }

        File f = new File(imageFolder, fileName);
        f.getParentFile().mkdirs();
        try {
            uploadedImageFile.getImage().transferTo(f);
            BufferedImage img = ImageUtil.change2jpg(f);
            ImageIO.write(img, "jpg", f);

            if (productImageService.type_single.equals(pi.getType())) {
                File f_small = new File(imageFolder_small, fileName);
                File f_middle = new File(imageFolder_middle, fileName);

                ImageUtil.resizeImage(f, 56, 56, f_small);
                ImageUtil.resizeImage(f, 217, 190, f_middle);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:admin_productImage_list?pid=" + pi.getPid();
    }

    @RequestMapping("admin_productImage_delete")
    public String delete(int id, HttpSession session) {

        ProductImage pi = productImageService.get(id);
        String fileName = id + ".jpg";
        String imageFolder;
        String imageFolder_small = null;
        String imageFolder_middle = null;
        if (productImageService.type_single.equals(pi.getType())) {
            imageFolder = session.getServletContext().getRealPath("img/productSingle");
            imageFolder_small = session.getServletContext().getRealPath("img/product/productSingle_small");
            imageFolder_middle = session.getServletContext().getRealPath("img/product/productSingle_middle");
            File imageFile = new File(imageFolder, fileName);
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            imageFile.delete();
            f_small.delete();
            f_middle.delete();
        } else {
            imageFolder = session.getServletContext().getRealPath("img/product/productDetail");
            new File(imageFolder, fileName).delete();
        }

        productImageService.delete(id);
        return "redirect:admin_productImage_list?pid=" + pi.getPid();
    }

    @RequestMapping("admin_productImage_list")
    public String list(int pid, Model model) {
        Product p = productService.get(pid);
        List<ProductImage> picSingle = productImageService.list(pid, productImageService.type_single);
        List<ProductImage> picDetail = productImageService.list(pid, productImageService.type_detail);

        model.addAttribute("p", p);
        model.addAttribute("pisSingle", picSingle);
        model.addAttribute("pisDetail", picSingle);

        return "admin/listProductImage";
    }
}
