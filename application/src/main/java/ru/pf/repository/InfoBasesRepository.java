package ru.pf.repository;

import ru.pf.entity.InfoBase;

/**
 * @author a.kakushin
 */
public interface InfoBasesRepository extends PfRepository<InfoBase> {

    @Override
    default InfoBase newInstance() {
        return new InfoBase();
    }
}
