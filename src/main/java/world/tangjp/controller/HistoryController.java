package world.tangjp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.tangjp.entity.History;


/**
 * 历史控制器
 *
 * @author Tangjp
 */
@RestController
@RequestMapping("history")
public class HistoryController extends BaseController<History> {

}
