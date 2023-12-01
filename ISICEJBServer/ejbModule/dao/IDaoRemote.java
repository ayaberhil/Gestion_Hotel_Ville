package dao;

import java.util.List;

import entities.Hotel;
import jakarta.ejb.Remote;

@Remote
public interface IDaoRemote <T> {
	
	public T create(T o);
	public boolean delete(T o);
	public boolean update(T o);
	public T findById(int id);
	public List<T> findAll();
	List<Hotel> findByVille(String nom);

}
