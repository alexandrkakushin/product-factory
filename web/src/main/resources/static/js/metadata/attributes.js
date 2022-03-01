function addAttribute(element) {
    $('#attributes> tbody:last-child')
        .append($('<tr>')
            .append($('<td>').text(element.uuid))
            .append($('<td>').text(element.name))
            .append($('<td>').text(element.synonym))
        );                
}

function addAttributes() {
    if (fields.attributes) {
        for (let f = 0; f < object[fields.attributes].length; f++) {
            addAttribute(object.attributes[f]);
        }            
    }
}