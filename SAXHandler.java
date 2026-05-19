/*******************************************************************
 * CSCI 428/528             ASSIGNMENT 6             SPRING 2026   *
 *                                                                 *
 *             App Name: Assignment 6 Web Content App              *
 *                                                                 *
 *           Class Name: SAXHandler.java                           *
 *       Developer Name: John Mark C Pascua                        *
 *             Due Date: 05/01/2026                                *
 *              Purpose: Handles the parsing of IGN RSS XML feed   *
 *              data using SAX parsing. Reading XML data and       *
 *              extracting article titles and links for display    *
 *              in the application.                                *
 ******************************************************************/
package edu.niu.android.ignrsswebapp;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;

/******************************************************************
 *              Class Name: SAXHandler                            *
 *                                                                *
 *              Purpose: Parses IGN RSS XML feed data using SAX   *
 *              parsing. Extracts item elements and stores        *
 *              article title and link into Item objects for      *
 *              later display in the application.                 *
 ******************************************************************/
public class SAXHandler extends DefaultHandler
{
    //store parsed items
    private final ArrayList<Item> items;
    //hold RSS item
    private Item currentItem;
    //collect the data between XMLs
    private final StringBuilder currentText;
    //tracks whether the parser is in the item
    private boolean insideItem = false;

    /******************************************************************
     *          Method name: SAXHandler                               *
     *                                                                *
     *          Purpose: Constructor initializes storage structures   *
     *          used during SAX parsing of the RSS feed.              *
     ******************************************************************/
    public SAXHandler()
    {
        //initialize list for storing parsed item
        items = new ArrayList<>();
        //string builder for XML
        currentText = new StringBuilder();
    }

    /******************************************************************
     *          Method name: getItems                                 *
     *                                                                *
     *          Purpose: Returns the list of parsed RSS feed items.   *
     ******************************************************************/
    public ArrayList<Item> getItems()
    {
        return items;
    }

    /******************************************************************
     *          Method name: startElement                             *
     *                                                                *
     *          Purpose: Called when an XML start tag is encountered. *
     *          Initializes a new Item object when an <item> tag is   *
     *          found and prepares text collection.                   *
     ******************************************************************/
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
    {
        //check if parser is in item block
        if (qName.equalsIgnoreCase("item"))
        {
            //create item object for RSS entry
            currentItem = new Item();
            //mark parser inside item block
            insideItem = true;
        }

        //clear the text content
        currentText.setLength(0);
    }

    /******************************************************************
     *          Method name: endElement                               *
     *                                                                *
     *          Purpose: Called when an XML end tag is encountered.   *
     *          Stores collected text into Item fields and adds the   *
     *          completed Item to the list when </item> ends.         *
     ******************************************************************/
    @Override
    public void endElement(String uri, String localName, String qName)
    {
        //ensures parsing only occurs inside an item block
        if (insideItem)
        {
            //check for end of RSS items
            if (qName.equalsIgnoreCase("item"))
            {
                //adds completed item to list
                items.add(currentItem);
                //exit item block
                insideItem = false;
            }
            //store title
            else if (qName.equalsIgnoreCase("title"))
            {
                currentItem.setTitle(currentText.toString().trim());
            }
            //store link
            else if (qName.equalsIgnoreCase("link"))
            {
                currentItem.setLink(currentText.toString().trim());
            }
        }
    }

    /******************************************************************
     *          Method name: characters                               *
     *                                                                *
     *          Purpose: Collects character data between XML tags     *
     *          and appends it to a buffer for later processing.      *
     ******************************************************************/
    @Override
    public void characters(char[] ch, int start, int length)
    {
        currentText.append(ch, start, length);
    }
}
