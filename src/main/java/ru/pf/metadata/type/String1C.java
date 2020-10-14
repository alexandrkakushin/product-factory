package ru.pf.metadata.type;

import lombok.Data;

/**
 * @author a.kakushin
 */
@Data
public class String1C implements IType {

    private int length;
    private AllowedLength allowedLength;

    enum AllowedLength {
        FIXED,
        VARIABLE
    }
}
