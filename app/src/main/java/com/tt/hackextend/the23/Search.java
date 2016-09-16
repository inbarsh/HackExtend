package com.tt.hackextend.the23;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Search extends AppCompatActivity {

    Button btnSearch;
    ListView SearchResultsListView;
    String desiredSkill;
    EditText searchEditText;
    private List<User> arrUser;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchResultsListView = (ListView) findViewById(R.id.searchResultsListView);
        btnSearch = (Button) findViewById(R.id.button);
        searchEditText = (EditText) findViewById(R.id.editSearchText);
        firebaseAuth = FirebaseAuth.getInstance();
        arrUser = new ArrayList<User>();
        initUsers();

    }


    public void searchUsers(View view) {

        // Get the desired skill from the Edit Box:
        desiredSkill = searchEditText.getText().toString();

        // Select only users with desired base skill:
        final List<User> relevantUsers = new ArrayList<User>();
        for (User curUser : arrUser) {
            //System.out.println(arrUser.get(i).name);
            //i = i+1;
            if (desiredSkill.toLowerCase().equals(curUser.base_skill.toLowerCase())) {
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
                Intent intent = new Intent(getApplicationContext(), ViewOtherProfile.class);
                intent.putExtra("user", relevantUsers.get(position));
                startActivity(intent);
            }
        });

        // If no relevant results found, push Toast:
        if (relevantUsers.isEmpty()) {
            Toast.makeText(getBaseContext(), "Sorry! No relevant results found!", Toast.LENGTH_LONG).show();
        }

    }

    private void initUsers() {
        UserClient client = new UserClient(this);
        client.getUsers();
    }

    public void setUsers(List<User> users) {
        this.arrUser = users;
    }

    public void clickEditSearchSkill(View view) {
        searchEditText.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.logout_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this,Main.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}