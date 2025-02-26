package world.tangjp.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.tangjp.entity.History;
import io.swagger.annotations.Api;
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

    @ApiOperation(value = "保存历史记录", notes = "新增或更新用户历史记录")
    @Override
    public RespResult save(@RequestBody History history) {
        return super.save(history);
    }

    @ApiOperation(value = "删除历史记录")
    @Override
    public RespResult delete(Integer id) {
        return super.delete(id);
    }
}
