package dao;

import entities.Ville;
import jakarta.ejb.Local;

@Local
public interface IdaoVille extends IDaoLocale<Ville> {
	boolean hasAssociatedHotels(Ville ville);

}
