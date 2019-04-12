function link1() {
    var hidden = document.getElementById("frmConfirmaRen:link1");
    hidden.click();
}
function link2() {
    var hidden = document.getElementById("frmConfirmaRen:link2");
    hidden.click();
}
function link3() {
    var hidden = document.getElementById("frmConfirmaRen:link3");
    hidden.click();
}
function foco1() {
    document.getElementById('frmMantRendicion:txtMontoTotal').blur();
}
function foco2() {
    document.getElementById('frmMantRendicion:txtImpuesto').blur();
}
function calcular() {
    var monto = parseFloat(document.getElementById('frmMantRendicion:txtMonto').value);
    var desc = parseFloat(document.getElementById('frmMantRendicion:txtDescuento').value);
    var imp = parseFloat(document.getElementById('frmMantRendicion:txtImpuesto').value);
    var por = parseFloat(document.getElementById('frmMantRendicion:txtPorcentaje').value);
    var total1 = parseFloat(0);
    var total2 = parseFloat(0);
    if (monto !== null) {
        total1 = total1 + monto;
    }
    if (desc !== null) {
        total1 = total1 - desc;
    }
    if (total1 !== null) {
        var igv = por;
        imp = (por * total1) / 100;
    }
    total2 = total1 + imp;
    document.getElementById('frmMantRendicion:txtMontoTotal').value = total2;
    document.getElementById('frmMantRendicion:txtImpuesto').value = imp;
}
function calcularTotal() {
    var monto;
    var imp;
    var montot = parseFloat(document.getElementById('frmMantRendicion:txtMontoTotal').value);
    var por = parseFloat(document.getElementById('frmMantRendicion:txtPorcentaje').value);
    monto = ((100 - por) * montot) / 100;
    imp = (por * montot) / 100;
    document.getElementById('frmMantRendicion:txtImpuesto').value = imp;
    document.getElementById('frmMantRendicion:txtMonto').value = monto;
}