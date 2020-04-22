<%-- 
    Document   : ListaUsuario
    Created on : 23/03/2017, 01:27:09 PM
    Author     : H-URBINA-M
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    var autorizacion = '${autorizacion}';
    var usuario = null;
    var mode = null;
    var msg = "";
    var lista = new Array();
    <c:forEach var="c" items="${objUsuarios}">
    var result = {usuario: '${c.usuario}', apellidos: '${c.nombres}', areaLaboral: '${c.areaLaboral}',
        cargo: '${c.cargo}', correo: '${c.correo}', telefono: '${c.telefono}',
        autorizacion: '${c.autorizacion}', estado: '${c.estado}'};
    lista.push(result);
    </c:forEach>
    $(document).ready(function () {
        //PARA CARGAR LOS ELEMENTOS DE LA GRILLA
        var source = {
            localdata: lista,
            datatype: "array",
            datafields:
                    [
                        {name: 'usuario', type: "string"},
                        {name: 'apellidos', type: "string"},
                        {name: 'areaLaboral', type: "string"},
                        {name: 'cargo', type: "string"},
                        {name: 'correo', type: "string"},
                        {name: 'telefono', type: "string"},
                        {name: 'autorizacion', type: "bool"},
                        {name: 'estado', type: "string"}
                    ],
            root: "Usuarios",
            record: "Usuarios",
            id: 'usuario'
        };
        var dataAdapter = new $.jqx.dataAdapter(source);
        //ESTILOS A LAS CELDAS DE LA GRILLA 
        var cellclass = function (row, datafield, value, rowdata) {
            if (datafield === "apellido") {
                return "RowBold";
            }
        };
        //DEFINIMOS LOS CAMPOS Y DATOS DE LA GRILLA
        $("#div_GrillaPrincipal").jqxGrid({
            width: '99.8%',
            height: ($(window).height() - 32),
            source: dataAdapter,
            autoheight: false,
            autorowheight: false,
            altrows: true,
            sortable: true,
            pageable: true,
            filterable: true,
            autoshowfiltericon: true,
            columnsresize: true,
            showfilterrow: true,
            showtoolbar: true,
            editable: false,
            pagesize: 100,
            rendertoolbar: function (toolbar) {
                // ADICIONAMOS BOTONES A LA BARRA DE ESTADOS
                var container = $("<div style='overflow: hidden; position: relative; margin: 1px;'></div>");
                var ButtonNuevo = $("<div style='float: left; margin-left: 5px;'><img style='position: relative; margin-top: 2px;' src='../Imagenes/Botones/nuevo42.gif' width=18 height=18 /><span style='margin-left: 4px; position: relative; top: -3px;'> </span></div>");
                var ButtonExportar = $("<div style='float: left; margin-left: 5px;'><img style='position: relative; margin-top: 2px;' src='../Imagenes/Botones/pauf42.gif' width=18 height=18/><span style='margin-left: 4px; position: relative; top: -3px;'> </span></div>");
                var ButtonRecargar = $("<div style='float: left; margin-left: 5px;'><img style='position: relative; margin-top: 2px;' src='../Imagenes/Botones/refresh42.gif' width=18 height=18/><span style='margin-left: 4px; position: relative; top: -3px;'> </span></div>");
                var ButtonSalir = $("<div style='float: left; margin-left: 5px;'><img style='position: relative; margin-top: 2px;' src='../Imagenes/Botones/exit42.gif' width=18 height=18/><span style='margin-left: 4px; position: relative; top: -3px;'> </span></div>");
                container.append(ButtonNuevo);
                container.append(ButtonExportar);
                container.append(ButtonRecargar);
                container.append(ButtonSalir);
                toolbar.append(container);
                ButtonNuevo.jqxButton({width: 30, height: 22});
                ButtonNuevo.jqxTooltip({position: 'bottom', content: "Nuevo Registro"});
                ButtonExportar.jqxButton({width: 30, height: 22});
                ButtonExportar.jqxTooltip({position: 'bottom', content: "Exportar Datos"});
                ButtonRecargar.jqxButton({width: 30, height: 22});
                ButtonRecargar.jqxTooltip({position: 'bottom', content: "Recargar"});
                ButtonSalir.jqxButton({width: 30, height: 22});
                ButtonSalir.jqxTooltip({position: 'bottom', content: "Salir de la Pantalla"});
                //ASIGNAMOS LAS FUNCIONES PARA EL BOTON NUEVO
                ButtonNuevo.click(function (event) {
                    if (autorizacion !== 'true') {
                        $.alert({
                            theme: 'material',
                            title: 'AVISO DEL SISTEMA',
                            content: 'Usuario no Autorizado para realizar este Tipo de Operación',
                            animation: 'zoom',
                            closeAnimation: 'zoom',
                            type: 'red',
                            typeAnimated: true
                        });
                    } else {
                        mode = 'I';
                        $("#txt_Usuario").val('');
                        $("#txt_Paterno").val('');
                        $("#txt_Materno").val('');
                        $("#txt_Nombres").val('');
                        $("#cbo_AreaLaboral").jqxDropDownList('setContent', 'Seleccione');
                        $("#txt_Cargo").val('');
                        $("#txt_Correo").val('');
                        $("#txt_Telefono").val('');
                        $("#cbo_Estado").jqxDropDownList('selectItem', 'AC');
                        $("#cbo_Autorizacion").jqxDropDownList('selectItem', 'false');
                        $('#txt_Usuario').jqxInput({disabled: false});
                        $('#div_VentanaPrincipal').jqxWindow({isModal: true, modalOpacity: 0.8});
                        $('#div_VentanaPrincipal').jqxWindow('open');
                    }
                });
                //ASIGNAMOS LAS FUNCIONES PARA EL BOTON EXPORTAR
                ButtonExportar.click(function (event) {
                    $("#div_GrillaPrincipal").jqxGrid('exportdata', 'xls', 'Listado de Usuarios');
                });
                //ASIGNAMOS LAS FUNCIONES PARA EL BOTON RECARGAR
                ButtonRecargar.click(function (event) {
                    fn_Refrescar();
                });
                //ASIGNAMOS LAS FUNCIONES PARA EL BOTON SALIR
                ButtonSalir.click(function (event) {
                    window.location.reload();
                });
            },
            columns: [
                {text: ' ', sortable: false, filterable: false, editable: false, groupable: false, draggable: false, resizable: false,
                    datafield: '', columntype: 'number', width: '2%', pinned: true, cellsrenderer: function (row, column, value) {
                        return "<div style='margin:4px; text-align: center;'>" + (value + 1) + "</div>";
                    }
                },
                {text: 'USUARIO', dataField: 'usuario', width: '8%', align: 'center', cellsAlign: 'center', cellclassname: cellclass},
                {text: 'APELLIDOS Y NOMBRES', dataField: 'apellidos', width: '23%', align: 'center', cellsAlign: 'left', cellclassname: cellclass},
                {text: 'AREA LABORAL', dataField: 'areaLaboral', filtertype: 'checkedlist', width: '11%', align: 'center', cellsAlign: 'center', cellclassname: cellclass},
                {text: 'CARGO', dataField: 'cargo', width: '18%', align: 'center', cellsAlign: 'center', cellclassname: cellclass},
                {text: 'CORREO', dataField: 'correo', width: '15%', align: 'center', cellsAlign: 'center', cellclassname: cellclass},
                {text: 'TELEFONO', dataField: 'telefono', width: '10%', align: 'center', cellsAlign: 'center', cellclassname: cellclass},
                {text: 'ADMIN', dataField: 'autorizacion', columntype: 'checkbox', filterable: false, width: '5%', align: 'center', cellsAlign: 'center', cellclassname: cellclass},
                {text: 'ESTADO', dataField: 'estado', filtertype: 'checkedlist', width: '8%', align: 'center', cellsAlign: 'center', cellclassname: cellclass}
            ]
        });
        // DEFINIMOS EL MENU CONTEXTUAL
        var contextMenu = $("#div_ContextMenu").jqxMenu({width: 200, height: 85, autoOpenPopup: false, mode: 'popup'});
        $("#div_GrillaPrincipal").on('contextmenu', function () {
            return false;
        });
        // HABILITAMOS LA OPCION DE CLICK DEL MENU CONTEXTUAL.
        $("#div_GrillaPrincipal").on('rowclick', function (event) {
            if (event.args.rightclick) {
                $("#div_GrillaPrincipal").jqxGrid('selectrow', event.args.rowindex);
                var scrollTop = $(window).scrollTop();
                var scrollLeft = $(window).scrollLeft();
                contextMenu.jqxMenu('open', parseInt(event.args.originalEvent.clientX) + 5 + scrollLeft, parseInt(event.args.originalEvent.clientY) + 5 + scrollTop);
                return false;
            }
        });
        //DEFINIMOS LOS EVENTOS SEGUN LA OPCION DEL MENU CONTEXTUAL
        $("#div_ContextMenu").on('itemclick', function (event) {
            var opcion = event.args;
            if (usuario === null || usuario === '') {
                $.alert({
                    theme: 'material',
                    title: 'AVISO DEL SISTEMA',
                    content: 'Debe Seleccionar un Registro',
                    animation: 'zoom',
                    closeAnimation: 'zoom',
                    type: 'orange',
                    typeAnimated: true
                });
            } else {
                if ($.trim($(opcion).text()) === "Editar") {
                    mode = 'U';
                    fn_EditarRegistro();
                } else if ($.trim($(opcion).text()) === "Inactivar") {
                    $.confirm({
                        theme: 'material',
                        title: 'AVISO DEL SISTEMA',
                        content: '¿Desea Inactivar este Usuario?',
                        animation: 'zoom',
                        closeAnimation: 'zoom',
                        type: 'red',
                        typeAnimated: true,
                        buttons: {
                            aceptar: {
                                text: 'Aceptar',
                                btnClass: 'btn-primary',
                                keys: ['enter', 'shift'],
                                action: function () {
                                    mode = 'D';
                                    fn_GrabarDatos();
                                }
                            },
                            cancelar: function () {
                            }
                        }
                    });
                } else if ($.trim($(opcion).text()) === "Opciones") {
                    fn_UsuarioOpciones();
                }
            }
        });
        //SELECCIONAMOS UN REGISTRO DE LA GRILLA
        $("#div_GrillaPrincipal").on('rowselect', function (event) {
            var args = event.args;
            var row = $("#div_GrillaPrincipal").jqxGrid('getrowdata', args.rowindex);
            usuario = row['usuario'];
        });
        //CREA LOS ELEMENTOS DE LAS VENTANAS
        var customButtonsDemo = (function () {
            function _createElements() {
                //INICIA LOS VALORES DE LA VENTANA
                var posicionX, posicionY;
                var ancho = 500;
                var alto = 295;
                posicionX = ($(window).width() / 2) - (ancho / 2);
                posicionY = ($(window).height() / 2) - (alto / 2);
                $('#div_VentanaPrincipal').jqxWindow({
                    position: {x: posicionX, y: posicionY},
                    width: ancho, height: alto, resizable: false,
                    cancelButton: $('#btn_Cancelar'),
                    initContent: function () {
                        $("#txt_Usuario").jqxInput({placeHolder: 'USUARIO', width: 250, height: 20, minLength: 8, maxLength: 20});
                        $("#txt_Paterno").jqxInput({placeHolder: 'APELLIDO PATERNO', width: 350, height: 20, maxLength: 100});
                        $("#txt_Materno").jqxInput({placeHolder: 'APELLIDO MATERNO', width: 350, height: 20, maxLength: 100});
                        $("#txt_Nombres").jqxInput({placeHolder: 'NOMBRES', width: 350, height: 20, maxLength: 100});
                        $("#cbo_AreaLaboral").jqxDropDownList({width: 250, height: 20, promptText: "Seleccione"});
                        $("#txt_Cargo").jqxInput({placeHolder: 'CARGO', width: 350, height: 20, maxLength: 200});
                        $("#txt_Correo").jqxInput({placeHolder: 'CORREO', width: 350, height: 20, maxLength: 200});
                        $("#txt_Telefono").jqxMaskedInput({mask: '###-###-###', width: 100, height: 20});
                        $("#cbo_Estado").jqxDropDownList({width: 100, height: 20, promptText: "Seleccione"});
                        $("#cbo_Autorizacion").jqxDropDownList({width: 80, height: 20, promptText: "Seleccione"});
                        $('#btn_Cancelar').jqxButton({width: '65px', height: 25});
                        $('#btn_Guardar').jqxButton({width: '65px', height: 25});
                        $('#btn_Guardar').on('click', function () {
                            $('#frm_Usuario').jqxValidator('validate');
                        });
                        $('#frm_Usuario').jqxValidator({
                            rules: [
                                {input: '#txt_Usuario', message: 'Ingrese la Usuario!', action: 'keyup, blur', rule: 'required'},
                                {input: '#txt_Paterno', message: 'Ingrese el Apellido Paterno!', action: 'keyup, blur', rule: 'required'},
                                {input: '#txt_Materno', message: 'Ingrese el Apellido Materno!', action: 'keyup, blur', rule: 'required'},
                                {input: '#txt_Nombres', message: 'Ingrese los Nombres!', action: 'keyup, blur', rule: 'required'},
                                {input: '#txt_Cargo', message: 'Ingrese el Cargo!', action: 'keyup, blur', rule: 'required'},
                                {input: '#txt_Correo', message: 'Ingrese el Correo!', action: 'keyup, blur', rule: 'required'},
                                {input: '#txt_Correo', message: 'Correo Invalido!!', action: 'keyup, blur', rule: 'email'}
                            ]
                        });
                        $('#frm_Usuario').jqxValidator({
                            onSuccess: function () {
                                fn_GrabarDatos();
                            }
                        });
                    }
                });
                //INICIA LOS VALORES DE LA VENTANA OPCIONES
                ancho = 400;
                alto = 470;
                posicionX = ($(window).width() / 2) - (ancho / 2);
                posicionY = ($(window).height() / 2) - (alto / 2);
                $('#div_VentanaOpciones').jqxWindow({
                    position: {x: posicionX, y: posicionY},
                    width: ancho, height: alto, resizable: false,
                    cancelButton: $('#btn_CancelarOpciones'),
                    initContent: function () {
                        $('#div_Opciones').jqxTree({width: 390, height: 400, hasThreeStates: true, checkboxes: true});
                        $('#div_Opciones').css('visibility', 'visible');
                        $('#btn_CancelarOpciones').jqxButton({width: '65px', height: 25});
                        $('#btn_GuardarOpciones').jqxButton({width: '65px', height: 25});
                        $('#btn_GuardarOpciones').on('click', function () {
                            fn_GrabarOpciones();
                        });
                    }
                });
            }
            return {init: function () {
                    _createElements();
                }
            };
        }());
        $(document).ready(function () {
            customButtonsDemo.init();
            fn_cargarComboAjax("#cbo_AreaLaboral", {mode: 'areaLaboral'});
        });
        //FUNCION PARA ACTUALIZAR DATOS DE LA GRILLA
        function fn_Refrescar() {
            $("#div_GrillaPrincipal").remove();
            $("#div_VentanaPrincipal").remove();
            $("#div_ContextMenu").remove();
            $("#div_VentanaOpciones").remove();
            var $contenidoAjax = $('#div_Detalle').html('<img src="../Imagenes/Fondos/cargando.gif">');
            $.ajax({
                type: "GET",
                url: "../Usuarios",
                data: {mode: 'G'},
                success: function (data) {
                    $contenidoAjax.html(data);
                }
            });
        }
        //FUNCION PARA CARGAR VENTANA PARA EDITAR REGISTRO
        function fn_EditarRegistro() {
            $("#txt_Usuario").val(usuario);
            $.ajax({
                type: "GET",
                url: "../Usuarios",
                data: {mode: mode, usuario: usuario},
                success: function (data) {
                    var dato = data.split("+++");
                    if (dato.length === 9) {
                        $("#txt_Paterno").val(dato[0]);
                        $("#txt_Materno").val(dato[1]);
                        $("#txt_Nombres").val(dato[2]);
                        $("#cbo_AreaLaboral").jqxDropDownList('selectItem', dato[3]);
                        $("#txt_Cargo").val(dato[4]);
                        $("#txt_Correo").val(dato[5]);
                        $("#txt_Telefono").val(dato[6]);
                        $("#cbo_Autorizacion").jqxDropDownList('selectItem', dato[7]);
                        $("#cbo_Estado").jqxDropDownList('selectItem', dato[8]);
                        $('#txt_Usuario').jqxInput({disabled: true});
                        $('#div_VentanaPrincipal').jqxWindow({isModal: true, modalOpacity: 0.8});
                        $('#div_VentanaPrincipal').jqxWindow('open');
                    }
                }
            });
        }
        //FUNCION PARA GRABAR LOS DATOS DE LA VENTANA PRINCIPAL
        function fn_GrabarDatos() {
            usuario = $("#txt_Usuario").val();
            var paterno = $("#txt_Paterno").val();
            var materno = $("#txt_Materno").val();
            var nombres = $("#txt_Nombres").val();
            var areaLaboral = $("#cbo_AreaLaboral").val();
            var cargo = $("#txt_Cargo").val();
            var correo = $("#txt_Correo").val();
            var telefono = $("#txt_Telefono").val();
            var autorizacion = $("#cbo_Autorizacion").val();
            var estado = $("#cbo_Estado").val();
            $.ajax({
                type: "POST",
                url: "../IduUsuarios",
                data: {mode: mode, usuario: usuario, paterno: paterno, materno: materno,
                    nombres: nombres, areaLaboral: areaLaboral, cargo: cargo, correo: correo,
                    telefono: telefono, autorizacion: autorizacion, estado: estado},
                success: function (data) {
                    msg = data;
                    if (mode === "I" && msg !== "GUARDO") {
                        $.confirm({
                            title: 'PASSWORD GENERADO : ',
                            content: msg,
                            type: 'blue',
                            typeAnimated: true,
                            autoClose: 'cerrarAction',
                            buttons: {
                                cerrarAction: {
                                    text: 'Cerrar',
                                    action: function () {
                                        $('#div_VentanaPrincipal').jqxWindow('close');
                                        fn_Refrescar();
                                    }
                                }
                            }
                        });
                    } else if (msg === "GUARDO") {
                        $.confirm({
                            title: 'AVISO DEL SISTEMA',
                            content: 'Datos procesados correctamente!!',
                            type: 'green',
                            typeAnimated: true,
                            autoClose: 'cerrarAction|1000',
                            buttons: {
                                cerrarAction: {
                                    text: 'Cerrar',
                                    action: function () {
                                        $('#div_VentanaPrincipal').jqxWindow('close');
                                        fn_Refrescar();
                                    }
                                }
                            }
                        });
                    } else {
                        $.alert({
                            theme: 'material',
                            title: 'AVISO DEL SISTEMA',
                            content: msg,
                            animation: 'zoom',
                            closeAnimation: 'zoom',
                            type: 'red',
                            typeAnimated: true
                        });
                    }
                }
            });
        }
        function fn_UsuarioOpciones() {
            $.ajax({
                type: "GET",
                url: "../Usuarios",
                data: {mode: 'M', usuario: usuario},
                success: function (data) {
                    data = data.replace("[", "");
                    var fila = data.split("[");
                    var data = new Array();
                    for (i = 1; i < fila.length; i++) {
                        var columna = fila[i];
                        var datos = columna.split("+++");
                        while (datos[3].indexOf(']') > 0) {
                            datos[3] = datos[3].replace("]", "");
                        }
                        while (datos[3].indexOf(',') > 0) {
                            datos[3] = datos[3].replace(",", "");
                        }
                        var row = {id: datos[0],
                            parentid: datos[1],
                            text: datos[3],
                            value: datos[0]};
                        data.push(row);
                        var mod = {id: datos[1],
                            parentid: -1,
                            text: datos[2],
                            value: datos[1]};
                        data.push(mod);
                    }
                    var source = {
                        datatype: "json",
                        datafields: [
                            {name: 'id'},
                            {name: 'parentid'},
                            {name: 'text'},
                            {name: 'value'}
                        ],
                        id: 'id',
                        localdata: data
                    };
                    var dataAdapter = new $.jqx.dataAdapter(source);
                    dataAdapter.dataBind();
                    var records = dataAdapter.getRecordsHierarchy('id', 'parentid', 'items', [{name: 'text', map: 'label'}]);
                    $('#div_Opciones').jqxTree({source: records});
                    $('#div_Opciones').jqxTree('expandAll');
                    $.ajax({
                        type: "GET",
                        url: "../Usuarios",
                        data: {mode: 'O', usuario: usuario},
                        success: function (data) {
                            data = data.replace("[", "");
                            data = data.replace("]", "");
                            var fila = data.split(",");
                            for (i = 0; i < fila.length; i++) {
                                var dato = "#" + fila[i].trim();
                                $("#div_Opciones").jqxTree('checkItem', $(dato)[0], true);
                            }
                        }
                    });
                }
            });
            $('#div_VentanaOpciones').jqxWindow({isModal: true, modalOpacity: 0.8});
            $('#div_VentanaOpciones').jqxWindow('open');
        }
        function fn_GrabarOpciones() {
            var lista = new Array();
            var items = $('#div_Opciones').jqxTree('getCheckedItems');
            for (var i = 0; i < items.length; i++) {
                var item = items[i];
                lista.push(item.id);
            }
            $.ajax({
                type: "POST",
                url: "../IduUsuarios",
                data: {mode: 'O', usuario: usuario, lista: JSON.stringify(lista)},
                success: function (data) {
                    msg = data;
                    if (msg === "GUARDO") {
                        $.confirm({
                            title: 'AVISO DEL SISTEMA',
                            content: 'Datos procesados correctamente!!',
                            type: 'green',
                            typeAnimated: true,
                            autoClose: 'cerrarAction|1000',
                            buttons: {
                                cerrarAction: {
                                    text: 'Cerrar',
                                    action: function () {
                                        $('#div_VentanaOpciones').jqxWindow('close');
                                        fn_Refrescar();
                                    }
                                }
                            }
                        });
                    } else {
                        $.alert({
                            theme: 'material',
                            title: 'AVISO DEL SISTEMA',
                            content: msg,
                            animation: 'zoom',
                            closeAnimation: 'zoom',
                            type: 'red',
                            typeAnimated: true
                        });
                    }
                }
            });
        }
    });
