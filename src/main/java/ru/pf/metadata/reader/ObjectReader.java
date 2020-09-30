package ru.pf.metadata.reader;

import ru.pf.metadata.object.Conf;
import ru.pf.metadata.object.Form;
import ru.pf.metadata.object.AbstractMetadataObject;
import ru.pf.metadata.object.MetadataObject;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

/**
 * @author a.kakushin
 */
public class ObjectReader extends XmlReader {

    private final AbstractMetadataObject metadataObject;

    public ObjectReader(AbstractMetadataObject metadataObject) {
        super(metadataObject.getFile());
        this.metadataObject = metadataObject;
    }

    private String nodeRoot() {
        return "/MetaDataObject/" + this.metadataObject.getXmlName();
    }

    public Set<Form> readForms() throws IOException {
        List<String> formNames = readChild(nodeRoot() + "/ChildObjects/Form");
        Path pathForms = metadataObject.getFile()
                .getParent()
                .resolve(metadataObject.getShortName(metadataObject.getFile()))
                .resolve("Forms");

        Set<Form> forms = new HashSet<>();
        for (String formName : formNames) {
            Form form = new Form(pathForms.resolve(formName + ".xml"));
            form.parse();
            forms.add(form);
        }

        return forms;
    }

    public Set<MetadataObject> readOwners() throws IOException {
        Set<MetadataObject> owners = new HashSet<>();

        List<String> refOwners = readChild(nodeRoot() + "/Properties/Owners/Item");
        Conf conf = this.metadataObject.getConf();
        for (String refOwner : refOwners) {
            MetadataObject object = conf.getObjectByRef(refOwner);
            if (object == null) {
                throw new IOException("Object not found");
            }
            owners.add(object);
        }

        return owners;
    }

    public void fillCommonField() {
        String nodeObject = nodeRoot();
        metadataObject.setUuid(readUUID(nodeObject + "/@uuid"));
        metadataObject.setName(read(nodeObject+ "/Properties/Name"));
        metadataObject.setSynonym(read(nodeObject + "/Properties/Synonym/item/content"));
        metadataObject.setComment(read(nodeObject+ "/Properties/Comment"));
    }
}
