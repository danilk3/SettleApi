package com.settle.settleapi.parser;

import com.settle.settleapi.domain.Event;
import com.settle.settleapi.domain.search.Apartment;
import com.settle.settleapi.domain.search.Filter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Parser {

    private Filter filter;

    private Event event;

    private String moscowBaseLink = "https://www.cian.ru/cat.php?deal_type=rent&engine_version=2&type=2";
    private String spbBaseLink = "https://spb.cian.ru/cat.php?deal_type=rent&engine_version=2";

    public Parser(Filter filter, Event event) {
        this.filter = filter;
        this.event = event;
    }

    public String getUrl() {
        String url;

        if (filter.getCity().equals("Москва")) {
            url = moscowBaseLink + "&metro%5B0%5D=" + MetroStations.moscowSubway.get(filter.getSubwayStation());
        } else {
            url = spbBaseLink + "&metro%5B0%5D=" + MetroStations.spbSubway.get(filter.getSubwayStation());
        }

        if (filter.getApartmentType().equals("flat")) {
            url += String.format("&offer_type=flat&%s=1&&minfloor=%d", filter.getNumberOfRooms(), filter.getMinFloor());
        } else {
            url += "&object_type%5B0%5D=1&offer_type=suburban";
        }
        return url +
                "&checkin=" + event.getDate() +
                "&checkout=" + event.getDate().plusDays(1);

    }

    public List<Apartment> getApartments() throws IOException {
        List<Apartment> apartments = new ArrayList<>();


        String url = "http://api.scraperapi.com?api_key=0386ae1ac22ff07273b1b3a946ebb9a1&url=" + getUrl();

        Document searchDocument = Jsoup
                .connect(url)
                .get();

        Elements apartmentElements = searchDocument.selectXpath("//article[@data-name='CardComponent']");

        for (Element apartmentElement : apartmentElements) {
            String apartmentUrl = apartmentElement.getElementsByClass("_93444fe79c--link--eoxce").attr("href");
            if (apartmentUrl.contains("cian.sutochno.ru")) {
                continue;
            }
            Document apartmentDocument = Jsoup.connect(apartmentUrl).get();

            String price = apartmentDocument.selectXpath("//span[@itemprop='price']").text();
            String title = apartmentDocument.selectXpath("//h1[@class='a10a3f92e9--title--UEAG3']").text();

            Elements addressElements = apartmentDocument.selectXpath("//address[@class='a10a3f92e9--address--F06X3']").first().selectXpath("//a[@class='a10a3f92e9--link--ulbh5 a10a3f92e9--address-item--ScpSN']");
            String address = addressElements.get(3).text() + addressElements.get(4).text();

            List<String> imagesUrls = apartmentDocument.selectXpath("//img[@class='a10a3f92e9--container--KIwW4 a10a3f92e9--container--contain--cYP76']").stream().map(el -> el.attr("src")).toList();

            String description = apartmentDocument.selectXpath("//p[@itemprop='description']").first().text();

            Elements descriptionElements = apartmentDocument.selectXpath("//div[@data-testid='object-summary-description-info']");

            String floor = "";

            for (Element descriptionElement : descriptionElements) {
                if (descriptionElement.child(1).text().equals("Этаж")) {
                    floor = descriptionElement.child(0).text();
                    break;
                }
            }

            String numberOfBeds = apartmentDocument.selectXpath("//span[@class='a10a3f92e9--value--Y34zN']").first().text();

            String phone = apartmentDocument.selectXpath("//a[@class='a10a3f92e9--phone--_OimW']").first().text();

            Apartment apartment = Apartment
                    .builder()
                    .name(title)
                    .address(address)
                    .imagesUrl(imagesUrls)
                    .price(price)
                    .floor(floor)
                    .description(description)
                    .numberOfBeds(numberOfBeds)
                    .phoneOfOwner(phone)
                    .build();

            apartments.add(apartment);
        }

        return apartments;
    }
}
