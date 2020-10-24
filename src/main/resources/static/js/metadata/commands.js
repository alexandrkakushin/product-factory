function addCommand(element) {
    $('#commands > tbody:last-child')
        .append($('<tr>')
            .append($('<td>').text(element.uuid))
            .append($('<td>').text(element.name))
            .append($('<td>').text(element.synonym))
        );                
}

function addCommands() {
    if (fields.commands) {
        for (let f = 0; f < object[fields.commands].length; f++) {
            addCommand(object.commands[f]);
        }            
    }
}