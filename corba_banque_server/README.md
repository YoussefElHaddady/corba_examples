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

## Banque Client


