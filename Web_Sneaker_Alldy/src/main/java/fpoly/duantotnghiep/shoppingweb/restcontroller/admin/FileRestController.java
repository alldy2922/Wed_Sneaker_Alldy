package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileRestController {
    @GetMapping("/images")
    public List<FileResponse> getImageFiles() {
        String folderPath = "src/main/resources/images/product"; // Đường dẫn đến thư mục chứa ảnh
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        List<FileResponse> fileResponses = new ArrayList<>();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    fileResponses.add(new FileResponse(file.getName()));
                }
            }
        }
        return fileResponses;
    }

    public static class FileResponse {
        private String anh;

        public FileResponse(String anh) {
            this.anh = anh;
        }

        public String getAnh() {
            return anh;
        }

        public void setAnh(String anh) {
            this.anh = anh;
        }
    }
}
