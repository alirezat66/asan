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

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    public List<Course> list;
    public onItemClick myListener;
    public Context context;
    ViewHolder viewHolder;


    public CourseAdapter(List<Course> list, onItemClick myListener, Context context) {
        this.list = list;
        this.myListener = myListener;
        this.context = context;
    }

    public interface onItemClick {
        public void onClick(Course item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_courses, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course data = list.get(position);
        holder.bind(list.get(position), myListener);
        holder.txtTitle.setText(data.getTitle());
        holder.txtDescription.setText(data.getDesc());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_description)
        TextView txtDescription;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final Course item, final onItemClick listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(item);
                }
            });
        }
    }
}
