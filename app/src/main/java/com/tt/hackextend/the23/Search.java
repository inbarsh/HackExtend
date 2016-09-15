package com.tt.hackextend.the23;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Search extends AppCompatActivity {

    Button btnSearch;
    ListView SearchResultsListView;
    String desiredSkill;
    EditText searchEditText;
    private List<User> arrUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchResultsListView = (ListView) findViewById(R.id.searchResultsListView);
        btnSearch = (Button) findViewById(R.id.button);
        searchEditText = (EditText) findViewById(R.id.editSearchText);
    }


    public void searchUsers(View view) {

        // Create Test array of Users:
        arrUser = new ArrayList<User>();
        initUsers();

        // Get the desired skill from the Edit Box:
        desiredSkill = searchEditText.getText().toString();
        System.out.println("00000000000000000000000000000" + desiredSkill);

        // Debug:
        for (int i = 0; i < arrUser.size(); i++) {
            System.out.println("00000000000000000000000000000" + arrUser.get(i).name);
        }

        // Select only users with desired base skill:
        List<User> relevantUsers = new ArrayList<User>();
        for (User curUser : arrUser)
        {
            if (desiredSkill.toLowerCase().equals(curUser.base_skill.toLowerCase())){
                relevantUsers.add(curUser);
            }
        }

        // Order relevant users by reputation:
        Collections.sort(relevantUsers);

        // Update Search List:
        SearchAdapter searchAdapter = new SearchAdapter(relevantUsers, this);
        ListView listView = (ListView) findViewById(R.id.searchResultsListView);
        listView.setAdapter(searchAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = ((TextView)view).getText().toString();
                System.out.println("0000000000000000000" + item);
            }
        });

        // If no relevant results found, push Toast:
        if (relevantUsers.isEmpty()) {
            Toast.makeText(getBaseContext(), "Sorry! No relevant results found!", Toast.LENGTH_LONG).show();
        }

    }

    private void initUsers() {
        UserClient client=new UserClient(this);
        client.getUsers();
    }


    public void setUsers(List<User> users) {
        this.arrUser = users;
        SearchResultsListView.setAdapter(new SearchAdapter(arrUser,this));
    }
    public void clickEditSearchSkill(View view) {
        searchEditText.setText("");
    }

}
