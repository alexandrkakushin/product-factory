function addCheckbox(id, value) {

    return $('<div>').addClass("form-check")
        .append($('<input>')
            .addClass("form-check-input")
            .attr("type", "checkbox")
            .attr("id", id)
            .attr("checked", value)
            .on('click', function() {return false})
    )
}