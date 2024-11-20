package com.devedu.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devedu.Model.CourseModel;
import com.devedu.Model.UserModel;
import com.devedu.PlayListActivity;
import com.devedu.R;
import com.devedu.databinding.RvCourseDesginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CourseAdapter extends  RecyclerView.Adapter<CourseAdapter.viewHolder>{

    Context context;
    ArrayList<CourseModel>list;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private  String postedBy;

    public CourseAdapter(Context context, ArrayList<CourseModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.rv_course_desgin,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        CourseModel model = list.get(position);

        Picasso.get().load(model.getThumbnail())
                .placeholder(R.drawable.placeholder)
                .into(holder.binding.courseImage);


        holder.binding.courseTitle.setText(model.getTitle());
        holder.binding.coursePrice.setText(model.getPrice()+"");

        database.getReference().child("admin_details").child(model.getPostedBy()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){

                    UserModel userModel = snapshot.getValue(UserModel.class);

                    Picasso.get().load(userModel.getProfile())
                            .placeholder(R.drawable.placeholder)
                            .into(holder.binding.postedByProfile);

                    postedBy = userModel.getName();
                    holder.binding.name.setText(userModel.getName());

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, PlayListActivity.class);
                intent.putExtra("postId",model.getPostId());
                intent.putExtra("name",postedBy);
                intent.putExtra("introUrl",model.getIntroVideo());
                intent.putExtra("title",model.getTitle());
                intent.putExtra("price",model.getPrice());
                intent.putExtra("rate",model.getRating());
                intent.putExtra("duration",model.getDuration());
                intent.putExtra("desc",model.getDescription());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class  viewHolder extends RecyclerView.ViewHolder {

        RvCourseDesginBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            binding = RvCourseDesginBinding.bind(itemView);
        }
    }
}
