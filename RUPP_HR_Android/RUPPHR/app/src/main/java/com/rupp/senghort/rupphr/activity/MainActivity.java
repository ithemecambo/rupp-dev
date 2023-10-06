package com.rupp.senghort.rupphr.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.rupp.senghort.rupphr.R;
import com.rupp.senghort.rupphr.adapter.UserAdapter;
import com.rupp.senghort.rupphr.model.User;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * Created by ADMIN on 4/3/2018.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView sectionHeader;
    private SectionedRecyclerViewAdapter sectionAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // setDefaultLanguage("en");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        sectionHeader = (RecyclerView)findViewById(R.id.rvList);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
//        sectionHeader.setLayoutManager(linearLayoutManager);
//        sectionHeader.setHasFixedSize(true);
//        sectionAdapter = new SectionedRecyclerViewAdapter();
//        sectionAdapter.addSection(new UserAdapter("BASIC INFO", getBasicInfo()));
//        sectionAdapter.addSection(new UserAdapter("CONTACT INFO", getContactInfo()));
//        sectionAdapter.addSection(new UserAdapter("WORK", getWork()));
//        sectionAdapter.addSection(new UserAdapter("EDUCATION", getEducation()));
//        sectionAdapter.addSection(new UserAdapter("PROFESSIONAL SKILLS", getProfessionalSkills()));
//        sectionAdapter.addSection(new UserAdapter("PLACES", getPlaces()));
//        sectionHeader.setAdapter(sectionAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<User> getBasicInfo() {
        List<User> data = new ArrayList<>();
        data.add(new User("Birthday", "May 25 1992"));
        data.add(new User("Gender", "Male"));
        data.add(new User("Interested", "New research"));
        data.add(new User("Languages", "English, France, China"));
        data.add(new User("Religious", "Buddhist"));
        return data;
    }

    private List<User> getContactInfo() {
        List<User> data = new ArrayList<>();
        data.add(new User("Mobile", "089856650"));
        data.add(new User("Email", "johnnine@gmail.com"));
        data.add(new User("Address", "St 158, No 9, Sangkat Doun Penh, Khan Doun Penh"));
        data.add(new User("Facebook", "johnnine.kotas"));
        data.add(new User("Skype", "johnnine.kotas"));
        data.add(new User("Line", "johnnine.kotas"));
        data.add(new User("Telegram", "johnnine.kotas"));
        data.add(new User("Twitter", "johnnine.kotas"));
        data.add(new User("LinkedIn", "johnnine.kotas"));
        return data;
    }

    private List<User> getWork() {
        List<User> data = new ArrayList<>();
        data.add(new User("", "Royal University of Phnom Penh"));
        data.add(new User("", "EZE APP Co., Ltd"));
        data.add(new User("", "Softbloom Co., Ltd"));
        data.add(new User("", "Assistant Lecture"));
        return data;
    }

    private List<User> getEducation() {
        List<User> data = new ArrayList<>();
        data.add(new User("", "Royal University of Phnom Penh"));
        data.add(new User("", "PUC"));
        data.add(new User("", "University of Minho"));
        return data;
    }

    private List<User> getProfessionalSkills() {
        List<User> data = new ArrayList<>();
        data.add(new User("", "IOS Developer"));
        data.add(new User("", "Android Developer"));
        data.add(new User("", "API website service"));
        return data;
    }

    private List<User> getPlaces() {
        List<User> data = new ArrayList<>();
        data.add(new User("", "Phnom Penh"));
        data.add(new User("", "Kandal, Phnom Penh, Cambodia"));
        return data;
    }


}
