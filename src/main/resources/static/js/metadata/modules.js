
function addMethod(nameModule, name, isFunction, isEmpty, isExport) {
    $('#' + nameModule + '_methods > tbody:last-child')
        .append($('<tr>')
            .append($('<td>')
                .append($('<a>').attr("href", "").text(name)))

            .append($('<td>').addClass("text-center")
                .append(addCheckbox("checkbox_isFunction", isFunction)))                        

            .append($('<td>').addClass("text-center")
                .append(addCheckbox("checkbox_isEmpty", isEmpty)))                        

            .append($('<td>').addClass("text-center")
                .append(addCheckbox("checkbox_isExport", isExport)))                        
            
            .append($('<td>').addClass("text-center")
                .append($('<button>').addClass("btn btn-link")
                    .attr("module", nameModule)
                    .attr("method", name)
                    .attr('data-toggle', 'modal')
                    .attr('data-target', '#detailModal')                                
                    .on('click', function () { btnMethodOnClick(event); })
                    .text("...")))
        );
}

function addModules() {                
    let modules = ['managerModule', 'objectModule', 'recordSetModule', 'plainModule', 'valueManagerModule', 'commandModule'];
    for (let m = 0; m < modules.length; m++) {
        if (fields[modules[m]]) {
            let nameModule = fields[modules[m]];                        
            if (object[nameModule] == null) {
                continue;
            }
            for (let i = 0; i < object[nameModule].methods.length; i++) {
                let method = object[nameModule].methods[i];
                addMethod(modules[m], method.name, method.function, method.empty, method.export)
            }
        } else {
            $('#pills-' + modules[m] + '-tab').hide();
        }
    }
}

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

function btnMethodOnClick(event) {
    let attrMethod = event.target.attributes.getNamedItem('method').value;
    let attrModule = event.target.attributes.getNamedItem('module').value;

    $('#label-detail').html(attrMethod);

    let modules = ['managerModule', 'objectModule', 'recordSetModule', 'plainModule', 'valueManagerModule', 'commandModule'];

    // Получение имени поля
    let fieldObject = "";
    for (let m = 0; m < modules.length; m++) {
        if (modules[m] == attrModule) {
            fieldObject = fields[modules[m]];
            break;
        }
    }

    let textMethod = "";
    for (let i = 0; i < object[fieldObject].methods.length; i++) {
        let method = object[fieldObject].methods[i];
        if (method.name === attrMethod) {
            textMethod = method.text;
            break;
        }
    }
    $('#body_code').text(textMethod);

    // todo
    document.querySelectorAll('pre code').forEach((block) => {
        hljs.highlightBlock(block);
    });
}

