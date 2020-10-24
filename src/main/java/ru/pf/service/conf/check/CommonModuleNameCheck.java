package ru.pf.service.conf.check;

import org.springframework.stereotype.Service;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.object.IMetadataObject;
import ru.pf.metadata.object.common.CommonModule;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Проверка имен общих модулей, которые должны формироваться согласно стандартам 1С
 * @author a.kakushin
 */
@Service
public class CommonModuleNameCheck implements ServiceCheck<IMetadataObject> {

    @Override
    public List<IMetadataObject> check(Conf conf) throws InvocationTargetException, IllegalAccessException {
        List<IMetadataObject> result = new ArrayList<>();

        Set<IMetadataObject> metadataObjects = conf.getCommonModules();
        for (IMetadataObject metadataObject : metadataObjects) {
            CommonModule module = (CommonModule) metadataObject;

            boolean resultCheck = true;
            String name = module.getName().toLowerCase();

            if (module.isServer() && module.isClientManagedApplication()) {
                if (!name.contains("КлиентСервер".toLowerCase())) {
                    resultCheck = false;
                }
            } else if (module.isClientManagedApplication()) {
                if (!name.contains("Клиент".toLowerCase())) {
                    resultCheck = false;
                }
            }

            if (module.isGlobal()) {
                if (!name.contains("Глобальный".toLowerCase())) {
                    resultCheck = false;
                }
            }

            if (module.isServerCall()) {
                if (!name.contains("ВызовСервера".toLowerCase())) {
                    resultCheck = false;
                }
            }

            if (!resultCheck) {
                result.add(metadataObject);
            }
        }

        return result;
    }

    @Override
    public String getAlias() {
        return "Имена общих модулей";
    }
}
