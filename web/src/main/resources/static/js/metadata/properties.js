function addLine(name, value) {
    $('#properties > tbody:last-child')
        .append($('<tr>')
            .append($('<td>').text(name))
            .append($('<td>').text(value))
        );
}

function addBooleanLine(name, id, value) {
    $('#properties > tbody:last-child')
        .append($('<tr>')
            .append($('<td>').text(name))
            .append($('<td>')
                .append(
                    addCheckbox(id, value)
                )
            )
        );
}

function addProperties() {
    if (object.xmlName === 'CommandGroup') {
        addProperties_CommandGroup(object);

    } else if (object.xmlName === 'CommonModule') {
        addProperties_CommonModule(object);

    } else if (object.xmlName === 'Role') {
        addProperties_Role(object);

    } else if (object.xmlName === 'Catalog') {
        addProperties_Catalog(object);

    } else if (object.xmlName == 'Enum') {
        addProperties_Enum(object);
    }
}