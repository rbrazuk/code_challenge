package com.example.android.codechallenge;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {

    private List<Person> mPeopleList;
    private Context mContext;

    public PeopleAdapter(Context context, List<Person> peopleList) {
        mContext = context;
        mPeopleList = peopleList;
    }

    private Context getContext() {
        return mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_name) TextView tvName;
        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public PeopleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View personView = inflater.inflate(R.layout.list_item_person, parent, false);

        ViewHolder personHolder = new ViewHolder(personView);

        return personHolder;

    }

    @Override
    public void onBindViewHolder(PeopleAdapter.ViewHolder holder, int position) {
        Person person = mPeopleList.get(position);
        TextView mNameTextView = holder.tvName;

        mNameTextView.setText(person.getFirstName() + " " + person.getLastName());
    }

    @Override
    public int getItemCount() {
        return mPeopleList.size();
    }


}
