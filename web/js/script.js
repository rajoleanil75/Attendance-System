function validate_admin() {
    var aid=sessionStorage.getItem("role");
    if(aid!=4)
        window.location="http://192.168.100.175:9090/feedback/admin.html";
}
function validate_stud() {
    var sid=sessionStorage.getItem("role");
    if(sid!=1)
        window.location="http://192.168.100.175:9090/feedback/index.html";
}
function validate_hod() {
    var sid=sessionStorage.getItem("role");
    if(sid!=3)
        window.location="http://192.168.100.175:9090/feedback/hod.html";
}
function validate_teacher() {
    var sid=sessionStorage.getItem("role");
    if(sid!=2)
        window.location="http://192.168.100.175:9090/feedback/teacher.html";
}
function logout() {
    sessionStorage.setItem("role","0");
    window.location="http://192.168.100.175:9090/feedback/index.html";
}
function logout_stud() {
    sessionStorage.setItem("role","0");
    window.location="http://192.168.100.175:9090/feedback/";
}
function logout_admin() {
    sessionStorage.setItem("role","0");
    window.location="http://192.168.100.175:9090/feedback/admin.html";
}
function logout_hod() {
    sessionStorage.setItem("role","0");
    window.location="http://192.168.100.175:9090/feedback/hod.html";
}
function logout_teacher() {
    sessionStorage.setItem("role","0");
    window.location="http://192.168.100.175:9090/feedback/teacher.html";
}