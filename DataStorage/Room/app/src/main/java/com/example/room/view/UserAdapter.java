package com.example.room.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room.R;
import com.example.room.model.User;
import com.example.room.model.UserAddress;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private List<UserAddress> listUser;
    private IClickItemUser iClickItemUser;

    public interface IClickItemUser{
        void updateUser(UserAddress user);
        void deleteUser(UserAddress user);
    }

    public UserAdapter(IClickItemUser iClickItemUser) {
        this.iClickItemUser = iClickItemUser;
    }

    public void setData(List<UserAddress> list){
        this.listUser = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserAddress user = listUser.get(position);
        if(user == null){
            return;
        }

        holder.tvUserName.setText(user.getUser().getUserName());
        holder.tvUserAge.setText(String.valueOf(user.getUser().getAge()));
        holder.tvAddress.setText(user.getAddress().getAddress());
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemUser.updateUser(user);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemUser.deleteUser(user);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(this.listUser != null){
            return listUser.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView tvUserName;
        private TextView tvUserAge;
        private TextView tvAddress;
        private Button btnUpdate;
        private Button btnDelete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserAge = itemView.findViewById(R.id.tvUserAge);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

    }
}
