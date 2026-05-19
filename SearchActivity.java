/*******************************************************************
 * CSCI 428/528             ASSIGNMENT 6             SPRING 2026   *
 *                                                                 *
 *             App Name: Assignment 6 Web Content App              *
 *                                                                 *
 *           Class Name: SearchActivity.java                       *
 *       Developer Name: John Mark C Pascua                        *
 *             Due Date: 05/01/2026                                *
 *              Purpose: User interface for the user to search     *
 *              their desired topic. Or to view all IGN RSS        *
 *              articles. By passing the selected search term to   *
 *              MainActivity for filtering and display.            *
 ******************************************************************/
package edu.niu.android.ignrsswebapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

/******************************************************************
 *              Class Name: SearchActivity                        *
 *                                                                *
 *              Purpose: Provides a user interface for entering   *
 *              a search term or choosing to view all IGN RSS     *
 *              articles. Passes the selected search term to      *
 *              MainActivity for filtering and display.           *
 ******************************************************************/
public class SearchActivity extends AppCompatActivity
{
    //key to pass search term between activities
    public static final String EXTRA_SEARCH_TERM = "search_term";
    //input field for search
    private EditText searchInput;
    //button to perform search and view all
    private Button searchButton;
    //button to view all
    private Button viewAllButton;

    /******************************************************************
     *          Method name: onCreate                                 *
     *                                                                *
     *          Purpose: Initializes UI components and sets up        *
     *          button click actions for searching or viewing all     *
     *          IGN RSS articles.                                     *
     ******************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //sets layout for search screen
        setContentView(R.layout.activity_search);

        //links UI elements to variables
        searchInput = findViewById(R.id.search_input);
        searchButton = findViewById(R.id.search_button);
        viewAllButton = findViewById(R.id.view_all_button);

        //handles search button click
        searchButton.setOnClickListener(v ->
        {
            //retrieve and trim input
            String term = searchInput.getText().toString().trim();
            //launch main activity with the searched input
            startMainActivity(term);
        });

        //handle view all button click
        viewAllButton.setOnClickListener(v ->
        {
            //launch activity without the search term
            startMainActivity("");
        });
    }

    /******************************************************************
     *          Method name: startMainActivity                        *
     *                                                                *
     *          Purpose: Creates an Intent to launch MainActivity     *
     *          and passes the selected search term as extra data.    *
     ******************************************************************/
    private void startMainActivity(String searchTerm)
    {
        //create intent
        Intent intent = new Intent(this, MainActivity.class);
        //attach term as extra data
        intent.putExtra(EXTRA_SEARCH_TERM, searchTerm);
        //start main activity
        startActivity(intent);
    }
}
