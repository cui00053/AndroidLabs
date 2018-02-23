package com.example.chenxiao.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    Button sendButton;
    EditText editText;
    ListView listView;
    ArrayList<String> chat = new ArrayList<>();

    ChatDatabaseHelper chatDatabaseHelper;
    ContentValues contentValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        sendButton = (Button) findViewById(R.id.sendButton);
        editText = (EditText) findViewById(R.id.editText);
        listView = (ListView) findViewById(R.id.listView);

        ChatAdapter messageAdapter = new ChatAdapter(this);
        listView.setAdapter((ListAdapter)messageAdapter);

        // Create a temporary ChatDatavaseHelper object
        chatDatabaseHelper = new ChatDatabaseHelper(this);
        SQLiteDatabase database = chatDatabaseHelper.getWritableDatabase();
        Cursor cursor = database.query(ChatDatabaseHelper.TABLE_NAME,new String[]{chatDatabaseHelper.KEY_ID,ChatDatabaseHelper.KEY_MESSAGE},null,null,null,null,null);
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                String message = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
                Log.i("ChatWindow","SQL MESSAGE: "+message);
                Log.i("ChatWindow","Cursor's column count = "+cursor.getColumnCount());
                chat.add(message);
                cursor.moveToNext();
            }
        }
        for(int i = 0; i < cursor.getColumnCount(); i++){
            Log.i("ChatWindow","The "+i+" row is "+cursor.getColumnName(i));
        }


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newMessage = editText.getText().toString();
                chat.add(newMessage);
                contentValues = new ContentValues();
                contentValues.put(chatDatabaseHelper.KEY_MESSAGE,newMessage);
                database.insert(chatDatabaseHelper.TABLE_NAME,null,contentValues);

                messageAdapter.notifyDataSetChanged();
                editText.setText("");
            }
        });

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(chatDatabaseHelper != null ){
            chatDatabaseHelper.close();
        }
    }

    private class ChatAdapter extends ArrayAdapter<String>{
        public ChatAdapter(Context ctx){
            super(ctx,0);
        }
        public int getCount(){
            return chat.size();
        }

        public String getItem(int position){
            return chat.get(position);
        }

        public View getView(int position,View convertView,ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null ;
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            TextView message = (TextView)result.findViewById(R.id.messageText);
            message.setText(   getItem(position)  ); // get the string at position
            //position++;
            return result;
        }

        public long getId(int position){
            return position;
        }


    }

}
