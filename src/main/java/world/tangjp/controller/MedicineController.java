package world.tangjp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.tangjp.entity.Medicine;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import world.tangjp.result.RespResult;


/**
 * 药品控制器
 *
 * @author Tangjp
 */
@Api(tags = "药品管理接口")
@RestController
@RequestMapping("medicine")
public class MedicineController extends BaseController<Medicine> {

    @ApiOperation(value = "保存药品", notes = "新增或更新药品信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "药品ID(更新时传入)"),
        @ApiImplicitParam(name = "medicineName", value = "药品名称", required = true),
        @ApiImplicitParam(name = "medicineEffect", value = "药品功效"),
        @ApiImplicitParam(name = "medicineBrand", value = "药品品牌"),
        @ApiImplicitParam(name = "medicineType", value = "药品类型(0:西药,1:中药,2:中成药)"),
        @ApiImplicitParam(name = "medicinePrice", value = "药品价格"),
        @ApiImplicitParam(name = "interaction", value = "药品相互作用"),
        @ApiImplicitParam(name = "taboo", value = "禁忌事项"),
        @ApiImplicitParam(name = "usAge", value = "用法用量"),
        @ApiImplicitParam(name = "imgPath", value = "药品图片地址")
    })
    @Override
    public RespResult save(Medicine medicine) {
        return super.save(medicine);
    }

    @ApiOperation("删除药品")
    @ApiImplicitParam(name = "id", value = "药品ID", required = true, example = "1")
    @Override
    public RespResult delete(Integer id) {
        return super.delete(id);
    }
}
