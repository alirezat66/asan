package ir.greencode.advicelawAndroid.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ir.greencode.advicelawAndroid.R;
import ir.greencode.advicelawAndroid.objects.MenuItem;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
   public ArrayList<MenuItem> list;
   public onItemClick myListener;
   public Context context;
    ViewHolder viewHolder;

    public MenuAdapter(ArrayList<MenuItem> list, onItemClick myListener, Context context) {
        this.list = list;
        this.myListener = myListener;
        this.context = context;

    }

    public interface onItemClick{
        public void onClick(int position);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        viewHolder = new ViewHolder( LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false));

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position),myListener);
        holder.txtTitle.setText(list.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle;

        public void bind(final MenuItem item, final onItemClick listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(item.getPosition());
                }
            });
        }

        public ViewHolder(View v) {

            super(v);

            txtTitle = v.findViewById(R.id.txt_title);

        }
    }
}
