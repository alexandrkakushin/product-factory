function addProperties_Catalog (object) {

    // Свойства иерархии
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
}