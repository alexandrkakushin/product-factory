function addProperties_Catalog (object) {

    // Иерархия
    addBooleanLine('Иерархический', 'hierarchical', object.hierarchical);
    if (object.hierarchical) {
        let hierarchyType = null;
        if (object.hierarchyType === 'HIERARCHY_OF_ITEMS') {
            hierarchyType = 'Иерархия элементов';
        } else if (object.hierarchyType === 'HIERARCHY_FOLDERS_AND_ITEMS') {
            hierarchyType = 'Иерархия групп и элементов';
        }
        addLine('Вид иерархии', hierarchyType);
        addBooleanLine('Размещать группы сверху', 'foldersOnTop', object.foldersOnTop);
        addBooleanLine('Ограничение количества уровней иерархии', 'limitLevelCount', object.limitLevelCount);
        if (object.limitLevelCount) {
            addLine('Количество уровней иерархии', object.levelCount);
        }
    }

    // Владельцы
    if (fields.owners) {
        if (object[fields.owners].length > 0) {
            let subordinationUse = null;
            if (object.subordinationUse === 'TO_ITEMS') {
                subordinationUse = 'Элементам';
            } else if (object.subordinationUse === 'TO_FOLDERS') {
                subordinationUse = 'Группам';
            } else if (object.subordinationUse === 'TO_FOLDERS_AND_ITEMS'){
                subordinationUse = 'Группам и элементам';
            }
            addLine('Использование подчинения', subordinationUse);
        }
    }

    // Данные
    addLine('Длина кода', object.codeLength);
    addLine('Длина наименования', object.descriptionLength);

    let codeType = null;
    if (object.codeType === 'STRING') {
        codeType = 'Строка';
    } else if (object.codeType === 'NUMBER') {
        codeType = 'Число';
    }
    addLine('Тип кода', codeType);

    let defaultPresentation = null;
    if (object.defaultPresentation === 'AS_DESCRIPTION') {
        defaultPresentation = 'В виде наименования';
    } else if (object.defaultPresentation = 'AS_CODE') {
        defaultPresentation = 'В виде кода';
    }
    addLine('Основное представление', defaultPresentation);

    // Нумерация
    addBooleanLine('Контроль уникальности', 'checkUnique', object.checkUnique);
    addBooleanLine('Автонумерация', 'autoNumbering', object.autoNumbering);
    let codeSeries = null;
    if (object.codeSeries === 'WITHIN_OWNER_SUBORDINATION') {
        codeSeries = 'В пределах подчинения владельцу';
    } else if (object.codeSeries === 'WITHIN_SUBORDINATION') {
        codeSeries = 'В пределах подчинения';
    } else if (object.codeSeries === 'WHOLE_CATALOG') {
        codeSeries = 'Во всем справочнике';
    }
    addLine('Серии кодов', codeSeries);

    // Поле ввода
    addBooleanLine('Быстрый выбор', object.quickChoice);
    let choiceMode = null;
    if (object.choiceMode === 'BOTH_WAYS') {
        choiceMode = 'Обоими способами';
    } else if (object.choiceMode === 'FROM_FORM') {
        choiceMode = 'Из формы';
    } else if (object.choiceMode === 'QUICK_CHOICE') {
        choiceMode = 'Быстрый выбор';
    }
    addLine('Способ выбора', choiceMode);
}