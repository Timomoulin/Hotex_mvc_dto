package ma.enset.clientsmvc.web;

import ma.enset.clientsmvc.entities.Utilisateur;
import ma.enset.clientsmvc.repositories.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ClientsController {
    private final ClientRepository clientRepository;

    public ClientsController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("/index")
    public String clients(Model model,
                          @RequestParam(name = "page", defaultValue = "0") int page,
                          @RequestParam(name = "size", defaultValue = "5") int size,
                          @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        // Récupère une liste paginée d'utilisateurs en fonction du mot-clé et de la pagination
        Page<Utilisateur> pageClients = clientRepository.findByNomIgnoreCaseContains(keyword, PageRequest.of(page, size));
        model.addAttribute("listClients", pageClients.getContent()); // Liste des utilisateurs affichés sur la page
        model.addAttribute("pages", new int[pageClients.getTotalPages()]); // Pagination
        model.addAttribute("currentPage", page); // Page actuelle
        model.addAttribute("keyword", keyword); // Mot-clé de recherche
        return "clients";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id,
                         @RequestParam String keyword,
                         @RequestParam int page) {
        clientRepository.deleteById(id);
        return "redirect:/index?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/clients")
    @ResponseBody
    public List<Utilisateur> listClient() {
        return clientRepository.findAll();
    }

    @GetMapping("/formClients")

    public String formClients(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "formClients";
    }

    @GetMapping("/formulaireConnexion")
    public String formulaireConexion(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "formulaireConnexion";
    }


    @GetMapping("/arrivee")
    public String arrivee(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "arrivee";
    }



    @GetMapping("/editClient")
    public String editClient(Model model,
                             @RequestParam Long id,
                             @RequestParam String keyword,
                             @RequestParam int page) {
        Utilisateur utilisateur = clientRepository.findById(id).orElse(null);
        if (utilisateur == null) {
            throw new RuntimeException("Utilisateur introuvable");
        }
        model.addAttribute("utilisateur",  utilisateur);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        return "editClient";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("utilisateur") @Valid Utilisateur utilisateur, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editClient";
        }
        clientRepository.save(utilisateur);
        return "redirect:/index";
    }
}
