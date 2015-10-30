package sanjiang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import sanjiang.domain.User;
import sanjiang.repository.UserRepository;
import sanjiang.service.UserService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangzhe on 15-10-28.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @Value("${application.message:Hello World}")
    private String message = "Hello World";

    @RequestMapping("/welcome")
    public Object welcome(Model model) {
        model.addAttribute("time", new Date());
        model.addAttribute("message", this.message);
        return "welcome";
    }

    @RequestMapping("/thymeleaf")
    public Object thymeleaf(Model model){
        List<User> all = userRepository.findAll();
        model.addAttribute("users",all);
        return "thymeleaf-test";
    }

    @RequestMapping("/hello")
    public String home() {
        return "Hello";
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public Object findAll(){
        Map map = new HashMap();
        map.put("currentPage",1);
        map.put("totalPage",12);
        map.put("total",110);
        map.put("pageSize",15);

        List<User> all = userRepository.findAll();

        map.put("rows",all);
        return map;
    }

    @RequestMapping("/findById")
    public String findById(Long id){
        User user = userService.findById(id);
        return user.getFirstName();
    }

    @RequestMapping("/save")
    @ResponseBody
    public Object saveUser(User user){
        User saveUser = userService.save(user);
        Map map = new HashMap();
        map.put("state",saveUser.getId() != null ? "success" : "error");
        List<User> all = userRepository.findAll();
        map.put("rows",all);
        return map;
    }
}
