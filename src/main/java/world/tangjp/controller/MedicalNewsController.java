package world.tangjp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.tangjp.entity.MedicalNews;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import world.tangjp.result.RespResult;

/**
 * 咨询控制器
 *
 * @author Tangjp
 */
@Api(tags = "医疗新闻接口")
@RestController
@RequestMapping("/medical-news")
public class MedicalNewsController extends BaseController<MedicalNews> {

    @ApiOperation(value = "保存医疗新闻", notes = "新增或更新医疗新闻信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "新闻ID(更新时传入)"),
        @ApiImplicitParam(name = "title", value = "新闻标题", required = true),
        @ApiImplicitParam(name = "content", value = "新闻内容", required = true),
        @ApiImplicitParam(name = "author", value = "作者"),
        @ApiImplicitParam(name = "publishTime", value = "发布时间"),
        @ApiImplicitParam(name = "source", value = "新闻来源")
    })
    @Override
    public RespResult save(MedicalNews news) {
        return super.save(news);
    }

    @ApiOperation(value = "删除医疗新闻")
    @ApiImplicitParam(name = "id", value = "新闻ID", required = true, example = "1")
    @Override
    public RespResult delete(Integer id) {
        return super.delete(id);
    }
}
