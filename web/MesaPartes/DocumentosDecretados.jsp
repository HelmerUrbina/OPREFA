<%-- 
    Document   : DocumentosDecretados
    Created on : 05/04/2020, 01:11:01 AM
    Author     : H-URBINA-M
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $(document).ready(function () {
        var theme = getTheme();
        $("#div_Titulo").jqxExpander({theme: theme, width: '100%'});
        $("#cbo_Periodo").jqxComboBox({theme: theme, autoOpen: true, promptText: "Seleccione", width: 100, dropDownWidth: 150, height: 20});
        $("#txt_Usuario").jqxInput({width: 350, height: 20, disabled: true});
        var fecha = new Date();
        $("#cbo_Periodo").jqxComboBox('selectItem', fecha.getFullYear());
        $("#txt_Usuario").val('${objUsuario.paterno}' + " " + '${objUsuario.materno}' + ", " + '${objUsuario.nombres}');
        fn_CargarBusqueda();
    });
    function fn_CargarBusqueda() {
        var periodo = $("#cbo_Periodo").val();
        $("#div_GrillaPrincipal").remove();
        $("#div_VentanaPrincipal").remove();
        $("#div_VentanaDetalle").remove();
        $("#div_ContextMenu").remove();
        var $contenidoAjax = $('#div_Detalle').html('<img src="../Imagenes/Fondos/cargando.gif">');
        $.ajax({
            type: "GET",
            url: "../Decretos",
            data: {mode: "L", periodo: periodo},
            success: function (data) {
                $contenidoAjax.html(data);
            }
        });
    }
</script>
<div  id='div_Titulo' style="border: none;">
    <div class="jqx-hideborder">CONSULTA DE DOCUMENTOS DECRETADOS</div>
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
                        <td class="Titulo">Usuario : </td>
                        <td><input type="text" id="txt_Usuario" name="txt_Usuario" style='text-transform:uppercase;'/></td>
                        <td><a href="javascript: fn_CargarBusqueda();" ><img src="../Imagenes/Botones/refresh42.gif" alt="Buscar Datos" name="imgrefresh" width="30" height="28" border="0" id="imgrefresh"></a></td>
                        <td><a href="../Login/Principal.jsp" target="_parent"><img src="../Imagenes/Botones/exit42.gif" alt="Salir de pantalla" name="imgexit" width="30" height="28"  border="0" id="imgexit" /></a></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div id="div_Detalle" class="maincen"></div>
    </div>
</div>