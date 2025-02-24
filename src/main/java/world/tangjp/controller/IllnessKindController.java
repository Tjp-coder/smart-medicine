package world.tangjp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.tangjp.entity.IllnessKind;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "分类ID(更新时传入)"),
        @ApiImplicitParam(name = "kindName", value = "分类名称", required = true),
        @ApiImplicitParam(name = "kindDesc", value = "分类描述"),
        @ApiImplicitParam(name = "parentId", value = "父分类ID"),
        @ApiImplicitParam(name = "sort", value = "排序号")
    })
    @Override
    public RespResult save(IllnessKind illnessKind) {
        return super.save(illnessKind);
    }

    @ApiOperation(value = "删除疾病分类")
    @ApiImplicitParam(name = "id", value = "分类ID", required = true, example = "1")
    @Override
    public RespResult delete(Integer id) {
        return super.delete(id);
    }
}
