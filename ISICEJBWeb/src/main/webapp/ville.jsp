<%@page import="entities.Ville"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Villes</title>
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

    <form action="VilleController" method="post">
        Nom : <input type="text" name="ville" /> 
        <input type="hidden" name="action" value="create" />
        <button type="submit">Enregistrer</button>
    </form>

    <h1>Liste des villes :</h1>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Supprimer</th>
                <th>Modifier</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${villes}" var="v">
                <tr>
                    <td>${v.id}</td>
                    <td>${v.nom}</td>

                    <td class="actions">
                        <form action="VilleController" method="post">
                            <input type="hidden" name="action" value="delete" />
                            <input type="hidden" name="Id" value="${v.id}" />
                            <button type="submit">Supprimer</button>
                        </form>
                    </td>

                    <td>                        
                        <button class="open-modal" data-ville-id="${v.id}">Modifier</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- Modal for modification form -->
    <div id="myModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <form action="VilleController" method="post">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="villeId" id="modalVilleId" />
                Nom : <input type="text" name="modifiedNom" id="modalModifiedNom" />
                <button type="submit">Enregistrer Modification</button>
            </form>
        </div>
    </div>
    


    <script>
        // JavaScript to open and close the modal
        document.querySelectorAll('.open-modal').forEach(button => {
            button.addEventListener('click', function() {
                const villeId = this.getAttribute('data-ville-id');
                const modalVilleId = document.getElementById('modalVilleId');
                const modalModifiedNom = document.getElementById('modalModifiedNom');
                modalVilleId.value = villeId;
                modalModifiedNom.value = ""; // Clear previous input
                document.getElementById('myModal').style.display = 'block';
            });
        });

        document.querySelector('.close').addEventListener('click', function() {
            document.getElementById('myModal').style.display = 'none';
        });

        window.addEventListener('click', function(event) {
            const modal = document.getElementById('myModal');
            if (event.target === modal) {
                modal.style.display = 'none';
            }
        });
    </script>
</body>
</html>
