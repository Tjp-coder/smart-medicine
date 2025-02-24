package world.tangjp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.tangjp.entity.IllnessMedicine;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import world.tangjp.result.RespResult;

/**
 * 疾病药品关联控制器
 *
 * @author Tangjp
 */
@Api(tags = "疾病药品关联接口")
@RestController
@RequestMapping("/illness-medicine")
public class IllnessMedicineController extends BaseController<IllnessMedicine> {

    @ApiOperation(value = "保存疾病药品关联", notes = "新增或更新疾病与药品的关联关系")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "关联ID(更新时传入)"),
        @ApiImplicitParam(name = "illnessId", value = "疾病ID", required = true),
        @ApiImplicitParam(name = "medicineId", value = "药品ID", required = true),
        @ApiImplicitParam(name = "dosage", value = "用药剂量"),
        @ApiImplicitParam(name = "frequency", value = "用药频率"),
        @ApiImplicitParam(name = "duration", value = "用药时长"),
        @ApiImplicitParam(name = "notes", value = "用药注意事项")
    })
    @Override
    public RespResult save(IllnessMedicine illnessMedicine) {
        return super.save(illnessMedicine);
    }

    @ApiOperation(value = "删除疾病药品关联")
    @ApiImplicitParam(name = "id", value = "关联ID", required = true, example = "1")
    @Override
    public RespResult delete(Integer id) {
        return super.delete(id);
    }
}
