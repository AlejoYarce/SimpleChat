package com.alejoyarce.simplechat.contactlist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.alejoyarce.simplechat.R;
import com.alejoyarce.simplechat.contactlist.adapter.ContactlListAdapter;
import com.alejoyarce.simplechat.contactlist.adapter.OnItemClickListener;
import com.alejoyarce.simplechat.contactlist.mvp.ContactListPresenter;
import com.alejoyarce.simplechat.contactlist.mvp.ContactListView;
import com.alejoyarce.simplechat.lib.ImageLoader;
import com.alejoyarce.simplechat.lib.impl.GlideImageLoader;
import com.alejoyarce.simplechat.login.domain.User;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactListActivity extends AppCompatActivity implements ContactListView, OnItemClickListener {

    @Bind(R.id.toolBar) Toolbar toolBar;
    @Bind(R.id.recyclerViewContacts) RecyclerView recyclerViewContacts;

    private ContactListPresenter contactListPresenter;
    private ContactlListAdapter contactlListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        ButterKnife.bind(this);

        // Setup Adapter
        ImageLoader imageLoader = new GlideImageLoader(this.getApplicationContext());
        contactlListAdapter = new ContactlListAdapter(new ArrayList<User>(), imageLoader, this);

        // Setup RecyclerView
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewContacts.setAdapter(contactlListAdapter);

        contactListPresenter.onCreate();

        // Setup Toolbar
        toolBar.setTitle(contactListPresenter.getCurrentUserMail());
        setSupportActionBar(toolBar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        contactListPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        contactListPresenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contactListPresenter.onDestroy();
    }

    @OnClick(R.id.floatingActionButton)
    public void addContact() {

    }

    @Override
    public void onContactAdded(User user) {

    }

    @Override
    public void onContactChanged(User user) {

    }

    @Override
    public void onContactRemoved(User user) {

    }

    @Override
    public void onItemClick(User user) {

    }

    @Override
    public void onItemLongClick(User user) {

    }
}
