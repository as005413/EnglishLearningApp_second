package designSolutions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.englishlearningapp.R;

import java.util.ArrayList;
import java.util.Random;

import entities.Card;

public class CustomAdapter extends ArrayAdapter<Card> {

    private ArrayList<Card> cards;
    Context mContext;

    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
    }

    public CustomAdapter(ArrayList<Card> cards, Context context) {
        super(context, R.layout.row_item, cards);
        this.cards = cards;
        this.mContext = context;

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Card dataModel = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.type);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(
                mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        String title;
        String def;

        if (dataModel.getWord().equals("Fisherman")){
            title = dataModel.getWord();
            def = "a person who catches fish as a job or as a hobby.";
        }
        else {
            title = dataModel.getTranslation();
            Random random = new Random();
            Integer version = random.nextInt() % 3 + 2;
            String versionToString = version.toString();
            def = "Frequency of use: ".concat(versionToString);
        }

        viewHolder.txtName.setText(title);
        viewHolder.txtType.setText(def);
        return convertView;
    }
}
