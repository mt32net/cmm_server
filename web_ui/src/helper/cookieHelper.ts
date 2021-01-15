function getCookie(cname: string): string {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function getDecodedJWT(): string {
    var cookieRAW = getCookie("cmmJWT")
    if (cookieRAW != "") {
        var encodedPayload = cookieRAW.split(".")[1]
        return atob(encodedPayload)
    }
    else return ""
}

export function getJSONJWT(): any {
    var json = getDecodedJWT()
    if (json != "")
        return JSON.parse(json)
    else return ""
}

export function delete_cookie(name: string, path: String, domain: String) {
    if (getCookie(name)) {
        document.cookie = name + "=" +
            ((path) ? ";path=" + path : "") +
            ((domain) ? ";domain=" + domain : "") +
            ";expires=Thu, 01 Jan 1970 00:00:01 GMT";
    }
}

export function isLoggedIn(): Boolean {
    return getJSONJWT() != ""
}