package com.semanientreprise.gridrealm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class ContestantAdapter extends BaseAdapter {

    private List<Contestant> contestants = Collections.emptyList();

    public ContestantAdapter(List<Contestant> contestants) {
        this.contestants = contestants;
    }

    public void setData(List<Contestant> contestant) {
        if (contestant == null) {
            contestant = Collections.emptyList();
        }
        this.contestants = contestant;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return contestants.size();
    }

    @Override
    public Contestant getItem(int position) {
        return contestants.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder {
        private TextView name;
        private TextView vote;
        private ImageView image;

        public ViewHolder(View view) {
            name = view.findViewById(R.id.name);
            vote = view.findViewById(R.id.votes);
            image = view.findViewById(R.id.image);
        }

        public void bind(Contestant contestant) {
            name.setText(contestant.getName());
            vote.setText(String.valueOf(contestant.getVotes()));
            Picasso.get().load(contestant.getImage()).into(image);
        }
    }

    @Override
    public View getView(int position, View currentView, ViewGroup parent) {
        // GridView requires ViewHolder pattern to ensure optimal performance
        ViewHolder viewHolder;

        if (currentView == null) {
            currentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contestant_view, parent, false);
            viewHolder = new ViewHolder(currentView);
            currentView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)currentView.getTag();
        }

        Contestant contestant = contestants.get(position);

        viewHolder.bind(contestant);

        return currentView;
    }
}