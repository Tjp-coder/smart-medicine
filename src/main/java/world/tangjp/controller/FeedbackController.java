package world.tangjp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "反馈ID(更新时传入)"),
        @ApiImplicitParam(name = "content", value = "反馈内容", required = true),
        @ApiImplicitParam(name = "userId", value = "用户ID", required = true),
        @ApiImplicitParam(name = "createTime", value = "创建时间")
    })
    @Override
    public RespResult save(Feedback feedback) {
        return super.save(feedback);
    }

    @ApiOperation(value = "删除反馈")
    @ApiImplicitParam(name = "id", value = "反馈ID", required = true, example = "1")
    @Override
    public RespResult delete(Integer id) {
        return super.delete(id);
    }
}
