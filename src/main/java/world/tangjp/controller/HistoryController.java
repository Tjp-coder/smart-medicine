package world.tangjp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.tangjp.entity.History;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import world.tangjp.result.RespResult;


/**
 * 历史控制器
 *
 * @author Tangjp
 */
@Api(tags = "历史记录接口")
@RestController
@RequestMapping("/history")
public class HistoryController extends BaseController<History> {

    @ApiOperation(value = "保存历史记录", notes = "新增或更新用户问诊历史记录")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "记录ID(更新时传入)"),
        @ApiImplicitParam(name = "userId", value = "用户ID", required = true),
        @ApiImplicitParam(name = "illnessId", value = "疾病ID", required = true),
        @ApiImplicitParam(name = "consultTime", value = "问诊时间"),
        @ApiImplicitParam(name = "consultContent", value = "问诊内容"),
        @ApiImplicitParam(name = "aiReply", value = "AI回复内容")
    })
    @Override
    public RespResult save(History history) {
        return super.save(history);
    }

    @ApiOperation(value = "删除历史记录")
    @ApiImplicitParam(name = "id", value = "记录ID", required = true, example = "1")
    @Override
    public RespResult delete(Integer id) {
        return super.delete(id);
    }
}
