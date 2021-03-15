package com.example.recyclevmlive;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> listUser;

    public UserAdapter(List<User> listUser) {
        this.listUser = listUser;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = listUser.get(position);
        if(user == null){
            return;
        }

        holder.ivUserAvatar.setImageResource(user.getAvatar());
        holder.tvUserName.setText(user.getName());
        holder.tvUserAddress.setText(user.getAddress());

    }

    @Override
    public int getItemCount() {
        if(listUser != null){
            return listUser.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivUserAvatar;
        private TextView tvUserName;
        private TextView tvUserAddress;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUserAvatar = itemView.findViewById(R.id.ivUserAvatar);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserAddress = itemView.findViewById(R.id.tvUserAddress);
        }


    }
}
