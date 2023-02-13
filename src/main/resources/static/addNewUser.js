const url = 'http://localhost:8080/api/admin';
const input = document.getElementById("tableUsers");
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
                temp += '<td>' + user.lastName + '</td>';
                temp += '<td>' + user.age + '</td>';
                temp += '<td>' + user.stringRoles + '</td>';
                temp += '<td>' +
                    '<button type="submit">' +
                        'Edit' +
                    '</button>' + '</td>';
                    // '<button type="button" class="btn btn-info" data-toggle="modal" ' +
                    // 'onclick="edit(' + user.id + ')">' +
                    // 'Edit' +
                    // '</button>' +
                    // </td>;
                temp += '<td>' +
                    '<button type="submit">' +
                    'Delete' +
                    '</button>' + '</td>';
                    // '<button type="button" class="btn btn-danger" data-toggle="modal" ' +
                    // 'onclick="deleteUser(' + user.id + ')">' +
                    // 'Delete' +
                    // '</button>' +
                    // '</td>';
                temp += '</tr>';
            });
            input.innerHTML = temp;
            temp = '';
        });
}
