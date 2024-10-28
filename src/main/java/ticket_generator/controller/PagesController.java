package ticket_generator.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ticket_generator.service.TicketService;

import java.util.UUID;

@Controller
public class PagesController {
    private final TicketService service;
    public PagesController(TicketService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String getTicketNumber(Model model) {
        Long ticketCount = this.service.getTicketNumber();
        model.addAttribute("ticketCount", ticketCount);
        return "index";
    }

    @GetMapping("/ticket-info/{id}")
    public String getTicketInfo(@PathVariable UUID id, Model model, @AuthenticationPrincipal OidcUser principal) {
        model.addAttribute("ticketInfo", this.service.getTicketInfo(id));
        if (principal != null) {
            model.addAttribute("userName", principal.getEmail());
        }
        return "ticket-info";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        //izmjeni nakon deploya http://localhost:8080/
        String logoutUrl = "https://fer-web2-zh.eu.auth0.com/v2/logout?returnTo=https://ticket-app-web2-lab1.onrender.com/&client_id=vMZYtCX7jV5wvMmoTO7SXPz1vPbsXYbB";
        return "redirect:" + logoutUrl;
    }
}
