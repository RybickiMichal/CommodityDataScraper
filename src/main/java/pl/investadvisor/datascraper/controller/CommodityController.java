package pl.investadvisor.datascraper.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.investadvisor.datascraper.model.Commodity;
import pl.investadvisor.datascraper.service.CommodityService;

@AllArgsConstructor
@RestController
public class CommodityController {

    private CommodityService commodityService;

    @PostMapping(value = "/commodity")
    public Commodity addCommodity(@RequestBody Commodity commodity) {
        return commodityService.addCommodity(commodity);
    }
}
