function sortTable() {
    var table, rows, switching, i, x, y, shouldSwitch;
    table = document.getElementById("myTable");
    switching = true;
    /* Сделайте цикл, который будет продолжаться до тех пор, пока
    никакого переключения не было сделано: */
    while (switching) {
        // Начните с того: переключение не выполняется:
        switching = false;
        rows = table.rows;
        /* Цикл через все строки таблицы (за исключением
        во-первых, который содержит заголовки таблиц): */
        for (i = 1; i < (rows.length - 1); i++) {
            // Начните с того, что не должно быть никакого переключения:
            shouldSwitch = false;
            /* Получите два элемента, которые вы хотите сравнить,
            один из текущей строки и один из следующей: */
            x = rows[i].getElementsByTagName("TD")[0];
            y = rows[i + 1].getElementsByTagName("TD")[0];
            // Проверьте, должны ли две строки поменяться местами:
            if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                // Если это так, отметьте как переключатель и разорвать цикл:
                shouldSwitch = true;
                break;
            }
        }
        if (shouldSwitch) {
            /* Если переключатель был отмечен, сделайте переключатель
            и отметьте, что переключатель был сделан: */
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
        }
    }
}