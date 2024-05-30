package fpoly.duantotnghiep.shoppingweb.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ImgUtil {
    public static void deleteImg(List<String> imgs,String folder)  {
        if(imgs.size()==0) return;
        for (String img:imgs) {
            System.out.println(img);
            Path fileToDeletePath = Paths.get("src/main/resources/images/"+folder+"/" + img);
            try {
                Files.delete(fileToDeletePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void deleteImg(String img,String folder)  {
            Path fileToDeletePath = Paths.get("src/main/resources/images/"+folder+"/" + img);
            try {
                Files.delete(fileToDeletePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static List<String> addImages(List<MultipartFile> file,String folder) throws IOException {
            List<String> setAnh = new ArrayList<>();
            int i = 0;
            for (MultipartFile f : file) {
                byte[] bytes = f.getBytes();
                String s = System.currentTimeMillis() + f.getOriginalFilename();
                String name = Integer.toHexString(s.hashCode()) + s.substring(s.lastIndexOf("."));
//                String typeImg = f.getContentType().split("/")[f.getContentType().split("/").length - 1];
//                String imgName = name + "." + typeImg;
                Path path = Paths.get("src/main/resources/images/"+folder+"/" + name);
                Path path1 = Files.write(path, bytes);
                setAnh.add(name);
                i++;
            }
            return setAnh;
    }
    public static String addImage(MultipartFile file,String folder) throws IOException {
            byte[] bytes = file.getBytes();
            String s = System.currentTimeMillis() + file.getOriginalFilename();
            String name = Integer.toHexString(s.hashCode()) + s.substring(s.lastIndexOf("."));
//                String typeImg = f.getContentType().split("/")[f.getContentType().split("/").length - 1];
//                String imgName = name + "." + typeImg;
            Path path = Paths.get("src/main/resources/images/"+folder+"/" + name);
            Files.write(path, bytes);
        return name;
    }
}
