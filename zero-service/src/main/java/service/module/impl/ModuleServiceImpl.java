package service.module.impl;

import common.base.ZeroTX;
import common.model.Module;
import dal.module.ModuleDO;
import dal.module.ModuleDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import service.module.ModuleService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ningcheng
 * @date 2017/10/9
 */
@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleDao moduleDao;

    @Override
    public Module get(long id) {
        Module ret = new Module();
        ModuleDO moduleDO = moduleDao.get(id);
        if (moduleDO == null) {
            return null;
        }
        BeanUtils.copyProperties(moduleDO, ret);
        return ret;
    }

    @Override
    public List<Module> getAll() {
        List<Module> ret = new ArrayList<>();
        List<ModuleDO> queryList = moduleDao.getAll();
        if (CollectionUtils.isEmpty(queryList)) {
            return ret;
        }
        for (ModuleDO moduleDO : queryList) {
            if (moduleDO != null) {
                Module module = new Module();
                BeanUtils.copyProperties(moduleDO, module);
                ret.add(module);
            }
        }
        return ret;
    }

    @Override
    @ZeroTX
    public void createOrUpdate(Module module) {
        if (module == null) {
            return;
        }
        ModuleDO moduleDO = new ModuleDO();
        BeanUtils.copyProperties(moduleDO, module);
        moduleDao.insertOrUpdate(moduleDO);
    }

    @Override
    @ZeroTX
    public void remove(long id) {
        moduleDao.delete(id);
    }
}
