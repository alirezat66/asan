package ir.greencode.advicelawAndroid.main;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.greencode.advicelawAndroid.R;
import ir.greencode.advicelawAndroid.objects.Advice;
import ir.greencode.advicelawAndroid.objects.MenuItem;
import ir.greencode.advicelawAndroid.utils.PersianCalculater;
import saman.zamani.persiandate.PersianDate;

public class AdviceAdapter extends RecyclerView.Adapter<AdviceAdapter.ViewHolder> {
    public List<Advice> list;
    public onItemClick myListener;
    public Context context;
    ViewHolder viewHolder;

    public AdviceAdapter(List<Advice> list, onItemClick myListener, Context context) {
        this.list = list;
        this.myListener = myListener;
        this.context = context;
    }

    public interface onItemClick {
        public void onClick(Advice item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_request, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Advice data = list.get(position);
        holder.bind(list.get(position), myListener);
        holder.txtTitle.setText(data.getTitle());
        holder.txtSubTitle.setText(data.getSubTitle());
        if(data.getState()==0) {
            holder.img_state.setImageDrawable(context.getResources().getDrawable(R.drawable.status_one));
            holder.countLayout.setVisibility(View.GONE);
            holder.imgIcon.setVisibility(View.VISIBLE);
            holder.txtCount.setTextSize(20);

        }else if(data.getState()==1) {
            holder.img_state.setImageDrawable(context.getResources().getDrawable(R.drawable.status_two));
            holder.countLayout.setVisibility(View.VISIBLE);
            holder.imgIcon.setVisibility(View.GONE);
            holder.txtCount.setText(data.getLawersCont()+"");
            holder.txtCount.setTextSize(20);


        }else {
            holder.img_state.setImageDrawable(context.getResources().getDrawable(R.drawable.status_three));
            holder.countLayout.setVisibility(View.VISIBLE);
            PersianDate date = new PersianDate(data.getCallTimestammp()*1000);
            holder.txtCount.setText(date.dayName()+" " + date.getShDay()+" "+date.monthName());
            holder.txtCount.setTextSize(12);
            holder.txt_sub_count.setText("ساعت "+PersianCalculater.getHourseAndMin(date.getTime()));
            holder.imgIcon.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends  RecyclerView.ViewHolder  {
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_sub_title)
        TextView txtSubTitle;
        @BindView(R.id.txtCount)
        TextView txtCount;
        @BindView(R.id.countLayout)
        LinearLayout countLayout;
        @BindView(R.id.imgIcon)
        SpinKitView imgIcon;
        @BindView(R.id.sub_count)
        TextView txt_sub_count;
        @BindView(R.id.img_state)
        ImageView img_state;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final Advice item, final onItemClick listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(item);
                }
            });
        }
    }
}
