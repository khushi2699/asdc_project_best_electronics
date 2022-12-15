function ShowHideDiv() {
var chkYes = document.getElementById("Card");
var dvPassport = document.getElementById("cardDetails");
var dvPassport1 = document.getElementById("cardDetails1");
dvPassport.style.display = chkYes.checked ? "block" : "none";
dvPassport1.style.display = chkYes.checked ? "block" : "none";
}