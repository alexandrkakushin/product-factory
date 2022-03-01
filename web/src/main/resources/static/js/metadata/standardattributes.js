function addStandardAttribute(element) {
    $('#standardAttributes> tbody:last-child')
        .append($('<tr>')
            .append($('<td>').text(element.russianName))
        );                
}

function addStandardAttributes() {
    if (fields.standardAttributes) {
        for (let f = 0; f < object[fields.standardAttributes].length; f++) {
            addStandardAttribute(object.standardAttributes[f]);
        }            
    }
}