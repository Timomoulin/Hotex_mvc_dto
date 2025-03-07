package ma.enset.clientsmvc;

import ma.enset.clientsmvc.entities.Chambre;
import ma.enset.clientsmvc.entities.Reservation;
import ma.enset.clientsmvc.entities.Utilisateur;
import ma.enset.clientsmvc.repositories.ChambreRepository;
import ma.enset.clientsmvc.repositories.UtilisateurRepository;
import ma.enset.clientsmvc.repositories.ReservationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class 																																		ClientsMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientsMvcApplication.class, args);
	}
}
@Component
class DatabaseLoader {
	private final UtilisateurRepository utilisateurRepository;
	private final ChambreRepository chambreRepository;

	private final ReservationRepository reservationRepository;

	public DatabaseLoader(UtilisateurRepository utilisateurRepository, ChambreRepository chambreRepository, ReservationRepository reservationRepository) {
		this.utilisateurRepository = utilisateurRepository;
		this.chambreRepository = chambreRepository;
		this.reservationRepository= reservationRepository;
	}

	@Bean
	CommandLineRunner commandLineRunner() {
		return args -> {
			Utilisateur client1= new Utilisateur(null, "Mme.", "Dupont", "Alice" , "12 Rue Seine, Lille, France 69000", "+33 6 81 45 56 45", "alice.dupont@email.com",new ArrayList<>());
			utilisateurRepository.save(client1);
			Chambre chambre101= new Chambre(null, "100", 1, 2, 80.00, Chambre.Statut.LIBRE,new ArrayList<>());
			chambreRepository.save(chambre101);
			chambreRepository.save(new Chambre(null, "102", 1, 3, 95.00, Chambre.Statut.LIBRE,new ArrayList<>()));
			chambreRepository.save(new Chambre(null, "101", 1, 3, 95.00, Chambre.Statut.LIBRE,new ArrayList<>()));
			chambreRepository.save(new Chambre(null, "103", 1, 3, 95.00, Chambre.Statut.LIBRE,new ArrayList<>()));
			chambreRepository.save(new Chambre(null, "104", 1, 3, 95.00, Chambre.Statut.LIBRE,new ArrayList<>()));
			chambreRepository.save(new Chambre(null, "105", 1, 3, 95.00, Chambre.Statut.LIBRE,new ArrayList<>()));
			chambreRepository.save(new Chambre(null, "200", 1, 3, 95.00, Chambre.Statut.LIBRE,new ArrayList<>()));
			chambreRepository.save(new Chambre(null, "201", 1, 3, 95.00, Chambre.Statut.LIBRE,new ArrayList<>()));
			chambreRepository.save(new Chambre(null, "202", 1, 3, 95.00, Chambre.Statut.LIBRE,new ArrayList<>()));
			chambreRepository.save(new Chambre(null, "203", 1, 3, 95.00, Chambre.Statut.LIBRE,new ArrayList<>()));
			chambreRepository.save(new Chambre(null, "204", 1, 3, 95.00, Chambre.Statut.LIBRE,new ArrayList<>()));
			chambreRepository.save(new Chambre(null, "205", 1, 3, 95.00, Chambre.Statut.LIBRE,new ArrayList<>()));
			chambreRepository.save(new Chambre(null, "300", 1, 3, 95.00, Chambre.Statut.LIBRE,new ArrayList<>()));
			chambreRepository.save(new Chambre(null, "301", 1, 3, 95.00, Chambre.Statut.LIBRE,new ArrayList<>()));
			chambreRepository.save(new Chambre(null, "302", 1, 3, 95.00, Chambre.Statut.LIBRE,new ArrayList<>()));
			chambreRepository.save(new Chambre(null, "303", 1, 3, 95.00, Chambre.Statut.LIBRE,new ArrayList<>()));
			chambreRepository.save(new Chambre(null, "304", 1, 3, 95.00, Chambre.Statut.LIBRE,new ArrayList<>()));
			chambreRepository.save(new Chambre(null, "305", 1, 3, 95.00, Chambre.Statut.LIBRE,new ArrayList<>()));

			LocalDate dateArrivee = LocalDate.now();
			LocalDate dateDepart = LocalDate.of(2023, 12, 31);

/*
			Reservation reservation = new Reservation(
					null,dateArrivee,dateDepart, "en cours", "2", "Avant 19H", "2", client1,List.of(chambre101)
			);

			reservationRepository.save(reservation);
*/
		};


	}

	}


