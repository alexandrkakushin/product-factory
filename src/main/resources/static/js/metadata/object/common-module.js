function addProperties_CommonModule (object) {
    addBooleanLine('Глобальный', 'global', object.global);
    addBooleanLine('Клиент (управляемое приложение)', 'clientManagedApplication', object.clientManagedApplication);
    addBooleanLine('Клиент (обычное приложение)', 'clientOrdinaryApplication', object.clientOrdinaryApplication);
    addBooleanLine('Сервер', 'server', object.server);
    addBooleanLine('Внешнее соединение', 'externalConnection', object.externalConnection);
    addBooleanLine('Вызов сервера', 'serverCall', object.serverCall);
    addBooleanLine('Привелегированный', 'privileged', object.privileged);

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