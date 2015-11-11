package sanjiang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import sanjiang.domain.Personnel;
import sanjiang.domain.User;
import sanjiang.repository.UserRepository;
import sanjiang.service.PersonnelService;
import sanjiang.service.UserService;
import sanjiang.utils.Json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.*;

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

    @Autowired
    private PersonnelService personnelService;


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

    @RequestMapping(value="/login",method = RequestMethod.POST)
    @ResponseBody
    public Object login(String username,String password){
        Map map = new HashMap();
        if("user".equals(username) && "pwd".equals(password)){
            map.put("state","ok");
        }else{
            map.put("state","no");
        }
        return map;
    }

    @RequestMapping(value="/findPersonnels",method = RequestMethod.GET)
   // @ResponseBody
    public Object findPersonnels(HttpServletRequest request, HttpServletResponse response){
        List<Personnel> personnels = personnelService.findAll();
        Json json = new Json();
        json.add("success","true");
        json.add("total",personnels.size());

        for (Personnel personnel : personnels) {
            Json json1 = new Json();
            json1.add("id",String.valueOf(personnel.getId()));
            json1.add("name",personnel.getName());
            json1.add("email",personnel.getEmail());
            json1.add("phone",personnel.getPhone());
            json.add("items",json1);
        }

      /*  response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        pw.write(json.toString());
        pw.flush();*/

        boolean jsonP = false;
        String cb = request.getParameter("callback");
        if (cb != null) {
            jsonP = true;
            response.setContentType("text/javascript");
        } else {
            response.setContentType("application/x-json");
        }
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (jsonP) {
            out.write(cb + "(");  //这拼一下js 数据
        }
        out.print(json);
        if (jsonP) {
            out.write(");");
        }
        out.flush();

        return null;
     /*   Map map = new HashMap();
        map.put("success","true");
        map.put("total",personnels.size());
        map.put("items",personnels);

        return map;*/
    }
}
