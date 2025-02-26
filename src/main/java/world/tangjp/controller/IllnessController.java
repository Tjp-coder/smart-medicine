package world.tangjp.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.tangjp.entity.Illness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import world.tangjp.result.RespResult;


/**
 * 疾病控制器
 *
 * @author Tangjp
 */
@Api(tags = "疾病管理接口")
@RestController
@RequestMapping("/illness")
public class IllnessController extends BaseController<Illness> {

    @ApiOperation(value = "保存疾病", notes = "新增或更新疾病信息")
    @Override
    public RespResult save(@RequestBody Illness illness) {
        return super.save(illness);
    }

    @ApiOperation(value = "删除疾病")
    @Override
    public RespResult delete(Integer id) {
        return super.delete(id);
    }
}
