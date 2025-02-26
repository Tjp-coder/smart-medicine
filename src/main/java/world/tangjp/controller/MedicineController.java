package world.tangjp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import world.tangjp.entity.Medicine;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    @Override
    public RespResult save(@RequestBody Medicine medicine) {
        return super.save(medicine);
    }

    @ApiOperation("删除药品")
    @Override
    public RespResult delete(Integer id) {
        return super.delete(id);
    }
}
