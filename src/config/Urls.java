package config;


import java.util.ArrayList;

import util.UrlLink;

public class Urls {

    public static final ArrayList<UrlLink> urls = new ArrayList<UrlLink>(){{
        add(new UrlLink(Views::inicio, "/home"));
    }};


}

