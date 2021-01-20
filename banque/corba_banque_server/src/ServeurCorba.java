import javax.naming.Context;
import javax.naming.InitialContext;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import servants.BanqueServiceImpl;

public class ServeurCorba {

	public static void main(String[] args) {

		try {
			ORB orb = ORB.init(args, null);
			POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));

			rootPOA.the_POAManager().activate();

			BanqueServiceImpl banqueServiceImpl = new BanqueServiceImpl();
			Context ctx = new InitialContext();
			
			Object ref = rootPOA.servant_to_reference(banqueServiceImpl);
			
			ctx.rebind("BANQUE", ref);
			System.out.println("ref : " + ref);
			
			orb.run();
			System.out.println("Banque Server started ...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
