package com.yahoo.sida.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class TodoActivity extends Activity {
	private ArrayList<String> todoItems;
	private ArrayAdapter<String> todoAdapter;
	private ListView lvItems;
	private EditText etNewItem;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        lvItems = (ListView) findViewById(R.id.lvTodoItems);
    	etNewItem = (EditText) findViewById(R.id.etNewitem);
        //populateItems();
        read();
    	todoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
        lvItems.setAdapter(todoAdapter);
        setupListViewListener();
    }

    private void setupListViewListener() {
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				todoItems.remove(arg2);
				todoAdapter.notifyDataSetChanged();
				save();
				return false;
			}		
		});		
	}
    
    private void read() {
    	File fileDir = getFilesDir();
    	File todoFile = new File(fileDir, "todo.txt");
    	try {
    		todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
    	} catch (IOException e) {
    		todoItems = new ArrayList<String>();
    		e.printStackTrace();
    	}
    }
    
    private void save() {
    	File fileDir = getFilesDir();
    	File todoFile = new File(fileDir, "todo.txt");
    	try {
    		FileUtils.writeLines(todoFile, todoItems);
    	} catch (IOException e) {
    		todoItems = new ArrayList<String>();
    		e.printStackTrace();
    	} 
    }
    
	public void onAddItem(View v) {
    	String itemText = etNewItem.getText().toString();
    	todoAdapter.add(itemText);
    	etNewItem.setText("");
    	save();
    }
}
