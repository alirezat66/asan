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
import ir.greencode.advicelawAndroid.objects.SubCat;

public class SubCatAdapter extends RecyclerView.Adapter<SubCatAdapter.ViewHolder> {
    public List<SubCat> list;
    public onItemClick myListener;
    public Context context;
    ViewHolder viewHolder;



    public SubCatAdapter(List<SubCat> list, onItemClick myListener, Context context) {
        this.list = list;
        this.myListener = myListener;
        this.context = context;
    }

    public interface onItemClick {
        public void onClick(SubCat item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sub_cat, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubCat data = list.get(position);
        holder.bind(list.get(position), myListener);
        holder.txtSubName.setText(data.getSubCatName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_sub_name)
        TextView txtSubName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final SubCat item, final onItemClick listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(item);
                }
            });
        }
    }
}
