package world.tangjp.controller;

import com.alibaba.dashscope.aigc.generation.GenerationResult;
import io.reactivex.Flowable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import world.tangjp.entity.User;
import world.tangjp.result.RespResult;

import java.io.IOException;

@RestController
@RequestMapping("/message")
public class MessageController extends BaseController<User> {

    /**
     * 发送消息并返回流式输出
     */
    @PostMapping("/stream-query")
    public SseEmitter streamQuery(String content) {
        SseEmitter emitter = new SseEmitter();
        Flowable<GenerationResult> resultFlow = apiService.streamQuery(content);

        StringBuilder fullContent = new StringBuilder();
        resultFlow.subscribe(
                result -> {
                    String chunk = result.getOutput().getChoices().get(0).getMessage().getContent();
                    fullContent.append(chunk);
                    emitter.send(SseEmitter.event().data(chunk));
                },
                error -> {
                    try {
                        emitter.send(SseEmitter.event().data("错误：" + error.getMessage()));
                        emitter.complete();
                    } catch (IOException e) {
                        emitter.completeWithError(e);
                    }
                },
                () -> {
                    apiService.saveStreamResult(fullContent.toString()); // 保存完整回复
                    emitter.complete();
                }
        );

        return emitter;
    }

    /**
     * 清除对话历史
     */
    @PostMapping("/clear-history")
    public RespResult clearHistory() {
        apiService.clearConversationHistory();
        return RespResult.success();
    }
}