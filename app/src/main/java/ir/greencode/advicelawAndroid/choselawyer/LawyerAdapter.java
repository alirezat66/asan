package ir.greencode.advicelawAndroid.choselawyer;

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
import ir.greencode.advicelawAndroid.objects.Advice;
import ir.greencode.advicelawAndroid.objects.Lawyer;
import ir.greencode.advicelawAndroid.utils.PreferencesData;
import ir.greencode.advicelawAndroid.utils.Utility;

public class LawyerAdapter extends RecyclerView.Adapter<LawyerAdapter.ViewHolder> {
    public List<Lawyer> list;
    public onItemClick myListener;
    public Context context;
    ViewHolder viewHolder;


    public LawyerAdapter(List<Lawyer> list, onItemClick myListener, Context context) {
        this.list = list;
        this.myListener = myListener;
        this.context = context;
    }

    public interface onItemClick {
        public void onClick(Lawyer item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_lawyer, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lawyer data = list.get(position);
        holder.bind(list.get(position), myListener);
        holder.txtName.setText(data.getfName()+" "+data.getlName());
        if(data.getJobTitle().size()>0) {
            holder.txtEducation.setText(data.getJobTitle().get(0));
        }else {
            holder.txtEducation.setText("مدرک تحصیلی ثبت نشده");
        }
        if(data.getUserImg().equals("")) {
            holder.imgProfile.setImageDrawable(context.getResources().getDrawable(R.drawable.profile_img_place_holder));
        }else {
            holder.imgProfile.setImageBitmap(Utility.getBitmapFromBase(data.getUserImg()));

        }
        holder.txtScore.setText(" "+data.getAverageScore()+" ");
        holder.txtVoteCount.setText(data.getRate()+" رای");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_profile)
        CircleImageView imgProfile;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_education)
        TextView txtEducation;
        @BindView(R.id.txt_vote_count)
        TextView txtVoteCount;
        @BindView(R.id.txt_score)
        TextView txtScore;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final Lawyer item, final onItemClick listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(item);
                }
            });
        }
    }
}
