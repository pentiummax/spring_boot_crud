function updateTable() {
    fetch("http://localhost:8080/admin/api/users/").then(response => response.json()).then(users => {
        let table_body = document.getElementById('table_data');
        table_body.innerHTML = '';
        users.forEach(user => {
            let roles = '';
            user.roles.forEach(role => roles += role + " ");
            table_body.innerHTML += "<tr><td>" + user.id + "</td>" +
                "<td>" + user.firstName + "</td>" +
                "<td>" + user.lastName + "</td>" +
                "<td>" + user.age + "</td>" +
                "<td>" + user.email + "</td>" +
                "<td>" + roles + "</td>" +
                "<td><button type='submit' class='btn btn-info' data-toggle='modal'  data-target='#editModal' onclick='setModalUserDataEdit(" + user.id + ")'>Edit</button></td>" +
                "<td><button type='submit' class='btn btn-danger' data-toggle='modal' data-target='#deleteModal' onclick='setModalUserDataDelete(" + user.id + ")'>Delete</button></td>" +
                "</tr>"
        })
    })
}

showUsersTablePage()

$("#editUserButton").click(function (event) {

    event.preventDefault();

    fetch("http://localhost:8080/admin/api/users/", {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            id: $('#edit_id').val(),
            firstName: $('#edit_firstname').val(),
            lastName: $('#edit_lastname').val(),
            age: $('#edit_age').val(),
            email: $('#edit_email').val(),
            password: $('#edit_password').val(),
            roles: $('#edit_roles').val()
        })
    }).then(closeModal).then(updateTable)

    function closeModal() {
        $('#editModal').modal('hide');
    }

})

$("#deleteUserButton").click(function (event) {
    event.preventDefault();
    fetch("http://localhost:8080/admin/api/users/" + document.getElementById("delete_id").value, {
        method: "DELETE"
    }).then(closeModal).then(updateTable)

    function closeModal() {
        $("#deleteModal").modal("hide");
    }
})

$("#newUserButton").click(function (event) {

    event.preventDefault();
    fetch("http://localhost:8080/admin/api/users", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            id: 0,
            firstName: $('#firstname').val(),
            lastName: $('#lastname').val(),
            age: $('#age').val(),
            email: $('#email').val(),
            password: $('#password').val(),
            roles: $('#roles').val()
        })
    }).then(showUsersTablePage)

})

function showUsersTablePage() {
    $("#new_user_page").hide();
    updateTable();
    $("#users_table_page").show();
}

function showNewUserPage() {
    $("#users_table_page").hide();
    $("#new_user_page").show();
}

function setModalUserDataDelete(id) {
    fetch("http://localhost:8080/admin/api/users/" + id).then(response => response.json()).then(user => {
        document.getElementById('delete_id').value = user.id;
        document.getElementById('delete_firstname').value = user.firstName;
        document.getElementById('delete_lastname').value = user.lastName;
        document.getElementById('delete_age').value = user.age;
        document.getElementById('delete_email').value = user.email;
    })
}

function setModalUserDataEdit(id) {
    fetch("http://localhost:8080/admin/api/users/" + id).then(response => response.json()).then(user => {
        document.getElementById('edit_id').value = user.id;
        document.getElementById('edit_firstname').value = user.firstName;
        document.getElementById('edit_lastname').value = user.lastName;
        document.getElementById('edit_age').value = user.age;
        document.getElementById('edit_email').value = user.email;
        document.getElementById('edit_password').value = "";
    })
}