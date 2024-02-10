package com.example.s17d2.rest;

import com.example.s17d2.dto.DeveloperResponse;
import com.example.s17d2.model.*;
import com.example.s17d2.utils.Constant;
import com.example.s17d2.validation.DeveloperValidation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.s17d2.tax.Taxable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping
public class DeveloperController {

    private Map<Integer, Developer> developers;

    private Taxable taxable;

    @PostConstruct
    public void init(){
        developers = new HashMap<>();
    }

    @Autowired
    public DeveloperController(Taxable taxable){
        this.taxable=taxable;
    }

    @PostMapping("/developers")
    public DeveloperResponse save (@RequestBody Developer developer){
        Developer createdDeveloper = DeveloperFactory.createDevoleper(developer,taxable);
        if (Objects.nonNull(createdDeveloper)){
            developers.put(createdDeveloper.getId(),createdDeveloper);
        }
        return new DeveloperResponse(createdDeveloper, Constant.SUCCEED_MSG, HttpStatus.OK.value());
    }

    @GetMapping("/developers")
    public List<Developer> getALL(){
        return developers.values().stream().toList();
    }

    @GetMapping("/developers/{id}")
    public DeveloperResponse findById(@PathVariable("id") Integer id){
        if (DeveloperValidation.isDeveloperExist(this.developers,id))
            return new DeveloperResponse(this.developers.get(id),Constant.SUCCEED_MSG,HttpStatus.OK.value());
        return new DeveloperResponse(null,"developer does not exist",HttpStatus.NOT_FOUND.value());
    }

    @PutMapping("/developers/{id}")
    public DeveloperResponse getById(@PathVariable("id") Integer id, @RequestBody Developer developer){
        if (!DeveloperValidation.isDeveloperExist(this.developers,id)){
            return new DeveloperResponse(null,"developer already not exist id= " + id,HttpStatus.NOT_FOUND.value());
        }
            developer.setId(id);
            Developer updateDeveloper = DeveloperFactory.createDevoleper(developer,taxable);
            this.developers.put(updateDeveloper.getId(), updateDeveloper);
            return new DeveloperResponse(updateDeveloper,Constant.SUCCEED_MSG,HttpStatus.OK.value());
    }

    @DeleteMapping("developers/{id}")
    public DeveloperResponse delete(@PathVariable("id") Integer id){
        Developer removedDeveloper = this.developers.get(id);
        this.developers.remove(id);
        return new DeveloperResponse(removedDeveloper,Constant.SUCCEED_MSG,HttpStatus.OK.value());
    }
}
