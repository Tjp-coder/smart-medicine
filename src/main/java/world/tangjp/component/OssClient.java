package world.tangjp.component;

import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 对象存储工具
 *
 * 该类用于与阿里云 OSS 对接，提供上传文件到 OSS 和生成文件 URL 的功能。
 * 使用此工具可以轻松上传文件，并返回文件的 URL 地址。
 *
 * @author Tangjp
 */
@Component
public class OssClient {

    // 从配置文件中读取 OSS 配置信息
    @Value("${oss.bucket-name}")
    private String bucketName;  // OSS 存储桶名称

    @Value("${oss.end-point}")
    private String endPoint;    // OSS 的 endpoint 地址

    @Value("${oss.access-key}")
    private String accessKeyId; // OSS Access Key ID

    @Value("${oss.access-secret}")
    private String accessKeySecret; // OSS Access Key Secret

    /**
     * 上传文件到 OSS
     *
     * @param file 要上传的文件
     * @param path 文件保存的路径
     * @return 上传成功后文件的 URL 地址
     * @throws IOException 上传过程中可能抛出的异常
     */
    public String upload(MultipartFile file, String path) throws IOException {
        // 检查文件和路径是否合法
        if (file == null || path == null) {
            return null;  // 若文件或路径为空，返回 null
        }

        // 创建 OSSClient 对象，用于操作 OSS
        OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);

        // 如果指定的存储桶不存在，则创建一个新的存储桶
        if (!ossClient.doesBucketExist(bucketName)) {
            ossClient.createBucket(bucketName);
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
            // 设置存储桶为公共读模式
            createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
            // 创建存储桶
            ossClient.createBucket(createBucketRequest);
        }

        // 获取文件的扩展名
        String extension = OssClient.getFileExtension(file);

        // 设置文件保存的路径（带上 UUID 来避免文件名冲突）
        String fileUrl = path + "/" + IdUtil.simpleUUID() + extension;

        // 生成文件的 URL 地址
        String url = "https://" + bucketName + "." + endPoint + "/" + fileUrl;

        // 创建上传请求对象并上传文件
        PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, fileUrl, file.getInputStream()));

        // 设置文件访问权限为公共读取
        ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);

        // 返回文件的 URL 地址
        return url;
    }

    /**
     * 获取文件的扩展名
     *
     * @param file 上传的文件
     * @return 文件的扩展名，例如 ".jpg", ".png"
     */
    public static String getFileExtension(MultipartFile file) {
        String filename = file.getOriginalFilename();
        // 确保文件名不为空
        assert filename != null;
        // 返回文件扩展名
        return filename.substring(filename.lastIndexOf("."));
    }
}
