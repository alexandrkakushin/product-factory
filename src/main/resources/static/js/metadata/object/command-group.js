function addProperties_CommandGroup(object) {
    // todo: вынести в типы данных
    let category = null;
    if (object.category === 'NAVIGATION_PANEL') {
        category = 'Панель навигации';
    } else if (object.category === 'FORM_NAVIGATION_PANEL') {
        category = 'Панель навигации формы';
    } else if (object.category === 'ACTIONS_PANEL') {
        category = 'Панель действий';
    } else if (object.category === 'FORM_COMMAND_BAR') {
        category = 'Командная панель формы';
    }
    addLine('Категория', category);

    // todo: вынести в типы данных
    let representation = null;
    if (object.representation === 'PICTURE_AND_TEXT') {
        representation = 'Картинка и текст';
    } else if (object.representation === 'PICTURE') {
        representation = 'Картинка';
    } else if (object.representation === 'TEXT') {
        representation = 'Текст';
    } else if (object.representation === 'AUTO') {
        representation = 'Авто';
    }
    addLine('Отображение', representation);

    addLine('Подсказка', object.toolTip);
}
