package controllers;

import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.List;

import dao.IDaoLocale;
import dao.IdaoVille;
import entities.Ville;

/**
 * Servlet implementation class VilleController
 */
public class VilleController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private IdaoVille ejb;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VilleController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        List<Ville> villes = ejb.findAll();
	        request.setAttribute("villes", villes);
	        request.getRequestDispatcher("/ville.jsp").forward(request, response);
	    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String action = request.getParameter("action");

	        if ("create".equals(action)) {
	            String nom = request.getParameter("ville");
	            ejb.create(new Ville(nom));

	            response.sendRedirect(request.getContextPath() + "/VilleController");
	            
	        } else if ("delete".equals(action)) {
	            int villeId = Integer.parseInt(request.getParameter("Id"));
	            Ville villeToDelete = ejb.findById(villeId);

	            if (villeToDelete != null) {
	                // Check if there are associated hotels
	                if (ejb.hasAssociatedHotels(villeToDelete)) {
	                  
	                	
	                } else {
	                    boolean deleted = ejb.delete(villeToDelete);

	                    if (deleted) {
	                        System.out.println("Ville supprimée avec succès");
	                       
	                    } else {
	                        System.out.println("La suppression de la ville a échoué");
	                        // Optionally, you can set another attribute for more detailed error handling
	                        request.setAttribute("deleteError", "Failed to delete the city.");
	                    }
	                }
	            } else {
	                System.out.println("Ville non trouvée avec l'ID : " + villeId);
	            }

	            // After the deletion attempt, always redirect to VilleController
	            response.sendRedirect(request.getContextPath() + "/VilleController");
	        } else if ("update".equals(action)) {
	            int villeId = Integer.parseInt(request.getParameter("villeId"));
	            String modifiedNom = request.getParameter("modifiedNom");
	            Ville villeToUpdate = ejb.findById(villeId);

	            if (villeToUpdate != null) {
	                villeToUpdate.setNom(modifiedNom);
	                ejb.update(villeToUpdate);
	            }

	            response.sendRedirect(request.getContextPath() + "/VilleController");
	        }

	    }
	}