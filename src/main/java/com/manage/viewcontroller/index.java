package com.manage.viewcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2017/3/16.
 */
@Controller
public class index {
        @RequestMapping(value = "/index",method = RequestMethod.GET)
        public String index(){
            return "index";
        }
    }
