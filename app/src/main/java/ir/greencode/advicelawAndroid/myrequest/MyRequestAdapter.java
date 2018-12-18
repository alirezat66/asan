package ir.greencode.advicelawAndroid.myrequest;

import android.content.Context;
import android.graphics.Color;
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
import ir.greencode.advicelawAndroid.objects.DocumentImg;
import ir.greencode.advicelawAndroid.retrofit.respObject.MyRequest;
import ir.greencode.advicelawAndroid.utils.Utility;

public class MyRequestAdapter extends RecyclerView.Adapter<MyRequestAdapter.ViewHolder> {
    public List<MyRequest> list;
    public onItemClick myListener;
    public Context context;
    ViewHolder viewHolder;


    public MyRequestAdapter(List<MyRequest> list, onItemClick myListener, Context context)
    {
        this.list = list;
        this.myListener = myListener;
        this.context = context;
    }


    public interface onItemClick
    {
        public void onClick(MyRequest item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_my_request, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MyRequest data = list.get(position);
        holder.bind(list.get(position), myListener);
        holder.txtCatTitle.setText(data.getTitle());
        holder.txtTitle.setText(data.getSubTitle());
        if(data.getState()==0){
            holder.txtStatus.setText("در حال بررسی");
            holder.txtStatus.setTextColor(context.getResources().getColor(R.color.in_progress));
        }else if(data.getState()==1){
            holder.txtStatus.setText("لغو شده");
            holder.txtStatus.setTextColor(context.getResources().getColor(R.color.canceled));
        }else {
            holder.txtStatus.setText("انجام شده");
            holder.txtStatus.setTextColor(context.getResources().getColor(R.color.done));
        }

        holder.txtTime.setText(Utility.getDefrence(data.getRequestTime()*1000));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_cat_title)
        TextView txtCatTitle;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_status)
        TextView txtStatus;
        @BindView(R.id.txt_time)
        TextView txtTime;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        public void bind(final MyRequest item, final onItemClick listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(item);
                }
            });
        }
    }


}
