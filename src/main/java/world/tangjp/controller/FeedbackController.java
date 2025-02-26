package world.tangjp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.tangjp.entity.Feedback;
import world.tangjp.result.RespResult;

/**
 * 反馈控制器
 *
 * @author Tangjp
 */
@Api(tags = "反馈管理接口")
@RestController
@RequestMapping("/feedback")
public class FeedbackController extends BaseController<Feedback> {

    @ApiOperation(value = "保存反馈", notes = "新增或更新反馈信息")
    @Override
    public RespResult save(@RequestBody Feedback feedback) {
        return super.save(feedback);
    }

    @ApiOperation(value = "删除反馈")
    @Override
    public RespResult delete(Integer id) {
        return super.delete(id);
    }
}
