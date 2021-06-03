package pl.investadvisor.datascraper.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.investadvisor.datascraper.model.Commodity;
import pl.investadvisor.datascraper.repository.CommodityRepository;

@AllArgsConstructor
@Service
public class CommodityService {

    private CommodityRepository commodityRepository;

    public Commodity addCommodity(Commodity commodity) {
        return commodityRepository.saveCommodity(commodity);
    }
}
