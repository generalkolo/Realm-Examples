package com.semanientreprise.encryptedrealm;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class MyAdapter extends RealmRecyclerViewAdapter<Countries, MyAdapter.MyAdapterViewHolder> {

    public MyAdapter(RealmResults<Countries> countires){
        super(countires, true);
    }

    @Override
    public MyAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_view,parent,false);

        return new MyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapterViewHolder holder, int position) {
        final Countries countries = getItem(position);

        holder.Name.setText(countries.getName());
        holder.Age.setText(countries.getAge());
    }

    class MyAdapterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.Name)
        TextView Name;
        @BindView(R.id.Age)
        TextView Age;

        MyAdapterViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
