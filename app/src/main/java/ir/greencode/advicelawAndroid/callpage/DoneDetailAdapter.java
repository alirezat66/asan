package ir.greencode.advicelawAndroid.callpage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import ir.greencode.advicelawAndroid.R;
import ir.greencode.advicelawAndroid.objects.DoneDetailItem;
import ir.greencode.advicelawAndroid.objects.Lawyer;

public class DoneDetailAdapter extends RecyclerView.Adapter<DoneDetailAdapter.ViewHolder> {
    public List<DoneDetailItem> list;
    public Context context;
    ViewHolder viewHolder;


    public DoneDetailAdapter(List<DoneDetailItem> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_call_page, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DoneDetailItem data = list.get(position);
        holder.txt_title.setText(data.getMainTitle());
        holder.txt_title_two.setText(data.getSecondTitle());
        holder.txt_desc.setText(data.getDesc());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_title)
        TextView txt_title;
        @BindView(R.id.txt_title_two)
        TextView txt_title_two;
        @BindView(R.id.txt_desc)
        TextView txt_desc;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


    }
}
