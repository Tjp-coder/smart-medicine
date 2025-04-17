package world.tangjp.service;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationOutput;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.MessageManager;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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

    @Value("${ai-key}")
    private String apiKey;

    @Autowired
    private HttpSession session;

    @Deprecated
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
     * @return 智能医生的纯文本回复内容
     */
    public String query(String queryMessage) {
        Constants.apiKey = apiKey;

        try {
            Generation gen = new Generation();
            MessageManager msgManager = new MessageManager(10);

            // 从会话中获取对话历史，如果没有则初始化
            List<Message> conversationHistory = (List<Message>) session.getAttribute("conversationHistory");
            if (conversationHistory == null) {
                conversationHistory = new ArrayList<>();
                // 添加系统消息，初始化对话
                Message systemMsg = Message.builder()
                        .role(Role.SYSTEM.getValue())
                        .content("你是智能医生，需以执业医师身份回答，提供专业诊断建议、用药指导和就医提示。回答需简洁、准确，优先使用中文，必要时引用医学知识库。仅回答医疗相关问题。")
                        .build();
                conversationHistory.add(systemMsg);
            }

            // 添加用户的新消息
            Message userMsg = Message.builder()
                    .role(Role.USER.getValue())
                    .content(queryMessage)
                    .build();
            conversationHistory.add(userMsg);

            // 将对话历史加载到消息管理器
            for (Message msg : conversationHistory) {
                msgManager.add(msg);
            }

            // 配置 Qwen-Turbo 参数
            QwenParam param = QwenParam.builder()
                    .model(Generation.Models.QWEN_TURBO)
                    .messages(msgManager.get())
                    .resultFormat(QwenParam.ResultFormat.MESSAGE)
                    .build();

            // 调用 AI 模型并获取回复
            GenerationResult result = gen.call(param);
            GenerationOutput output = result.getOutput();
            Message aiMessage = output.getChoices().get(0).getMessage();

            // 将 AI 回复添加到对话历史
            conversationHistory.add(aiMessage);

            // 更新会话中的对话历史
            session.setAttribute("conversationHistory", conversationHistory);

            // 返回纯文本回复
            String plainText = aiMessage.getContent();
            return plainText;
        } catch (Exception e) {
            return "智能医生现在不在线，请稍后再试～";
        }
    }

    /**
     * 清除会话中的对话历史
     */
    public void clearConversationHistory() {
        session.removeAttribute("conversationHistory");
    }

    @Deprecated
    private boolean isMedicalQuery(String queryMessage) {
        return MEDICAL_KEYWORDS.stream().anyMatch(queryMessage::contains);
    }
}