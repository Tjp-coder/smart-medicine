package world.tangjp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.tangjp.entity.Illness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "疾病ID(更新时传入)"),
        @ApiImplicitParam(name = "illnessName", value = "疾病名称", required = true),
        @ApiImplicitParam(name = "illnessDesc", value = "疾病描述"),
        @ApiImplicitParam(name = "kindId", value = "疾病分类ID", required = true),
        @ApiImplicitParam(name = "symptom", value = "症状描述"),
        @ApiImplicitParam(name = "treatment", value = "治疗方案")
    })
    @Override
    public RespResult save(Illness illness) {
        return super.save(illness);
    }

    @ApiOperation(value = "删除疾病")
    @ApiImplicitParam(name = "id", value = "疾病ID", required = true, example = "1")
    @Override
    public RespResult delete(Integer id) {
        return super.delete(id);
    }
}
