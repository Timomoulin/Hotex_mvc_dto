package ma.enset.clientsmvc.web;

import ma.enset.clientsmvc.dto.UtilisateurEtReservationDTO;
import ma.enset.clientsmvc.entities.Chambre;
import ma.enset.clientsmvc.entities.Reservation;
import ma.enset.clientsmvc.entities.Utilisateur;
import ma.enset.clientsmvc.repositories.ChambreRepository;
import ma.enset.clientsmvc.repositories.ReservationRepository;
import ma.enset.clientsmvc.repositories.UtilisateurRepository;
import ma.enset.clientsmvc.service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ReservationController {

    private final ReservationService reservationService;
    private final ChambreRepository chambreRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationService reservationService, ChambreRepository chambreRepository, UtilisateurRepository utilisateurRepository,
                                 ReservationRepository reservationRepository) {
        this.reservationService = reservationService;
        this.chambreRepository = chambreRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/admin/reservation")
    public String index(Model model) {
        List<Reservation> reservations = reservationService.getAll();
        model.addAttribute("reservations", reservations);
        return "/admin/reservation/index";
    }

    @GetMapping("/admin/reservation/add")
    public String add(Model model) {
        Reservation reservation = new Reservation();
        List<Chambre> chambres = chambreRepository.findByStatut(Chambre.Statut.LIBRE);
        List<Utilisateur> clients = utilisateurRepository.findAll();
        model.addAttribute("reservation", reservation);
        model.addAttribute("clients", clients);
        model.addAttribute("chambres", chambres);
        return "/admin/reservation/create";
    }

    @GetMapping("/admin/addClientEtreservation")
    public String addClientEtReservation(Model model) {
        Utilisateur utilisateur = new Utilisateur();
        Reservation reservation = new Reservation();
        UtilisateurEtReservationDTO dto = new UtilisateurEtReservationDTO(utilisateur, reservation);
        List<Chambre> chambres = chambreRepository.findByStatut(Chambre.Statut.LIBRE);
        List<Utilisateur> clients = utilisateurRepository.findAll();
        model.addAttribute("dto", dto);
        model.addAttribute("clients", clients);
        model.addAttribute("chambres", chambres);
        return "/admin/reservation/addClientEtReservation";
    }

    @PostMapping("/admin/create")
    public String createReservation(@ModelAttribute("reservation") @Valid Reservation reservation, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/reservation/create";  // Correction : chemin relatif sans '/'
        }

        // Le reste de votre logique pour enregistrer la réservation

        return "redirect:/admin/reservation";  // Rediriger vers la page d'index des réservations
    }


    @PostMapping("/admin/addClientEtreservation")
    public String storeClientEtReservation(@ModelAttribute @Valid UtilisateurEtReservationDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/reservation/addClientEtReservation";
        }

        Utilisateur existingUser = utilisateurRepository.findByeMail(dto.getUtilisateur().getEMail());
        if (existingUser != null) {
            bindingResult.rejectValue("utilisateur.eMail", "error.user", "Cet email est déjà utilisé.");
            return "admin/reservation/addClientEtReservation";
        }

        Utilisateur savedUser = utilisateurRepository.save(dto.getUtilisateur());
        Reservation reservation = dto.getReservation();
        reservation.setUtilisateur(savedUser);
        reservationService.saveReservation(reservation);
        return "redirect:/admin/reservation";
    }

    @PostMapping("/admin/reservation")
    public String store(@ModelAttribute("reservation") @Valid Reservation reservation, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/reservation/create"; // Correction : chemin relatif sans '/'
        }

        Chambre chambre = chambreRepository.findById(reservation.getUtilisateur().getChambre().getId()).orElse(null);
        if (chambre == null) {
            throw new RuntimeException("Chambre introuvable");
        }

        chambre.setStatut(Chambre.Statut.OCCUPEE);
        chambreRepository.save(chambre);
        reservationService.saveReservation(reservation);
        return "redirect:/admin/reservation";
    }
}
