package com.mathieuclement.lib.airplane.pictures.providers.airliners;

import com.mathieuclement.lib.airplane.pictures.common.AirplanePicture;
import com.mathieuclement.lib.airplane.pictures.common.AirplanePictureProvider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Mathieu Clément
 * @since 09.05.2013
 */
public class AirlinersProvider implements AirplanePictureProvider {
    @Override
    public String getDomainName() {
        return "airliners.net";
    }

    @Override
    public Collection<AirplanePicture> getPictures(String registration) throws IOException, ParseException {
        try {
            Document document = Jsoup.connect("http://www.airliners.net/search/photo.search?regsearch=" + registration).get();
            Elements links = document.select("a.one[href^=/search/photo.search]");
            if (links.size() < 8) {
                return new LinkedList<AirplanePicture>();
            }

            // Tip: http://try.jsoup.org

        /*
        Typical elements:
            0 : Air Fribourg
            1 : Cessna 172R Skyhawk
            2 : Ecuvillens (LSGE)
            3 : Switzerland
            4 : April 26, 2008
            5 : HB-CQR
            6 : 17280506
            7 : Jerome Zbinden

            => For other elements, apply modulo 8.
         */
            AirplanePicture airplanePicture = null;
            List<AirplanePicture> airplanePictures = new LinkedList<AirplanePicture>();

            int index = 0;
            while (index < links.size()) {
                Element element = links.get(index);
                switch (index % 8) {
                    case 0: // operator
                        airplanePicture = new AirplanePicture();
                        break;

                    case 1: // brand / model
                        // ignore
                        break;

                    case 6: // cn ID
                        // ignore
                        break;

                    case 2: // location
                        String location = element.text();
                        airplanePicture.setLocation(location);
                        break;

                    case 3: // country
                        String country = element.text();
                        airplanePicture.setLocation(airplanePicture.getLocation() + ", " + country);
                        break;

                    case 4: // date
                        String dateStr = element.text();
                        try {
                            Date date = strToDate(dateStr);
                            airplanePicture.setDate(date);
                        } catch (Throwable t) {
                            t.printStackTrace();
                        }
                        break;

                    case 5: // registration
                        // Check consistent with search
                        String parsedRegistration = element.text();
                        if (!registration.equals(parsedRegistration)) {
                            throw new ParseException("The stupid airliners.net website gave results for other " +
                                    "registrations, such as " + parsedRegistration + " instead of " + registration + ".",
                                    index);
                        }
                        break;

                    case 7: // author
                        String author = element.text();
                        airplanePicture.setAuthor(author);
                        // Add to collection
                        airplanePictures.add(airplanePicture);
                        break;
                } // end switch
                index++;
            } // end while

            /** Images URL */
            Elements smallImagesElements = document.select("img[src^=http://cdn-www.airliners.net/aviation-photos/small/]");
            if (smallImagesElements.size() != airplanePictures.size()) {
                throw new ParseException("The two parsing methods gave a different number of results.", 0);
            }
            int imgIndex = 0;
            while (imgIndex < smallImagesElements.size()) { // if we found too much images here
                Element element = smallImagesElements.get(imgIndex);
                String imgUrl = element.attr("src");
                airplanePictures.get(imgIndex).setImageUrl(AirplanePicture.PictureSize.SMALL, new URL(imgUrl));
                airplanePictures.get(imgIndex).setImageUrl(AirplanePicture.PictureSize.MEDIUM, new URL(imgUrl.replace("small", "middle")));
                airplanePictures.get(imgIndex).setImageUrl(AirplanePicture.PictureSize.LARGE, new URL(imgUrl.replace("small", "photos")));
                airplanePictures.get(imgIndex).setImageUrl(AirplanePicture.PictureSize.ORIGINAL, null);
                imgIndex++;
            }

            return airplanePictures;

        } catch (Throwable throwable) {
            throw new IOException(throwable);
        }
    } // end getPictures()

    static Date strToDate(String dateStr) throws ParseException {
        return DateFormat.getDateInstance().parse(dateStr);
    }
}
