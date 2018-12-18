package ir.greencode.advicelawAndroid.cats;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.greencode.advicelawAndroid.R;
import ir.greencode.advicelawAndroid.objects.Category;
import ir.greencode.advicelawAndroid.utils.Utility;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder> {
    public List<Category> list;
    public onItemClick myListener;
    public Context context;
    ViewHolder viewHolder;


    public CatAdapter(List<Category> list, onItemClick myListener, Context context) {
        this.list = list;
        this.myListener = myListener;
        this.context = context;
    }

    public interface onItemClick {
        public void onClick(Category item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cats, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category data = list.get(position);
        holder.bind(list.get(position), myListener);
        holder.txtCatName.setText(data.getCatName());
        if(data.getCatImg().equals("")){
            holder.imgLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.sample_icon_1));
        }else {
            holder.imgLogo.setImageBitmap(Utility.getBitmapFromBase(data.getCatImg()));

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgLogo)
        ImageView imgLogo;
        @BindView(R.id.txt_cat_name)
        TextView txtCatName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final Category item, final onItemClick listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(item);
                }
            });
        }
    }
}
