package com.alejoyarce.simplechat.contactlist.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alejoyarce.simplechat.R;
import com.alejoyarce.simplechat.lib.ImageLoader;
import com.alejoyarce.simplechat.login.domain.User;
import com.alejoyarce.simplechat.utils.AvatarHelper;
import com.alejoyarce.simplechat.utils.Constants;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ContactlListAdapter extends RecyclerView.Adapter<ContactlListAdapter.ViewHolder> {

    private List<User> contactList;
    private ImageLoader imageLoader;
    private OnItemClickListener onItemClickListener;

    public ContactlListAdapter(List<User> contactList, ImageLoader imageLoader, OnItemClickListener onItemClickListener) {
        this.contactList = contactList;
        this.imageLoader = imageLoader;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = contactList.get(position);
        holder.setClickListener(user, onItemClickListener);

        boolean online = user.isOnline();
        String mail = user.getMail();

        holder.txtUser.setText(mail);
        holder.txtUserStatus.setText(online ? Constants.USER_ONLINE : Constants.USER_OFFLINE);
        holder.txtUserStatus.setTextColor(online ? Color.GREEN : Color.RED);

        imageLoader.load(holder.imgAvatar, AvatarHelper.avatarUrl(mail));
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.imgAvatar) CircleImageView imgAvatar;
        @Bind(R.id.txtUser) TextView txtUser;
        @Bind(R.id.txtUserStatus) TextView txtUserStatus;

        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }

        private void setClickListener(final User user, final OnItemClickListener onItemClickListener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(user);
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onItemLongClick(user);
                    return false;
                }
            });
        }
    }
}
