package controllers;

import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import dao.IDaoLocale;
import dao.IdaoHotel;
import dao.IdaoVille;
import entities.Hotel;
import entities.Ville;

/**
 * Servlet implementation class HotelController
 */
public class HotelController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private IdaoHotel ejb;
	@EJB
    private IdaoVille ejb2;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HotelController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	List<Hotel> HotelList = ejb.findAll();
		List<Ville> VilleList = ejb2.findAll();
		request.setAttribute("hotels", HotelList);
		request.setAttribute("villes", VilleList);
		request.getRequestDispatcher("/hotel.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String action = request.getParameter("action");

	        if ("create".equals(action)) {
	            String nom = request.getParameter("hotel");
	            String adresse = request.getParameter("adresse");
	            String telephone = request.getParameter("telephone");
	            int villeId = Integer.parseInt(request.getParameter("ville"));
				Ville ville = ejb2.findById(villeId);
	            ejb.create(new Hotel(nom,adresse,telephone,ville));

	            response.sendRedirect(request.getContextPath() + "/HotelController");
	            
	        } else if ("delete".equals(action)) {
	            int hotelId = Integer.parseInt(request.getParameter("Id"));
	            Hotel hotelToDelete = ejb.findById(hotelId);

	            if (hotelToDelete != null) {
	                boolean deleted = ejb.delete(hotelToDelete);

	                if (deleted) {
	                    System.out.println("Hotel supprimé avec succès");
	                } else {
	                    System.out.println("La suppression de l'hotel a échoué");
	                }
	            } else {
	                System.out.println("Hotel non trouvée avec l'ID : " + hotelId);
	            }

	            // Après la suppression, rediriger vers la page HotelController
	            response.sendRedirect(request.getContextPath() + "/HotelController");
	            
	        } else if ("update".equals(action)) {
	            int hotelId = Integer.parseInt(request.getParameter("hotelId"));
	            String modifiedNom = request.getParameter("modifiedNom");
	            String modifiedAdresse = request.getParameter("modifiedAdresse");
	            String modifiedTelephone = request.getParameter("modifiedTelephone");
	            int modifiedVilleId = Integer.parseInt(request.getParameter("modifiedVille"));
	            
	            // Retrieve the Ville entity based on the modifiedVilleId
	            Ville modifiedVille = ejb2.findById(modifiedVilleId);

	            Hotel hotelToUpdate = ejb.findById(hotelId);

	            if (hotelToUpdate != null && modifiedVille != null) {
	                hotelToUpdate.setNom(modifiedNom);
	                hotelToUpdate.setAdresse(modifiedAdresse);
	                hotelToUpdate.setTelephone(modifiedTelephone);
	                hotelToUpdate.setVille(modifiedVille); // Set the modified Ville
	                ejb.update(hotelToUpdate);
	            }

	            response.sendRedirect(request.getContextPath() + "/HotelController");
	            
	        } else if ("filterByVille".equals(action)) {
	            int villeId = Integer.parseInt(request.getParameter("filterVille"));

	            if (villeId == 0) {
	                List<Hotel> hotelList = ejb.findAll();
	                request.setAttribute("hotels", hotelList);
	            } else {
	                Ville ville = ejb2.findById(villeId);
	                String villeName = ville.getNom();
	                List<Hotel> hotelList = ejb.findByVille(villeName);
	                request.setAttribute("hotels", hotelList);
	            }

	            List<Ville> villeList = ejb2.findAll();
	            request.setAttribute("villes", villeList);

	            request.getRequestDispatcher("/hotel.jsp").forward(request, response);
	        }
	       

	    }
	
}

