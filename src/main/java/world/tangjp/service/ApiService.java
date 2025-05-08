package world.tangjp.service;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.MessageManager;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.utils.Constants;
import io.reactivex.Flowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApiService {

    @Value("${ai-key}")
    private String apiKey;

    @Autowired
    private HttpSession session;

    /**
     * 查询并返回智能医生的流式回答
     *
     * @param queryMessage 用户输入的问题
     * @return Flowable<GenerationResult> 流式输出结果
     */
    public Flowable<GenerationResult> streamQuery(String queryMessage) {
        Constants.apiKey = apiKey;

        try {
            Generation gen = new Generation();
            MessageManager msgManager = new MessageManager(10);

            // 从会话中获取对话历史，如果没有则初始化
            List<Message> conversationHistory = (List<Message>) session.getAttribute("conversationHistory");
            if (conversationHistory == null) {
                conversationHistory = new ArrayList<>();
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

            // 配置流式输出的参数
            QwenParam param = QwenParam.builder()
                    .model(Generation.Models.QWEN_PLUS)
                    .messages(msgManager.get())
                    .resultFormat(QwenParam.ResultFormat.MESSAGE)
                    .incrementalOutput(true) // 启用增量输出
                    .build();

            // 返回流式结果
            return gen.streamCall(param);
        } catch (Exception e) {
            return Flowable.error(new RuntimeException("智能医生现在不在线，请稍后再试～"));
        }
    }

    /**
     * 将流式结果保存到会话历史
     */
    public void saveStreamResult(String fullContent) {
        List<Message> conversationHistory = (List<Message>) session.getAttribute("conversationHistory");
        if (conversationHistory != null) {
            Message aiMessage = Message.builder()
                    .role(Role.ASSISTANT.getValue())
                    .content(fullContent)
                    .build();
            conversationHistory.add(aiMessage);
            session.setAttribute("conversationHistory", conversationHistory);
        }
    }

    /**
     * 清除会话中的对话历史
     */
    public void clearConversationHistory() {
        session.removeAttribute("conversationHistory");
    }
}