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
import ir.greencode.advicelawAndroid.R;
import ir.greencode.advicelawAndroid.objects.Course;
import ir.greencode.advicelawAndroid.objects.SubCat;
import ir.greencode.advicelawAndroid.objects.Subject;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {
    public List<Subject> list;
    public Context context;
    ViewHolder viewHolder;


    public SubjectAdapter(List<Subject> list,  Context context) {
        this.list = list;
        this.context = context;
    }

    public interface onItemClick {
        public void onClick(Course item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_pre_payment, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Subject data = list.get(position);

        holder.txtTitle.setText(data.getSubject());
        holder.txtDescription.setText(data.getDesc());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtSubject)
        TextView txtTitle;
        @BindView(R.id.txt_desc)
        TextView txtDescription;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


    }
}
