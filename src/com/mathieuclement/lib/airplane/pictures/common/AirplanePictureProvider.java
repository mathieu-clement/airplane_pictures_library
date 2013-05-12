package com.mathieuclement.lib.airplane.pictures.common;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;

/**
 * Interface for airplane pictures providers.
 *
 * @author Mathieu Clément
 * @since 09.05.2013
 */
public interface AirplanePictureProvider {
    String getDomainName();

    Collection<AirplanePicture> getPictures(String registration) throws IOException, ParseException;
}
