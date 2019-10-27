package ru.pf.metadata.object.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.Module;
import ru.pf.metadata.annotation.PlainModule;
import ru.pf.metadata.object.AbstractMetadataObject;
import ru.pf.metadata.reader.ModuleReader;
import ru.pf.metadata.reader.ObjectReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CommonModule extends AbstractMetadataObject {

    private boolean global;
    private boolean clientManagedApplication;
    private boolean server;
    private boolean externalConnection;
    private boolean clientOrdinaryApplication;
    private boolean serverCall;
    private boolean privileged;
    private ReturnValuesReuse returnValuesReuse;

    @PlainModule
    private Module module;

    public CommonModule(Path path) {
        super(path);
    }

    public boolean isClientManagedApplication() {
        return clientManagedApplication;
    }

    public boolean isServer() {
        return server;
    }

    public boolean isGlobal() {
        return global;
    }

    public boolean isServerCall() {
        return serverCall;
    }

    @Override
    public String getListPresentation() {
        return "Общие модули";
    }

    @Override
    public ObjectReader parse() throws IOException {

        ObjectReader objReader = super.parse();

        String nodeProperties = "/MetaDataObject/" + getXmlName() + "/Properties/";
        this.global = objReader.readBool(nodeProperties + "Global");
        this.clientManagedApplication = objReader.readBool(nodeProperties + "ClientManagedApplication");
        this.server = objReader.readBool(nodeProperties + "Server");
        this.externalConnection = objReader.readBool(nodeProperties + "ExternalConnection");
        this.clientOrdinaryApplication = objReader.readBool(nodeProperties + "ClientOrdinaryApplication");
        this.serverCall = objReader.readBool(nodeProperties + "ServerCall");
        this.privileged = objReader.readBool(nodeProperties + "Privileged");
        this.returnValuesReuse = ReturnValuesReuse.valueByName(objReader.read(nodeProperties + "ReturnValuesReuse"));

        return objReader;
    }

    public enum ReturnValuesReuse {
        DONT_USE, DURING_REQUEST, DURING_SESSION;

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