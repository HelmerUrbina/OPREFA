<%-- 
    Document   : index.jsp
    Created on : 18/03/2016, 12:11:54 PM
    Author     : H-URBINA-M
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="WEB-INF/jspf/browser.jspf" %>
<%    session.removeAttribute("objUsuario" + request.getSession().getId());
    session.removeAttribute("ID");
    session.removeAttribute("autorizacion");
    session.invalidate();
%>
<!DOCTYPE html> 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
        <meta http-equiv="Expires" content="0"/>
        <meta http-equiv="Last-Modified" content="0"/>
        <meta http-equiv="Cache-Control" content="no-cache, mustrevalidate"/>
        <meta http-equiv="Pragma" content="no-cache"/>
        <meta http-equiv="refresh" content="300; url=index.jsp">
        <title>.:: OPREFA - Oficina Previsional de las Fuerzas Armadas ::.</title>
        <link type="text/css" rel="stylesheet" href="css/styles/jqx.base.css">
        <link type="text/css" rel="stylesheet" href="css/login.css">
        <link type="text/css" rel="stylesheet" href="css/scaf.css">
        <link type="text/css" rel="stylesheet" href="css/bundled.css">
        <link type="text/css" rel="stylesheet" href="css/jquery-confirm.css"> 
        <script type="text/javascript" src="javascript/theme.js"></script>
        <script type="text/javascript" src="javascript/bundled.js"></script>
        <script type="text/javascript" src="javascript/jquery.js"></script>
        <script type="text/javascript" src="javascript/jquery-confirm.js"></script>
        <script type="text/javascript" src="javascript/jquery.hoverplay.js"></script>
        <script type="text/javascript" src="javascript/jqwidgets/jqxcore.js"></script>
        <script type="text/javascript" src="javascript/jqwidgets/jqxinput.js"></script>
        <script type="text/javascript" src="javascript/jqwidgets/jqxpasswordinput.js"></script>
        <script type="text/javascript" src="javascript/jqwidgets/jqxtooltip.js"></script>
        <script type="text/javascript" src="javascript/jqwidgets/jqxbuttons.js"></script>
        <script type="text/javascript" src="javascript/jqwidgets/jqxvalidator.js"></script>
        <script type="text/javascript" src="javascript/jqwidgets/jqxwindow.js"></script>
        <script type="text/javascript" src="javascript/jqwidgets/jqxcombobox.js"></script>
        <script type="text/javascript" src="javascript/jqwidgets/jqxdropdownlist.js"></script>
        <script type="text/javascript" src="javascript/jqwidgets/jqxscrollbar.js"></script>
        <script type="text/javascript" src="javascript/jqwidgets/jqxlistbox.js"></script>
        <script type="text/javascript" src="javascript/jqwidgets/jqxexpander.js"></script>
        <script type="text/javascript" src="javascript/pdfobject.js" ></script>
        <script type="text/javascript">
            $(document).ready(function () {
                fn_Refrescar();
                var theme = getTheme();
                $('#div_Normas').jqxExpander({showArrow: false, toggleMode: 'none', width: '300px', height: '200px'});
                $('#div_Descarga').jqxExpander({showArrow: false, toggleMode: 'none', width: '300px', height: '190px'});
                $('#div_Comunicados').jqxExpander({showArrow: false, toggleMode: 'none', width: '300px', height: '390px'});
                $("#txt_Usuario").jqxInput({theme: theme, placeHolder: "Usuario", height: 25, width: 150});
                $("#txt_Password").jqxPasswordInput({theme: theme, placeHolder: "Contraseña", width: 150, height: 25, showStrength: true, showStrengthPosition: "right"});
                $("#txt_Verificacion").jqxInput({theme: theme, placeHolder: "Verificación", height: 25, width: 150, minLength: 1});
                $("#btn_Ingresar").jqxButton({theme: theme, width: 150, height: 25});
                $("#btn_Ingresar").on('click', function () {
                    $('#frm_Login').jqxValidator('validate');
                });
                $('#frm_Login').jqxValidator({
                    rules: [
                        {input: '#txt_Usuario', message: 'Ingrese el Usuario!', action: 'keyup, blur', rule: 'required'},
                        {input: '#txt_Usuario', message: 'Ingrese Solo Numeros!', action: 'keyup, blur', rule: 'number'},
                        {input: '#txt_Password', message: 'Ingrese la Contraseña!', action: 'keyup, blur', rule: 'required'},
                        {input: '#txt_Verificacion', message: 'Ingrese los Caracteres de Verificacion!', action: 'keyup, blur', rule: 'required'}]
                });
                $('#frm_Login').jqxValidator({
                    onSuccess: function () {
                        fn_login();
                    }
                });
            });
            function fn_login() {
                var usuario = $("#txt_Usuario").val();
                var password = $("#txt_Password").val();
                var verificacion = $("#txt_Verificacion").val();
                $.ajax({
                    type: "POST",
                    url: "VerificaUsuario",
                    data: {accion: "LOGIN", usuario: usuario, password: password, verificacion: verificacion},
                    success: function (data) {
                        var result = data.substr(0, 5);
                        if (result === "Login") {
                            window.location = data;
                            fn_Refrescar();
                        } else {
                            $.alert({
                                theme: 'material',
                                title: 'Mensaje!',
                                content: data,
                                animation: 'zoom',
                                closeAnimation: 'zoom',
                                type: 'orange',
                                typeAnimated: true
                            });
                            fn_Refrescar();
                            $("#txt_Verificacion").val('');
                        }
                    }
                });
            }
            function fn_Refrescar() {
                var d = new Date();
                $.ajax({
                    type: "POST",
                    url: "captcha.jsp?" + d.getTime(),
                    data: {},
                    success: function (data) {
                        $("#img_stickyImg").html(data);
                    }
                });
            }
        </script>
    </head>
    <body oncontextmenu='return false'>
        <table width="100%" height="100%">
            <tr>
                <td colspan="3" height="30px">&nbsp;</td>
            </tr>
            <tr>
                <td align="right" style="padding: 20px">&nbsp;
                    <div id='div_Normas' style="position: relative">
                        <div style="text-align: center">NORMAS LEGALES</div>
                        <div>
                            <ul>
                                <li class="link"> 1. <a href="Descarga/DL1440-LeydelSistemaNacionaldePresupuestoPublico.pdf" target="_blank">DL 1440 - Ley del Sistema Nacional de Presupuesto Público</a></li>
                                <li class="link"> 2. <a href="Descarga/DU014_2019.pdf" target="_blank">Decreto Urgencia N° 014-2019</a></li>
                                <li class="link"> 3. <a href="Descarga/Anexo4-FtesFinanciamiento2020.pdf" target="_blank">Fuentes de Financiamiento 2020 - MEF</a></li>
                                <li class="link"> 4. <a href="Descarga/Anexo1-ClasificadoresIngreso2020.pdf" target="_blank">Clasificador de Ingresos 2020 - MEF</a></li>
                                <li class="link"> 5. <a href="Descarga/Anexo2-ClasificadoresGasto2020.pdf" target="_blank">Clasificador de Gastos 2020 - MEF</a></li>
                                <li class="link"> 6. <a href="Descarga/GlosariodeTerminosFinancieros-MEF.pdf" target="_blank">Glosario de Términos Financieros - MEF</a></li>
                            </ul>
                        </div>
                    </div>
                    <div id='div_Descarga' style="position: relative">
                        <div style="text-align: center">MANUALES</div>
                        <div>
                            <ul>
                                <li class="link"> 1. <a href="Descarga/OPREFA_ManualUsuario.pdf" target="_blank">OPREFA - Manual de Usuario</a></li>
                            </ul>
                        </div>
                    </div>
                </td>
                <td align="center" style="width: 450px;">&nbsp;
                    <form method="post" id="frm_Login" >
                        <div class="header">INGRESO AL SISTEMA</div>
                        <div style="float: left; margin-top: 60px; margin-left: 15px "> 
                            <img src="Imagenes/Logos/login.jpg" alt="Icon-Login" width="160px" height="150px"/>
                        </div>
                        <div id="content">
                            <table >
                                <tr>
                                    <td><label for="usuario">Usuario </label></td>
                                    <td><input type="text" name="txt_Usuario" id="txt_Usuario"/></td>
                                </tr>
                                <tr>
                                    <td colspan="2">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td><label for="password">Password </label></td>
                                    <td><input type="password" name="txt_Password" id="txt_Password"/></td>
                                </tr>
                                <tr>
                                    <td colspan="2">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td colspan="2"><label for="verificacion">Escriba estos caracteres </label></td>
                                </tr>
                                <tr>
                                    <td colspan="2"><div id="img_stickyImg"></div></td>
                                </tr> 
                                <tr>
                                    <td colspan="2" class="link"><a href="javascript:fn_Refrescar();">Refrescar</a></td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td><input type="text" name="txt_Verificacion" id="txt_Verificacion"/></td>
                                </tr>
                                <tr>
                                    <td colspan="2">&nbsp;</td>
                                </tr>
                            </table> 
                            <div class="Summit">
                                <div style="text-align: center;"><input name="btn_Ingresar" id="btn_Ingresar" type="button" value="Ingresar"/></div>
                            </div>
                        </div>
                        <div class="footer">
                            <span style="font-weight: bold">Oficina Previsional de las Fuerzas Armadas</span><br/>
                            &copy; Todos los derechos reservados
                        </div>
                    </form> 
                </td>
                <td align="left" style="padding: 20px">&nbsp;
                    <div id='div_Comunicados' style="position: relative">
                        <div style="text-align: center">DISPOSICIONES GENERALES</div>
                        <div>
                            <ul>
                                <li class="link"> 1. <a href="Descarga/VinculacióndelPlaneamientoconelPresupuesto-CEPLAN.pdf" target="_blank">Vinculación del Planeamiento con el Presupuesto - CEPLAN.</a></li>
                                <li class="link"> 2. <a href="Descarga/Anexo1-DefinicionesProgramacionMultianual.pdf" target="_blank">Definiciones - Programación Multianual.</a></li>
                            </ul>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
    </body>
</html>