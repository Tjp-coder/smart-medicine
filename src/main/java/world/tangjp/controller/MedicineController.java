package world.tangjp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.tangjp.entity.Medicine;


/**
 * 药品控制器
 *
 * @author Tangjp
 */
@RestController
@RequestMapping("medicine")
public class MedicineController extends BaseController<Medicine> {

}
