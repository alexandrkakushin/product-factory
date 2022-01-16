function addProperties_Role(object) {

    // Свойства прав
    addBooleanLine('Устанавливать права для новых объектов',
        'setForNewObjects', object.setForNewObjects);

    addBooleanLine('Устанавливать права для реквизитов и табличных частей по умолчанию',
        'setForAttributesByDefault', object.setForAttributesByDefault);

    addBooleanLine('Независимые права подчиненных объектов',
        'independentRightsOfChildObjects', object.independentRightsOfChildObjects);
}

function addMetadata() {

    for (let i = 0; i < object.rightObjects.length; i++) {
        let element = object.rightObjects[i];

        let td = $('<td>');
        for (let r = 0; r < element.rights.length; r++) {
            right = element.rights[r];

            td.append(addCheckboxWithLabel("right_" + i, right.name, right.value));
        }

        $('#rightObjects > tbody:last-child')
            .append($('<tr>')
                .append($('<td>').text(element.metadataObject))
                .append(td)
            );
    }
}