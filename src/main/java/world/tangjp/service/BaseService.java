package world.tangjp.service;

import org.springframework.beans.factory.annotation.Autowired;
import world.tangjp.dao.*;

/**
 * 基础服务类
 *
 * @author Tangjp
 */
public abstract class BaseService<T> implements IService<T> {

    @Autowired
    protected UserDao userDao;

    @Autowired
    protected HistoryDao historyDao;

    @Autowired
    protected IllnessDao illnessDao;

    @Autowired
    protected IllnessKindDao illnessKindDao;

    @Autowired
    protected IllnessMedicineDao illnessMedicineDao;

    @Autowired
    protected MedicineDao medicineDao;
    
    @Autowired
    protected PageviewDao pageviewDao;

}
