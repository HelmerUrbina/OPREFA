<%-- 
    Document   : ListaMesaPartes
    Created on : 31/03/2020, 01:36:27 PM
    Author     : H-URBINA-M
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    var periodo = $("#cbo_Periodo").val();
    var tipo = $("#cbo_Tipo").val();
    var mes = $("#cbo_Mes").val();
    var FechaBus = $("#div_diaBus").val();
    var codigo = null;
    var archivo = null;
    var institucion = null;
    var estado = null;
    var mode = null;
    var msg = null;
    var lista = new Array();
    <c:forEach var="d" items="${objMesaPartes}">
    var result = {numero: '${d.numero}', numeroDocumento: '${d.numeroDocumento}', asunto: '${d.asunto}',
        institucion: '${d.institucion}', prioridad: '${d.prioridad}', fecha: '${d.fecha}', estado: '${d.estado}', firma: '${d.postFirma}',
        legajo: '${d.legajo}', folio: '${d.folio}', usuarioResponsable: '${d.usuarioResponsable}', referencia: "${d.referencia}",
        codigoUsuario: '${d.area}', archivo: '${d.archivo}'};
    lista.push(result);
    </c:forEach>
    var listaInstitucion = new Array();
    var rows = '${objInstitucion}';
    <c:forEach var="c" items="${objInstituciones}">
        <c:set value="${c.descripcion}" var="descripcion"></c:set>
        <c:set value="${c.codigo}" var="codigo"></c:set>
    var result = {label: "${descripcion}", value: "${codigo}"};
    listaInstitucion.push(result);
    </c:forEach>
    $(document).ready(function () {
        //PARA CARGAR LOS ELEMENTOS DE LA INSTITUCION
        var sourceInstitucion = {
            datafields: [{name: 'label'}, {name: 'value'}],
            localdata: listaInstitucion
        };
        var dataAdapterInstitucion = new $.jqx.dataAdapter(sourceInstitucion);
        //PARA CARGAR LOS ELEMENTOS DE LA GRILLA
        var source = {
            localdata: lista,
            datatype: "array",
            datafields:
                    [
                        {name: 'numero', type: "string"},
                        {name: 'numeroDocumento', type: "string"},
                        {name: 'asunto', type: "string"},
                        {name: 'institucion', type: "string"},
                        {name: 'prioridad', type: "string"},
                        {name: 'fecha', type: "date"},
                        {name: 'estado', type: "string"},
                        {name: 'firma', type: "string"},
                        {name: 'legajo', type: "number"},
                        {name: 'folio', type: "string"},
                        {name: 'usuarioResponsable', type: "string"},
                        {name: 'referencia', type: "string"},
                        {name: 'codigoUsuario', type: "string"},
                        {name: 'archivo', type: "string"}
                    ],
            root: "MesaPartes",
            record: "MesaPartes",
            id: 'numero'
        };
        var dataAdapter = new $.jqx.dataAdapter(source);
        //ESTILOS A LAS CELDAS DE LA GRILLA 
        var cellclass = function (row, datafield, value, rowdata) {
            if (rowdata['estado'] === "ANULADO") {
                return "RowAnulado";
            }
            if (datafield === "numero") {
                return "RowBold";
            }
            if (datafield === "usuarioResponsable") {
                return "RowBlue";
            }
            if (datafield === "institucion") {
                return "RowPurple";
            }
        };
        //DEFINIMOS LOS CAMPOS Y DATOS DE LA GRILLA
        $("#div_GrillaPrincipal").jqxGrid({
            width: '99.8%',
            height: ($(window).height() - 60),
            source: dataAdapter,
            autoheight: false,
            autorowheight: false,
            altrows: true,
            showtoolbar: true,
            sortable: true,
            pageable: true,
            filterable: true,
            autoshowfiltericon: true,
            columnsresize: true,
            showfilterrow: true,
            editable: false,
            rendertoolbar: function (toolbar) {
                var container = $("<div style='overflow: hidden; position: relative; margin: 1px;'></div>");
                var ButtonNuevo = $("<div style='float: left; margin-left: 5px;'><img style='position: relative; margin-top: 2px;' src='../Imagenes/Botones/nuevo42.gif' width=18 height=18 /><span style='margin-left: 4px; position: relative; top: -3px;'> </span></div>");
                var ButtonExportar = $("<div style='float: left; margin-left: 5px;'><img style='position: relative; margin-top: 2px;' src='../Imagenes/Botones/pauf42.gif' width=18 height=18/><span style='margin-left: 4px; position: relative; top: -3px;'> </span></div>");
                var ButtonReporte = $("<div style='float: left; margin-left: 5px;'><img style='position: relative; margin-top: 2px;' src='../Imagenes/Botones/printer42.gif' width=18 height=18/><span style='margin-left: 4px; position: relative; top: -3px;'> </span></div>");
                container.append(ButtonNuevo);
                container.append(ButtonExportar);
                container.append(ButtonReporte);
                toolbar.append(container);
                ButtonNuevo.jqxButton({width: 30, height: 22});
                ButtonNuevo.jqxTooltip({position: 'bottom', content: "Nuevo Registro"});
                ButtonExportar.jqxButton({width: 30, height: 22});
                ButtonExportar.jqxTooltip({position: 'bottom', content: "Exportar Datos"});
                ButtonReporte.jqxButton({width: 30, height: 22});
                ButtonReporte.jqxTooltip({position: 'bottom', content: "Reporte Diario"});
                ButtonNuevo.click(function (event) {
                    mode = 'I';
                    codigo = 0;
                    fn_NuevoRegistro();
                });
                //ASIGNAMOS LAS FUNCIONES PARA EL BOTON EXPORTAR
                ButtonExportar.click(function (event) {
                    $("#div_GrillaPrincipal").jqxGrid('exportdata', 'xls', 'Listado de Mesa de Partes');
                });
                ButtonReporte.click(function (event) {
                    var url = '../Reportes?reporte=MPA0001&periodo=' + periodo + '&tipo=' + tipo + '&codigo=' + FechaBus + '/' + mes + '/' + periodo;
                    window.open(url, '_blank');
                });
            },
            columns: [
                {text: 'CÓDIGO', dataField: 'numero', width: '6%', align: 'center', cellsAlign: 'center', cellclassname: cellclass},
                {text: 'NRO DOCUMENTO', dataField: 'numeroDocumento', width: '8%', align: 'center', cellsAlign: 'left', cellclassname: cellclass},
                {text: 'ASUNTO', dataField: 'asunto', width: '20%', align: 'center', cellsAlign: 'left', cellclassname: cellclass},
                {text: 'INSTITUCIÓN', dataField: 'institucion', width: '10%', align: 'center', cellsAlign: 'center', cellclassname: cellclass},
                {text: 'PRIORIDAD', dataField: 'prioridad', filtertype: 'checkedlist', width: '8%', align: 'center', cellsAlign: 'center', cellclassname: cellclass},
                {text: 'FEC. DOC', datafield: 'fecha', filtertype: 'date', width: '8%', align: 'center', cellsAlign: 'center', cellsformat: 'd', cellclassname: cellclass},
                {text: 'FIRMA', dataField: 'firma', width: '15%', align: 'center', cellsAlign: 'left', cellclassname: cellclass},
                {text: 'USUARIO RESP.', dataField: 'usuarioResponsable', filtertype: 'checkedlist', width: '15%', align: 'center', cellsAlign: 'center', cellclassname: cellclass},
                {text: 'LEGAJO', dataField: 'legajo', width: '4%', align: 'center', cellsAlign: 'center', cellsFormat: 'f', cellclassname: cellclass},
                {text: 'FOLIO', dataField: 'folio', width: '4%', align: 'center', cellsAlign: 'center', cellsFormat: 'f', cellclassname: cellclass},
                {text: 'ESTADO', dataField: 'estado', filtertype: 'checkedlist', width: '9%', align: 'center', cellsAlign: 'center', cellclassname: cellclass}
            ]
        });
        // DEFINIMOS EL MENU CONTEXTUAL
        var alto = 115;
        var contextMenu = $("#div_ContextMenu").jqxMenu({width: 200, height: alto, autoOpenPopup: false, mode: 'popup'});
        $("#div_GrillaPrincipal").on('contextmenu', function () {
            return false;
        });
        // HABILITAMOS LA OPCION DE CLICK DEL MENU CONTEXTUAL.
        $('#div_GrillaPrincipal').on('rowclick', function (event) {
            if (event.args.rightclick) {
                $("#div_GrillaPrincipal").jqxGrid('selectrow', event.args.rowindex);
                var scrollTop = $(window).scrollTop();
                var scrollLeft = $(window).scrollLeft();
                if (parseInt(event.args.originalEvent.clientY) > 600) {
                    scrollTop = scrollTop - alto;
                }
                contextMenu.jqxMenu('open', parseInt(event.args.originalEvent.clientX) + 5 + scrollLeft, parseInt(event.args.originalEvent.clientY) + 5 + scrollTop);
                return false;
            }
        });
        //DEFINIMOS LOS EVENTOS SEGUN LA OPCION DEL MENU CONTEXTUAL
        $("#div_ContextMenu").on('itemclick', function (event) {
            var opcion = event.args;
            if ($.trim($(opcion).text()) === "Editar") {
                if (estado !== "PENDIENTE") {
                    $.alert({
                        theme: 'material',
                        title: 'AVISO DEL SISTEMA',
                        content: 'El registro no puede ser editado, se encuentra ' + estado,
                        animation: 'zoom',
                        closeAnimation: 'zoom',
                        type: 'red',
                        typeAnimated: true
                    });
                } else {
                    mode = 'U';
                    fn_EditarRegistro();
                }
            } else if ($.trim($(opcion).text()) === "Anular") {
                if (tipo === 'S') {
                    $.alert({
                        theme: 'material',
                        title: 'AVISO DEL SISTEMA',
                        content: 'El documento no puede ser anulado',
                        animation: 'zoom',
                        closeAnimation: 'zoom',
                        type: 'red',
                        typeAnimated: true
                    });
                } else {
                    if (estado !== "PENDIENTE") {
                        $.alert({
                            theme: 'material',
                            title: 'AVISO DEL SISTEMA',
                            content: 'El registro no puede ser editado, se encuentra ' + estado,
                            animation: 'zoom',
                            closeAnimation: 'zoom',
                            type: 'red',
                            typeAnimated: true
                        });
                    } else {
                        if (codigo !== null || codigo === '') {
                            mode = 'D';
                            fn_AnularDocumento();
                        } else {
                            $.alert({
                                theme: 'material',
                                title: 'AVISO DEL SISTEMA',
                                content: 'Debe Seleccionar un Registro',
                                animation: 'zoom',
                                closeAnimation: 'zoom',
                                type: 'red',
                                typeAnimated: true
                            });
                        }
                    }
                }
            } else if ($.trim($(opcion).text()) === "Ver Documento") {
                if (archivo !== null && archivo !== '') {
                    document.location.target = "_blank";
                    document.location.href = "../Descarga?opcion=MesaPartes&periodo=" + periodo + "&codigo=" + tipo + "-" + codigo + "&documento=" + archivo;
                } else {
                    $.alert({
                        theme: 'material',
                        title: 'AVISO DEL SISTEMA',
                        content: 'No existe Archivo a Vizualizar!!!',
                        animation: 'zoom',
                        closeAnimation: 'zoom',
                        type: 'red',
                        typeAnimated: true
                    });
                }
            } else if ($.trim($(opcion).text()) === "Adjuntar Documento") {
                if (tipo === 'S') {
                    mode = 'C';
                    fn_AdjuntarArchivo();
                } else {
                    $.alert({
                        theme: 'material',
                        title: 'AVISO DEL SISTEMA',
                        content: 'Opción no permitida para el ingreso de documentos',
                        animation: 'zoom',
                        closeAnimation: 'zoom',
                        type: 'orange',
                        typeAnimated: true
                    });
                }
            } else {
                $.alert({
                    theme: 'material',
                    title: 'AVISO DEL SISTEMA',
                    content: "No hay Opción a mostrar",
                    animation: 'zoom',
                    closeAnimation: 'zoom',
                    type: 'orange',
                    typeAnimated: true
                });
            }
        });
        //SELECCIONAMOS UN REGISTRO DE LA GRILLA
        $("#div_GrillaPrincipal").on('rowselect', function (event) {
            var args = event.args;
            var row = $("#div_GrillaPrincipal").jqxGrid('getrowdata', args.rowindex);
            codigo = row['numero'];
            estado = row['estado'];
            archivo = row['archivo'];
        });
        //CREA LOS ELEMENTOS DE LAS VENTANAS
        var customButtonsDemo = (function () {
            function _createElements() {
                //INICIA LOS VALORES DE LA VENTANA
                var posicionX, posicionY;
                var ancho = 600;
                var alto = 365;
                posicionX = ($(window).width() / 2) - (ancho / 2);
                posicionY = ($(window).height() / 2) - (alto / 2);
                $('#div_VentanaPrincipal').jqxWindow({
                    position: {x: posicionX, y: posicionY},
                    width: ancho, height: alto, resizable: false,
                    cancelButton: $('#btn_Cancelar'),
                    initContent: function () {
                        $("#txt_Numero").jqxInput({width: 120, height: 20, disabled: true});
                        $("#txt_Institucion").jqxInput({height: 20, width: 450, minLength: 1, items: 15, source: dataAdapterInstitucion});
                        $('#txt_Institucion').on('select', function (event) {
                            if (event.args) {
                                var item = event.args.item;
                                if (item) {
                                    institucion = item.value;
                                }
                            }
                        });
                        $("#cbo_Prioridad").jqxDropDownList({animationType: 'fade', width: 200, height: 20});
                        $("#cbo_Documento").jqxDropDownList({animationType: 'fade', width: 200, height: 20});
                        $("#txt_NumeroDocumento").jqxInput({width: 150, height: 20});
                        $("#cbo_Clasificacion").jqxDropDownList({animationType: 'fade', width: 200, height: 20});
                        $("#div_FechaDocumento").jqxDateTimeInput({culture: 'es-PE', animationType: 'fade', width: 150, height: 20});
                        $("#div_FechaRecepcion").jqxDateTimeInput({culture: 'es-PE', width: 150, height: 20, disabled: true});
                        $("#txt_Asunto").jqxInput({placeHolder: "Ingrese el asunto", width: 450, height: 20});
                        $("#txt_PostFirma").jqxInput({placeHolder: "Ingrese la Post Firma", width: 450, height: 20});
                        $("#div_Legajos").jqxNumberInput({width: 50, height: 20, max: 999, digits: 3, decimalDigits: 0});
                        $("#div_Folios").jqxNumberInput({width: 50, height: 20, max: 999, digits: 3, decimalDigits: 0});
                        $("#txt_Archivo").jqxInput({width: 450, height: 20});
                        $('#btn_Cancelar').jqxButton({width: '65px', height: 25});
                        $('#btn_Guardar').jqxButton({width: '65px', height: 25});
                        $('#btn_Guardar').on('click', function () {
                            msg = "";
                            msg += fn_validaCampo(institucion, "Seleccione la Institución.");
                            msg += fn_validaCombos('#cbo_Prioridad', "Seleccione la Prioridad.");
                            msg += fn_validaCombos('#cbo_Documento', "Seleccione el Tipo de Documento.");
                            msg += fn_validaCombos('#cbo_Clasificacion', "Seleccione la Clasificación.");
                            if (msg === "") {
                                $('#frm_MesaPartes').jqxValidator('validate');
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
                        });
                        $('#frm_MesaPartes').jqxValidator({
                            rules: [
                                {input: '#txt_Institucion', message: 'Ingrese el nombre de la institución', action: 'keyup, blur', rule: 'required'},
                                {input: '#txt_NumeroDocumento', message: 'Ingrese el Numero de Documento!', action: 'keyup, blur', rule: 'required'},
                                {input: '#txt_Asunto', message: 'Ingrese el Asunto!', action: 'keyup, blur', rule: 'required'},
                                {input: '#div_FechaDocumento', message: 'Ingrese la fecha de documento', action: 'keyup, blur', rule: 'required'},
                                {input: '#txt_PostFirma', message: 'Ingrese la firma!', action: 'keyup, blur', rule: 'required'},
                                {input: '#txt_Archivo', message: 'Seleccione un Archivo!', action: 'keyup, blur', rule: 'required'}
                            ]
                        });
                        $('#frm_MesaPartes').jqxValidator({
                            onSuccess: function () {
                                fn_GrabarDatos();
                            }
                        });
                    }
                });
                //VENTANA DE DETALLE DE ANULACION
                ancho = 500;
                alto = 175;
                posicionX = ($(window).width() / 2) - (ancho / 2);
                posicionY = ($(window).height() / 2) - (alto / 2);
                $('#div_DetalleAnulacion').jqxWindow({
                    position: {x: posicionX, y: posicionY},
                    width: ancho, height: alto, resizable: false,
                    cancelButton: $('#btn_CancelarAnulacion'),
                    initContent: function () {
                        $("#txt_ComentarioAnulacion").jqxInput({placeHolder: 'Ingrese detalle de anulación', height: 90, width: 430, maxLength: 500, minLength: 1});
                        $("#txt_ComentarioAnulacion").val("");
                        $('#btn_CancelarAnulacion').jqxButton({width: '65px', height: 25});
                        $('#btn_GuardarAnulacion').jqxButton({width: '65px', height: 25});
                        $('#btn_GuardarAnulacion').on('click', function (event) {
                            var msg = "";
                            if (msg === "") {
                                $('#frm_AnulacionDocumento').jqxValidator('validate');
                            }
                        });
                        $('#frm_AnulacionDocumento').jqxValidator({
                            rules: [
                                {input: '#txt_ComentarioAnulacion', message: 'Ingrese el detalle de la anulación', action: 'keyup, blur', rule: 'required'},
                                {input: '#txt_ComentarioAnulacion', message: 'Ingrese sustento de la anulación', action: 'keyup', rule: 'length=5,500'}
                            ]
                        });
                        $('#frm_AnulacionDocumento').jqxValidator({
                            onSuccess: function () {
                                fn_GrabarDatosAnulacion();
                            }
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
        });
        //FUNCION PARA ABRIR VENTANA DE ANULACION
        function fn_AnularDocumento() {
            $("#txt_ComentarioAnulacion").val('');
            $("#txt_ComentarioAnulacion").jqxInput('focus');
            $('#div_DetalleAnulacion').jqxWindow({isModal: true});
            $('#div_DetalleAnulacion').jqxWindow('open');
        }
        //FUNCION PARA ADJUNTAR ARCHIVO
        function fn_AdjuntarArchivo() {
            $.confirm({
                title: 'ADJUNTAR DOCUMENTO',
                type: 'blue',
                content: '' +
                        '<form method="post"  name="frm_AdjuntarDocumento" id="frm_AdjuntarDocumento" action="../IduMesaPartes" enctype="multipart/form-data">' +
                        '<label>Documento : </label>' +
                        '<input type="file" name="fichero" id="fichero" style="text-transform: uppercase; width= 600px" class="name form-control" multiple/>' +
                        '</form>',
                buttons: {
                    formSubmit: {
                        text: 'Enviar',
                        btnClass: 'btn-blue',
                        action: function () {
                            fn_GuardarDocumento();
                        }
                    },
                    cancel: function () {
                    }
                },
                onContentReady: function () {
                    // bind to events
                    var jc = this;
                    this.$content.find('form').on('submit', function (e) {
                        // if the user submits the form by pressing enter in the field.
                        e.preventDefault();
                        jc.$$formSubmit.trigger('click'); // reference the button and click it
                    });
                }
            });
        }
        //FUNCION PARA GRABAR EL DETALLE DE LA ANULACION DEL DOCUMENTO
        function fn_GrabarDatosAnulacion() {
            var comentarioAnulacion = $("#txt_ComentarioAnulacion").val();
            $.ajax({
                type: "POST",
                url: "../IduMesaPartes",
                data: {mode: mode, periodo: periodo, tipo: tipo, numero: codigo, asunto: comentarioAnulacion},
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
                                        $('#div_DetalleAnulacion').jqxWindow('close');
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
        //FUNCION PARA ADJUNTAR EL DOCUMENTO DE SALIDA
        function fn_GuardarDocumento() {
            var fichero = $("#fichero").val();
            if (fichero !== '') {
                var formData = new FormData(document.getElementById("frm_AdjuntarDocumento"));
                formData.append("mode", mode);
                formData.append("periodo", periodo);
                formData.append("tipo", tipo);
                formData.append("numero", codigo);
                $.ajax({
                    type: "POST",
                    url: "../IduMesaPartes",
                    data: formData,
                    dataType: "html",
                    cache: false,
                    contentType: false,
                    processData: false,
                    success: function (data) {
                        msg = data;
                        if (msg === "GUARDO") {
                            $.confirm({
                                title: 'AVISO DEL SISTEMA',
                                content: 'Datos procesados correctamente',
                                type: 'green',
                                typeAnimated: true,
                                autoClose: 'cerrarAction|1000',
                                buttons: {
                                    cerrarAction: {
                                        text: 'Cerrar',
                                        action: function () {
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
            } else {
                $.alert({
                    theme: 'material',
                    title: 'AVISO DEL SISTEMA',
                    content: "Debe Seleccionar un Archivo a subir\n Proceso cancelado!!!.",
                    animation: 'zoom',
                    closeAnimation: 'zoom',
                    type: 'red',
                    typeAnimated: true
                });
            }
        }
        //FUNCION PARA ACTUALIZAR DATOS DE LA GRILLA
        function fn_Refrescar() {
            $("#div_GrillaPrincipal").remove();
            $("#div_VentanaPrincipal").remove();
            $("#div_DetalleAnulacion").remove();
            $("#div_ContextMenu").remove();
            var $contenidoAjax = $('#div_Detalle').html('<img src="../Imagenes/Fondos/cargando.gif">');
            $.ajax({
                type: "POST",
                url: "../MesaPartes",
                data: {mode: 'G', periodo: periodo, mes: mes, tipo: tipo, codigo: FechaBus},
                success: function (data) {
                    $contenidoAjax.html(data);
                }
            });
        }
        //FUNCION PARA CARGAR VENTANA DE NUEVO REGISTRO
        function fn_NuevoRegistro() {
            if (tipo === 'S') {
                fn_cargarComboAjax("#cbo_Usuario", {mode: 'usuarioMesaPartes', periodo: periodo, codigo: $("#cbo_Area").val()});
            }
            $.ajax({
                type: "POST",
                url: "../MesaPartes",
                data: {mode: mode, periodo: periodo, mes: mes, tipo: tipo},
                success: function (data) {
                    $('#txt_Numero').val(data);
                }
            });
            $("#cbo_Prioridad").jqxDropDownList('selectItem', '2');
            $("#cbo_Documento").jqxDropDownList('selectItem', '1');
            $("#txt_Asunto").val('');
            $("#cbo_Clasificacion").jqxDropDownList('selectItem', '1');
            $("#txt_Institucion").val('');
            $("#div_FechaRecepcion").val(new Date);
            $("#txt_NumeroDocumento").val('');
            $("#txt_PostFirma").val('');
            $("#div_Legajos").val('0');
            $("#div_Folios").val('0');
            $('#div_VentanaPrincipal').jqxWindow({isModal: true, modalOpacity: 0.9});
            $('#div_VentanaPrincipal').jqxWindow('open');
        }
        //FUNCION PARA CARGAR VENTANA PARA EDITAR REGISTRO
        function fn_EditarRegistro() {
            $.ajax({
                type: "GET",
                url: "../MesaPartes",
                data: {mode: mode, periodo: periodo, mes: mes, tipo: tipo, codigo: codigo},
                success: function (data) {
                    var dato = data.split("+++");
                    if (dato.length === 13) {
                        $("#txt_Numero").val(dato[0]);
                        institucion = dato[1];
                        $("#txt_Institucion").val(dato[2]);
                        $("#cbo_Prioridad").jqxDropDownList('selectItem', dato[3]);
                        $("#cbo_Documento").jqxDropDownList('selectItem', dato[4]);
                        $("#txt_NumeroDocumento").val(dato[5]);
                        $("#cbo_Clasificacion").jqxDropDownList('selectItem', dato[6]);
                        $('#div_FechaDocumento').jqxDateTimeInput('setDate', dato[7]);
                        $('#div_FechaRecepcion').jqxDateTimeInput('setDate', dato[8]);
                        $("#txt_Asunto").val(dato[9]);
                        $("#txt_PostFirma").val(dato[10]);
                        $("#div_Legajos").val(dato[11]);
                        $("#div_Folios").val(dato[12]);
                    }
                }
            });
            $('#div_VentanaPrincipal').jqxWindow({isModal: true});
            $('#div_VentanaPrincipal').jqxWindow('open');
        }
        //FUNCION PARA GRABAR LOS DATOS DE LA VENTANA PRINCIPAL
        function fn_GrabarDatos() {
            var numero = $("#txt_Numero").val();
            archivo = $("#txt_Archivo").val();
            if (archivo !== '') {
                var formData = new FormData(document.getElementById("frm_MesaPartes"));
                formData.append("mode", mode);
                formData.append("periodo", periodo);
                formData.append("tipo", tipo);
                formData.append("numero", numero);
                formData.append("mes", mes);
                formData.append("institucion", institucion);
                formData.append("prioridad", $("#cbo_Prioridad").val());
                formData.append("documento", $("#cbo_Documento").val());
                formData.append("numeroDocumento", $("#txt_NumeroDocumento").val());
                formData.append("clasificacion", $("#cbo_Clasificacion").val());
                formData.append("fechaDocumento", $("#div_FechaDocumento").val());
                formData.append("asunto", $("#txt_Asunto").val());
                formData.append("postFirma", $("#txt_PostFirma").val());
                formData.append("legajos", $("#div_Legajos").val());
                formData.append("folios", $("#div_Folios").val());
                formData.append("archivo", $("#txt_Archivo").val());
                $.ajax({
                    type: "POST",
                    url: "../IduMesaPartes",
                    data: formData,
                    dataType: "html",
                    cache: false,
                    contentType: false,
                    processData: false,
                    success: function (data) {
                        msg = data;
                        if (msg === "GUARDO") {
                            $.confirm({
                                title: 'AVISO DEL SISTEMA',
                                content: 'Datos procesados correctamente',
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
            } else {
                $.alert({
                    theme: 'material',
                    title: 'AVISO DEL SISTEMA',
                    content: "Debe Seleccionar un Archivo a subir\n Proceso cancelado!!!.",
                    animation: 'zoom',
                    closeAnimation: 'zoom',
                    type: 'red',
                    typeAnimated: true
                });
            }
        }
    });
</script>
<div id="div_GrillaPrincipal"></div>
<div id="div_VentanaPrincipal" style="display: none">
    <div>
        <span style="float: left">REGISTRO MESA DE PARTES</span>
    </div>
    <div style="overflow: hidden">
        <form id="frm_MesaPartes" name="frm_MesaPartes" enctype="multipart/form-data" action="javascript: fn_GrabarDatos();" method="post">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="inputlabel">Correlativo : </td>
                    <td><input type="text" id="txt_Numero" name="txt_Numero"/></td>
                </tr>
                <tr>
                    <td class="inputlabel">Instituci&oacute;n : </td>
                    <td><input type="text" id="txt_Institucion" name="txt_Institucion" style="text-transform: uppercase;" autocomplete="off"/></td>
                </tr>
                <tr>
                    <td class="inputlabel">Prioridad : </td>
                    <td>
                        <select id="cbo_Prioridad" name="cbo_Prioridad">
                            <option value="0">Seleccione</option>
                            <c:forEach var="d" items="${objPrioridades}">   
                                <option value="${d.codigo}">${d.descripcion}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="inputlabel">Documento : </td>
                    <td>
                        <select id="cbo_Documento" name="cbo_Documento">
                            <option value="0">Seleccione</option>
                            <c:forEach var="d" items="${objDocumentos}">
                                <option value="${d.codigo}">${d.descripcion}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="inputlabel">Nro Documento : </td>
                    <td><input type="text" id="txt_NumeroDocumento" name="txt_NumeroDocumento"/></div></td>
                </tr>
                <tr>
                    <td class="inputlabel">Clasificaci&oacute;n : </td>
                    <td>
                        <select id="cbo_Clasificacion" name="cbo_Clasificacion">
                            <option value="0">Seleccione</option>
                            <c:forEach var="d" items="${objClasificaciones}">
                                <option value="${d.codigo}">${d.descripcion}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="inputlabel">Fec. Documento : </td>
                    <td><div id="div_FechaDocumento"></div>
                </tr>
                <tr>
                    <td class="inputlabel">Fec. Recepci&oacute;n : </td>
                    <td><div id="div_FechaRecepcion"></td>
                </tr>
                <tr>
                    <td class="inputlabel">Asunto : </td>
                    <td><input type="text" id="txt_Asunto" name="txt_Asunto" style="text-transform: uppercase;"/></td>
                </tr>
                <tr>
                    <td class="inputlabel">Post Firma : </td>
                    <td><input type="text" id="txt_PostFirma" name="txt_PostFirma" style="text-transform: uppercase;"/></td>
                </tr>
                <tr>
                    <td class="inputlabel">Legajos : </td>
                    <td><div id="div_Legajos"></div></td>
                </tr>
                <tr>
                    <td class="inputlabel">Folios : </td>
                    <td><div id="div_Folios"></div></td>
                </tr>
                <tr>
                    <td class="inputlabel">Archivo : </td>
                    <td><input type="file" name="txt_Archivo" id="txt_Archivo" style="text-transform: uppercase;"/></td>
                </tr>
                <tr>
                    <td class="Summit" colspan="4">
                        <div>
                            <input type="button" id="btn_Guardar"  value="Guardar" style="margin-right: 20px"/>
                            <input type="button" id="btn_Cancelar" value="Cancelar" style="margin-right: 20px"/>
                        </div>
                    </td>
                </tr>
            </table>  
        </form>
    </div>
</div>
<div id="div_DetalleAnulacion" style="display: none">
    <div>
        <span style="float: left">DETALLE ANULACIÓN DEL DOCUMENTO</span>
    </div>
    <div style="overflow: hidden">
        <form id="frm_AnulacionDocumento" name="frm_AnulacionDocumento" method="post" >
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="inputlabel">Detalle : </td>
                    <td><textarea id="txt_ComentarioAnulacion" name="txt_ComentarioAnulacion" style="text-transform: uppercase;"/></textarea></td> 
                </tr>
                <tr>
                    <td class="Summit" colspan="2">
                        <div>
                            <input type="button" id="btn_GuardarAnulacion"  value="Guardar" style="margin-right: 20px"/>
                            <input type="button" id="btn_CancelarAnulacion" value="Cancelar" style="margin-right: 20px"/>
                        </div>
                    </td>
                </tr>
            </table>  
        </form>
    </div>
</div>
<div id='div_ContextMenu' style='display:none;'>
    <ul>
        <li style="font-weight: bold">Editar</li>
        <li style="font-weight: bold">Anular</li> 
        <li type='separator'></li>
        <li style="font-weight: bold; color: maroon">Ver Documento</li>
        <li type='separator'></li>
        <li style="font-weight: bold; color: blue">Adjuntar Documento</li>
    </ul>
</div>