</script>
<div id="div_GrillaPrincipal"></div>
<div id="div_VentanaPrincipal" style="display: none">
    <div>
        <span style="float: left">USUARIO DEL SISTEMA</span>
    </div>
    <div style="overflow: hidden">
        <form id="frm_Usuario" name="frm_Usuario" method="post" >
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="inputlabel">Usuario : </td>
                    <td><input type="text" id="txt_Usuario" name="txt_Usuario" style="text-transform: uppercase;"/></td>
                </tr>
                <tr>
                    <td class="inputlabel">Ap. Paterno : </td>
                    <td><input type="text" id="txt_Paterno" name="txt_Paterno" style="text-transform: uppercase;"/></td>
                </tr>
                <tr>
                    <td class="inputlabel">Ap. Materno : </td>
                    <td><input type="text" id="txt_Materno" name="txt_Materno" style="text-transform: uppercase;"/></td>
                </tr>
                <tr>
                    <td class="inputlabel">Nombres : </td>
                    <td><input type="text" id="txt_Nombres" name="txt_Nombres" style="text-transform: uppercase;"/></td>
                </tr> 
                <tr>
                    <td class="inputlabel">Area Lab. : </td>
                    <td>
                        <select id="cbo_AreaLaboral" name="cbo_AreaLaboral">
                            <option value="0">Seleccione</option>
                        </select>
                    </td> 
                </tr>
                <tr>
                    <td class="inputlabel">Cargo : </td>
                    <td><input type="text" id="txt_Cargo" name="txt_Cargo" style="text-transform: uppercase;"/></td>
                </tr> 
                <tr>
                    <td class="inputlabel">Correo : </td>
                    <td><input type="email" id="txt_Correo" name="txt_Correo" style="text-transform: uppercase;"/></td>
                </tr> 
                <tr>
                    <td class="inputlabel">Teléfono : </td>
                    <td><input type="text" id="txt_Telefono" name="txt_Telefono" style="text-transform: uppercase;"/></td>
                </tr>
                <tr>
                    <td class="inputlabel">Administrador : </td>
                    <td>
                        <select id="cbo_Autorizacion" name="cbo_Autorizacion">
                            <option value="true">SI</option> 
                            <option value="false">NO</option> 
                        </select>
                    </td> 
                </tr>
                <tr>
                    <td class="inputlabel">Estado : </td>
                    <td>
                        <select id="cbo_Estado" name="cbo_Estado">
                            <option value="AC">ACTIVO</option> 
                            <option value="IN">INACTIVO</option> 
                        </select>
                    </td> 
                </tr>
                <tr>
                    <td class="Summit" colspan="4">
                        <div >
                            <input type="button" id="btn_Guardar"  value="Guardar" style="margin-right: 20px"/>
                            <input type="button" id="btn_Cancelar" value="Cancelar" style="margin-right: 20px"/>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div id="div_VentanaOpciones" style="display: none">
    <div>
        <span style="float: left">SELECCIONE LAS OPCIONES DEL USUARIO</span>
    </div>
    <div style="overflow: hidden">
        <div id='div_Opciones'></div>
        <div class="Summit">
            <input type="button" id="btn_GuardarOpciones"  value="Guardar" style="margin-right: 20px"/>
            <input type="button" id="btn_CancelarOpciones" value="Cancelar" style="margin-right: 20px"/>
        </div>
    </div>
</div>
<div id='div_ContextMenu' style='display: none;'>
    <ul>
        <li>Editar</li>
        <li>Inactivar</li>
        <li type='separator'></li>
        <li style="color: blue;">Opciones</li>
    </ul>
</div>