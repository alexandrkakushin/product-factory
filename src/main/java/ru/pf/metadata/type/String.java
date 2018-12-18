package ru.pf.metadata.type;

/**
 * @author a.kakushin
 */
public class String implements IType {

    private int length;
    private AllowedLength allowedLength;

    enum AllowedLength {
        FIXED,
        VARIABLE
    }
}
