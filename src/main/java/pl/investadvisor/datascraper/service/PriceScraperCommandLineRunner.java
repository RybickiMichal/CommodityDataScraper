package pl.investadvisor.datascraper.service;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.investadvisor.datascraper.service.pricescraping.PriceScrapeService;

@AllArgsConstructor
@Component
class PriceScraperCommandLineRunner implements CommandLineRunner {

    private PriceScrapeService priceScrapeService;

    @Override
    public void run(String... args) throws Exception {
        priceScrapeService.scrapePrices();
    }

}
