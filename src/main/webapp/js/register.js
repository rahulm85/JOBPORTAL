window.onload = function () {

    toggleCompanyFields();

};

function toggleCompanyFields() {

    var role = document.getElementById("role").value;

    var section = document.getElementById("companyFields");

    if (role === "RECRUITER") {

        section.style.display = "block";

    } else {

        section.style.display = "none";

    }

}

function register() {

    var request = {

        fullName: document.getElementById("fullName").value.trim(),
        email: document.getElementById("email").value.trim(),
        password: document.getElementById("password").value.trim(),
        phone: document.getElementById("phone").value.trim(),
        role: document.getElementById("role").value,

        companyName: "",
        description: "",
        website: "",
        location: ""

    };

    if (request.role === "RECRUITER") {

        request.companyName = document.getElementById("companyName").value.trim();
        request.description = document.getElementById("description").value.trim();
        request.website = document.getElementById("website").value.trim();
        request.location = document.getElementById("location").value.trim();

    }

    var xhr = new XMLHttpRequest();

    xhr.open("POST", "/api/register", true);

    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function () {

        if (xhr.readyState === 4) {

            if (xhr.status === 200) {

                alert(xhr.responseText);

                if (xhr.responseText === "Registration Successful") {

                    window.location.href = "/login";

                }

            } else {

                alert("Registration Failed");

            }

        }

    };

    xhr.send(JSON.stringify(request));

}