# Banque project
## Banque Server

1. Create java project
1. Create `src/Banque.idl` file
```java
module corbaBanque {
	struct Compte {
		long code;
		double solde;
	};
	
	typedef sequence<Compte> Comptes;

	interface IBanqueRemote {
		double conversion(in double mt);
		Compte getCompte(in long code);
		Comptes getComptes();
	};
};
```

1. Compile `Banque.idl` with java
   1. make sure you have this exe in `path_to/jdk/bin/idlj.exe`.
   1. make sure the `jdk/bin` is in your path env.
   1. run this command in `path_to/project/src/`
```
idlj -fall -v Banque.idl
```
1. If all is good a package of stubs will be generated
1. Implementation of service
Create 'src/servants/BanqueServiceImpl.java`
```java
package servants;

import corbaBanque.Compte;
import corbaBanque.IBanqueRemotePOA;

public class BanqueServiceImpl extends IBanqueRemotePOA {

	@Override
	public double conversion(double mt) {
		return mt * 11.3;
	}

	@Override
	public Compte getCompte(int code) {
		return new Compte(code, Math.random()*9000);
	}

	@Override
	public Compte[] getComptes() {
		Compte[] cptes = new Compte[3];
		cptes[0] = new Compte(1, Math.random()*9000);
		cptes[1] = new Compte(2, Math.random()*9000);
		cptes[2] = new Compte(3, Math.random()*9000);
		return cptes;
	}

}
```
1. Create Server `src/ServeurCorba.java` 
```java
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
```
1. Usage of JNDI
Create `src/jndi.properties` file
```
java.naming.factory.initial=com.sun.jndi.cosnaming.CNCtxFactory
java.naming.provider.url=iiop://localhost:900
```

## Banque Client


