package Spr.web;

import Spr.Data.User;
import Spr.Data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@Controller
@RequestMapping("/data")
public class DataController {
    @Autowired
    public UserRepository userRepository;

    @GetMapping("/{numb}")
    public String paginationGet(@PathVariable(name = "numb") int numb, Model model){
        PageRequest p = PageRequest.of(numb,2);
        Page<User> pag = userRepository.findAll(p);
        model.addAttribute("users",pag.getContent());
        System.out.println(pag.getContent());
        return "DataTemplate";
    }
}
