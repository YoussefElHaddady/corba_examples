# Banque project
## Banque Server

1. create java project
1. create `src/Banque.idl` file
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

## Banque Client


