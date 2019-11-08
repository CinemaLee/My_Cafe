package com.study.mycafe.web;

import com.study.mycafe.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private List<UserDto> userDtoList = new ArrayList<UserDto>();

    @PostMapping("/create")
    public String create(UserDto userDto) {

        System.out.println(userDto);
        userDtoList.add(userDto);
        return "redirect:/list";
    }


    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users",userDtoList);
        return "list";
    }
}
