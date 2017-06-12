package gupta.p.todo.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import gupta.p.todo.Pojo.ToDoPojo;
import gupta.p.todo.R;

import static android.content.ContentValues.TAG;

/**
 * Created by lenovo1 on 6/12/2017.
 */

public class MyArrayAdapter extends ArrayAdapter {

    private Context context;
    private int layoutRes;
    private ArrayList<ToDoPojo> arrayList;

    private LayoutInflater inflater;

    public MyArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<ToDoPojo> arrayList) {
        super(context, resource, arrayList);
        this.context=context;
        layoutRes=resource;
        this.arrayList=arrayList;

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view=inflater.inflate(layoutRes,null);

        TextView date= (TextView) view.findViewById(R.id.textViewDate);
        TextView time= (TextView) view.findViewById(R.id.textViewTime);
        TextView name= (TextView) view.findViewById(R.id.textViewName);
        TextView alpha= (TextView) view.findViewById(R.id.textViewAlpha);
        CircleImageView imageView= (CircleImageView) view.findViewById(R.id.profile_image);

        ToDoPojo toDoPojo=arrayList.get(position);
        imageView.setImageResource(toDoPojo.getImageRes());
        name.setText(toDoPojo.getName());
        time.setText(toDoPojo.getTime());
        date.setText(toDoPojo.getDate());
        alpha.setText(toDoPojo.getAlpha());

        return view;
    }
}
