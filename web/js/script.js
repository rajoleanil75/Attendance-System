function validate_admin() {
    var aid=sessionStorage.getItem("role");
    if(aid!=4)
        window.location="http://localhost:8080/attendance_war_exploded/admin.html";
}
function validate_stud() {
    var sid=sessionStorage.getItem("role");
    if(sid!=1)
        window.location="http://localhost:8080/attendance_war_exploded/index.html";
}
function validate_hod() {
    var sid=sessionStorage.getItem("role");
    if(sid!=3)
        window.location="http://localhost:8080/attendance_war_exploded/hod.html";
}
function validate_teacher() {
    var sid=sessionStorage.getItem("role");
    if(sid!=2)
        window.location="http://localhost:8080/attendance_war_exploded/";
}
function logout() {
    sessionStorage.setItem("role","0");
    window.location="http://localhost:8080/attendance_war_exploded/index.html";
}
function logout_stud() {
    sessionStorage.setItem("role","0");
    window.location="http://localhost:8080/attendance_war_exploded/";
}
function logout_admin() {
    sessionStorage.setItem("role","0");
    window.location="http://localhost:8080/attendance_war_exploded/admin.html";
}
function logout_hod() {
    sessionStorage.setItem("role","0");
    window.location="http://localhost:8080/attendance_war_exploded/hod.html";
}
function logout_teacher() {
    sessionStorage.setItem("role","0");
    window.location="http://localhost:8080/attendance_war_exploded/";
}
function tConvert (time) {
    // Check correct time format and split into components
    time = time.toString ().match (/^([01]\d|2[0-3])(:)([0-5]\d)(:[0-5]\d)?$/) || [time];

    if (time.length > 1) { // If time format correct
        time = time.slice (1);  // Remove full string match value
        time[5] = +time[0] < 12 ? 'AM' : 'PM'; // Set AM/PM
        time[0] = +time[0] % 12 || 12; // Adjust hours
    }
    return time.join (''); // return adjusted time or original string
}