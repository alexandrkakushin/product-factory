package ru.pf.controller.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.pf.entity.licence.LicenceKey;
import ru.pf.repository.licence.LicenceKeyRepository;
import ru.pf.repository.PfRepository;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

/**
 * Контроллер для реализации CRUD-операция с ключами системы лицензирования
 * @author a.kakushin
 */
@Controller
@RequestMapping(LicenceKeyCrudController.URL)
public class LicenceKeyCrudController implements PfCrudController<LicenceKey> {

    static final String URL = "licence/keys";

    @Autowired
    private LicenceKeyRepository licenceKeyRepository;

    @Override
    public String getUrl() {
        return URL;
    }

    @Override
    public String getTemplateItem() {
        return "key-item";
    }

    @Override
    public String getName() {
        return "Ключи системы лицензирования";
    }

    @Override
    public PfRepository<LicenceKey> getRepository() {
        return this.licenceKeyRepository;
    }

    @Override
    public String submit(LicenceKey entity) throws SubmitException {
        if (entity.getId() != null) {
            getRepository().findById(entity.getId()).ifPresent(
                    fromDb -> entity.setAttachedFile(fromDb.getAttachedFile()));
        }

        LicenceKey saved = getRepository().save(entity);
        return "redirect:/" + getUrl() + "/" + saved.getId();
    }

    @PostMapping(path = "/uploadFile", consumes = { MULTIPART_FORM_DATA_VALUE })
    public String uploadFile(@RequestParam Long id, @RequestParam("file") MultipartFile multipartFile) throws SubmitException {
        try {
            Optional<LicenceKey> optionalLicenceKey = getRepository().findById(id);
            if (optionalLicenceKey.isPresent()) {
                LicenceKey entity = optionalLicenceKey.get();

                entity.setFileName(multipartFile.getOriginalFilename());
                entity.setAttachedFile(multipartFile.getBytes());
                getRepository().save(entity);
            }
        } catch (IOException ex) {
            throw new SubmitException(ex);
        }

        return "redirect:/" + getUrl() + "/" + id;
    }
}
