<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="recourses/jquery.min.js"></script>
    <script src="recourses/bootstrap.min.js"></script>
    <script src="recourses/datepicher.min.js"></script>
    <link href="recourses/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="recourses/application.css" rel="stylesheet" type="text/css">
    <link href="recourses/bootstrap-dialog.min.css" rel="stylesheet" type="text/css">
    <script src="recourses/bootstrap-dialog.min.js"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-lg-6">
            <h1>Сервис «Эксперты»</h1>
            <br>
            <form name="expert">
                <div class="form-group"><input type="hidden" class="form-control" name="id" value="${table1.id}"></div>
                <div class="form-group"><input type="number" class="form-control" name="x" value="${table1.x}"></div>
                <div class="form-group"><input type="number" class="form-control" name="y" value="${table1.y}"></div>
                <%--<div class="form-group"><input type="number" class="form-control" name="z" value="${table1.z}"></div>--%>
                <div class="form-group modal-footer"><input id="buttonxy" onclick="messageExpert()"
                                                            class="btn btn-primary"
                                                            value="Сохранить">
                </div>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-6">
            <h1>Сервис «Черный список»</h1>
            <br>
            <form name="blacklist">
                <%--<div class="form-group"><input type="text" class="form-control" name="phonenumber" placeholder="phonenumber"></div>  --%>
                <div class="form-group"><input id="phonenumber" type="text" class="form-control" name="phonenumber"
                                               placeholder="0_ _ _ _ _ _ _ _" maxlength="10"></div>
                <div class="form-group"><input id="dateFrom" class="form-control" name="ban_Start_Date"
                                               placeholder="ban_Start_Date (dd.MM.yyyy)" type="text"></div>
                <div class="form-group"><input id="dateTo" class="form-control" name="ban_Stop_Date"
                                               placeholder="ban_Start_Date (dd.MM.yyyy)" type="text"></div>
                <table>
                    <tr>
                        <td>
                            <div align="left">banActive "YES"&nbsp;&nbsp;&nbsp;</div>
                        </td>
                        <td>
                            <div class="form-group"><input type="radio" name="banActive" value="TRUE"></div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div align="left">banActive "NO"&nbsp;&nbsp;&nbsp;</div>
                        </td>
                        <td>
                            <div class="form-group"><input type="radio" name="banActive" value="FALSE"></div>
                        </td>
                    </tr>
                </table>
                <div class="form-group modal-footer"><input class="btn btn-primary" onclick="messagePhone()"
                                                            value="Применить">
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    var input = document.getElementById("phonenumber");
    input.onkeypress = function (e) {
        e = e || event;

        if (e.ctrlKey || e.altKey || e.metaKey) return;

        var chr = getChar(e);

        // с null надо осторожно в неравенствах,
        // т.к. например null >= '0' => true
        // на всякий случай лучше вынести проверку chr == null отдельно
        if (chr == null) return;

        if (chr < '0' || chr > '9') {
            return false;
        }
    }

    function getChar(event) {
        if (event.which == null) {
            if (event.keyCode < 32) return null;
            return String.fromCharCode(event.keyCode) // IE
        }

        if (event.which != 0 && event.charCode != 0) {
            if (event.which < 32) return null;
            return String.fromCharCode(event.which) // остальные
        }
        return null; // специальная клавиша
    }
    var windowAlertDanger = function (message) {
        BootstrapDialog.show({
            size: BootstrapDialog.SIZE_SMALL,
            type: BootstrapDialog.TYPE_DANGER,
            draggable: true,
            title: 'Ошибка',
            message: message,
            buttons: [{
                label: 'Закрыть',
                action: function (dialog) {
//                    location.reload(true);
                    dialog.close();
                }
            }]
        });
    };
    var windowAlertSuccess = function (message) {
        BootstrapDialog.show({
            size: BootstrapDialog.SIZE_SMALL,
            type: BootstrapDialog.TYPE_SUCCESS,
            draggable: true,
            title: 'Уведомление',
            message: message,
            buttons: [{
                label: 'Закрыть',
                action: function (dialog) {
                    location.reload(true);
                    dialog.close();
                }
            }]
        });
    };
    // XY
    var messageExpert = function () {
        var formData = new FormData(document.forms.expert);
        // отослать
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/Task/submitXY");
        xhr.send(formData);
        xhr.onreadystatechange = function () { // (3)
            if (xhr.readyState != 4) return;
            if (xhr.status != 302 && xhr.status != 200) {
                var message = "Ошибка сохранения. Код возврата: " + xhr.status
                windowAlertDanger(message);
            } else {
                var message = "Информация добавлена успешно"
                windowAlertSuccess(message);

            }
        }
    }

    //blackList
    var messagePhone = function () {
        if (input.value.length == 10) {
            var formData = new FormData(document.forms.blacklist);
            // отослать
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "/Task/submitPhone");
            xhr.send(formData);
            xhr.onreadystatechange = function () { // (3)
                if (xhr.readyState != 4) return;
                if (xhr.status != 302 && xhr.status != 200) {
                    var message = "Ошибка сохранения. Код возврата: " + xhr.status
                    windowAlertDanger(message)

                } else {
                    var message = "Информация добавлена успешно"
                    windowAlertSuccess(message);
                }
            }
        } else {
            windowAlertDanger('Некорректный ввод. Введите 10-значный номер телефона, начиная с 0')
        }
    }

</script>

<script>
    $('#dateFrom').datepicker({
        autoClose: true,
        dateFormat: 'dd.mm.yyyy',
        clearButton: true,
        todayButton: new Date()
    });
    $('#dateTo').datepicker({
        autoClose: true,
        dateFormat: 'dd.mm.yyyy',
        clearButton: true,
        todayButton: new Date()
    });
</script>

</body>
</html>