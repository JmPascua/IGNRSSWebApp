/*******************************************************************
 * CSCI 428/528             ASSIGNMENT 6             SPRING 2026   *
 *                                                                 *
 *             App Name: Assignment 6 Web Content App              *
 *                                                                 *
 *           Class Name: ParseTask.java                            *
 *       Developer Name: John Mark C Pascua                        *
 *             Due Date: 05/01/2026                                *
 *              Purpose: Background processing to download and     *
 *              parse the IGN RSS feed using SAX parsing.          *
 *              Returns a list of Item objects to the MainActivity *
 *              for display.                                       *
 ******************************************************************/
package edu.niu.android.ignrsswebapp;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/******************************************************************
 *              Class Name: ParseTask                             *
 *                                                                *
 *              Purpose: Performs background processing to        *
 *              download and parse the IGN RSS feed using SAX     *
 *              parsing. Returns a list of Item objects to the    *
 *              MainActivity for display.                         *
 ******************************************************************/
public class ParseTask extends AsyncTask<String, Void, ArrayList<Item>>
{
    //reference mainactivity for sending parsed results
    private MainActivity activity;

    /******************************************************************
     *          Method name: ParseTask (Constructor)                  *
     *                                                                *
     *          Purpose: Initializes the AsyncTask with a reference   *
     *          to MainActivity so parsed results can be returned.    *
     ******************************************************************/
    public ParseTask(MainActivity fromActivity)
    {
        activity = fromActivity;
    }

    /******************************************************************
     *          Method name: doInBackground                           *
     *                                                                *
     *          Purpose: Runs on a background thread to download      *
     *          and parse the RSS XML feed using SAXParser.           *
     ******************************************************************/
    @Override
    protected ArrayList<Item> doInBackground(String... urls)
    {
        try
        {
            //create SAX parser instance
            SAXParserFactory factory = SAXParserFactory.newInstance();
            //creates SAX parser
            SAXParser saxParser = factory.newSAXParser();
            //creates custom handler
            SAXHandler handler  = new SAXHandler();
            //parses RSS feed from URL
            saxParser.parse(urls[0], handler);
            //return parsed RSS
            return handler.getItems();
        }
        catch (Exception e)
        {
            //log parsing errors
            Log.w("IGNNewsApp", "ParseTask error: " + e.toString());
            //return null if failed
            return null;
        }
    }

    /******************************************************************
     *          Method name: onPostExecute                            *
     *                                                                *
     *          Purpose: Runs on the main UI thread after background  *
     *          processing is complete and sends results to           *
     *          MainActivity for display.                             *
     ******************************************************************/
    @Override
    protected void onPostExecute(ArrayList<Item> returnedItems)
    {
        activity.displayList(returnedItems);
    }
}
