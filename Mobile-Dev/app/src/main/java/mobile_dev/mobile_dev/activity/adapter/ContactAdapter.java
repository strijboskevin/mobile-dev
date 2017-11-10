package mobile_dev.mobile_dev.activity.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import mobile_dev.mobile_dev.R;

public class ContactAdapter extends BaseAdapter {

    private List<String> names;
    private Context context;
    private LayoutInflater inflater;

    public ContactAdapter(Context context, List<String> names) {
        this.context = context;
        this.names = names;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_contacts_element, null);
        }

        final TextView name = (TextView) (view.findViewById(R.id.contacts_element_text));
        name.setText(this.names.get(position));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Voer het e-mail adres in");

                final EditText input = new EditText(context);
                builder.setView(input);
                builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog,int which){
                        sendEmail(input.getText().toString(), names.get(position));
                    }
                });
                builder.setNegativeButton("Annuleren",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog,int which){
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        return view;
    }

    private void sendEmail(String address, String name) {
        if (address.equals("")) {
            Toast.makeText(context, "Adres kan niet leeg zijn!", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{address});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Hello " + name);
            intent.putExtra(Intent.EXTRA_TEXT, "Join our app now!");
            context.startActivity(Intent.createChooser(intent, "Send mail..."));
        }
    }
}
