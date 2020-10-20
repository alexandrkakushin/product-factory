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
}