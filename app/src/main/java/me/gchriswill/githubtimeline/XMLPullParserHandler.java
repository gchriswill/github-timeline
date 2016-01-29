package me.gchriswill.githubtimeline;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLPullParserHandler {

    List<News> entries;
    private News entry;
    private String text;

    public XMLPullParserHandler() {

        entries = new ArrayList<>();

    }

    public List<News> parse(InputStream is) {

        XmlPullParserFactory factory;
        XmlPullParser parser;

        try {

            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();

            parser.setInput(is, null);

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                String tagName = parser.getName();

                switch (eventType) {

                    case XmlPullParser.START_TAG:

                        if (tagName.equalsIgnoreCase("entry") ) {

                            entry = new News();

                        }else if(tagName.equalsIgnoreCase("thumbnail") ) {

                            entry.imgUrl = parser.getAttributeValue(null, "url");

                        }

                        break;

                    case XmlPullParser.TEXT:

                        text = parser.getText();

                        break;

                    case XmlPullParser.END_TAG:

                        if(entry != null) {

                            if (tagName.equalsIgnoreCase("id") ) {

                                entry.id = text;

                            } else if (tagName.equalsIgnoreCase("published") ) {

                                entry.published = text;

                            } else if (tagName.equalsIgnoreCase("updated") ) {

                                entry.updated = text;

                            } else if (tagName.equalsIgnoreCase("title") ) {

                                entry.title = text;

                            } else if (tagName.equalsIgnoreCase("name") ) {

                                entry.name = text;

                            } else if (tagName.equalsIgnoreCase("email") ) {

                                entry.email = text;

                            } else if (tagName.equalsIgnoreCase("uri") ) {

                                entry.uri = text;

                            } else if(tagName.equalsIgnoreCase("link") ){

                                entry.link = parser.getAttributeValue(null, "href");

                            } else if (tagName.equalsIgnoreCase("entry") ) {

                                entries.add(entry);
                                entry = null;

                            }

                        }

                        break;

                    default:

                        break;

                }

                eventType = parser.next();

            }

        } catch (XmlPullParserException | IOException e) {

            e.printStackTrace();

        }

        return entries;

    }

}
