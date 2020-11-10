
function addTabularSection(element, isActive) {

    // Элемент навигации
    let item = $('<a>')
        .addClass("list-group-item")
        .addClass("list-group-item-action")
        .attr("data-toggle", "list")
        .attr("href", "#content_" + element.uuid)
        .attr("role", "tab")
        .text(element.name);

    if (isActive) {
        item.addClass("active");
    }

    $('#listTabularSections').append(item);

    // Реквизиты ТЧ
    let content = $('<div>')
        .addClass("tab-pane")
        .attr("role", "tabpanel")
        .attr("id", "content_" + element.uuid);

    if (isActive) {
        content.addClass("active");
    }

    content
        .append($('<table>')
            .addClass("table table-hover")
            .attr("id", element.uuid)
            .append($('<thead>')
                .append($('<tr>')
                    .append($('<th>').addClass("w-25").text("UUID"))
                    .append($('<th>').addClass("w-50").text("Имя"))
                    .append($('<th>').text("Синоним"))
                )
            )
            .append($('<tbody>'))
        );

    $('#contentTabularSections').append(content);

    for (let f = 0; f < element.attributes.length; f++) {
       attribute = element.attributes[f];
       $('#' + element.uuid + '> tbody:last-child')
        .append($('<tr>')
            .append($('<td>').text(attribute.uuid))
            .append($('<td>').text(attribute.name))
            .append($('<td>').text(attribute.synonym))
        );
    }
}

function addTabularSections() {
    if (fields.tabularSections) {
        for (let f = 0; f < object[fields.tabularSections].length; f++) {
            addTabularSection(object[fields.tabularSections][f], f == 0);
        }
    }
}