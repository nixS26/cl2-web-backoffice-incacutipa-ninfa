package pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.controller;

import pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.dto.FilmDetailDto;
import pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.dto.FilmDto;
import pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.dto.FilmInsertDto;
import pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceController {

    @Autowired
    MaintenanceService maintenanceService;

    @GetMapping("/start")
    public String start(Model model) {

        List<FilmDto> films= maintenanceService.findAllFilms();
        model.addAttribute("films",films);
        return "maintenance";

    }
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model){

        FilmDetailDto filmDetailDto = maintenanceService.findFilmById(id);
        model.addAttribute("film",filmDetailDto);
        return "maintenance_detail"; //siempre devuelve una pagina
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){

        FilmDetailDto filmDetailDto = maintenanceService.findFilmById(id);
        model.addAttribute("film",filmDetailDto);
        return "maintenance_edit";
    }
    @PostMapping("/edit-confirm")
    public String editConfirm(@ModelAttribute FilmDetailDto filmDetailDto, Model model){
        maintenanceService.updateFilm(filmDetailDto);
        return "redirect:/maintenance/start";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("filmInsertDto", new FilmInsertDto("", "", null, null, null, null, null, null, "", ""));
        model.addAttribute("languages", maintenanceService.findAllLanguages());
        return "maintenance_add";
    }

    @PostMapping("/add-confirm")
    public String addConfirm(@ModelAttribute FilmInsertDto filmInsertDto) {
        boolean success = maintenanceService.addFilm(filmInsertDto);
        if (success) {
            return "redirect:/maintenance/start";
        } else {
            return "maintenance_add";
        }
    }

    @PostMapping("/remove/{id}")
    public String remove(@PathVariable Integer id) {
        boolean success = maintenanceService.deleteFilm(id);
        if (success) {
            return "redirect:/maintenance/start";
        } else {
            return "redirect:/maintenance/start?error=notfound&id=" + id;
        }
    }








}
