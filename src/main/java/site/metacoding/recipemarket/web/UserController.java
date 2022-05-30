package site.metacoding.recipemarket.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("main")
    public String main() {
        return "/main";
    }

}
