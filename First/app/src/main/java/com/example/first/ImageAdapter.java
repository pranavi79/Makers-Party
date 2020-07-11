package com.example.first;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Upload> mUpload;
    private OnItemClickListener itemClickListener;

    public ImageAdapter (Context context, List<Upload> upload){
        mContext=context;
        mUpload=upload;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.image_item,parent,false);
        return  new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
      Upload uploadcurrent =mUpload.get(position);
      holder.t1.setText(uploadcurrent.getname());
      holder.tcontact.setText(uploadcurrent.getcontact());// all these holders fetch information from the Upload class that has stored details of the object on sale
      holder.tuploader.setText(uploadcurrent.getuploader());
      holder.tprice.setText(uploadcurrent.getprice());
        Picasso.with(mContext)
                .load(uploadcurrent.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.i1);//we used picasso library to upload images to the android from the firebase datebase
    }

    @Override
    public int getItemCount() {
        return mUpload.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView t1,tuploader,tcontact,tprice;
        public ImageView i1;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.text_view_name);
            i1=itemView.findViewById(R.id.imageview3);//by all these ID description connecting the image adapter to the image_item xml file
            tuploader=itemView.findViewById(R.id.ownername);
            tcontact=itemView.findViewById(R.id.contact_number);
            tprice=itemView.findViewById(R.id.price_of_object);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);//these are created  to create action of tapping the image and long pressing the image(show menu bar)
        }

        @Override
        public void onClick(View v) {
            if(itemClickListener!=null){
                int position=getAdapterPosition();
            if(position!=RecyclerView.NO_POSITION)
            itemClickListener.OnItemClick(position);
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem buy=menu.add(Menu.NONE, 1, 1, "Buy this Item");//created two menu options for the long pressing of the image
            MenuItem delete=menu.add(Menu.NONE, 2, 2, "Delete");

            buy.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(itemClickListener!=null){
                int position=getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION){
                    switch (item.getItemId()){
                        case 1:
                            itemClickListener.onWhateverClick(position);//on clicking different option in menu bar,tells which option is clicked and return the respective value
                            return true;
                        case 2:
                            itemClickListener.OnDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener{
        void OnItemClick(int position);

        void onWhateverClick(int position);

        void OnDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        itemClickListener=listener;
    }
}
