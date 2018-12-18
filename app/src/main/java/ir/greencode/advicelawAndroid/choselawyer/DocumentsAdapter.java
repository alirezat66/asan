package ir.greencode.advicelawAndroid.choselawyer;

import android.content.Context;
import android.media.Image;
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
import de.hdodenhof.circleimageview.CircleImageView;
import ir.greencode.advicelawAndroid.R;
import ir.greencode.advicelawAndroid.objects.DocumentImg;

public class DocumentsAdapter extends RecyclerView.Adapter<DocumentsAdapter.ViewHolder> {
    public List<DocumentImg> list;
    public onItemClick myListener;
    public Context context;
    ViewHolder viewHolder;
    boolean showDelete = false;


    public DocumentsAdapter(List<DocumentImg> list, onItemClick myListener, Context context,boolean showDelete)
    {
        this.list = list;
        this.myListener = myListener;
        this.context = context;
        this.showDelete = showDelete;
    }


    public interface onItemClick
    {
        public void onClick(DocumentImg item);
        public void  onDelete(DocumentImg item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_document, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DocumentImg data = list.get(position);
        holder.bind(list.get(position), myListener);
        holder.txtSize.setText(data.getDocSize());
        holder.txtUnitSize.setText(" کیلوبایت");
        holder.txtName.setText("فایل "+ (position+1));
        if(showDelete) {
            holder.imgDelete.setVisibility(View.VISIBLE);
        }else {
            holder.imgDelete.setVisibility(View.GONE);

        }
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListener.onDelete(data);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_size)
        TextView txtSize;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_unit_size)
        TextView txtUnitSize;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.img_delete)
        ImageView imgDelete;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        public void bind(final DocumentImg item, final onItemClick listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(item);
                }
            });
        }
    }
    public void addDoc(DocumentImg doc){
        list.add(doc);
        notifyDataSetChanged();
    }
    public void deleteDoc(DocumentImg doc){
        list.remove(doc);
        notifyDataSetChanged();
    }
}
