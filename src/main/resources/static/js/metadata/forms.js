function addForm(form) {
    $('#forms > tbody:last-child')
        .append($('<tr>')
            .append($('<td>').text(form.name))
            .append($('<td>').text(form.uuid))                        
            .append($('<td>').text(form.synonym))
            .append($('<td>').text(form.comment))
        );                
}

function addForms() {                
    if (fields.forms) {
        for (let f = 0; f < object[fields.forms].length; f++) {
            addForm(object.forms[f]);    
        }            
    } else {
        $('#pills-forms-tab').css("visibility","hidden");
    }
}