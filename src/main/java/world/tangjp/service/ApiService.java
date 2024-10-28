package world.tangjp.service;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationOutput;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.MessageManager;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.utils.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 智慧医生服务
 *
 * @author Tangjp
 */
@Service
public class ApiService {

    @Value("${ai-key}")
    private String apiKey;

    // 医疗相关的关键词列表
    private static final List<String> MEDICAL_KEYWORDS = Arrays.asList(
            "病", "症状", "治疗", "药物", "健康", "医学", "诊断", "医", "手术", "护理", "药品",
            "感冒", "发烧", "咳嗽", "头痛", "疼痛", "头晕", "恶心", "过敏", "炎症", "感染",
            "化验", "检查", "X光", "CT", "MRI", "注射", "输液", "复诊", "体检", "预约",
            "医生", "护士", "医院", "诊所", "急诊", "挂号", "候诊", "处方", "康复", "费用",
            "医保", "自费", "报销", "账单", "手术后", "体温", "血压", "心率", "呼吸", "疼痛等级",
            "药物过敏", "手术预约", "复查", "随访", "饮食建议", "病历", "住院", "出院", "化疗", "放疗",
            "疫苗", "血糖", "胆固醇", "血液检查", "心电图", "尿检", "病人", "病房", "护理计划",
            "阿司匹林", "布洛芬", "对乙酰氨基酚", "青霉素", "阿莫西林", "头孢", "左氧氟沙星", "地塞米松", "利尿剂",
            "降压药", "胰岛素", "抗生素", "抗病毒药", "抗组胺药", "止痛药", "镇静剂", "抗抑郁药", "激素", "抗癌药",
            "抗凝药", "抗心绞痛药", "哮喘喷雾剂", "胃药", "降脂药", "免疫抑制剂", "利胆药", "维生素", "钙片", "鱼油",
            "中药", "草药", "膏药", "止咳糖浆", "感冒药", "退烧药", "肠胃药", "咳嗽药水", "滴眼液", "滴鼻液"
    );

    public String query(String queryMessage) {
        // 检查输入中是否包含医疗相关关键词
        if (!isMedicalQuery(queryMessage)) {
            return "请提出与医疗相关的问题，我只能回答医疗领域的问题哦！";
        }

        Constants.apiKey = apiKey;
        try {
            //构建对话上下文
            Generation gen = new Generation();
            MessageManager msgManager = new MessageManager(10);
            Message systemMsg = Message.builder().role(Role.SYSTEM.getValue()).content("你是智能医生，你只回答与医疗相关的问题，不要回答其他问题！").build();
            Message userMsg = Message.builder().role(Role.USER.getValue()).content(queryMessage).build();
            msgManager.add(systemMsg);
            msgManager.add(userMsg);
            QwenParam param = QwenParam.builder().model(Generation.Models.QWEN_TURBO).messages(msgManager.get()).resultFormat(QwenParam.ResultFormat.MESSAGE).build();
            GenerationResult result = gen.call(param);
            GenerationOutput output = result.getOutput();
            Message message = output.getChoices().get(0).getMessage();
            return message.getContent();
        } catch (Exception e) {
            return "智能医生现在不在线，请稍后再试～";
        }
    }

    // 判断输入是否与医疗相关
    private boolean isMedicalQuery(String queryMessage) {
        for (String keyword : MEDICAL_KEYWORDS) {
            if (queryMessage.contains(keyword)) {
                return true;  // 如果包含任意一个关键词，则认为是医疗相关问题
            }
        }
        return false;
    }
}
