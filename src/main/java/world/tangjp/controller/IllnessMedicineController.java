package world.tangjp.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.tangjp.entity.IllnessMedicine;
import io.swagger.annotations.Api;
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
    @Override
    public RespResult save(@RequestBody IllnessMedicine illnessMedicine) {
        return super.save(illnessMedicine);
    }

    @ApiOperation(value = "删除疾病药品关联")
    @Override
    public RespResult delete(Integer id) {
        return super.delete(id);
    }
}
