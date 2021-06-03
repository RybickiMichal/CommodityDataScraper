package pl.investadvisor.datascraper.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.investadvisor.datascraper.service.commodityprice.PriceScrapeService;

@AllArgsConstructor
@Component
class PriceCommandLineRunner implements CommandLineRunner {

    private PriceScrapeService priceScrapeService;

    @Override
    public void run(String... args) throws Exception {
        priceScrapeService.scrapePrices();

        System.exit(0);
    }
}
