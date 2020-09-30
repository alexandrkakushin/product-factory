function addOwner(owner) {
    $('#owners > tbody:last-child')
        .append($('<tr>')
            .append($('<td>')
                .append($('<a>').attr("href", `/development/conf/item/${projectId}/object/${owner.uuid}`).text(owner.name)))
            .append($('<td>').text(owner.uuid))
        );                
}

function addOwners() {
    if (fields.owners) {
        for (let f = 0; f < object[fields.owners].length; f++) {
            addOwner(object.owners[f]);
        }            
    }
}