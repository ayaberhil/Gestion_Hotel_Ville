package services;

import java.util.List;

import dao.IDaoLocale;
import dao.IDaoRemote;
import dao.IdaoHotel;
import entities.Hotel;
import entities.Ville;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Stateless(name = "berhil1")
public class HotelService implements IDaoRemote<Hotel>, IdaoHotel {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	@PermitAll
	public Hotel create(Hotel o) {
		em.persist(o);
		return o;
	}
	
	@Override
	@PermitAll
	public boolean delete(Hotel o) {
		em.remove(em.contains(o) ? o: em.merge(o));
		return true;
	}




	@Override
	@PermitAll
	@Transactional
	public boolean update(Hotel o) {
		try {
	        em.merge(o);
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	@Override
	@PermitAll
	public Hotel findById(int id) {
		// TODO Auto-generated method stub
		return em.find(Hotel.class, id);
	}

	@Override
	@PermitAll
	public List<Hotel> findAll() {
		Query query = em.createQuery("select v from Hotel v");
		return query.getResultList();
	}
	
	@Override
	@PermitAll
	public List<Hotel> findByVille(String nom) {
		
		List<Hotel> hotels = null;
		hotels = em
		              .createQuery("select h from Hotel h where h.ville.nom = ?1", Hotel.class)
		              .setParameter(1, nom)
		              .getResultList();
		return hotels;
	}
	
	
	
	
	

}
