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
 * 该服务类用于处理用户提出的医疗相关问题，使用 AI 生成智能回复。
 *
 * @author Tangjp
 */
@Service
public class ApiService {

    // 从配置文件中读取 AI 的 API 密钥
    @Value("${ai-key}")
    private String apiKey;

    // 定义医疗相关的关键词列表，用于判断用户输入是否是医疗相关的问题
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

    /**
     * 查询并返回智能医生的回答
     *
     * @param queryMessage 用户输入的问题
     * @return 智能医生的回复内容
     */
    public String query(String queryMessage) {
        // 检查输入中是否包含医疗相关的关键词
        if (!isMedicalQuery(queryMessage)) {
            // 如果不是医疗问题，返回提示信息
            return "请提出与医疗相关的问题，我只能回答医疗领域的问题哦！";
        }

        // 设置 API 密钥
        Constants.apiKey = apiKey;

        try {
            // 创建 Generation 对象用于调用 AI 模型
            Generation gen = new Generation();

            // 创建消息管理器，用于管理多个消息
            MessageManager msgManager = new MessageManager(10);

            // 添加系统消息，提示 AI 自身的角色是智能医生
            Message systemMsg = Message.builder().role(Role.SYSTEM.getValue()).content("你是智能医生，你只回答与医疗相关的问题，不要回答其他问题！").build();
            // 添加用户消息
            Message userMsg = Message.builder().role(Role.USER.getValue()).content(queryMessage).build();
            msgManager.add(systemMsg);
            msgManager.add(userMsg);

            // 构建请求参数，指定使用的 AI 模型（Qwen_Turbo），并获取消息的结果
            QwenParam param = QwenParam.builder()
                    .model(Generation.Models.QWEN_TURBO)
                    .messages(msgManager.get())
                    .resultFormat(QwenParam.ResultFormat.MESSAGE)
                    .build();

            // 调用 AI 生成结果
            GenerationResult result = gen.call(param);
            GenerationOutput output = result.getOutput();
            Message message = output.getChoices().get(0).getMessage();

            // 返回生成的智能医生回复内容
            return message.getContent();
        } catch (Exception e) {
            // 如果调用 AI 过程发生异常，返回错误信息
            return "智能医生现在不在线，请稍后再试～";
        }
    }

    /**
     * 判断输入的消息是否与医疗相关
     *
     * @param queryMessage 用户输入的消息
     * @return 如果是医疗问题，返回 true；否则返回 false
     */
    private boolean isMedicalQuery(String queryMessage) {
        // 遍历医疗相关的关键词列表，检查输入消息是否包含这些关键词
        for (String keyword : MEDICAL_KEYWORDS) {
            if (queryMessage.contains(keyword)) {
                return true;  // 如果包含任意一个关键词，则认为是医疗相关问题
            }
        }
        // 如果没有包含任何关键词，返回 false
        return false;
    }
}
