package ru.pf.metadata.object.common;

import lombok.Data;
import ru.pf.metadata.Module;
import ru.pf.metadata.object.AbstractObject;
import ru.pf.metadata.reader.ModuleReader;
import ru.pf.metadata.reader.ObjectReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author a.kakushin
 */
@Data
public class CommonModule extends AbstractObject<CommonModule> {

	private boolean global;
    private boolean clientManagedApplication;
	private boolean server;
    private boolean externalConnection;
	private boolean clientOrdinaryApplication;
	private boolean	serverCall;
	private boolean privileged;
	private ReturnValuesReuse returnValuesReuse;

    private Module module;

    public CommonModule(Path path) {
        super(path);
    }

    @Override
    public void parse() throws IOException {

        Path fileXml = super.getFile().getParent().resolve(super.getFile());
        if (Files.exists(fileXml)) {
            ObjectReader objReader = new ObjectReader(fileXml);
            objReader.fillCommonField(this);

            String nodeProperties = "/MetaDataObject/" + getMetadataName() + "/Properties/";
            this.global = objReader.readBool(nodeProperties + "Global");
            this.clientManagedApplication  = objReader.readBool(nodeProperties + "ClientManagedApplication");
            this.server                    = objReader.readBool(nodeProperties + "Server");
            this.externalConnection        = objReader.readBool(nodeProperties + "ExternalConnection");
            this.clientOrdinaryApplication = objReader.readBool(nodeProperties + "ClientOrdinaryApplication");
            this.serverCall                = objReader.readBool(nodeProperties + "ServerCall");
            this.privileged                = objReader.readBool(nodeProperties + "Privileged");
            this.returnValuesReuse         = ReturnValuesReuse.valueByName(
                    objReader.read(nodeProperties + "ReturnValuesReuse"));
        }

        // object module
        Path fileModule = super.getFile()
                .getParent()
                .resolve(this.getShortName(super.getFile()))
                .resolve("Ext")
                .resolve("Module.bsl");

        if (Files.exists(fileModule)) {
            this.module = ModuleReader.read(fileModule);
        }
    }

    public enum ReturnValuesReuse {
        DONT_USE,
        DURING_REQUEST,
        DURING_SESSION;

        public static ReturnValuesReuse valueByName(String value) {
            if (value.equalsIgnoreCase("DontUse")) {
                return DONT_USE;
            } else if (value.equalsIgnoreCase("DuringRequest")) {
                return DURING_REQUEST;
            } else if (value.equalsIgnoreCase("DuringSession")) {
                return DURING_SESSION;
            }
            return null;
        }
    }

}
