package com.example.s17d2.model;

import com.example.s17d2.tax.Taxable;
import com.example.s17d2.utils.Constant;

public class DeveloperFactory {

    public static Developer createDevoleper(Developer developer, Taxable taxable){
        Developer createdDeveloper = null;
        if (developer.getExperience().name().equalsIgnoreCase(Constant.JUNIOR)){
            createdDeveloper = new JuniorDeveloper(developer.getId(), developer.getName(), (developer.getSalary() - (developer.getSalary() * taxable.getSimpleTaxRate())/100));
        } else if (developer.getExperience().name().equalsIgnoreCase(Constant.MID)){
            createdDeveloper = new MidDeveloper(developer.getId(), developer.getName(), (developer.getSalary() - (developer.getSalary() * taxable.getMiddleTaxRate())/100));
        }
        else if (developer.getExperience().name().equalsIgnoreCase(Constant.SENIOR)){
            createdDeveloper = new SeniorDeveloper(developer.getId(), developer.getName(), (developer.getSalary() - (developer.getSalary() * taxable.getUpperTaxRate())/100));
        }
        return createdDeveloper;
    }
}
