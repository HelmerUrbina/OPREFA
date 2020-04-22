<%-- 
    Document   : Modulos
    Created on : 18/03/2020, 08:28:01 PM
    Author     : H-URBINA-M
--%>

<!DOCTYPE html>
<script type="text/javascript">
    $(document).ready(function () {
        var theme = getTheme();
        $("#div_Titulo").jqxExpander({theme: theme, width: '100%'});
        fn_CargarBusqueda();
    });
    function fn_CargarBusqueda() {
        $("#div_GrillaPrincipal").remove();
        $("#div_VentanaPrincipal").remove();
        $("#div_ContextMenu").remove();
        var $contenidoAjax = $('#div_Detalle').html('<img src="../Imagenes/Fondos/cargando.gif">');
        $.ajax({
            type: "GET",
            url: "../Modulos",
            data: {mode: "G"},
            success: function (data) {
                $contenidoAjax.html(data);
            }
        });
    }
</script>
<div style="border: none;" id='div_Titulo'>
    <div class="jqx-hideborder">LISTADOS DE MÓDULOS DEL SISTEMA</div>
    <div>
        <div id="div_Detalle" class="maincen"></div>
    </div>
</div>