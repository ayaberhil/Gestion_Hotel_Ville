import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import dao.IDaoRemote;
import entities.Ville;

public class TestVille {

	public static IDaoRemote<Ville> lookUpEmployeRemote() throws NamingException {
		final Hashtable jndiProperties = new Hashtable();
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		final Context context = new InitialContext(jndiProperties);

		return (IDaoRemote<Ville>) context.lookup("ejb:ISICEJBEAR/ISICEJBServer/berhil!dao.IDaoRemote");

	}
	public static void main(String[] args) {
		
		try {
			IDaoRemote<Ville> dao = lookUpEmployeRemote();
			Ville villetoDelete = dao.findById(146);
			dao.delete(villetoDelete);
			
			
			for(Ville v : dao.findAll()) {
				System.out.println(v.getNom());
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
