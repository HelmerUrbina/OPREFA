<%-- 
    Document   : ConsultaPlanilla
    Created on : 12/07/2020, 11:38:53 PM
    Author     : H-URBINA-M
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $(document).ready(function () {
        var theme = getTheme();
        $("#div_Titulo").jqxExpander({theme: theme, width: '100%'});
        $("#txt_Busqueda").jqxInput({width: 350, height: 20, placeHolder: "Ingrese Apellidos o Documento"});
    });
    function fn_CargarBusqueda() {
        var busqueda = $("#txt_Busqueda").val();
        $("#div_VentanaPrincipal").remove();
        $("#div_ContextMenu").remove();
        var $contenidoAjax = $('#div_Detalle').html('<img src="../Imagenes/Fondos/cargando.gif">');
        $.ajax({
            type: "GET",
            url: "../ConsultaPlanilla",
            data: {mode: "G", busqueda: busqueda},
            success: function (data) {
                $contenidoAjax.html(data);
            }
        });
    }
</script>
<div style="border: none;" id='div_Titulo'>
    <div class="jqx-hideborder">CONSULTA DE DEVENGADOS Y REINTEGROS</div>
    <div>
        <div id="div_Cabecera">
            <table class="navy">
                <tbody>
                    <tr>
                        <td class="Titulo">Buscar por : </td>
                        <td>
                            <input type="text" name="txt_Busqueda" id="txt_Busqueda" style="text-transform: uppercase;" autocomplete="off"/>
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
