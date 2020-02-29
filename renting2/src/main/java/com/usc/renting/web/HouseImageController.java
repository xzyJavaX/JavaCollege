package com.usc.renting.web;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.usc.renting.pojo.House;
import com.usc.renting.pojo.HouseImage;
import com.usc.renting.service.HouseholderService;
import com.usc.renting.service.HouseImageService;
import com.usc.renting.service.HouseService;
import com.usc.renting.util.ImageUtil;

@RestController
public class HouseImageController {

    @Autowired HouseService houseService;
    @Autowired HouseImageService houseImageService;
    @Autowired HouseholderService householderService;
//1.获取图片
    @GetMapping("/houses/{hid}/houseImages")
    public List<HouseImage> list(@RequestParam("type") String type, @PathVariable("hid") int hid) throws Exception {
        House house = houseService.get(hid);
        if(HouseImageService.type_single.equals(type)) {
            List<HouseImage> singles =  houseImageService.listSingleHouseImages(house);
            return singles;
        }
        else if(HouseImageService.type_detail.equals(type)) {
            List<HouseImage> details =  houseImageService.listDetailHouseImages(house);
            return details;
        }
        else {
            return new ArrayList<>();
        }
    }
//2.上传图片
    @PostMapping("/houseImages")
    public Object add(@RequestParam("hid") int hid, @RequestParam("type") String type, MultipartFile image, HttpServletRequest request) throws Exception {
        HouseImage bean = new HouseImage();
        House house = houseService.get(hid);
        bean.setHouse(house);
        bean.setType(type);
        houseImageService.add(bean);
//        本地
//        String folder = "img/";

//        服务器
        String folder="/";
        if (HouseImageService.type_single.equals(bean.getType())) {
            folder += "houseSingle";
        } else {
            folder += "houseDetail";
        }
//        本地
//        获取本机上的绝对路径
//        File img1 = new File(request.getServletContext().getResource(folder).getPath());
//        File imageFolder = new File(img1.getAbsolutePath());

//        服务器
        File imageFolder = new File("/home/wwwroot/ftptest/nginx/img/renting"+folder);

        File file = new File(imageFolder, bean.getId() + ".jpg");
        String fileName = file.getName();
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        try {
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (HouseImageService.type_single.equals(bean.getType())) {
//            本地
//            获取本机上的绝对路径
//            String imageFolder_small = request.getServletContext().getResource("/img/houseSingle_small").getPath();
//            String imageFolder_middle = request.getServletContext().getResource("/img/houseSingle_middle").getPath();

//            服务器
            String imageFolder_small = "/home/wwwroot/ftptest/nginx/img/renting/houseSingle_small";
            String imageFolder_middle ="/home/wwwroot/ftptest/nginx/img/renting/houseSingle_middle";

            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.getParentFile().mkdirs();
            f_middle.getParentFile().mkdirs();
            ImageUtil.resizeImage(file, 56, 56, f_small);
            ImageUtil.resizeImage(file, 217, 190, f_middle);
        }
        return bean;
        }
//3.删除图片
        @DeleteMapping("/houseImages/{id}")
        public String delete ( @PathVariable("id") int id, HttpServletRequest request)  throws Exception {
            HouseImage bean = houseImageService.get(id);
            houseImageService.delete(id);
//            本地
//            String folder = "/img/";

//            服务器
            String folder="/";
            if (HouseImageService.type_single.equals(bean.getType()))
                folder += "houseSingle";
            else
                folder += "houseDetail";
//本地
//            File imageFolder = new File(request.getServletContext().getResource(folder).getPath());

            //        服务器
            File imageFolder = new File("/home/wwwroot/ftptest/nginx/img/renting"+folder);

            File file = new File(imageFolder, bean.getId() + ".jpg");
            String fileName = file.getName();
            file.delete();
            if (HouseImageService.type_single.equals(bean.getType())) {
//                本地
//                String imageFolder_small = request.getServletContext().getRealPath("/img/houseSingle_small");
//                String imageFolder_middle = request.getServletContext().getRealPath("/img/houseSingle_middle");

                //            服务器
                String imageFolder_small = "/home/wwwroot/ftptest/nginx/img/renting/houseSingle_small";
                String imageFolder_middle ="/home/wwwroot/ftptest/nginx/img/renting/houseSingle_middle";

                File f_small = new File(imageFolder_small, fileName);
                File f_middle = new File(imageFolder_middle, fileName);
                f_small.delete();
                f_middle.delete();
            }
            return null;
        }
}