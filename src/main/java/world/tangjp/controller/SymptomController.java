package world.tangjp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import world.tangjp.dto.SymptomSaveDTO;
import world.tangjp.dto.SymptomSearchDTO;
import world.tangjp.entity.Illness;
import world.tangjp.entity.SymptomLog;
import world.tangjp.entity.User;
import world.tangjp.result.RespResult;
import world.tangjp.service.SymptomService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Api(tags = "症状自检接口")
@Controller
@RequestMapping("/symptom")
public class SymptomController extends BaseController<SymptomLog> {

    @ApiOperation("症状匹配查询")
    @PostMapping("/search")
    @ResponseBody
    public RespResult search(@Validated @RequestBody SymptomSearchDTO dto) {
        List<Illness> illnesses = symptomService.searchBySymptom(dto.getKeyword());
        return RespResult.success("查询成功", illnesses);
    }

    @ApiOperation("保存症状记录")
    @ResponseBody
    @PostMapping("/saveSymptom")
    public RespResult save(@Validated @RequestBody SymptomSaveDTO dto) {
        if (loginUser == null) {
            return RespResult.fail("请先登录");
        }

        // 1. 先查询匹配的疾病
        List<Illness> illnesses = ((SymptomService) service).searchBySymptom(dto.getKeyword());
        if (illnesses.isEmpty()) {
            return RespResult.fail("未找到匹配的疾病");
        }

        // 2. 构建症状记录，确保所有必要字段都设置
        SymptomLog symptomLog = SymptomLog.builder()
                .userId(loginUser.getId())
                .keyword(dto.getKeyword())
                .matchedIllnessIds(illnesses.stream()
                        .map(i -> i.getId().toString())
                        .collect(Collectors.joining(",")))
                .createTime(LocalDateTime.now())  // 显式设置创建时间
                .build();

        // 3. 调用父类的save方法
        return super.save(symptomLog);
    }

    @Override
    @ApiOperation("删除症状记录")
    @ResponseBody
    @PostMapping("/delete")
    public RespResult delete(Integer id) {
        if (loginUser == null) {
            return RespResult.fail("请先登录");
        }

        SymptomLog log = service.get(id);
        if (log == null || !log.getUserId().equals(loginUser.getId())) {
            return RespResult.fail("无权限删除该记录");
        }

        return super.delete(id);
    }
}
