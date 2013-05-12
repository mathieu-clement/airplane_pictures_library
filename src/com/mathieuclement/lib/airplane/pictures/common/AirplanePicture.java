package com.mathieuclement.lib.airplane.pictures.common;

import java.net.URL;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

/**
 * A picture of an airplane.
 *
 * @author Mathieu Clément
 * @since 09.05.2013
 */
public class AirplanePicture {
    /**
     * The size of a picture.
     */
    public enum PictureSize {
        SMALL, MEDIUM, LARGE, ORIGINAL
    }

    private String author;
    private Date date;
    private String location;
    private Map<PictureSize, URL> picturesBySize = new EnumMap<PictureSize, URL>(PictureSize.class);

    /**
     * Lazy constructor.
     */
    public AirplanePicture() {
    }

    /**
     * Create a new picture.
     *
     * @param author      Author / Photographer
     * @param date        Date the picture was taken or published
     * @param location    Location (airport)
     * @param smallUrl    URL for small size
     * @param mediumUrl   URL for medium size
     * @param largeUrl    URL for large size
     * @param originalUrl URL for original size
     */
    public AirplanePicture(String author, Date date, String location,
                           URL smallUrl, URL mediumUrl, URL largeUrl, URL originalUrl) {
        this.author = author;
        this.date = date;
        this.location = location;
        setImageUrl(PictureSize.SMALL, smallUrl);
        setImageUrl(PictureSize.MEDIUM, mediumUrl);
        setImageUrl(PictureSize.LARGE, largeUrl);
        setImageUrl(PictureSize.ORIGINAL, originalUrl);
    }

    /**
     * Returns the image URL matching the specified size. Can be null.
     *
     * @param size Image size
     * @return the image URL matching the specified size or <code>null</code>.
     */
    public URL getImageUrl(PictureSize size) {
        return picturesBySize.get(size);
    }

    /**
     * Sets URL of image matching the specified size.
     *
     * @param size Size
     * @param url  Url of the image
     */
    public void setImageUrl(PictureSize size, URL url) {
        this.picturesBySize.put(size, url);
    }

    /**
     * Returns the author of the picture.
     *
     * @return the author of the picture.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the picture
     *
     * @param author author of the picture
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Returns the date the picture was taken, or published if not available.
     *
     * @return the date the picture was taken, or published if not available.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date the picture was taken (preferably) or published.
     *
     * @param date the date the picture was taken (preferably) or published.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returns the location (probably an airport) the picture was taken.
     *
     * @return the location (probably an airport) the picture was taken.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location (probably an airport) the picture was taken.
     *
     * @param location the location (probably an airport) the picture was taken.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        // Equal if picturesBySize maps are equal
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AirplanePicture that = (AirplanePicture) o;

        if (!picturesBySize.equals(that.picturesBySize)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = author != null ? author.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + picturesBySize.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AirplanePicture{" +
                "author='" + author + '\'' +
                ", date=" + date +
                ", location='" + location + '\'' +
                ", picturesBySize=" + picturesBySize +
                '}';
    }
}
