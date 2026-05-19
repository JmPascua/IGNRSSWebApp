/*******************************************************************
 * CSCI 428/528             ASSIGNMENT 6             SPRING 2026   *
 *                                                                 *
 *             App Name: Assignment 6 Web Content App              *
 *                                                                 *
 *           Class Name: Item.java                                 *
 *       Developer Name: John Mark C Pascua                        *
 *             Due Date: 05/01/2026                                *
 *              Purpose: IGN RSS feed items. Storing article       *
 *              titles and links for display in the application.   *
 *              Used for parsing and displaying IGN RSS articles.  *
 ******************************************************************/
package edu.niu.android.ignrsswebapp;

/******************************************************************
 *              Class Name: Item                                  *
 *                                                                *
 *              Purpose: Represents a single RSS feed entry.      *
 *              Stores basic article information including the    *
 *              title and link for display and navigation in the  *
 *              application.                                      *
 ******************************************************************/
public class Item
{
    //stores the title of the article
    private String title;
    //stores the link of the article
    private String link;

    /******************************************************************
     *          Method name: Item (Constructor)                       *
     *                                                                *
     *          Purpose: Initializes an Item object with default      *
     *          empty values for title and link.                      *
     ******************************************************************/
    public Item()
    {
        this.title = "";
        this.link = "";
    }

    /******************************************************************
     *          Method name: getTitle                                 *
     *                                                                *
     *          Purpose: Retrieves the title of the RSS article.      *
     ******************************************************************/
    public String getTitle()
    {
        return title;
    }

    /******************************************************************
     *          Method name: setTitle                                 *
     *                                                                *
     *          Purpose: Updates the title of the RSS article.        *
     ******************************************************************/
    public void setTitle(String title)
    {
        this.title = title;
    }

    /******************************************************************
     *          Method name: getLink                                  *
     *                                                                *
     *          Purpose: Retrieves the link of the RSS article.       *
     ******************************************************************/
    public String getLink()
    {
        return link;
    }

    /******************************************************************
     *          Method name: setLink                                  *
     *                                                                *
     *          Purpose: Updates the link of the RSS article.         *
     ******************************************************************/
    public void setLink(String link)
    {
        this.link = link;
    }
}
