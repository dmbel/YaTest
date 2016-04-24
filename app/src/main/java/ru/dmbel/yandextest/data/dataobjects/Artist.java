package ru.dmbel.yandextest.data.dataobjects;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dm on 21.04.16.
 */
public class Artist implements Serializable {
    public int id;
    public String name;
    public List<String> genres;
    public int tracks;
    public int albums;
    public String link;
    public String description;
    public CoverSet cover;
}
