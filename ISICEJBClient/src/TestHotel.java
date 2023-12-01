import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import dao.IDaoRemote;
import entities.Hotel;
import entities.Ville;

public class TestHotel {

	public static IDaoRemote<Hotel> lookUpEmployeRemote() throws NamingException {
		final Hashtable jndiProperties = new Hashtable();
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		final Context context = new InitialContext(jndiProperties);

		return (IDaoRemote<Hotel>) context.lookup("ejb:ISICEJBEAR/ISICEJBServer/berhil1!dao.IDaoRemote");

	}
	
	public static IDaoRemote<Ville> lookUpEmployeRemote2() throws NamingException {
		final Hashtable jndiProperties = new Hashtable();
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		final Context context = new InitialContext(jndiProperties);

		return (IDaoRemote<Ville>) context.lookup("ejb:ISICEJBEAR/ISICEJBServer/berhil!dao.IDaoRemote");

	}
	public static void main(String[] args) {
		
		try {
			IDaoRemote<Hotel> dao = lookUpEmployeRemote();
			IDaoRemote<Ville> dao2 = lookUpEmployeRemote2();
			//dao.create(new Hotel("KENZI","brrrr","09854333",dao2.findById(147)));
			//dao.create(new Hotel("AYA","ureh","09876544",dao2.findById(148)));
			
			//Hotel hotelToUpdate = dao.findById(73);
			//hotelToUpdate.setNom("SOFITEL");
			//hotelToUpdate.setAdresse("gggggg");
			//hotelToUpdate.setTelephone("065432221");
			//hotelToUpdate.setVille(dao2.findById(148));
			//dao.update(hotelToUpdate);
			
			//Hotel hotelToDelete = dao.findById(73);
			//dao.delete(hotelToDelete);
			
			
			//dao.findByVille(dao2.findById(148).getNom());
			
			
						
			
			for(Hotel v : dao.findAll()) {
				System.out.println(v.getNom()+" "+v.getAdresse()+" "+v.getTelephone()+" "+v.getVille().getNom());
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
