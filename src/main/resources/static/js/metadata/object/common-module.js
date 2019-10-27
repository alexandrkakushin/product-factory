function addProperties_CommonModule (object) {
    addLine('Глобальный', object.global);
    addLine('Клиент (управляемое приложение)', object.clientManagedApplication);
    addLine('Клиент (обычное приложение)', object.clientOrdinaryApplication);
    addLine('Сервер', object.server);
    addLine('Внешнее соединение', object.externalConnection);
    addLine('Вызов сервера', object.serverCall);
    addLine('Привелегированный', object.privileged);

    let returnValuesReuse = null;
    if (object.returnValuesReuse === 'DONT_USE') {
        returnValuesReuse = 'Не использовать';

    } else if (object.returnValuesReuse === 'DURING_REQUEST') {
        returnValuesReuse = 'На время вызова';

    } else if (object.returnValuesReuse === 'DURING_SESSION') {
        returnValuesReuse = 'На время сеанса';
    }

    addLine('Повторное использование', returnValuesReuse);
}