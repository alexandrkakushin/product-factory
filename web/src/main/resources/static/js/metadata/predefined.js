function addPredefinedElement(element) {
    $('#predefined > tbody:last-child')
        .append($('<tr>')
            .append($('<td>').text(element.name))
            .append($('<td>').text(element.code))
            .append($('<td>').text(element.description))
            .append($('<td>').addClass("text-center")
                .append(addCheckbox("checkbox_isFolder", element.folder)))
            .append($('<td>').text(element.id))
        );
}

function addPredefined() {
    if (fields.predefined) {
        for (let f = 0; f < object[fields.predefined].length; f++) {
            addPredefinedElement(object.predefined[f]);
        }            
    }
}