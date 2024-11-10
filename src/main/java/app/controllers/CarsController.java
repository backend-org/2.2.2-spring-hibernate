package app.controllers;

import app.model.Car;
import app.service.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CarsController {

    @Autowired
    private CarsService carsService;

    @GetMapping("/cars")
    public String carsPage(@RequestParam(value = "count", required = false) Integer count,
                           Model model) {
        if (count != null) {
            model.addAttribute("cars", carsService.listCars(count));
        } else {
            model.addAttribute("cars", carsService.listCars());
        }
        return "cars/cars_table";
    }

    @GetMapping("/filldb")
    public String fillDBPage() {
        carsService.add(new Car("Bmw", "black"));
        carsService.add(new Car("Volvo", "white"));
        carsService.add(new Car("Opel", "red"));
        carsService.add(new Car("Audi", "green"));
        carsService.add(new Car("Nissan", "yellow"));
        return "cars/cars_added_info";
    }

}