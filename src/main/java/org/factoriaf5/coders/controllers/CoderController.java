package org.factoriaf5.coders.controllers;

import org.factoriaf5.coders.repositories.Coder;
import org.factoriaf5.coders.repositories.CoderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CoderController {

    private CoderRepository coderRepository;

    @Autowired
    public CoderController(CoderRepository coderRepository) {
        this.coderRepository = coderRepository;
    }

    @GetMapping("/coders")
    String listCoders(Model model) {
        List<Coder> coders = (List<Coder>) coderRepository.findAll();
        model.addAttribute("title", "Lista de Coders");
        model.addAttribute("coders", coders);
        return "coders/all";
    }

    @GetMapping("/coders/new")
    String newCoder(Model model) {
    Coder coder = new Coder();
    model.addAttribute("coder", coder);
    model.addAttribute("title", "Crear nueva Coder");
    return "coders/edit";
    }

    @PostMapping("/coders/new")
    String addCoder(@ModelAttribute Coder coder) {
        coderRepository.save(coder);
        return "redirect:/coders";
    }
    @GetMapping("/coders/edit/{id}")
    String editCoder(Model model, @PathVariable Long id){
        Coder coder = coderRepository.findById(id).get();
        model.addAttribute("coder", coder);
        model.addAttribute("title", "Editar coder");
        return "coders/edit";
    }

    @GetMapping("/coders/delete/{id}")
    String remove(@PathVariable Long id) {
        coderRepository.deleteById(id);
        return "redirect:/coders";
    }
}