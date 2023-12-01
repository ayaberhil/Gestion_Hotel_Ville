<%@page import="entities.Hotel"%>
<%@page import="entities.Ville"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Hôtels</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }

        form {
            margin-bottom: 20px;
            background-color: #fff;
            padding: 15px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        input[type="text"] {
            padding: 10px;
            width: 200px;
        }

        button {
            padding: 10px 15px;
            cursor: pointer;
            background-color: #4caf50;
            color: #fff;
            border: none;
            border-radius: 3px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #4caf50;
            color: #fff;
        }

        tr:hover {
            background-color: #f5f5f5;
        }

        /* Modal styles */
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgb(0,0,0);
            background-color: rgba(0,0,0,0.4);
            padding-top: 60px;
        }

        .modal-content {
            background-color: #fefefe;
            margin: 5% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
    </style>
</head>
<body>
<form action="/ISICEJBWeb/Home.jsp" class="mb-3">
            <button  class="btn btn-primary btn-block">Gestion</button>
        </form>
        
<form action="HotelController" method="post" class="mb-4">
    <div class="form-group">
        <label for="filterVille">Filtrer by Ville:</label>
        <select name="filterVille" class="form-control">
            <option value="0">Sélectionnez</option>
            <c:forEach items="${villes}" var="ville">
                <option value="${ville.id}">${ville.nom}</option>
            </c:forEach>
        </select>
        <input type="hidden" name="action" value="filterByVille">
         <button type="submit" class="btn btn-primary">Filtrer</button>
    </div>
   
</form>

<form action="HotelController" method="post">
    Nom : <input type="text" name="hotel" /> 
    Adresse : <input type="text" name="adresse" />
    Téléphone : <input type="text" name="telephone" />
    Ville :
    <select name="ville" class="form-control" required>
    <option value="0">Sélectionnez</option>
        <c:forEach items="${villes}" var="v">
            <option value="${v.id}">${v.nom}</option>
        </c:forEach>
    </select>
    <input type="hidden" name="villeId" value="${v.id}" /> <!-- Add this line -->
    <input type="hidden" name="action" value="create" />
    <button type="submit">Enregistrer</button>
</form>

<h1>Liste des Hotels :</h1>
<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Adresse</th>
            <th>Téléphone</th>
            <th>Ville</th>
            <th>Supprimer</th>
            <th>Modifier</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${hotels}" var="h">
            <tr>
                <td>${h.id}</td>
                <td>${h.nom}</td>
                <td>${h.adresse}</td>
                <td>${h.telephone}</td>
                <td>${h.ville.nom}</td>

                <td class="actions">
                    <form action="HotelController" method="post">
                        <input type="hidden" name="action" value="delete" />
                        <input type="hidden" name="Id" value="${h.id}" />
                        <button type="submit">Supprimer</button>
                    </form>
                </td>

                <td>                        
                    <button class="open-modal" data-hotel-id="${h.id}">Modifier</button>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<!-- Modal for modification form -->
<div id="myModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <form action="HotelController" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="hotelId" id="modalHotelId" />
            Nom : <input type="text" name="modifiedNom" id="modalModifiedNom" />
            Adresse : <input type="text" name="modifiedAdresse" id="modalModifiedAdresse" />
            Téléphone : <input type="text" name="modifiedTelephone" id="modalModifiedTelephone" />
            Ville :
            <select name="modifiedVille" class="form-control" required>
            <option value="0">Sélectionnez</option>
                <c:forEach items="${villes}" var="v">
                    <option value="${v.id}">${v.nom}</option>
                </c:forEach>
            </select>
            <button type="submit">Enregistrer Modification</button>
        </form>
    </div>
</div>

<script>
document.querySelectorAll('.open-modal').forEach(button => {
    button.addEventListener('click', function () {
        const hotelId = this.getAttribute('data-hotel-id');
        const modalHotelId = document.getElementById('modalHotelId');
        const modalModifiedNom = document.getElementById('modalModifiedNom');
        const modalModifiedAdresse = document.getElementById('modalModifiedAdresse');
        const modalModifiedTelephone = document.getElementById('modalModifiedTelephone');
        const modalModifiedVille = document.querySelector('select[name="modifiedVille"]');
                
        modalHotelId.value = hotelId;
        modalModifiedNom.value = ""; // Clear previous input
        modalModifiedAdresse.value = ""; // Clear previous input
        modalModifiedTelephone.value = ""; // Clear previous input
        modalModifiedVille.selectedIndex = 0; // Reset to the first option

        const modal = document.getElementById('myModal');
        modal.style.display = 'block';
    });
});

document.querySelector('.close').addEventListener('click', function () {
    const modal = document.getElementById('myModal');
    modal.style.display = 'none';
});

window.addEventListener('click', function (event) {
    const modal = document.getElementById('myModal');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
});
</script>

</body>
</html>