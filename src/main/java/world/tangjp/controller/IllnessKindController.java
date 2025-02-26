package world.tangjp.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.tangjp.entity.IllnessKind;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import world.tangjp.result.RespResult;

/**
 * 疾病分类控制器
 *
 * @author Tangjp
 */
@Api(tags = "疾病分类接口")
@RestController
@RequestMapping("/illness-kind")
public class IllnessKindController extends BaseController<IllnessKind> {

    @ApiOperation(value = "保存疾病分类", notes = "新增或更新疾病分类信息")
    @Override
    public RespResult save(@RequestBody IllnessKind illnessKind) {
        return super.save(illnessKind);
    }

    @ApiOperation(value = "删除疾病分类")
    @Override
    public RespResult delete(Integer id) {
        return super.delete(id);
    }
}
