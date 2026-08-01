function login() {

    var request = {

        email: document.getElementById("email").value.trim(),
        password: document.getElementById("password").value.trim()

    };

    if (request.email === "" || request.password === "") {

        alert("Please enter Email and Password.");
        return;

    }

    var xhr = new XMLHttpRequest();

    xhr.open("POST", "/api/login", true);

    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function () {

        if (xhr.readyState === 4) {

            if (xhr.status === 200) {

                var result = JSON.parse(xhr.responseText);

                alert(result.message);

                if (result.message === "Login Successful") {

                    sessionStorage.setItem("userId", result.userId);
                    sessionStorage.setItem("role", result.role);

                    if (result.companyId != null) {

                        sessionStorage.setItem("companyId", result.companyId);

                    }

                    if (result.role === "RECRUITER") {

                        window.location.href = "/recruiter-dashboard";

                    } else {

                        window.location.href = "/candidate-dashboard";

                    }

                }

            } else {

                alert("Login Failed");

            }

        }

    };

    xhr.send(JSON.stringify(request));

}