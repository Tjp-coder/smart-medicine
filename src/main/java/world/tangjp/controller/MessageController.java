package world.tangjp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.tangjp.result.RespResult;
import world.tangjp.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 消息控制器
 *
 * @author Tangjp
 */
@Api(tags = "消息管理接口")
@RestController
@RequestMapping("/message")
public class MessageController extends BaseController<User> {

    /**
     * 发送消息
     */
    @ApiOperation(value = "发送消息", notes = "向AI助手发送消息并获取回复")
    @ApiImplicitParam(name = "content", value = "消息内容", required = true)
    @PostMapping("/query")
    public RespResult query(String content) {
        String result = apiService.query(content);
        return RespResult.success(result);
    }

    @ApiOperation(value = "保存消息记录", notes = "保存用户与AI的对话记录")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userId", value = "用户ID", required = true),
        @ApiImplicitParam(name = "content", value = "消息内容", required = true),
        @ApiImplicitParam(name = "type", value = "消息类型(0:用户消息,1:AI回复)", required = true)
    })
    @Override
    public RespResult save(User user) {
        return super.save(user);
    }
}
