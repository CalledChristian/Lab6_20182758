package com.example.lab6_sol.controller;

import com.example.lab6_sol.entity.Usuario;
import com.example.lab6_sol.repository.RolRepository;
import com.example.lab6_sol.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/estudiante")
public class EstudianteController {

    @Autowired
    UsuarioRepository usuarioRepository;

    final
    RolRepository rolRepository;

    public EstudianteController(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @GetMapping("/lista")
    public String listaUsuarios(Model model){
        List<Usuario> estudiantes = usuarioRepository.findByRolid(5);
        model.addAttribute("estudiantes", estudiantes);
        return "lista_usuarios";
    }

    @GetMapping("/new")
    public String nuevoEstudiante(Model model , @ModelAttribute("usuario") Usuario usuario){

        model.addAttribute("roles", rolRepository.findAll());
        return "newForm";
    }

    @GetMapping("/edit")
    public String editarEstudiante(Model model , @ModelAttribute("usuario") Usuario usuario , @RequestParam("id") int id){

        Optional<Usuario> optProduct = usuarioRepository.findById(id);

        if (optProduct.isPresent()) {
            usuario = optProduct.get();
            model.addAttribute("usuario", usuario);
            return "newForm";
        } else {
            return "redirect:/estudiante/lista";
        }

    }

    @PostMapping("/save")
    public String guardarEstudiante(RedirectAttributes attr,
                                    Model model,
                                    @ModelAttribute("usuario") Usuario usuario ){


            if (usuario.getId() == 0) {
                attr.addFlashAttribute("msg", "Producto creado exitosamente");
            } else {
                attr.addFlashAttribute("msg", "Producto actualizado exitosamente");
            }
            //String password = new BCryptPasswordEncoder().encode(usuario.getPassword());
            usuarioRepository.save(usuario);
            return "redirect:/estudiante/lista";
            }
    }


