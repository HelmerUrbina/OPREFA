<%-- 
    Document   : Documentos
    Created on : 31/03/2020, 01:25:10 PM
    Author     : H-URBINA-M
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $(document).ready(function () {
        var theme = getTheme();
        $("#div_Titulo").jqxExpander({theme: theme, width: '100%'});
        $("#cbo_Periodo").jqxComboBox({theme: theme, autoOpen: true, promptText: "Seleccione", width: 100, dropDownWidth: 150, height: 20});
        $("#cbo_Tipo").jqxComboBox({theme: theme, autoOpen: true, promptText: "Seleccione", width: 200, dropDownWidth: 350, height: 20});
        $("#cbo_Mes").jqxComboBox({theme: theme, autoOpen: true, promptText: "Seleccione", width: 100, dropDownWidth: 220, height: 20});
        $("#div_diaBus").jqxNumberInput({width: 50, height: 20, max: 31, digits: 2, decimalDigits: 0});
        var fecha = new Date();
        $("#cbo_Periodo").jqxComboBox('selectItem', fecha.getFullYear());
        $("#cbo_Mes").jqxComboBox('selectIndex', fecha.getMonth());
        $("#div_diaBus").val(fecha.getDate());
        $('#cbo_Periodo').on('change', function () {
            fn_CargarBusqueda();
        });
        $('#cbo_Tipo').on('change', function () {
            fn_CargarBusqueda();
        });
        $('#cbo_Mes').on('change', function () {
            fn_CargarBusqueda();
        });
        fn_CargarBusqueda();
    });
    function fn_CargarBusqueda() {
        var msg = "";
        msg += fn_validaCombos('#cbo_Periodo', "Seleccione el Periodo.");
        msg += fn_validaCombos('#cbo_Tipo', "Seleccione el Tipo.");
        msg += fn_validaCombos('#cbo_Mes', "Seleccione el Mes.");
        if (msg === "") {
            var periodo = $("#cbo_Periodo").val();
            var tipo = $("#cbo_Tipo").val();
            var mes = $("#cbo_Mes").val();
            var diaBus = $("#div_diaBus").val();
            $("#div_GrillaPrincipal").remove();
            $("#div_VentanaPrincipal").remove();
            $("#div_DetalleAnulacion").remove();
            $("#div_ContextMenu").remove();
            var $contenidoAjax = $('#div_Detalle').html('<img src="../Imagenes/Fondos/cargando.gif">');
            $.ajax({
                type: "GET",
                url: "../MesaPartes",
                data: {mode: "G", periodo: periodo, mes: mes, tipo: tipo, codigo: diaBus},
                success: function (data) {
                    $contenidoAjax.html(data);
                }
            });
        } else {
            $.alert({
                theme: 'material',
                title: 'Aviso del Sistema',
                content: msg,
                type: 'blue',
                animation: 'zoom',
                closeAnimation: 'zoom',
                typeAnimated: true,
                boxWidth: '50%'
            });
        }
    }
</script>
<div style="border: none;" id='div_Titulo'>
    <div class="jqx-hideborder">REGISTRO DE DOCUMENTOS</div>
    <div>
        <div id="div_Cabecera">
            <table class="navy">
                <tbody>
                    <tr>
                        <td class="Titulo">Periodo : </td>
                        <td>
                            <select id="cbo_Periodo" name="cbo_Periodo">
                                <c:forEach var="a" items="${objPeriodos}">
                                    <option value="${a.codigo}">${a.codigo}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td class="Titulo">Tipo : </td>
                        <td>
                            <select id="cbo_Tipo" name="cbo_Tipo">
                                <option value="E">INGRESO DE DOCUMENTO</option>
                                <option value="S">SALIDA DE DOCUMENTO</option>
                            </select>
                        </td>
                        <td class="Titulo">Mes : </td>
                        <td>
                            <select id="cbo_Mes" name="cbo_Mes">
                                <c:forEach var="c" items="${objMeses}">
                                    <option value="${c.codigo}">${c.descripcion}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td class="Titulo">D&iacute;a : </td>
                        <td>
                            <div id="div_diaBus"></div>
                        </td>
                        <td><a href="javascript: fn_CargarBusqueda();" ><img src="../Imagenes/Botones/refresh42.gif" alt="Buscar Datos" name="imgrefresh" width="30" height="28" border="0" id="imgrefresh"></a></td>
                        <td><a href="../Login/Principal.jsp" target="_parent"><img src="../Imagenes/Botones/exit42.gif" alt="Salir de pantalla" name="imgexit" width="30" height="28"  border="0" id="imgexit" /></a></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div id="div_Detalle" class="maincen"></div>
    </div>
</div>