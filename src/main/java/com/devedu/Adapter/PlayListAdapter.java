package com.devedu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devedu.Model.PlayListModel;
import com.devedu.R;
import com.devedu.databinding.RvPlaylistDesignBinding;

import java.util.ArrayList;


public class PlayListAdapter extends  RecyclerView.Adapter<PlayListAdapter.viewHolder>{

    Context context;
    ArrayList<PlayListModel>list;
    videoListener listener;

    public PlayListAdapter(Context context, ArrayList<PlayListModel> list, videoListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.rv_playlist_design,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        PlayListModel model = list.get(position);

        holder.binding.title.setText(model.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onClick(position, list.get(position).getKey(), model.getVideoUrl(), list.size());
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class  viewHolder extends RecyclerView.ViewHolder {

        RvPlaylistDesignBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            binding = RvPlaylistDesignBinding.bind(itemView);
        }
    }

    public interface videoListener{

        public void onClick(int position,String key,String videoUrl,int size);
    }

}
