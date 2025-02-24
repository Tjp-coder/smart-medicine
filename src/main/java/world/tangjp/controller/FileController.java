package world.tangjp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import world.tangjp.component.OssClient;
import world.tangjp.result.RespResult;
import world.tangjp.entity.User;
import world.tangjp.utils.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;

/**
 * 文件控制器
 *
 * @author Tangjp
 */
@Api(tags = "文件管理接口")
@RestController
@RequestMapping("/file")
public class FileController extends BaseController<User> {

    @Autowired
    private OssClient ossClient;

    /**
     * 上传文件到OSS
     */
    @ApiOperation(value = "上传文件", notes = "上传文件到阿里云OSS存储")
    @ApiImplicitParam(name = "file", value = "上传的文件", required = true, dataType = "file")
    @PostMapping("/upload")
    public RespResult upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return RespResult.fail("上传文件不能为空");
        }
        String url = ossClient.upload(file, String.valueOf(loginUser.getId()));
        if (Assert.isEmpty(url)) {
            return RespResult.fail("上传失败", url);
        }
        return RespResult.success("上传成功", url);
    }
}
