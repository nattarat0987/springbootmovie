package com.example.projectarm.Controller;

import com.example.projectarm.Service.StoreServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assets")
public class AssetsController {

    @Autowired
    private StoreServiceImp serviceImp;

    @GetMapping("/{filename:.+}")
    public Resource imgresourse(@PathVariable ("filename") String filename){
        return serviceImp.LoadResource(filename);
    }
}
