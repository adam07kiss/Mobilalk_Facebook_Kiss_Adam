package com.example.facebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.ViewHolder> implements Filterable {
    private ArrayList<Profiles> mProfileData;
    private ArrayList<Profiles> mProfileDataAll;
    private Context mContext;
    private int lastPosition = -1;

    HomePageAdapter(Context context, ArrayList<Profiles> profileData) {
        this.mProfileData = profileData;
        this.mProfileDataAll = profileData;
        this.mContext = context;
    }

    @Override
    public HomePageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.posts, parent, false));
    }

    @Override
    public void onBindViewHolder(HomePageAdapter.ViewHolder holder, int position) {
        Profiles currentItem = mProfileData.get(position);

        holder.bindTo(currentItem);

        if (holder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mProfileData.size();
    }


    /**
     * RecycleView filter
     **/
    @Override
    public Filter getFilter() {
        return profilesFilter;
    }

    private Filter profilesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Profiles> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if (charSequence == null || charSequence.length() == 0) {
                results.count = mProfileDataAll.size();
                results.values = mProfileDataAll;
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Profiles item : mProfileDataAll) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mProfileData = (ArrayList) filterResults.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mPersonName;
        private ImageView mProfilePic;
        private TextView mPostText;

        ViewHolder(View itemView) {
            super(itemView);

            mPersonName = itemView.findViewById(R.id.personName);
            mProfilePic = itemView.findViewById(R.id.itemImage);
            mPostText = itemView.findViewById(R.id.post_text);
        }

        void bindTo(Profiles currentItem) {
            mPersonName.setText(currentItem.getName());
            mPostText.setText(currentItem.getPosts());

            Glide.with(mContext).load(currentItem.getImageResource()).into(mProfilePic);

            itemView.findViewById(R.id.add_friend).setOnClickListener(view -> ((HomePageActivity) mContext).updateAlertIcon(currentItem));
            itemView.findViewById(R.id.delete).setOnClickListener(view -> ((HomePageActivity) mContext).deleteItem(currentItem));
        }
    }

}
