const url = 'http://localhost:8080/api/admin';
const input = document.querySelector('#tableUsers tbody');

let temp = '';
$(document).ready(function () {
    getUsers();
});

function getUsers() {
    fetch('/api/users')
        .then(response => response.json())
        .then(users => {
            users.forEach(user => {
                temp += '<tr>';
                temp += '<td>' + user.id + '</td>';
                temp += '<td>' + user.username + '</td>';
                temp += '<td>' + user.surname + '</td>';
                temp += '<td>' + user.age + '</td>';
                temp += '<td>' + user.stringRoles + '</td>';
                temp += '<td>' +
                    '<button type="button" class="btn btn-info" data-target="#editModal" data-toggle="modal" ' +
                    'onclick="openEditModal(' + user.id + ')">' +
                    'Edit' +
                    '</button>' +
                    '</td>';
                temp += '<td>' +
                    '<button type="button" class="btn btn-danger" data-target="#deleteModal"data-toggle="modal" ' +
                    'onclick="openDeleteModal(' + user.id + ')">' +
                    'Delete' +
                    '</button>' +
                    '</td>';
                temp += '</tr>';
            });
            input.innerHTML = temp;

            temp = '';
        });
}
function openEditModal(id) {
    fetch("/api/users/" + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(userEdit => {
            document.getElementById('idEdit').value = userEdit.id;
            document.getElementById('usernameEdit').value = userEdit.username;
            document.getElementById('surnameEdit').value = userEdit.surname;
            document.getElementById('passwordEdit').value = userEdit.password;
            document.getElementById('ageEdit').value = userEdit.age;
            document.getElementById('rolesEdit').value = userEdit.stringRoles;

        })
    });
}
function openDeleteModal(id) {
    fetch("/api/users/" + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(userDelete => {
            document.getElementById('idDelete').value = userDelete.id;
            document.getElementById('usernameDelete').value = userDelete.username;
            document.getElementById('surnameDelete').value = userDelete.surname;
            document.getElementById('passwordDelete').value = userDelete.password;
            document.getElementById('ageDelete').value = userDelete.age;
            document.getElementById('rolesDelete').value = userDelete.roles;

        })
    });
}
function deleteUser() {
    event.preventDefault();
    let id = document.getElementById('idDelete').value;

    fetch('/api/users/' + id, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        },

    })
        .then(() => {
            $("#deleteModal .close").click();
            getUsers()

        })

}
function editUser() {
    event.preventDefault();
    let id = document.getElementById('idEdit').value;
    let username = document.getElementById('usernameEdit').value;
    let surname = document.getElementById('surnameEdit').value;
    let age = document.getElementById('ageEdit').value;
    let password = document.getElementById('passwordEdit').value;
    let roles = $("#rolesEdit").val()


    for (let i = 0; i < roles.length; i++) {
        if (roles[i] === 'ROLE_ADMIN') {
            roles[i] = {
                'id': 1,
                'role': 'ROLE_ADMIN'
            }
        }
        if (roles[i] === 'ROLE_USER') {
            roles[i] = {
                'id': 2,
                'role': 'ROLE_USER'
            }
        }
    }

    fetch('/api/users', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify({
            'id': id,
            'username':  username,
            'surname': surname,
            'age': age,
            'password': password,
            'roles': roles
        })
    })
        .then(() => {
            $("#editModal .close").click();
            getUsers()

        })

}

function addUser() {
    let username = document.getElementById('usernameAdd').value;
    let surname = document.getElementById('surnameAdd').value;
    let age = document.getElementById('ageAdd').value;
    let password = document.getElementById('passwordAdd').value;
    let roles = $("#addRolesUser").val()

    fetch('/api/users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify({
          'username':  username,
           'surname': surname,
           'age': age,
           'password': password,
            'roles': roles
        })
    })
        .then(() => {
            document.getElementById('nav-home-tab').click()
            getUsers()
            document.formNewUser.reset()
        })

}