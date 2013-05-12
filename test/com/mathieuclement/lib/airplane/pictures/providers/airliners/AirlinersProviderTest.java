package com.mathieuclement.lib.airplane.pictures.providers.airliners;

import com.mathieuclement.lib.airplane.pictures.common.AirplanePicture;
import org.junit.Test;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author Mathieu Clément
 * @since 09.05.2013
 */
public class AirlinersProviderTest {
    @Test
    public void testGetPictures() throws Exception {
        List<AirplanePicture> pictures = (List<AirplanePicture>) new AirlinersProvider().getPictures("HB-CQR");
        assertTrue(pictures.size() >= 3);

        AirplanePicture expectedPicture1 = new AirplanePicture("Jerome Zbinden", new Date(1209160800000L),
                "Ecuvillens (LSGE), Switzerland",
                new URL("http://cdn-www.airliners.net/aviation-photos/small/8/7/6/1445678.jpg"),
                new URL("http://cdn-www.airliners.net/aviation-photos/middle/8/7/6/1445678.jpg"),
                new URL("http://cdn-www.airliners.net/aviation-photos/photos/8/7/6/1445678.jpg"),
                null);
        assertEquals(expectedPicture1, pictures.get(0));
    }

    @Test
    public void testStrToDate() throws Exception {
        Date expectedDate = new Date(1283896800000L);
        Date actualDate = AirlinersProvider.strToDate("September 8, 2010");
        assertEquals(expectedDate.getTime(), actualDate.getTime());
    }
}
