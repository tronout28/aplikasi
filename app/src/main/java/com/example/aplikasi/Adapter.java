package com.example.aplikasi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.GnssAntennaInfo;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private Context context;
    private List<EPLTeamModel> contactList;
    private ContactsAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView tvClubname, tvStadiun;
        public ImageView imgimage;
        public CardView cardView;

        public MyViewHolder(@NonNull View view) {
            super(view);
            tvClubname = view.findViewById(R.id.tvclubname);
            tvStadiun = view.findViewById(R.id.tvstadium);
            imgimage = view.findViewById(R.id.imgImage);
            cardView = view.findViewById(R.id.cardView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onContactSelected(contactList.get(getAdapterPosition()));
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                    popupMenu.inflate(R.menu.delete_menu);

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.menu_delete:
                                    int position = getAdapterPosition();
                                    if (position != RecyclerView.NO_POSITION) {
                                        showConfirmationDialog(v.getContext(), position);
                                    }
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popupMenu.show();
                    return false;
                }
            });
        }
    }
    private void showConfirmationDialog(Context context, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Konfirmasi");
        builder.setMessage("Apakah kamu yakin ingin menghapus item ini?");
        builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteItem(position);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    public Adapter(Context context, List<EPLTeamModel> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.contactList = contactList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
        final EPLTeamModel contact = this.contactList.get(position);
        holder.tvClubname.setText(contact.getTeamname());
        holder.tvStadiun.setText(contact.getStadiun());
        Glide.with(holder.itemView.getContext()).load(contact.getStrTeamBadge()).into(holder.imgimage);
    }


    @Override
    public int getItemCount() {
        return this.contactList.size();
    }

    private void deleteItem(int position) {
        contactList.remove(position);
        notifyItemRemoved(position);
    }

    public interface ContactsAdapterListener {
        void onContactSelected(EPLTeamModel myteam);
    }

}
