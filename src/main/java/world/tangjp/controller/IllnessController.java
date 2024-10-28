package world.tangjp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.tangjp.entity.Illness;


/**
 * 疾病控制器
 *
 * @author Tangjp
 */
@RestController
@RequestMapping("illness")
public class IllnessController extends BaseController<Illness> {

}
