package com.example.lab6_sol.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "profesor")
    private Usuario profesor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario getProfesor() {
        return profesor;
    }

    public void setProfesor(Usuario profesor) {
        this.profesor = profesor;
    }
}

/*import jakarta.validation.Valid;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.validation.BindingResult;
        import org.springframework.web.bind.annotation.*;
        import org.springframework.web.servlet.mvc.support.RedirectAttributes;

        import java.util.List;
        import java.util.Optional;

@Controller
@RequestMapping("/estudiante")
public class EstudianteController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/lista")
    public String listaUsuarios(Model model){
        List<Usuario> estudiantes = usuarioRepository.findByRolid(5);
        model.addAttribute("estudiantes", estudiantes);
        return "lista_usuarios";
    }

    @GetMapping("/nuevo")
    public String nuevoUsuario(@ModelAttribute("usuario") Usuario usuario, Model model) {
        return "usuarioForm";
    }

    //para Editar Usuario
    @GetMapping("/edit")
    public String nuevo(@ModelAttribute("usuario")Usuario usurio, Model model, @RequestParam("id") Integer id){
        //buscamos al usuario por id
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);

        if (optionalUsuario.isPresent()) {
            //si esta presente lo obtenemos y amn
            Usuario usuario = optionalUsuario.get();
            model.addAttribute("usuario", usuario);
            return "usuarioForm";
        } else {
            return "redirect:/estudiant/lista";
        }
    }
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult bindingResult, RedirectAttributes attr){

        //si hay errores , entonces vuelve al formulario
        if(bindingResult.hasErrors()){
            return "usuarioForm";
        }else{
            //si el id usuario mandado con model atrbute es 0 , entonces es un nuevo
            //usuario , entonces estamos creando
            if(usuario.getId()==0){
                attr.addFlashAttribute("msg", "Estudiante creado exitosamente");
            }else{
                //sino lo estamos actualziando
                attr.addFlashAttribute("msg", "EStudiante actualizado exitosamente");
            }
            //el usuario se setea con rol d = 5 , para que sea estudiante
            usuario.setRolid(5);
            usuario.setActivo(true);
            //usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
            //finalemnte guardamos
            usuarioRepository.save(usuario);
            return "redirect:/estudiante/lista";
        }
    } */

