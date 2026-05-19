/*******************************************************************
 * CSCI 428/528             ASSIGNMENT 6             SPRING 2026   *
 *                                                                 *
 *             App Name: Assignment 6 Web Content App              *
 *                                                                 *
 *           Class Name: MainActivity.java                         *
 *       Developer Name: John Mark C Pascua                        *
 *             Due Date: 05/01/2026                                *
 *              Purpose: Write an app similar to Web Content App   *
 *              Choosing two different RSS feeds, one that defaults*
 *              as many titles as possible, that also allows a     *
 *              filter for a user to enter the amount of days a    *
 *              an article was posted. While another app uses      *
 *              another RSS feed that adds a front activity        *
 *              that allows the user to specify a search term and  *
 *              browse the various articles.                       *
 ******************************************************************/
package edu.niu.android.ignrsswebapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/******************************************************************
 *              Class Name: MainActivity                          *
 *                                                                *
 *              Purpose: Handles loading IGN RSS feed data,       *
 *              displaying article titles in a ListView, and      *
 *              optionally filtering results based on a search    *
 *              term passed from a previous activity. Also allows *
 *              users to open selected articles in a browser.     *
 ******************************************************************/
public class MainActivity extends AppCompatActivity
{
    //RSS feed source URL
    private static final String IGN_RSS_URL =
            "https://feeds.feedburner.com/ign/all";

    //UI components for RSS data
    private ListView listView;
    private TextView headingLabel;
    private TextView resultCount;
    private ImageButton backButton;
    //store search term
    private String searchTerm = "";

    /******************************************************************
     *          Method name: onCreate                                 *
     *                                                                *
     *          Purpose: Initializes UI components, retrieves the     *
     *          search term from intent (if any), sets the heading    *
     *          label, and starts the RSS parsing task.               *
     ******************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //connects layout file to activity
        setContentView(R.layout.activity_main);

        //links UI elements to variables
        listView     = findViewById(R.id.list_view);
        headingLabel = findViewById(R.id.heading_label);
        resultCount  = findViewById(R.id.result_count);
        backButton   = findViewById(R.id.back_button);

        //closes current activity when back button is clicked
        backButton.setOnClickListener(v -> finish());

        //retrieve searched term
        String extra = getIntent().getStringExtra(SearchActivity.EXTRA_SEARCH_TERM);
        //assign empty string if null
        searchTerm = (extra != null) ? extra.trim() : "";

        //set heading label based on search term
        if (searchTerm.isEmpty())
        {
            headingLabel.setText("All IGN Headlines");
        }
        else
        {
            headingLabel.setText("Results for: \"" + searchTerm + "\"");
        }

        //start background RSS parsing
        ParseTask task = new ParseTask(this);
        task.execute(IGN_RSS_URL);
    }

    /******************************************************************
     *          Method name: displayList                              *
     *                                                                *
     *          Purpose: Receives parsed RSS items and determines     *
     *          whether to display all results or filter them based   *
     *          on a search term.                                     *
     ******************************************************************/
    public void displayList(ArrayList<Item> items)
    {
        //check if RSS is valid and display error message
        if (items == null || items.isEmpty())
        {
            Toast.makeText(this, "Sorry - no IGN articles found.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        //show all items if no search is specified
        if (searchTerm.isEmpty())
        {
            renderList(items);
        }
        else
        {
            //filters items based off the search
            renderList(filterByTerm(items, searchTerm));
        }
    }

    /******************************************************************
     *          Method name: filterByTerm                             *
     *                                                                *
     *          Purpose: Filters RSS items by checking whether the    *
     *          article title contains the user-provided search term. *
     ******************************************************************/
    private ArrayList<Item> filterByTerm(ArrayList<Item> source, String term)
    {
        //convert search to lowercase for case sensitivity
        String lower = term.toLowerCase();
        //store filtered results
        ArrayList<Item> filtered = new ArrayList<>();
        //loops through RSS items
        for (Item item : source)
        {
            //check if title contains term
            if (item.getTitle().toLowerCase().contains(lower))
            {
                filtered.add(item);
            }
        }

        //toast message if there is no matching results
        if (filtered.isEmpty())
        {
            Toast.makeText(this,
                    "No articles found matching \"" + term + "\".",
                    Toast.LENGTH_LONG).show();
        }

        //return list
        return filtered;
    }

    /******************************************************************
     *          Method name: renderList                               *
     *                                                                *
     *          Purpose: Displays RSS article titles in a ListView    *
     *          and allows users to click an item to open it in a     *
     *          web browser.                                          *
     ******************************************************************/
    private void renderList(ArrayList<Item> items)
    {
        //store article titles for the display
        ArrayList<String> titles = new ArrayList<>();
        //extracts titles from item objects
        for (Item item : items)
        {
            titles.add(item.getTitle());
        }

        //create adapter for ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                titles);

        //attaches adapter to listview
        listView.setAdapter(adapter);
        //update result count
        resultCount.setText(items.size() + " article(s) found");

        //handle item click to open article in the browser
        listView.setOnItemClickListener((parent, view, position, id) ->
        {
            //retrieve selected item
            Item selected = items.get(position);
            //convert link to URI
            Uri uri = Uri.parse(selected.getLink());
            //create the browser intent
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
            //open selected article
            startActivity(browserIntent);
        });
    }
}
