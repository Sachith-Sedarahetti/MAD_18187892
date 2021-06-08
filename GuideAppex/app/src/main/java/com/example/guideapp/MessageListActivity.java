package com.example.guideapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;


public class MessageListActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private List<UserMessage> messageList;
    private Button send;
    private EditText msg;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        mMessageRecycler = (RecyclerView) findViewById(R.id.recycler_gchat);
        send = findViewById(R.id.button_gchat_send);
        msg = findViewById(R.id.edit_gchat_message);

        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        messageList = new ArrayList<>();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseMessage sender = new BaseMessage("Wanni");
                UserMessage message = new UserMessage(msg.getText().toString(), sender);
                messageList.add(message);
                callBot(message);

                mMessageAdapter = new MessageListAdapter(getApplicationContext(), messageList);
                mMessageRecycler.setAdapter(mMessageAdapter);

                msg.setText("");

                mMessageRecycler.smoothScrollToPosition(mMessageAdapter.getItemCount());
            }
        });
    }

    private void callBot(UserMessage message) {
        BaseMessage sender = new BaseMessage("Bot");
        UserMessage reply_message = new UserMessage("", sender);
        String msg = message.getMessage().toLowerCase().trim().replaceAll(" ","");
        switch(msg)
        {
            case "hi":
                reply_message.setMessage("Hi how are you");
                messageList.add(reply_message);
                break;
            case "ihaveaproblem":
                reply_message.setMessage("Please tell me");
                messageList.add(reply_message);
                break;
            case "ok":
                reply_message.setMessage("ok");
                messageList.add(reply_message);
                break;
            case "whatarethecommonplaceswehavetovisit":
                reply_message.setMessage("Tell me the district");
                messageList.add(reply_message);
                break;
            case "kandy":
                reply_message.setMessage("Temple of the tooth, Udawaththa kale, Hanthana, pinnawala elephant openage, Bahirawakanda viharaya, Gatabe, Peradeniya Bottanical Garden");
                messageList.add(reply_message);
                break;
            case "colombo":
                reply_message.setMessage("Galleface, National museum, Lotus Tower, zoo, Beira Lake, seema malaka, world trade center,Gangaramaya, Mount Lavinia, Leisure world, National Art gallary");
                messageList.add(reply_message);
                break;
            case "Matale":
                reply_message.setMessage("Sembuwatta Lake, Kala Bokka, Riverston, Alu viharaya Temple, Sri Muthumariamman temple, Sigiriya, Dambulla Temple,Nalanda gedige, Babaragiriya alla lake");
                messageList.add(reply_message);
                break;
            case "whatarethetopactivitiesincolombo":
                reply_message.setMessage("Sigiriya lions rock fortress day tour, Village experience from colombo, colombo city half day tour, balapitiya river safari, Nature tour from colombo");
                messageList.add(reply_message);
                break;
            case "whataretheplacesforonedaytripinsrilanka":
                reply_message.setMessage("Sigiriya rock, Kandy, Adam's peak, Yala National Park, Arugambe gall dutch fort, Mirissa, Nuwaraeliya, Polonnaruwa, Dambulla Goalden temple");
                messageList.add(reply_message);
                break;
            case "whatarethemostbeautifulbeachesinsrilanka":
                reply_message.setMessage("Unawatuna beach, Nilaveli, Kalkudah, Arugam Bay, Mirissa, Unawatuna, Uppuveli, Negombo, Tangalle");
                messageList.add(reply_message);
                break;
            case "whatarethebestplacestotravelalone":
                reply_message.setMessage("");
                messageList.add(reply_message);
                break;
            case "whatarethebestholidayplaces":
                reply_message.setMessage("");
                messageList.add(reply_message);
                break;
            default:
                reply_message.setMessage("T");
                messageList.add(reply_message);
        }
    }
}