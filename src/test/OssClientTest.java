import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSSClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import world.tangjp.component.OssClient;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class OssClientTest {

    private OssClient ossClient;

    @Value("${oss.bucket-name}")
    private String bucketName;

    @Value("${oss.end-point}")
    private String endPoint;

    @Value("${oss.access-key}")
    private String accessKeyId;

    @Value("${oss.access-secret}")
    private String accessKeySecret;

    @BeforeEach
    public void setUp() {
        // 初始化OssClient实例
        ossClient = new OssClient();
    }

    @Test
    public void testUpload() throws IOException {
        // 模拟一个MultipartFile文件
        FileInputStream fis = new FileInputStream("C:\\Users\\83821\\Desktop\\图片资源\\1111.png");
        MultipartFile file = new MockMultipartFile("file", "sample-image.jpg", "image/jpeg", fis);

        // 调用上传方法
        String fileUrl = ossClient.upload(file, "test/path");

        // 验证上传结果
        assertNotNull(fileUrl);
        assertTrue(fileUrl.startsWith("https://"));
        System.out.println(fileUrl);
    }
}
